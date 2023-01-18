<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};


import ${endpoint.basePackage}.configuration.${endpoint.entityName}TestTableInitializer;
import ${endpoint.basePackage}.configuration.TestDatabaseConfiguration;
<#if endpoint.isWithPostgres() && endpoint.isWithTestContainers()>
import ${endpoint.basePackage}.database.PostgresTestContainer;
</#if>
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Repository integration help catch syntax errors in custom
 * queries that may get added to the Repository interface.
 * This class only needs to test those repository methods
 * that use custom queries; there's no value added by
 * testing the default repository query methods.
 */
@SpringBootTest
@ComponentScan(basePackageClasses = {
  TestDatabaseConfiguration.class, 
  ${endpoint.entityName}TestTableInitializer.class })
<#if ((endpoint.isWithPostgres()) && (endpoint.isWithTestContainers()))>
class ${endpoint.entityName}RepositoryIntegrationTest extends PostgresTestContainer {
<#else>
class ${endpoint.entityName}RepositoryIntegrationTest {
</#if>

    @Autowired
    ${endpoint.entityName}Repository repositoryUnderTest;

    @Test
    void shouldFindFirstRecord() {
        ${endpoint.ejbName} firstOne = repositoryUnderTest.findAll().blockFirst();
        assertThat(firstOne).isNotNull();
    }
}


