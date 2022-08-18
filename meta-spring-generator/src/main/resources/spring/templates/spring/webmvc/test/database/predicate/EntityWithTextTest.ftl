<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.predicate;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mockito;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

/**
 * Unit tests
 */
public class ${endpoint.entityName}WithTextTest {

    private final CriteriaBuilder mockCB = Mockito.mock(CriteriaBuilder.class);
    private final Predicate mockPredicate = Mockito.mock(Predicate.class);

    private final Root<?> mockRoot = Mockito.mock(Root.class);
    private final CriteriaQuery<?> mockQuery = Mockito.mock(CriteriaQuery.class);

    @BeforeEach
    public void mockUpCriteriaBuilder() {
        given(mockCB.isTrue(any())).willReturn(mockPredicate);
        given(mockCB.like(any(), anyString())).willReturn(mockPredicate);
    }

    @Nested
    class ConstructorTests {
        @Test
        void shouldConvertTextToLowerCase() {
            final String text = "SOMETHING";
            ${endpoint.entityName}WithText spec = new ${endpoint.entityName}WithText(text);
            assertThat(spec.getText()).contains(text.toLowerCase());
        }

        @Test
        void shouldSupportNullText() {
            ${endpoint.entityName}WithText spec = new ${endpoint.entityName}WithText(null);
            assertThat(spec.getText()).isNull();
        }

        @Test
        void shouldSupportEmptyString() {
            ${endpoint.entityName}WithText spec = new ${endpoint.entityName}WithText("");
            assertThat(spec.getText()).isEqualTo("");
        }
    }

    @Nested
    @SuppressWarnings("unchecked")
    /**
     * These tests only confirm branch coverage. Integration tests
     * in the controller verify the returned Predicate provides the correct semantics.
     */
    class ToPredicateTests {
        @ParameterizedTest
        @NullAndEmptySource
        @ValueSource(strings = {"something", "SOMETHING"})
        void shouldReturnPredicateForAnyText(String source) {
            ${endpoint.entityName}WithText spec = new ${endpoint.entityName}WithText(source);
            Predicate actual = spec.toPredicate((Root<${endpoint.ejbName}>) mockRoot, mockQuery, mockCB);
            assertThat(actual).isNotNull();
        }
    }
}