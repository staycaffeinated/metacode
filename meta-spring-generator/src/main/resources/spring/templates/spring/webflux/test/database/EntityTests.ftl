<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
* Unit tests of BookEntity
*/
@SuppressWarnings("java:S5838") // false positive from sonarqube
class ${endpoint.ejbName}Tests {

    ${endpoint.ejbName} underTest;
    String resourceId = "12345";

    @BeforeEach
    public void setUp() {
        underTest = new ${endpoint.ejbName}();
        underTest.setResourceId(resourceId);
        underTest.setId(1L);
    }

    @Nested
    class TestEquals {
        @Test
        void whenNullObject_thenReturnsFalse() {
            assertThat(underTest.equals(null)).isFalse();
        }

        @Test
        void whenMatchingResourceId_thenReturnsTrue() {
            ${endpoint.ejbName} sample = new ${endpoint.ejbName}();
            sample.setResourceId(resourceId);
            assertThat(underTest.equals(sample)).isTrue();
        }

        @Test
        void whenSelf_thenReturnsTrue() {
            ${endpoint.ejbName} sample = underTest;
            assertThat(underTest.equals(sample)).isTrue();
        }

        @Test
        @SuppressWarnings("all")
        void whenDifferentClasses_thenReturnsFalse() {
            assertThat(underTest.equals("hello,world")).isFalse();
        }
    }

    @Nested
    class TestHashCode {
        @Test
        void whenEqualObjects_thenReturnsSameHashCode() {
            ${endpoint.ejbName} sample = new ${endpoint.ejbName}();
            sample.setResourceId(resourceId);

            assertThat(sample.hashCode()).isEqualTo(underTest.hashCode());
        }
    }
    
    
	  @Nested
	  class TestCopyMutableFields {
		    @Test
		    void shouldCopyMutableFields() {
			      ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithResourceId();

			      ${endpoint.ejbName} entity = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId();
			      final String immutableId = entity.getResourceId();

			      ${endpoint.ejbName} actual = entity.copyMutableFieldsFrom(pojo);

			      assertThat(actual.getResourceId()).isEqualTo(immutableId);
			      assertThat(actual.getText()).isEqualTo(pojo.getText());
		    }
	  }    
    
}
