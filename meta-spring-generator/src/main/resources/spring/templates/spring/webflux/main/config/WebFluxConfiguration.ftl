<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.reactive.config.EnableWebFlux;

@Configuration
@EnableWebFlux
public class WebFluxConfiguration implements WebFluxConfigurer {

    /**
     * This allows you to set the allowed-origins in an environment variable or
     * application.yaml/properties. Its not unusual to have a variety of origins in
     * a cloud environment; for example, something like: api.acme.com,
     * admin.acme.com, some-application.acme.com.
     * The default, localhost, is usually suitable for development.
     *
     * There are no other dependencies on this property name; rename it if you want.
     */
<#noparse>
    @Value("${application.cors.allowed-origins:http://*.localhost}")
</#noparse>
    private String allowedOriginsPattern;

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                // you need to adjust the originPatterns to your environment
                .allowedOriginPatterns(allowedOriginsPattern)
                ;
    }
}