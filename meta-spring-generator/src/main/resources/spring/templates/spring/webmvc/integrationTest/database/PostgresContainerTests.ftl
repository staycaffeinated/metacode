<#include "/common/Copyright.ftl">
package ${project.basePackage}.database;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.containers.wait.strategy.Wait;
import org.testcontainers.junit.jupiter.Testcontainers;

import java.sql.SQLException;
import java.sql.Statement;
import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Provide support for MongoDB TestContainer
 */
@Slf4j
@Testcontainers
@SuppressWarnings("all")
public class PostgresContainerTests {

    private static final String IMAGE = "postgres";

    // @formatter:off
    // Note: the container is started as a singleton instead of using the @Container
    // annotation. When @Container is applied, multiple containers may get started.
    // When multiple containers are started, tests will hang from socket timeouts.
    private static final PostgreSQLContainer<?> testContainer = (PostgreSQLContainer) new PostgreSQLContainer(IMAGE)
        .withReuse(true)
        .withStartupTimeout(Duration.ofMinutes(1))
        .waitingFor(Wait.forListeningPort());
    // @formatter:on

    static {
        testContainer.start();
    }

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
<#if project.schema?has_content>
        // Be aware that calling testContainer.getJdbcUrl returns a URL that starts
        // something like "jdbc:postgresql://localhost...etc...".
        // The URL needs to start with "jdbc:tc:postgresql", The 'tc' between 'jdbc' and
        // 'postgres' is the hint to the driver that the TestContainer handles the URL,
        // not the typical runtime JDCB driver.

        final String initFunction = PostgresContainerTests.class.getName() + "::initFunction";
        registry.add("spring.datasource.url",
                () -> "jdbc:tc:postgresql:15.1-alpine:///public?TC_INITFUNCTION=" + initFunction);
<#else>
        registry.add("spring.datasource.url",
                () -> "jdbc:tc:postgresql:15.1-alpine:///public);
</#if>
        registry.add("spring.datasource.driver-class-name", () -> "org.testcontainers.jdbc.ContainerDatabaseDriver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
    }

<#if project.schema?has_content>
    /**
     * This method illustrates how to add a schema and tables to test containers.
     * If your tables need to be in a schema, this method will create the schema and
     * tables for you. Hibernate does not automagically create tables or schemas
     * when a schema is used (at least, not when using TestContainers).
     */
    public static void initFunction(java.sql.Connection connection) {
        // This contains example DDL statements; the statements do need to be
        // modified to match your requirements.
        // The statements forgo the `if not exists` clause since we know these don't
        // exist in the temporary database of the container.
        final String createSchema = "create schema ${project.schema}";

        try (Statement stmt = connection.createStatement()) {
            stmt.execute(createSchema);
        } catch (SQLException ex) {
            log.error(ex.getMessage());
        }
    }
</#if>

    @Test
    void shouldCreateContainer() {
        assertThat(testContainer).isNotNull();
        assertThat(testContainer.isCreated()).isTrue();
        assertThat(testContainer.isRunning()).isTrue();
    }
}
