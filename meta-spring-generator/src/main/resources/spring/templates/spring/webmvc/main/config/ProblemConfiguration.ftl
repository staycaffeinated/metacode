<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.jackson.Jackson2ObjectMapperBuilderCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

/**
 * Configuration of Zalando Problem library.
 * The method name {@code objectMapper} is intentionally <bold>NOT</bold> used.
 * If {@code objectMapper} is used, these modules get overridden (something to do
 * with Spring/Jackson, not with the Problem library.
 */
@Configuration
@Slf4j
@SuppressWarnings({"squid:S125"})
public class ProblemConfiguration implements WebMvcConfigurer {

    /*
     * DO NOT rename this method to {@code objectMapper}. If you do, these Jackson modules
     * will not be applied when Problem's are serialized by Jackson. This
     * is a known issue, and not using the method name {@code objectMapper}
     * is one suggested work-around.
     * See:
     * 1) https://github.com/zalando/problem-spring-web/issues/83
     * 2) https://stackoverflow.com/questions/56291441/zalando-problem-spring-web-generates-unwanted-stack-trace
     * 3) https://github.com/zalando/problem-spring-web/issues/238
     */
    @Bean
    public Jackson2ObjectMapperBuilderCustomizer problemObjectMapperModules() {
        return jacksonObjectMapperBuilder -> jacksonObjectMapperBuilder.modules(
        new ProblemModule(),
        new ConstraintViolationProblemModule()
        );
    }
}

