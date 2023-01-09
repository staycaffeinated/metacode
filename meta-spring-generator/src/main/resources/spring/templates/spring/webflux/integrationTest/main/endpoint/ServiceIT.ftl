<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.r2dbc.DataR2dbcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import reactor.core.publisher.Flux;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration test of the service component
 */
@DataR2dbcTest
@Slf4j
class ${endpoint.entityName}ServiceIntegrationTest extends PostgresTestContainer {

    @Autowired
    ${endpoint.entityName}Repository repository;

    @MockBean
    ApplicationEventPublisher applicationEventPublisher;

    ${endpoint.entityName}Service serviceUnderTest;

    public ConversionService conversionService = FakeConversionService.build();

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @BeforeEach
    public void setUp() {
        serviceUnderTest = new ${endpoint.entityName}Service(repository, conversionService, applicationEventPublisher, randomSeries);
    }


    @Test
    void shouldFindResults() {
        Flux<${endpoint.entityName}> source = serviceUnderTest.findAll${endpoint.entityName}s();

        // Verify something came back.
        StepVerifier.create(source).expectSubscription().thenCancel().verify();
    }
}