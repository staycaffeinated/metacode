<#include "/common/Copyright.ftl">
package ${project.basePackage};

import org.springframework.test.context.ActiveProfiles;

import static ${project.basePackage}.common.SpringProfiles.INTEGRATION_TEST;

<#if (project.testcontainers)??>
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
</#if>
<#if (project.testcontainers)?? &&  (project.postgres)??>
import org.testcontainers.containers.PostgreSQLContainer;
</#if>

@ActiveProfiles({INTEGRATION_TEST})
<#if (project.testcontainers)??>
@Testcontainers
</#if>
public abstract class AbstractIntegrationTest {
}