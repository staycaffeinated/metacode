<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.SerializationFeature;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.AbstractJackson2HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;
import java.util.Optional;

@Configuration
public class WebMvcConfiguration implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(true)
                // you need to adjust the originPatterns to your environment
                .allowedOriginPatterns("http://*.localhost", "https://*.localhost")         
                ;
    }

    /**
     * This can also be configured in the application.yml file
     *
     * @param converters system-provided list of converters
     */
    @Override
    public void extendMessageConverters(final List<HttpMessageConverter<?>> converters) {

        // JSON converter
        final Optional<HttpMessageConverter<?>> jsonConverterFound = converters.stream().filter( c -> c instanceof MappingJackson2HttpMessageConverter ).findFirst();
        if ( jsonConverterFound.isPresent()) {
            final AbstractJackson2HttpMessageConverter converter = (AbstractJackson2HttpMessageConverter) jsonConverterFound.get();
            converter.getObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
            converter.getObjectMapper().enable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
        }
    }
}