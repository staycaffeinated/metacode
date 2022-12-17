<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EmptySource;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;

import jakarta.validation.ConstraintValidatorContext;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;


/**
 * Unit tests of SearchTextValidator
 */
class SearchTextValidatorTests {
    SearchTextValidator validationUnderTest = new SearchTextValidator();

    @Mock
    ConstraintValidatorContext mockContext;

    @Nested
    class PositiveTestCases {
        @ParameterizedTest
        @ValueSource(strings = {"something", "SOMETHING"})
        @EmptySource
        void shouldAllowAlphabetic(String candidateText) {
            assertThat(validationUnderTest.isValid(Optional.of(candidateText), mockContext)).isTrue();
        }
    }

    @Nested
    class NegativeTestCases {

        @ParameterizedTest
        @ValueSource(strings = {
            "192.168.0.1",	// contains non-alpha characters
            "supercalifragilisticexpialidocious"  // too long
        })
        void shouldNotAllowInvalidText(String candidateText) {
            assertThat(validationUnderTest.isValid(Optional.of(candidateText), mockContext)).isFalse();
        }

        @Test
        void shouldNotAllowNull() {
            assertThrows(NullPointerException.class, () -> validationUnderTest.isValid(null, mockContext));
        }
    }

}
