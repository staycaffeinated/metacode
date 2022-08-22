<#include "/common/Copyright.ftl">
package ${project.basePackage}.common;

import static ${project.basePackage}.common.SpringProfiles.INTEGRATION_TEST;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
<#if (project.isWithTestContainers())>
import org.testcontainers.junit.jupiter.Testcontainers;
</#if>

@ActiveProfiles({INTEGRATION_TEST})
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
<#if project.isWithTestContainers()>
@Testcontainers
</#if>
<#if project.isWithTestContainers() && project.isWithPostgres()>
@TestPropertySource(properties = {
    "spring.datasource.url=jdbc:tc:postgresql:13.2-alpine:///public",
    "spring.datasource.driver-class-name=org.testcontainers.jdbc.ContainerDatabaseDriver",
    "spring.jpa.hibernate.ddl-auto=create-drop"})
</#if>
public abstract class AbstractDataJpaTest {
}