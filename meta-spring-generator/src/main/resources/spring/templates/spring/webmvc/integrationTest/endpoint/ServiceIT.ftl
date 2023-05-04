<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.common.AbstractIntegrationTest;
import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.junit.jupiter.api.*;
<#if endpoint.isWithPostgres() && endpoint.isWithTestContainers()>
import org.springframework.boot.test.context.SpringBootTest;
</#if>
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
<#if endpoint.isWithPostgres() && endpoint.isWithTestContainers()>
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
</#if>

<#if endpoint.isWithPostgres() && endpoint.isWithTestContainers()>
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ${endpoint.entityName}ServiceIT extends PostgresContainerTests {
<#else>
class ${endpoint.entityName}ServiceIT extends AbstractIntegrationTest {
</#if>
    @Autowired
    private ${endpoint.entityName}Repository ${endpoint.entityVarName}Repository;

    @Autowired
    private ${endpoint.entityName}DataStore ${endpoint.entityVarName}DataStore;

    private ${endpoint.entityName}ServiceProvider serviceUnderTest;

    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        DatabaseInitFunction.registerDatabaseProperties(registry);
    }

    @BeforeEach
    void init${endpoint.entityName}Service() {
        serviceUnderTest = new ${endpoint.entityName}ServiceProvider(${endpoint.entityVarName}DataStore);
    }

    @BeforeEach
    void insertTestData() {
        ${endpoint.entityVarName}Repository.saveAll(${endpoint.ejbName}TestFixtures.allItems());
    }

    @AfterEach
    void deleteTestData() {
        ${endpoint.entityVarName}Repository.deleteAll();
    }

    /*
     * FindById
     */
    @Nested
    public class FindByResourceId {
        @Test
        @SuppressWarnings("all")
        void shouldFind${endpoint.entityName}ById() throws Exception {
            // given: the public ID of an item known to be in the database
            String expectedId = ${endpoint.entityName}EntityTestFixtures.allItems().get(0).getResourceId();

            // when: the service is asked to find the item
            Optional<${endpoint.pojoName}> optional = serviceUnderTest.find${endpoint.entityName}ByResourceId(expectedId);

            // expect: the item is found, and has the ID that's expected
            assertThat(optional).isNotNull().isPresent();
            assertThat(optional.get().getResourceId()).isEqualTo(expectedId);
        }
    }

    /*
     * Create method
     */
    @Nested
    public class Create${endpoint.entityName} {
        @Test
        void shouldCreateNew${endpoint.entityName}() throws Exception {
            // given: a new item to be inserted into the database
            ${endpoint.pojoName} expected = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();

            // when: the service is asked to create the item
            ${endpoint.pojoName} actual = serviceUnderTest.create${endpoint.entityName}(expected);

            // expect: the item is added, and its returned, along with the ID newly assigned to it
            assertThat(actual).isNotNull().hasNoNullFieldsOrProperties();
            assertThat(actual.getResourceId()).isNotBlank().isNotEmpty();
        }
    }


    /*
     * Update method
     */
    @Nested
    public class Update${endpoint.entityName} {

        @Test
        @SuppressWarnings("all")
        void shouldUpdate${endpoint.entityName}() throws Exception {
            String resourceId = ${endpoint.ejbName}TestFixtures.allItems().get(0).getResourceId();
            ${endpoint.pojoName} modified = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
            final String newValue = "modified";
            modified.setText(newValue);
            modified.setResourceId(resourceId); // use and ID known to exit

            Optional<${endpoint.pojoName}> option = serviceUnderTest.update${endpoint.entityName}(modified);

            assertThat(option).isNotNull().isPresent();
            assertThat(option.get().getResourceId()).isEqualTo(resourceId);
            assertThat(option.get().getText()).isEqualTo(newValue);
        }
    }

    /*
     * Delete method
     */
    @Nested
    public class Delete${endpoint.entityName} {
        @Test
        void shouldDelete${endpoint.entityName}() throws Exception {
            // given: the ID of an item known to exist in the database
            String knownId = ${endpoint.ejbName}TestFixtures.allItems().get(2).getResourceId();

            // given: the service is asked to delete the item
            serviceUnderTest.delete${endpoint.entityName}ByResourceId(knownId);

            // expect: a subsequent attempt to find the deleted item comes back empty
            Optional<${endpoint.pojoName}> option = serviceUnderTest.find${endpoint.entityName}ByResourceId(knownId);
            assertThat(option).isNotNull().isNotPresent();
        }
    }
}
