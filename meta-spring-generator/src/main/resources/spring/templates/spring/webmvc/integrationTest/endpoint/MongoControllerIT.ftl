<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
<#if (endpoint.isWithTestContainers())>
class ${endpoint.entityName}ControllerIT extends MongoDbContainerTests {
<#else>
class ${endpoint.entityName}ControllerIT {
</#if>

    @Autowired
    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @Autowired
    private ${endpoint.entityName}Repository repository;

    private List<${endpoint.documentName}> documentList;


    @BeforeEach
    void setUp() {
        documentList = ${endpoint.documentName}TestFixtures.allItems();
        repository.saveAll(${endpoint.documentName}TestFixtures.allItems());
    }

    @AfterEach
    public void tearDownEachTime() {
        repository.deleteAll();
    }

    @Nested
    public class ValidateFindByText {
        @Test
        void whenSearchFindsHits_expectOkAndMatchingRecords() throws Exception {
            searchByText("Bluey").andExpect(status().isOk());
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
            ${endpoint.documentName} pet = documentList.get(0);
            String docId = pet.getResourceId();

            mockMvc.perform(get(${endpoint.routeConstants.findOne}, docId)).andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(pet.getText())));
        }
    }

    /*
     * Create method
     */
    @Nested
    public class ValidateCreate${endpoint.pojoName} {
        @Test
        void shouldCreateNew${endpoint.pojoName}() throws Exception {
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().text("I am a new resource").build();

            mockMvc.perform(post(${endpoint.routeConstants.create}).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resource))).andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is(resource.getText())));
        }

        /**
         * Verify the controller's data validation catches malformed inputs, such as
         * missing required fields, and returns either 'unprocessable entity' or 'bad
         * request'.
         */
        @Test
        void shouldReturn4xxWhenCreateNew${endpoint.pojoName}WithoutText() throws Exception {
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().build();

            // Oddly, depending on whether the repository uses MongoDB or H2, there are two
            // different outcomes. With H2, the controller's @Validated annotation is
            // applied and a 400 status code is returned. With MongoDB, the @Validated
            // is ignored and a 422 error occurs when the database catches the invalid data.
            mockMvc.perform(post(${endpoint.routeConstants.create}).content(objectMapper.writeValueAsString(resource)))
                .andExpect(status().is4xxClientError());
            }
        }

    /*
     * Update method
     */
    @Nested
    public class ValidateUpdate${endpoint.entityName} {

        @Test
        @SuppressWarnings("all")
        void shouldUpdate${endpoint.entityName}() throws Exception {
            ${endpoint.documentName} doc = documentList.get(0);
            ${endpoint.pojoName} modified = new {$endpoint.documentName}ToPojoConverter().convert(document);
            modified.setText("modified");

            mockMvc.perform(put(${endpoint.routeConstants.update}, doc.getResourceId()).contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(modified))).andExpect(status().isOk())
                .andExpect(jsonPath("$..text").value(modified.getText()));
        }
    }

    /*
    * Delete method
    */
    @Nested
    public class ValidateDelete${endpoint.entityName} {
        @Test
        void shouldDelete${endpoint.entityName}() throws Exception {
            ${endpoint.documentName} document = documentList.get(0);

            mockMvc.perform(delete(${endpoint.routeConstants.delete}, document.getResourceId())).andExpect(status().isOk())
            .andExpect(jsonPath("$.text", is(pet.getText())));
        }
    }

    // ---------------------------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ---------------------------------------------------------------------------------------------------------------

    protected ResultActions searchByText(String text) throws Exception {
        return mockMvc.perform(get(${endpoint.routeConstants.search}).param("text", text));
    }
}
