<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
* Unit tests of ResourceIdValidator
*/
class ResourceIdValidatorTests {

    final ResourceIdValidator validationUnderTest = new ResourceIdValidator();

    /**
     * Valid resource Ids are 27 characters long (from from SecureRandomSeries.ENTROPY_STRING_LENGTH).
     * Therefore,
     * - numbers with less than 27 digits are invalid
     * - strings containing characters not found in the cipher alphabet are invalid
     * - empty strings are invalid (appears in a later test)
     * - blank strings are invalid (appears in a later test)
     * - values with less that 27 characters are invalid
     * - values with more than 27 characters are invalid
     */
    @ParameterizedTest
    @CsvSource(value = {
    //   123456789.123456789.1234567
        "123456",                           // too short
        "abcd-34843-abkkcuu4-kkuyzu4",      // contains characters not in the cipher alphabet
        "PZ4PD3xVOYNFYMJDOrumUCV44",        // 1 character too few
        "NeUDAQPAGP0WbqOB7OHfQTaMM1234",    // 1 character too many
        "abcd{34843-}ab#![]%kkuyf$1234"     // contains characters not in the cipher alphabet

    })
    void shouldDetectInvalidIds(String candidateId) {
        assertThat(validationUnderTest.isValid(candidateId, null)).isFalse();
    }

    /**
     * We have a separate tests for the empty string and blank string because, when the empty string
     * is added to the above ParameterizedTest, these two errors occurs:
     * 1) org.junit.platform.commons.PreconditionViolationException: Record at index 3 contains invalid CSV: ""
     * 2) org.junit.platform.commons.PreconditionViolationException: Configuration error:
     *     You must configure at least one set of arguments for this @ParameterizedTest
     */
    @Test
    void shouldDetectEmptyStringAsInvalid() {
        assertThat(validationUnderTest.isValid("", null)).isFalse();
    }

    @Test
    void shouldDetectBlankStringAsInvalid() {
        assertThat(validationUnderTest.isValid("     ", null)).isFalse();
    }

    /**
     * Valid resource IDs 27 characters long
     * (from SecureRandomSeries.ENTROPY_STRING_LENGTH).
     */
    @ParameterizedTest
    @CsvSource(value = {
    //   123456789.123456789.1234567
        "PZ4PD3xVOYNFYMJDOrumUCVDuvi",
        "TvKziJ2az5OyTxz40lasUAZpxyz",
        "bkVAQFIacs7ey0zHQewIJFBu125",
        "8qdOk3M2GHI5daFIanZjOJoNu22",
        "NeUDAQPAGP0WbqOB7OHfQTaMB45"
    })
    void shouldDetectValidIds(String candidateId) {
        assertThat(validationUnderTest.isValid(candidateId)).isTrue();
    }

    @Test
    void shouldTreatNullAsInvalid() {
        assertThat(validationUnderTest.isValid(null)).isFalse();
    }
}