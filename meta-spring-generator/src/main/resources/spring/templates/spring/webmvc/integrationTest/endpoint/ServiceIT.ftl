<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.common.AbstractIntegrationTest;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;

import java.util.ArrayList;
import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.startsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

public class ${endpoint.entityName}ServiceIT extends AbstractIntegrationTest {

    @Autowired
    private ${endpoint.entityName}Repository ${endpoint.entityVarName}Repository;

    // This holds sample ${endpoint.ejbName}s that will be saved to the database
    private List<${endpoint.ejbName}> ${endpoint.entityVarName}List = null;

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    ConversionService conversionService = FakeConversionService.build();

    private ${endpoint.entityName}Service serviceUnderTest;

    @BeforeEach
    void init${endpoint.entityName}Service() {
        serviceUnderTest = new ${endpoint.entityName}Service(${endpoint.entityVarName}Repository, conversionService, randomSeries);
    }


    @BeforeEach
    void insertTestData() {
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

    /*
     * FindById
     */
    @Nested
    public class ValidateFindById {
        @Test
        void shouldFind${endpoint.entityName}ById() throws Exception {
            // TODO: Implement
        }
    }

    /*
     * Create method
     */
    @Nested
    public class ValidateCreate${endpoint.entityName} {
        @Test
        void shouldCreateNew${endpoint.entityName}() throws Exception {
            // TODO: Implement
        }
    }


    /*
     * Update method
     */
    @Nested
    public class ValidateUpdate${endpoint.entityName} {

        @Test
        void shouldUpdate${endpoint.entityName}() throws Exception {
            // TODO: Implement
        }
    }

    /*
     * Delete method
     */
    @Nested
    public class ValidateDelete${endpoint.entityName} {
        @Test
        void shouldDelete${endpoint.entityName}() throws Exception {
            // TODO: Implement
        }
    }
}
