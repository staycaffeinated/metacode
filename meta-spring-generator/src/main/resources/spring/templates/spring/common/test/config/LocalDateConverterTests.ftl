<#include "/common/Copyright.ftl">
package ${project.basePackage}.config;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DateTimeException;
import java.time.LocalDate;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
* LocalDateConverterTests
*/
class LocalDateConverterTests {

LocalDateConverter converterUnderTest = new LocalDateConverter();

    @Test
    void testNullArgument() {
        assertThrows(NullPointerException.class, () -> converterUnderTest.convert(null));
    }

    @Test
    void testBlankArgument() {
        assertThrows(DateTimeException.class, () -> converterUnderTest.convert(""));
        assertThrows(DateTimeException.class, () -> converterUnderTest.convert("     "));
    }

    @ParameterizedTest
    @CsvSource(value = {
        "2022-31-12", // yyyy-dd-MM is not a supported format
        "2022-Apr-01",
        "2021" // the year and nothing else is not allowed
        })
    void testInvalidFormats(String invalidDates) {
        // Invalid formats should raise DateTimeException
        assertThrows(DateTimeException.class, () -> converterUnderTest.convert(invalidDates));
    }

    /*
     * Valid formats are: dd-MM-yyyy", "yyyy-MM-dd"
     */
    @ParameterizedTest
    @CsvSource(value = {
        "31-12-2022",
        "2022-12-31",
        "28-02-2022",
        "2022-02-28"
        })
    void testValidFormats(String candidate) {
        LocalDate converted = converterUnderTest.convert(candidate);

        // Since all the test dates are for 2022, check the year
        assertThat(converted.getYear()).isEqualTo(2022);
    }
}
