<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;

<#-- ======================================= -->
<#-- When using Postgres with TestContainers -->
<#-- ======================================= -->
<#if project.isWithPostgres() && project.isWithTestContainers()>
import ${project.basePackage}.database.PostgresContainerTests;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;

@SpringBootTest(webEnvironment = RANDOM_PORT)
@AutoConfigureMockMvc
public class RootControllerIT extends PostgresContainerTests {
    @Autowired
    MockMvc mockMvc;
<#else>
<#-- ======================================= -->
<#-- Otherwise...                            -->
<#-- ======================================= -->
import ${project.basePackage}.common.AbstractIntegrationTest;

public class RootControllerIT extends AbstractIntegrationTest {
</#if>
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}