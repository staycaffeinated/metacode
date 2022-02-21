<#include "/common/Copyright.ftl">
package ${project.basePackage}.math;


import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests of the SecureRandomSeries class
 */
class SecureRandomSeriesTests {

    private static final int ENTROPY_MINIMUM_STRING_LENGTH = 27;

    @Test
    void shouldReturnValueWithRequiredEntropy() {
        String value = SecureRandomSeries.nextString();
        assertThat(value).isNotNull();
        assertThat(value.length()).isGreaterThanOrEqualTo(ENTROPY_MINIMUM_STRING_LENGTH);
    }
}
