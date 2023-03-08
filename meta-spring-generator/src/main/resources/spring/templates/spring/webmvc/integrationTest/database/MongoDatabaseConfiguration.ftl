<#include "/common/Copyright.ftl">
package ${project.basePackage}.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;

@Slf4j
public class DatabaseConfiguration {

    public static void registerDatabaseProperties(DynamicPropertyRegistry registry) {
<#if ((project.isWithMongoDb()) && (!project.isWithTestContainers()))>
        registry.add("spring.data.mongodb.uri", () -> "mongodb://localhost:27017/testdb");
        registry.add("spring.data.mongodb.database", () -> "testdata"); // this property is not required
</#if>
    }
}
