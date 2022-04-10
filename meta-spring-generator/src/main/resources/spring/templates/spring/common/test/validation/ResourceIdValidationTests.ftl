<#include "/common/Copyright.ftl">
package ${project.basePackage}.validation;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.Assertions.assertThat;

/**
* Unit tests of ResourceIdValidation
*/
class ResourceIdValidationTests {

    final ResourceIdValidation validationUnderTest = new ResourceIdValidation();

    /**
     * Valid resource Ids are 48 or 49 digits long. Therefore,
     * - numbers with less than 48 digits are invalid
     * - alpha-numeric strings are invalid
     * - empty strings are invalid
     * - blank strings are invalid
     * - values with less that 48 digits are invalid
     * - values with more than 49 digits are invalid
     */
    @ParameterizedTest
    @CsvSource(value = {
        "123456",                                               // too short
        "abcd-34843-abkkcuu4-kkuy4f",                           // alpha-numeric string
        "",                                                     // empty string
        "     ",                                                // blank string
        "85637831860933685547972368108919006136931041174",      // 1 digit too few
        "14286950711364307339524413794755217854384596615030",   // 1 digit too manu
    })
    void shouldDetectInvalidIds(String candidateId) {
        assertThat(validationUnderTest.isValid(candidateId, null)).isFalse();
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