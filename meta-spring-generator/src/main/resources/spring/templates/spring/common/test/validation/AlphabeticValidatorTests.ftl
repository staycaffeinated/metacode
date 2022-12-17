<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.Mock;

import jakarta.validation.ConstraintValidatorContext;

import static org.assertj.core.api.Assertions.assertThat;


/**
 * Unit tests of AlphabeticValidation
 */
class AlphabeticValidatorTests {

    AlphabeticValidator validationUnderTest = new AlphabeticValidator();

    @Mock
    ConstraintValidatorContext mockContext;

    @Nested
    class PositiveTestCases {
        @Test
        void shouldAllowAlphabetic() {
            assertThat(validationUnderTest.isValid("SampleValue", mockContext)).isTrue();
        }
    }

    @Nested
    class NegativeTestCases {

        @Test void shouldNotAllowNull() {
            assertThat ( validationUnderTest.isValid(null, mockContext)).isFalse();
        }

        @Test void shouldNotAllowAlphanumeric() {
            assertThat(validationUnderTest.isValid("abc123DEF", mockContext)).isFalse();
        }

        @Test void shouldNotAllowKebabCase() {
            assertThat(validationUnderTest.isValid("abc-123-def-xyzzy", mockContext)).isFalse();
        }

        @Test void shouldNotAllowSnakeCase() {
            assertThat(validationUnderTest.isValid("abc_123_def_xyzzy", mockContext)).isFalse();
        }

        @Test void shouldNotAllowNumeric() {
            assertThat(validationUnderTest.isValid("12345", mockContext)).isFalse();
        }
    }
}
