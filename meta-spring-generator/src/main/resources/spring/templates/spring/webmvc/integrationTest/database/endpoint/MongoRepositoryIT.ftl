<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

<#if (endpoint.isWithTestContainers())>
import ${endpoint.basePackage}.database.MongoDbContainerTests;
<#else>
import ${endpoint.basePackage}.database.DatabaseConfiguration;
</#if>
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
<#if (!endpoint.isWithTestContainers())>
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
</#if>
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * These tests verify custom queries added to the repository. If the JPA queries
 * are not modified, or not custom methods are added to the Repository class,
 * these tests may be deleted.
 */
@DataMongoTest
@SuppressWarnings("all")
<#if (endpoint.isWithTestContainers())>
class ${endpoint.entityName}RepositoryIT extends MongoDbContainerTests {
<#else>
class ${endpoint.entityName}RepositoryIT {
</#if>

    @Autowired
    private ${endpoint.entityName}Repository repositoryUnderTest;

    // Generates the public identifier of an entity
    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    // Increment for rowIds in the database
    private long rowId = 0;

<#if (!endpoint.isWithTestContainers())>
    @DynamicPropertySource
    static void setProperties(DynamicPropertyRegistry registry) {
        DatabaseConfiguration.registerDatabaseProperties(registry);
    }
</#if>

    @BeforeEach
    void insertTestData() {
        // Might want to refactor to use TestFixtures
        repositoryUnderTest.save(new${endpoint.entityName}Document("First value"));
        repositoryUnderTest.save(new${endpoint.entityName}Document("Second value"));
        repositoryUnderTest.save(new${endpoint.entityName}Document("Third value"));
    }

    @AfterEach
    public void tearDownEachTime() {
        repositoryUnderTest.deleteAll();
    }

    /*
     * Test custom methods.
     * <p>
     * Its worth testing custom queries to ensure the query semantics are correct;
     * simply having proper syntax does not ensure the records that _should_ be
     * returned are the one's being returned.
     * </p>
     * The scope of these tests is verify the semantics of custom JPA queries added
     * to the Repository interface. The repository methods that are available
     * out-of-the-box, such as findById, do not need to be tested. Its entirely
     * possible that this test class can be removed altogether.
     */
    @Nested
    public class ValidateCustomMethod {
        /**
         * This is an example test. You do not actually need to verify the findAll
         * method. This test is only an example of how you might want to write such a
         * test.
         */
        @Test
        void testFindAll() throws Exception {
            List<${endpoint.documentName}> page = repositoryUnderTest.findAll();

            assertThat(page).isNotNull();
            assertThat(page.size()).isGreaterThan(0);
        }
    }

    // ------------------------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ------------------------------------------------------------------------------------------------------------

    private ${endpoint.documentName} new${endpoint.entityName}Document(final String value) {
        return ${endpoint.documentName}.builder().text(value).resourceId(randomSeries.nextResourceId()).build();
    }
}

