<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.configuration.*;
import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
<#if !endpoint.isWithTestContainers()>
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;
</#if>
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * Integration test of the service component
 */
@ComponentScan(basePackageClasses={TestDatabaseConfiguration.class,${endpoint.entityName}TestTableInitializer.class})
@SpringBootTest
@Slf4j
<#if (endpoint.isWithPostgres() && endpoint.isWithTestContainers())>
class ${endpoint.entityName}ServiceIntegrationTest extends PostgresTestContainer {
<#else>
class ${endpoint.entityName}ServiceIntegrationTest {
</#if>

    @Autowired
    ${endpoint.entityName}DataStore dataStore;

    ${endpoint.entityName}ServiceProvider serviceUnderTest;

<#if !endpoint.isWithTestContainers()>
    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        DatabaseInitFunction.registerDatabaseProperties(registry);
    }

</#if>
    @BeforeEach
    void setUp() {
        serviceUnderTest = new ${endpoint.entityName}ServiceProvider(dataStore);
        ${endpoint.entityName}TestFixtures.allItems().forEach(item -> {
          serviceUnderTest.create${endpoint.entityName}(item).blockOptional(Duration.ofSeconds(1)); 
        });
    }

    @AfterEach
    void tearDown() {
        // empty
    }

    @Test
    void shouldFindResults() {
        int expectedCount = ${endpoint.ejbName}TestFixtures.allItems().size();

        Flux<${endpoint.entityName}> source = serviceUnderTest.findAll${endpoint.entityName}s();

        // Expect as many rows back as were inserted by setUp 
        StepVerifier.create(source).expectSubscription().expectNextCount(expectedCount).thenCancel().verify();
    }
}
