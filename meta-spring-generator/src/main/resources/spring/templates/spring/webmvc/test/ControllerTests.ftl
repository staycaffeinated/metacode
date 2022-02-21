<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.zalando.problem.jackson.ProblemModule;
import org.zalando.problem.violations.ConstraintViolationProblemModule;

import java.util.ArrayList;
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
class ${endpoint.entityName}ControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ${endpoint.entityName}Service ${endpoint.entityVarName}Service;

    @Autowired
    private ObjectMapper objectMapper;

    private List<${endpoint.pojoName}> ${endpoint.entityVarName}List;

    @BeforeEach
    void setUp() {
        ${endpoint.entityVarName}List = new ArrayList<>();
        ${endpoint.entityVarName}List.add(${endpoint.pojoName}.builder().resourceId(111L).text("text 1").build());
        ${endpoint.entityVarName}List.add(${endpoint.pojoName}.builder().resourceId(222L).text("text 2").build());
        ${endpoint.entityVarName}List.add(${endpoint.pojoName}.builder().resourceId(333L).text("text 3").build());

        objectMapper.registerModule(new ProblemModule());
        objectMapper.registerModule(new ConstraintViolationProblemModule());
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
            given(${endpoint.entityVarName}Service.findAll${endpoint.entityName}s()).willReturn(${endpoint.entityVarName}List);

            mockMvc.perform(get( ${endpoint.entityName}Routes.GET_ALL ))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(${endpoint.entityVarName}List.size())));
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
            Long resourceId = 1L;
            ${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.pojoName}.builder().resourceId( resourceId ).text("text 1").build();

            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId( resourceId ))
                .willReturn(Optional.of(${endpoint.entityVarName}));

            // when/then
            mockMvc.perform(get(${endpoint.entityName}Routes.EXACTLY_ONE, resourceId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())))
            ;
        }

        @Test
        void shouldReturn404WhenFetchingNonExisting${endpoint.entityName}() throws Exception {
            // given
            Long resourceId = 1L;
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId( resourceId ))
                    .willReturn(Optional.empty());

            // when/then
            mockMvc.perform(get(${endpoint.entityName}Routes.EXACTLY_ONE, resourceId ))
                .andExpect(status().isNotFound());

        }
    }

    @Nested
    class Create${endpoint.entityName}Tests {
        @Test
        void shouldCreateNew${endpoint.entityName}() throws Exception {
            // given
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().text("sample").build();
            ${endpoint.pojoName} resourceAfterSave = ${endpoint.pojoName}.builder().text("sample").resourceId(1L).build();
            given(${endpoint.entityVarName}Service.create${endpoint.entityName}( any(${endpoint.pojoName}.class))).willReturn(resourceAfterSave);

            // when/then
            mockMvc.perform(post(${endpoint.entityName}Routes.CREATE_ONE)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(resource)))
                    .andExpect(status().isCreated())
                    .andExpect(jsonPath("$.resourceId", notNullValue()))
                    .andExpect(jsonPath("$.text", is(resourceAfterSave.getText())))
            ;

        }
    }

    @Nested
    class Update${endpoint.entityName}Tests {
        @Test
        void shouldUpdate${endpoint.entityName}() throws Exception {
            // given
            Long resourceId = 1L;
            ${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.pojoName}.builder().resourceId(resourceId).text("sample text").build();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.of(${endpoint.entityVarName}));
            given(${endpoint.entityVarName}Service.update${endpoint.entityName}(any(${endpoint.pojoName}.class))).willReturn(Optional.of(${endpoint.entityVarName}));

            // when/then
            mockMvc.perform(put(${endpoint.entityName}Routes.EXACTLY_ONE, ${endpoint.entityVarName}.getResourceId())
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString(${endpoint.entityVarName})))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())))
                    ;

        }

        @Test
        void shouldReturn404WhenUpdatingNonExisting${endpoint.entityName}() throws Exception {
            // given
            Long resourceId = 1L;
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.empty());

            // when/then
            ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(resourceId).text("updated text").build();

            mockMvc.perform(put(${endpoint.entityName}Routes.EXACTLY_ONE, resourceId)
                    .contentType(MediaType.APPLICATION_JSON)
                    .content(objectMapper.writeValueAsString( resource )))
                    .andExpect(status().isNotFound());

        }
    }

    @Nested
    class Delete${endpoint.entityName}Tests {
        @Test
        void shouldDelete${endpoint.entityName}() throws Exception {
            // given
            Long resourceId = 1L;
            ${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.pojoName}.builder().resourceId(resourceId).text("delete me").build();
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.of(${endpoint.entityVarName}));
            doNothing().when(${endpoint.entityVarName}Service).delete${endpoint.entityName}ByResourceId(${endpoint.entityVarName}.getResourceId());

            // when/then
            mockMvc.perform(delete(${endpoint.entityName}Routes.EXACTLY_ONE, resourceId))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$.text", is(${endpoint.entityVarName}.getText())))
                    ;
        }

        @Test
        void shouldReturn404WhenDeletingNonExisting${endpoint.entityName}() throws Exception {
            Long resourceId = 1L;
            given(${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(resourceId)).willReturn(Optional.empty());

            mockMvc.perform(delete(${endpoint.entityName}Routes.EXACTLY_ONE,resourceId))
                    .andExpect(status().isNotFound());
        }
    }

    @Nested
    class SearchByTextTests {
        @Test
        void shouldReturnListWhenMatchesAreFound() throws Exception {
            given (${endpoint.entityVarName}Service.findByText(anyString(), anyInt(), anyInt())).willReturn(${endpoint.entityVarName}List);

            // when/then
            mockMvc.perform(get(${endpoint.entityName}Routes.SEARCH)
                    .param("text", "sample")
                    .param("page", "1")
                    .param("size", "10"))
                    .andExpect(status().isOk())
            ;
        }
    }
}