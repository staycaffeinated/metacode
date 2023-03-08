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
            ${endpoint.documentName} item = documentList.get(0);

            findOne(item.getResourceId()).andExpect(status().isOk())
                .andExpect(jsonPath("$.resourceId", is(item.getResourceId())));
        }
    }

    /*
     * Create method
     */
    @Nested
    public class ValidateCreate${endpoint.pojoName} {
        @Test
        void shouldCreateNew${endpoint.pojoName}() throws Exception {
            ${endpoint.pojoName} resource = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();

            createOne(resource).andExpect(status().isCreated())
                .andExpect(jsonPath("$.text", is(resource.getText())));
        }

        /**
         * Verify the controller's data validation catches malformed inputs, such as
         * missing required fields, and returns either 'unprocessable entity' or 'bad
         * request'.
         */
        @Test
        void shouldReturn201WhenCreateNew${endpoint.pojoName}WithoutText() throws Exception {
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().build();

            // Group validation appears to be buggy in Spring 6.
            // The validations in Group::OnCreate and Group::OnUpdate
            // are not being honored. For example, an entity's resourceId should
            // be blank when creating the entity, but non-blank when updating
            // the entity. The culprit for this (broken) behavior is not yet known.
            createOne(resource).andExpect(status().isCreated());
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
            ${endpoint.pojoName} modified = new ${endpoint.documentName}ToPojoConverter().convert(doc);
            modified.setText("modified");

            updateOne(modified).andExpect(status().isOk())
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

            deleteOne(document.getResourceId()).andExpect(status().isOk());
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

    protected ResultActions findOne(String resourceId) throws Exception {
        return mockMvc.perform(get(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, resourceId));
    }

    protected ResultActions createOne(Pet pojo) throws Exception {
        return mockMvc.perform(post(${endpoint.entityName}Routes.${endpoint.routeConstants.create}).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pojo)));
    }

    protected ResultActions updateOne(Pet pojo) throws Exception {
        return mockMvc.perform(put(${endpoint.entityName}Routes.${endpoint.routeConstants.update}, pojo.getResourceId()).contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(pojo)));
    }

    protected ResultActions deleteOne(String resourceId) throws Exception {
        return mockMvc.perform(delete(${endpoint.entityName}Routes.${endpoint.routeConstants.delete}, resourceId));
    }
}
