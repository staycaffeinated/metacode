<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = RANDOM_PORT)
class RootControllerIT {
    @Autowired
    MockMvc mockMvc;

    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.data.mongodb.uri", ()->"mongodb://localhost:27017");
        registry.add("spring.data.mongodb.database", ()->"testdata");
    }

    @Test
    public void testGetHome() throws Exception {
        mockMvc.perform(get("/")).andExpect(status().isOk());
    }
}