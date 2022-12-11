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
     * Valid resource Ids are 24 characters long. Therefore,
     * - numbers with less than 24 digits are invalid
     * - strings containing characters not found in the cipher alphabet are invalid
     * - empty strings are invalid (appears in a later test)
     * - blank strings are invalid (appears in a later test)
     * - values with less that 24 characters are invalid
     * - values with more than 24 characters are invalid
     */
    @ParameterizedTest
    @CsvSource(value = {
    //   123456789.123456789.1234
        "123456",                       // too short
        "abcd-34843-abkkcuu4-kkuy",     // contains characters not in the cipher alphabet
        "PZ4PD3xVOYNFYMJDOrumUCV",      // 1 character too few
        "NeUDAQPAGP0WbqOB7OHfQTaMM",    // 1 character too many
        "abcd{34843-}ab#![]%kkuyf"      // contains characters not in the cipher alphabet

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
     * Valid resource IDs 24 characters long
     * (from SecureRandomSeries.ENTROPY_STRING_LENGTH).
     */
    @ParameterizedTest
    @CsvSource(value = {
    //   123456789.123456789.1234
        "PZ4PD3xVOYNFYMJDOrumUCVD",
        "TvKziJ2az5OyTxz40lasUAZp",
        "bkVAQFIacs7ey0zHQewIJFBu",
        "8qdOk3M2GHI5daFIanZjOJoN",
        "NeUDAQPAGP0WbqOB7OHfQTaM"
    })
    void shouldDetectValidIds(String candidateId) {
        assertThat(validationUnderTest.isValid(candidateId)).isTrue();
    }

    @Test
    void shouldTreatNullAsInvalid() {
        assertThat(validationUnderTest.isValid(null)).isFalse();
    }
}