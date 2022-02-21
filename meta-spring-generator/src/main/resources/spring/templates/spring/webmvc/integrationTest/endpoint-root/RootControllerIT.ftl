<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import ${project.basePackage}.common.AbstractIntegrationTest;
import org.junit.jupiter.api.Test;

public class RootControllerIT extends AbstractIntegrationTest {
    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}