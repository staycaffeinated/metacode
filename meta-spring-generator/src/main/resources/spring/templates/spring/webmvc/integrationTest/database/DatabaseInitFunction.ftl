<#include "/common/Copyright.ftl">
package ${project.basePackage}.database;

import lombok.extern.slf4j.Slf4j;
import org.springframework.test.context.DynamicPropertyRegistry;

@Slf4j
public class DatabaseInitFunction {

    public static void registerDatabaseProperties(DynamicPropertyRegistry registry) {
<#if ((project.isWithPostgres()) && (project.isWithTestContainers()))>
        // If the tables need to be part of a schema, see the comments   
        registry.add("spring.datasource.url", () -> "jdbc:tc:postgresql:15.1-alpine:///public");
        registry.add("spring.datasource.driver-class-name", () -> "org.testcontainers.jdbc.ContainerDatabaseDriver");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.PostgreSQLDialect");
<#else>
        registry.add("spring.datasource.url", () -> "jdbc:h2:mem:testdb;DB_CLOSE_DELAY=-1");
        registry.add("spring.datasource.driver-class-name", () -> "org.h2.Driver");
        registry.add("spring.datasource.initialization-mode", () -> "embedded");
        registry.add("spring.jpa.database-platform", () -> "org.hibernate.dialect.H2Dialect");
        registry.add("spring.jpa.properties.hibernate.id.new_generator_mappings", () -> "false");

        // The username and password here must match the username and password in the
        // src/test/resources/application-test.yaml.  Otherwise, you will see
        // "org.h2.jdbc.JdbcSQLInvalidAuthorizationSpecException: Wrong user name or password"
        registry.add("spring.datasource.username", () -> "sa");
        registry.add("spring.datasource.password", () -> "password");
</#if>
        registry.add("spring.jpa.hibernate.ddl-auto", () -> "create-drop");
        registry.add("spring.jpa.show-sql", () -> "true");
        registry.add("spring.jpa.properties.hibernate.format_sql", () -> "true");
    }

    // This method is here to illustrate a technique. If it is useful, use it.
    // This method illustrates how to add a 
    // If your tables need to be in a schema, this method with create the schema and
    // table for you.  Hibernate does not automagically create tables when a schema
    // is used (at least, not when using TestContainers). To invoke this method,
    // change your datasource URL to follow this pattern and update the 'registerDatabaseProperties' accordingly:
    // <code>
    //  final String initFunction = DatabaseInitFunction.class.getName() + "::initFunction";
    //  registry.add("spring.datasource.url",
    //            () -> "jdbc:tc:postgresql:15.1-alpine:///database?TC_INITFUNCTION=" + initFunction);
    // </code>
    //
    public static void initFunction(java.sql.Connection connection) {
        // This contains example DDL statements; the statements do need to be
        // modified to match your requirements.
        // The statements forgo the `if not exists` clause since we know these don't exist
        // in the temporary database of the container.
        final String createSchema = "create schema acme";

        try {
            connection.createStatement().execute(createSchema);
        } catch (java.sql.SQLException ex) {
            log.error(ex.getMessage());
        }
    }
}

