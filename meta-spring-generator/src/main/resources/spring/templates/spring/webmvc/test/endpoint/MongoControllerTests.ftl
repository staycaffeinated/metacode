<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.transaction.TransactionSystemException;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.reset;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(${endpoint.entityName}Controller.class)
@ActiveProfiles("test")
class ${endpoint.entityName}ControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ${endpoint.entityName}Service ${endpoint.entityVarName}Service;

    @Autowired
    private ObjectMapper objectMapper;

    private List<${endpoint.pojoName}> ${endpoint.entityVarName}List;
    private Page<${endpoint.pojoName}> pageOfData;

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @BeforeEach
    void setUp() {
        ${endpoint.entityVarName}List = ${endpoint.entityName}TestFixtures.allItems();

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());

        pageOfData = new PageImpl<>(${endpoint.entityVarName}List);
    }

    @AfterEach
    void tearDownEachTime() {
        reset ( ${endpoint.entityVarName}Service );
    }

    @Nested
    class FindAllTests {
        /*
         * shouldFetchAll${endpoint.entityName}s
         */
        @Test
        void shouldFetchAll${endpoint.entityName}s() throws Exception {
            int expectedSize = ${endpoint.entityName}TestFixtures.allItems().size();
            given(${endpoint.entityVarName}Service.findAll${endpoint.entityName}s()).willReturn(${endpoint.entityName}TestFixtures.allItems());

            findAllEntities()
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(expectedSize)));
        }
    }

    @Nested
    class FindByIdTests {
        /*
         *  shouldFind${endpoint.entityName}ById
         */
        @Test
        void shouldFind${endpoint.entityName}ById() throws Exception {
            // given
            ${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.entityName}TestFixtures.oneWithResourceId();
            String resourceId = ${endpoint.entityVarName}.getResourceId();

            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId( resourceId ))
                .willReturn(Optional.of(${endpoint.entityVarName}));

            // when/then
            findSpecificEntity(resourceId)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())))
            ;
        }

        @Test
        void shouldReturn404WhenFetchingNonExisting${endpoint.entityName}() throws Exception {
            // given
            String resourceId = randomSeries.nextResourceId();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId( resourceId ))
                    .willReturn(Optional.empty());

            // when/then
            findSpecificEntity(resourceId)
                .andExpect(status().isNotFound());

        }
    }

    @Nested
    class Create${endpoint.entityName}Tests {
        @Test
        void shouldCreateNew${endpoint.entityName}() throws Exception {
            // given
            ${endpoint.pojoName} resourceBeforeSave = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
            ${endpoint.pojoName} resourceAfterSave = ${endpoint.entityName}TestFixtures.copyOf(resourceBeforeSave);
            resourceAfterSave.setResourceId(randomSeries.nextResourceId());
            given(${endpoint.entityVarName}Service.create${endpoint.entityName}( any(${endpoint.pojoName}.class))).willReturn(resourceAfterSave);

            // when/then
            createEntity(resourceBeforeSave).andExpect(status().isCreated())
                    .andExpect(jsonPath("$.resourceId", notNullValue()))
                    .andExpect(jsonPath("$.text", is(resourceAfterSave.getText())));
        }

        @Test
        void whenDatabaseThrowsException_expectUnprocessableEntityResponse() throws Exception {
            // given the database throws an exception when the entity is saved
            given(${endpoint.entityVarName}Service.create${endpoint.entityName}( any(${endpoint.pojoName}.class))).willThrow(TransactionSystemException.class);
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().build();

            createEntity(resource).andExpect(status().isUnprocessableEntity());
        }
    }

    @Nested
    class Update${endpoint.entityName}Tests {
        @Test
        void shouldUpdate${endpoint.entityName}() throws Exception {
            // given
            String resourceId = randomSeries.nextResourceId();
            ${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.pojoName}.builder().resourceId(resourceId).text("sample text").build();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.of(${endpoint.entityVarName}));
            given(${endpoint.entityVarName}Service.update${endpoint.entityName}(any(${endpoint.pojoName}.class))).willReturn(List.of(${endpoint.entityVarName}));

            // when/then
            updateEntity(${endpoint.entityVarName}).andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].text", is(${endpoint.entityVarName}.getText())))
                    .andExpect(jsonPath("$[0].resourceId", is(${endpoint.entityVarName}.getResourceId())));
        }

        @Test
        void shouldReturn404WhenUpdatingNonExisting${endpoint.entityName}() throws Exception {
            // given
            String resourceId = randomSeries.nextResourceId();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.empty());

            // when/then
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(resourceId).text("updated text").build();

            // Attempt to update an entity that does not exist
            updateEntity(resource).andExpect(status().isNotFound());
        }

        /**
         * When the Ids in the query string and request body do not match, expect
         * an 'Unprocessable Entity' response code
         */
        @Test
        void shouldReturn422WhenIdsMismatch() throws Exception {
            // given
            String resourceId = randomSeries.nextResourceId();
            String mismatchingId = randomSeries.nextResourceId();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.empty());

            // when the ID in the request body does not match the ID in the query string...
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(mismatchingId).text("updated text").build();

            // Submit an update request, with the ID in the URL not matching the ID in the body.
            // Expect back an UnprocessableEntity status code
            updateEntity(resourceId, resource).andExpect(status().isUnprocessableEntity());
        }
    }

    @Nested
    class Delete${endpoint.entityName}Tests {
        @Test
        void shouldDelete${endpoint.entityName}() throws Exception {
            // given
            ${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.entityName}TestFixtures.oneWithResourceId();
            String resourceId = ${endpoint.entityVarName}.getResourceId();
            
            // Mock the service layer finding the resource being deleted
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.of(${endpoint.entityVarName}));
            doNothing().when(${endpoint.entityVarName}Service).delete${endpoint.entityName}ByResourceId(${endpoint.entityVarName}.getResourceId());

            // when/then
            deleteEntity(resourceId).andExpect(status().isOk())
                    .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())));
        }

        @Test
        void shouldReturn404WhenDeletingNonExisting${endpoint.entityName}() throws Exception {
            String resourceId = randomSeries.nextResourceId();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.empty());

            deleteEntity(resourceId).andExpect(status().isNotFound());
        }
    }

    @Nested
    class SearchByTextTests {
        @Test
        @SuppressWarnings("unchecked")
        void shouldReturnListWhenMatchesAreFound() throws Exception {
            given (${endpoint.entityVarName}Service.findByText(any(String.class), any(Pageable.class))).willReturn(pageOfData);

            // when/then (the default Pageable in the controller is sufficient for testing)
            searchByText("text").andExpect(status().isOk());
        }
    }

    @Nested
    class SearchTextValidationTests {
        @Test
        void whenTextIsTooLong_expectError() throws Exception {
            searchByText("supercalifragilisticexpialidocious").andExpect(status().is4xxClientError());
        }
        @Test
        void whenTextContainsInvalidCharacters_expectError() throws Exception {
            searchByText("192.168.0.0<555").andExpect(status().is4xxClientError());
        }
    }

    // ---------------------------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ---------------------------------------------------------------------------------------------------------------

    /**
     * Sends a findAll request
     */ 
    protected ResultActions findAllEntities() throws Exception {
  	    return mockMvc.perform(get( ${endpoint.entityName}Routes.${endpoint.routeConstants.findAll} ));
	  }

    /**
     * Sends a findOne request
     */
	  protected ResultActions findSpecificEntity(String resourceId) throws Exception {
		    return mockMvc.perform(get(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, resourceId ));
	  }
   
   
    /**
     * Submits a search request
     */
    protected ResultActions searchByText(String text) throws Exception {
        return mockMvc.perform(get(${endpoint.entityName}Routes.${endpoint.routeConstants.search}).param("text", text));
    }

    /**
     * Submits a Delete request
     */
    protected ResultActions deleteEntity(String resourceId) throws Exception {
        return mockMvc.perform(delete(${endpoint.entityName}Routes.${endpoint.routeConstants.delete},resourceId));
    }

    /**
     * To support the use case of a well-formed update request
     */
    protected ResultActions updateEntity(${endpoint.pojoName} ${endpoint.entityVarName}) throws Exception {
        return mockMvc.perform(put(${endpoint.entityName}Routes.${endpoint.routeConstants.update}, ${endpoint.entityVarName}.getResourceId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(${endpoint.entityVarName})));
    }

    /**
     * To support the use case of the ID in the query string not matching the ID in the payload
     */
    protected ResultActions updateEntity(String resourceId, ${endpoint.pojoName} ${endpoint.entityVarName}) throws Exception {
        return mockMvc.perform(put(${endpoint.entityName}Routes.${endpoint.routeConstants.update}, resourceId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(${endpoint.entityVarName})));
    }

    /**
     * Submits a Post request
     */
    protected ResultActions createEntity(${endpoint.pojoName} ${endpoint.entityVarName}) throws Exception {
        return mockMvc.perform(post(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(${endpoint.entityVarName})));
    }
}
