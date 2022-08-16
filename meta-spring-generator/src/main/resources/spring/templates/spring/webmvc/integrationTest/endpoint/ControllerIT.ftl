<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.common.AbstractIntegrationTest;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ${endpoint.entityName}ControllerIT extends AbstractIntegrationTest {

    @Autowired
    private ${endpoint.entityName}Repository ${endpoint.entityVarName}Repository;

    // This holds sample ${endpoint.ejbName}s that will be saved to the database
    private List<${endpoint.ejbName}> ${endpoint.entityVarName}List = null;

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @BeforeEach
    void setUp() {
        ${endpoint.entityVarName}List = new ArrayList<>();
        ${endpoint.entityVarName}List.add(new ${endpoint.ejbName}(1L, randomSeries.nextResourceId(), "First ${endpoint.entityName}"));
        ${endpoint.entityVarName}List.add(new ${endpoint.ejbName}(2L, randomSeries.nextResourceId(), "Second ${endpoint.entityName}"));
        ${endpoint.entityVarName}List.add(new ${endpoint.ejbName}(3L, randomSeries.nextResourceId(), "Third ${endpoint.entityName}"));
        ${endpoint.entityVarName}List = ${endpoint.entityVarName}Repository.saveAll(${endpoint.entityVarName}List);
    }

    @AfterEach
    public void tearDownEachTime() {
        ${endpoint.entityVarName}Repository.deleteAll();
    }

    @Nested
    public class ValidateFindByText {
        @Test
        void whenSearchFindsHits_expectOkAndMatchingRecords() throws Exception {
            searchByText("First").andExpect(status().isOk());
        }

        @Test
        void whenSearchComesUpEmpty_expectOkButNoRecords() throws Exception {
            searchByText("xyzzy").andExpect(status().isOk());
        }
    }


    /*
     * FindById
     */
    @Nested
    public class ValidateFindById {
        @Test
        void shouldFind${endpoint.entityName}ById() throws Exception {
            ${endpoint.ejbName} ${endpoint.entityVarName} = ${endpoint.entityVarName}List.get(0);
            String ${endpoint.entityVarName}Id = ${endpoint.entityVarName}.getResourceId();

            mockMvc.perform(get(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, ${endpoint.entityVarName}Id))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())));

        }
    }

    /*
     * Create method
     */
    @Nested
    public class ValidateCreate${endpoint.entityName} {
        @Test
        void shouldCreateNew${endpoint.entityName}() throws Exception {
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().text("I am a new resource").build();

            mockMvc.perform(post(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(resource)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.text", is(resource.getText())))
                    ;
        }

        /**
         * Verify the controller's data validation catches malformed inputs, 
         * such as missing required fields, and returns either 'unprocessable entity'
         * or 'bad request'.
         */
        @Test
        void shouldReturn4xxWhenCreateNew${endpoint.entityName}WithoutText() throws Exception {
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().build();

            // Oddly, depending on whether the repository uses Postgres or H2, there are two
            // different outcomes. With H2, the controller's @Validated annotation is
            // applied and a 400 status code is returned. With Postgres, the @Validated
            // is ignored and a 422 error occurs when the database catches the invalid data.
            mockMvc.perform(post(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
                    .content(objectMapper.writeValueAsString(resource)))
                    .andExpect(status().is4xxClientError());
        }
    }


    /*
     * Update method
     */
    @Nested
    public class ValidateUpdate${endpoint.entityName} {

        @Test
        void shouldUpdate${endpoint.entityName}() throws Exception {
            ${endpoint.ejbName} ${endpoint.entityVarName} = ${endpoint.entityVarName}List.get(0);
            ${endpoint.pojoName} resource = new ${endpoint.entityName}EntityToPojoConverter().convert(${endpoint.entityVarName});

            mockMvc.perform(put(${endpoint.entityName}Routes.${endpoint.routeConstants.update}, ${endpoint.entityVarName}.getResourceId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(resource)))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())));

        }
    }

    /*
     * Delete method
     */
    @Nested
    public class ValidateDelete${endpoint.entityName} {
        @Test
        void shouldDelete${endpoint.entityName}() throws Exception {
            ${endpoint.ejbName} ${endpoint.entityVarName} = ${endpoint.entityVarName}List.get(0);

            mockMvc.perform(
                    delete(${endpoint.entityName}Routes.${endpoint.routeConstants.delete}, ${endpoint.entityVarName}.getResourceId()))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())));
        }
    }

    // ---------------------------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ---------------------------------------------------------------------------------------------------------------

    protected ResultActions searchByText(String text) throws Exception {
        return mockMvc.perform(get(${endpoint.entityName}Routes.${endpoint.routeConstants.search}).param("text", text));
    }

}
