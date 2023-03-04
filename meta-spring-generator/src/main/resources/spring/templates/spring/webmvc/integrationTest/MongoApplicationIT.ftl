<#include "/common/Copyright.ftl">
package ${project.basePackage};

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
<#if (!project.isWithTestContainers())>
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
<#else>
import ${project.basePackage}.database.MongoDbContainerTests;
</#if>

@Slf4j
@SpringBootTest
<#if (project.isWithTestContainers())>
class ApplicationTests extends MongoDbContainerTests {
<#else>
class ApplicationTests {
</#if>
    <#if (!project.isWithTestContainers())>
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", ()->"mongodb://localhost:27017");
        registry.add("spring.data.mongodb.database", ()->"testdata");
    }
    </#if>

    @Test
    void contextLoads() {
        // If this test runs without throwing an exception, then SpringBoot started successfully
    }
}
