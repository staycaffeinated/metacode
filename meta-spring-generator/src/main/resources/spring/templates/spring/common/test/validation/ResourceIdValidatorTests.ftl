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
     * Valid resource Ids are 48 or 49 digits long. Therefore,
     * - numbers with less than 48 digits are invalid
     * - alpha-numeric strings are invalid
     * - empty strings are invalid (appears in a later test)
     * - blank strings are invalid (appears in a later test)
     * - values with less that 48 digits are invalid
     * - values with more than 49 digits are invalid
     */
    @ParameterizedTest
    @CsvSource(value = {
        "123456",                                               // too short
        "abcd-34843-abkkcuu4-kkuy4f",                           // alpha-numeric string
        "85637831860933685547972368108919006136931041174",      // 1 digit too few
        "14286950711364307339524413794755217854384596615030",   // 1 digit too manu
        "abcdefghijklmnopqrstuvwxyzabcdefghijklmnopqrstuvw",    // check against all characters, not digits
        "ABCDEFGHIJKLMNOPQRSTUVWXYZABCDEFGHIJKLMNOPQRSTUVW"     // another check of all characters, not digits
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
     * Valid resource IDs are either 48 or 49 digits long.
     */
    @ParameterizedTest
    @CsvSource(value = {
        "1428695071136430733952441379475521785438459661503",
        "856378318609336855479723681089190061369310411743",
        "761298199457596918940941480681377710621764943581",
        "1037502964891443764016773433562418332624623306209",
        "869805249448410994558598733914144074320919938157"
    })
    void shouldDetectValidIds(String candidateId) {
        assertThat(validationUnderTest.isValid(candidateId)).isTrue();
    }

    @Test
    void shouldTreatNullAsInvalid() {
        assertThat(validationUnderTest.isValid(null)).isFalse();
    }
}