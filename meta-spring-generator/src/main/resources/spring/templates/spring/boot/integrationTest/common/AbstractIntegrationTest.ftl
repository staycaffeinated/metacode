<#include "/common/Copyright.ftl">
package ${project.basePackage}.common;

import static ${project.basePackage}.common.SpringProfiles.INTEGRATION_TEST;
import static ${project.basePackage}.common.SpringProfiles.TEST;
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
<#if (project.testcontainers)??>
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
</#if>
<#if (project.testcontainers)?? &&  (project.postgres)??>
import org.testcontainers.containers.PostgreSQLContainer;
</#if>

@ActiveProfiles({TEST, INTEGRATION_TEST})
@SpringBootTest(webEnvironment = RANDOM_PORT)
<#if (project.testcontainers)??>
@Testcontainers
@SuppressWarnings({"rawtypes"})
</#if>
public abstract class AbstractIntegrationTest {

    @Autowired
    protected ObjectMapper objectMapper;
<#if (project.testcontainers)??>

    // if possible, initialize these from bootstrap-test.properties
    private static final String databaseName = "testdb";
    private static final String username = "postgres";
    private static final String password = "root";
</#if>
<#if (project.testcontainers)?? &&  (project.postgres)??>

    // Task: Set the Postgres version to what your application will use
    @Container
    final static PostgreSQLContainer postgresqlContainer =
                new PostgreSQLContainer("postgres:latest")
                        .withDatabaseName( databaseName )
                        .withUsername( username )
                        .withPassword( password );
</#if>
}