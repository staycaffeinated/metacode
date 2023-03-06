<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.*;

/**
* Unit tests
*/
@SuppressWarnings("all")
public class ${endpoint.entityName}DocumentTests {

    private ${endpoint.documentName} objectUnderTest;

    @BeforeEach
    void setUp() {
        objectUnderTest = ${endpoint.entityName}DocumentTestFixtures.getSampleOne();
    }

    @Nested
    class EqualsMethod {
        @Test
        void shouldEqualItself() {
            assertThat(objectUnderTest.equals(objectUnderTest)).isTrue();
        }

        @Test
        void shouldNotEqualNull() {
            assertThat(objectUnderTest.equals(null)).isFalse();
        }

        @Test // for code coverage
        void shouldNotBeEqualWhenResourceIdsAreDifferent() {
            // This test merely exercises a branch within the equals method;
            ${endpoint.documentName} other = ${endpoint.entityName}DocumentTestFixtures.getSampleTwo();
            assertThat(objectUnderTest.getResourceId()).isNotEqualTo(other.getResourceId());
            assertThat(objectUnderTest.equals(other)).isFalse();
        }

        @Test
        void shouldNotEqualOtherClasses() {
            ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.sampleOne();
            assertThat(objectUnderTest.equals(pojo)).isFalse();
        }

        @Test
        void shouldBeEqual() {
            ${endpoint.documentName} that = ${endpoint.entityName}DocumentTestFixtures.getSampleOne();
            assertThat(objectUnderTest.equals(that)).isTrue();
        }
    }

    @Nested
    class HashCodeMethod {
        @Test
        void shouldComputeHashCode() {
            assertThat(objectUnderTest.hashCode()).isBetween(Integer.MIN_VALUE, Integer.MAX_VALUE);
        }

        @Test
        void shouldYieldSameHashCode() {
            final String randomId = "abc12345XYZ";
            ${endpoint.documentName} sampleOne = ${endpoint.entityName}DocumentTestFixtures.copyOf(${endpoint.entityName}DocumentTestFixtures.getSampleOne());
            sampleOne.setResourceId(randomId);

            ${endpoint.documentName} sampleTwo = ${endpoint.entityName}DocumentTestFixtures.copyOf(${endpoint.entityName}DocumentTestFixtures.getSampleTwo());
            sampleTwo.setResourceId(randomId);

            assertThat(sampleOne.hashCode()).isEqualTo(sampleTwo.hashCode());
        }
    }
}

