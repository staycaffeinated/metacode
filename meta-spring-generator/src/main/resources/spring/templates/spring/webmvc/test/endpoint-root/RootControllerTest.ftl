<#include "/common/Copyright.ftl">

package ${project.basePackage}.endpoint.root;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(RootController.class)
@ActiveProfiles("test")
class RootControllerTest {

    @Autowired
    private MockMvc mockMvc;
    
    @MockBean
    private RootService mockRootService;

    @Autowired
    private ObjectMapper objectMapper;

    @BeforeEach
    void setUp() {
        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
    }
    
    @AfterEach
    void tearDownEachTime() {
        reset ( mockRootService );
    }

    @Nested
    class TestRootRoute {
        /*
         * Ensure the root controller handles GET requests
         */
        @Test
        void shouldGetRootPage() throws Exception {
            mockMvc.perform(get("/"))
                    .andExpect(status().isOk());
        }
    }
}
