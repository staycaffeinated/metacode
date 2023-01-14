<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};


import ${endpoint.basePackage}.configuration.${endpoint.entityName}TestTableInitializer;
import ${endpoint.basePackage}.configuration.TestDatabaseConfiguration;
import ${endpoint.basePackage}.database.PostgresTestContainer;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ComponentScan(basePackageClasses = {
  TestDatabaseConfiguration.class, 
  ${endpoint.entityName}TestTableInitializer.class })
public class ${endpoint.entityName}RepositoryIntegrationTest extends PostgresTestContainer {

    @Autowired
    ${endpoint.entityName}Repository repositoryUnderTest;
    
    
    @Test
    void shouldFindFirstRecord() {
        ${endpoint.ejbName} firstOne = repositoryUnderTest.findAll().blockFirst();
        assertThat(firstOne).isNotNull();
    }
}


