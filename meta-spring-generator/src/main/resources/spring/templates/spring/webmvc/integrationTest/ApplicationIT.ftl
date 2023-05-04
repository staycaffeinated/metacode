<#include "/common/Copyright.ftl">
package ${project.basePackage};

<#-- ========================= -->
<#-- Postgres & TestContainers -->
<#-- ========================= -->
<#if project.isWithPostgres() && project.isWithTestContainers()>
import ${project.basePackage}.database.PostgresContainerTests;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static ${project.basePackage}.common.SpringProfiles.INTEGRATION_TEST;
import static ${project.basePackage}.common.SpringProfiles.TEST;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@ActiveProfiles({TEST,INTEGRATION_TEST})
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTests extends PostgresContainerTests {
<#else>
<#-- ========================= -->
<#-- Vanilla                   -->
<#-- ========================= -->
import org.junit.jupiter.api.Test;
import ${project.basePackage}.common.AbstractIntegrationTest;

class ApplicationTests extends AbstractIntegrationTest {
</#if>

    @Test
    void contextLoads() {
        // If this test runs without throwing an exception, then SpringBoot started successfully
    }
}
