<#include "/common/Copyright.ftl">
package ${project.basePackage};

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;

@Slf4j
@SpringBootTest
class ApplicationTests {
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", ()->"mongodb://localhost:27017");
        registry.add("spring.data.mongodb.database", ()->"testdata");
    }

    @Test
    void contextLoads() {
        // If this test runs without throwing an exception, then SpringBoot started successfully
    }
}
