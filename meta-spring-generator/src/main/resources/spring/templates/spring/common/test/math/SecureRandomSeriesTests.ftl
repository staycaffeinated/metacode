<#include "/common/Copyright.ftl">
package ${project.basePackage}.math;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests of the SecureRandomSeries class
 */
class SecureRandomSeriesTests {

    static SecureRandomSeries randomSeriesUnderTest;

    @BeforeAll
    public static void setUpOneTime() {
        randomSeriesUnderTest = new SecureRandomSeries();
    }

    @Test
    void shouldReturnValueWithRequiredEntropy() {
        String value = randomSeriesUnderTest.nextString();
        assertThat(value).isNotNull();
        assertThat(value.length()).isGreaterThanOrEqualTo(SecureRandomSeries.ENTROPY_STRING_LENGTH);
    }

    /**
     * When an unknown algorithm is specified, the SecureRandomSeries falls back to
     * a default algorithm to ensure the application can continue running.  There's
     * always a chance that a given platform may not implement one of the secure
     * algorithms, hence the fallback to the platforms default secure algorithm.
     */
    @Test
    void shouldRecoverFromNoSuchAlgorithmException() {
        SecureRandomSeries series = new SecureRandomSeries("XYZ");
        assertThat(series).isNotNull();
    }

    /**
     * Verify sending a null argument raises an NPE
     */
    @Test
    void shouldThrowExceptionWhenAlgorithmIsNull() {
        assertThrows(NullPointerException.class, () -> new SecureRandomSeries(null));
    }

    /**
     * Exercise the nextLong() method.  Under the covers, a SecureRandom
     * is used, so its presumed the SecureRandom has been verified by
     * the JRE implementors to ensure randomness. This test verifies
     * the SecureRandomSeries wrapper around the SecureRandom is working.
     */
    @Test
    void shouldReturnRandomLong() {
        assertThat(randomSeriesUnderTest.nextLong()).isNotNull();
    }


    @Test
    void shouldReturnResourceIds() {
        var resourceId = randomSeriesUnderTest.nextResourceId();

        // For the alphanumeric resource Ids, the string length is constant.
        assertThat(resourceId).isNotBlank().hasSize(SecureRandomSeries.ENTROPY_STRING_LENGTH);

        // In our implementation, resourceIds are all digits
        resourceId.chars().forEach(ch -> assertThat(Character.isDigit(ch)));
    }

    @Test
    void whenNumericResourceId_shouldReturnNumericId() {
        var resourceId = randomSeriesUnderTest.nextNumericResourceId();

        // Our numeric resource Ids have an entropy of 160 bits, so can reach a length of 49 digits.
        assertThat(resourceId).isNotBlank().hasSizeBetween(SecureRandomSeries.ENTROPY_STRING_LENGTH, 49);
        resourceId.chars().forEach(ch -> assertThat(Character.isDigit(ch)));
    }
}
