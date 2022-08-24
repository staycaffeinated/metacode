<#include "/common/Copyright.ftl">
package ${project.basePackage};

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;


import static org.assertj.core.api.Assertions.assertThat;

/**
 * This test admittedly does nothing. For a Spring Boot project
 * (one that does not leverage Spring WebMVC or Spring Webflux),
 * the code generator has no context for generating a meaningful unit test.
 * For the sake of having a starting point, this place-holder test is generated.
 */
class PlaceholderTest {

    @ParameterizedTest
    @CsvSource(value = {
        "2022-31-12",
        "2022-Apr-01",
        "2021"
    })
    void testStringValues(String givenValue) {
        assertThat(givenValue).isNotEmpty();
    }
}
