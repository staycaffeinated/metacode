<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.predicate.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import org.junit.jupiter.api.*;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.*;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.DynamicPropertyRegistry;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
<#if endpoint.isWithPostgres() && endpoint.isWithTestContainers()>
import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT;
</#if>
/**
 * These tests verify custom queries added to the repository.
 * If the JPA queries are not modified, or not custom methods are added to the
 * Repository class, these tests may be deleted.
 */
<#if endpoint.isWithPostgres() && endpoint.isWithTestContainers()>
@SpringBootTest(webEnvironment = RANDOM_PORT)
class ${endpoint.entityName}RepositoryIT extends PostgresContainerTests {
<#else>
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
class ${endpoint.entityName}RepositoryIT {
</#if>
    @Autowired
    private ${endpoint.entityName}Repository repositoryUnderTest;

    // Generates the public identifier of an entity
    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    // Increment for rowIds in the database
    private long rowId = 0;


    @DynamicPropertySource
    static void registerProperties(DynamicPropertyRegistry registry) {
        DatabaseInitFunction.registerDatabaseProperties(registry);
    }

    @BeforeEach
    void insertTestData() {
        repositoryUnderTest.save(new${endpoint.ejbName}("First value"));
        repositoryUnderTest.save(new${endpoint.ejbName}("Second value"));
        repositoryUnderTest.save(new${endpoint.ejbName}("Third value"));
    }

    @AfterEach
    public void tearDownEachTime() {
        repositoryUnderTest.deleteAll();
    }

    /*
     * Test custom methods.
     *
     * Its worth testing custom queries to ensure the query semantics are correct;
     * simply having proper syntax does not ensure the records that _should_ be
     * returned are the one's being returned.
     *
     * The scope of these tests is verify the semantics of custom JPA queries added
     * to the Repository interface. The repository methods that are available out-of-the-box,
     * such as findById, do not need to be tested. Its entirely possible that this test class
     * can be removed altogether.
     */
    @Nested
    public class ValidateCustomMethod {
        /**
         * This is an example test. You do not actually need to verify the findAll method.
         * This test is only an example of how you might want to write such a test.
         */
        @Test
        void testFindAll() throws Exception {
            Pageable pageable = PageRequest.of(0, 10);
            Page<${endpoint.ejbName}> page = repositoryUnderTest.findAll(pageable);

            assertThat(page).isNotNull();
            assertThat(page.hasContent()).isTrue();
        }
    }

    @Nested
    public class ValidatePredicates {
        @Test
        void shouldIgnoreCase() {
            ${endpoint.entityName}WithText spec = new ${endpoint.entityName}WithText("first value");
            List<${endpoint.ejbName}> list = repositoryUnderTest.findAll(spec);
            assertThat(list).isNotNull();
            assertThat(list.size()).isEqualTo(1);
        }

        @Test
        void shouldFindAllWhenValueIsEmpty() {
            ${endpoint.entityName}WithText spec = new ${endpoint.entityName}WithText("");
            List<${endpoint.ejbName}> list = repositoryUnderTest.findAll(spec);
            assertThat(list).isNotNull();
            assertThat(list.size()).isEqualTo(3);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ------------------------------------------------------------------------------------------------------------

    private ${endpoint.ejbName} new${endpoint.ejbName}(final String value)  {
        return new ${endpoint.ejbName}(++rowId, randomSeries.nextResourceId(), value);
    }

}
