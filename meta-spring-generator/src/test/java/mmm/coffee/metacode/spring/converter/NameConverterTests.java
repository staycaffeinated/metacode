/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.converter;

import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * NameConverter unit tests
 */
class NameConverterTests {
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();

    final NameConverter converterUnderTest = new NameConverter();


    @Nested
    class EntityNameSyntaxTests {
        /**
         * Contract: the first character of the entityName must be capitalized.
         * All other characters are left as-is.
         *
         * @param sample the test string to evaluate
         * @param expected the expected outcome
         */
        @ParameterizedTest
        @CsvSource( {
                // test data, expected result
                "foo,       Foo",
                "Foo,       Foo",
                "fooBar,    FooBar"
        } )
        void shouldReturnEntityNameStartingWithUpperCaseLetter(String sample, String expected) {
            assertThat(converterUnderTest.toEntityName(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toEntityName(null));
        }
    }

    @Nested
    class EntityVarNameSyntaxTests {
        /**
         * Contract: the first character of the {@code resource} is converted to lower-case
         * (i.e., uncapitalized). All other characters remain as-is.
         *
         * @param sample the test value to convert
         * @param expected the expected outcome
         */
        @ParameterizedTest
        @CsvSource({
                "FooBar,    fooBar",
                "FOOBAR,    fOOBAR",
                "WineGlass, wineGlass",
                "Wineglass, wineglass"
        })
        void shouldReturnCamelCaseStartingWithLowerCaseLetter(String sample, String expected) {
            assertThat(converterUnderTest.toEntityVariableName(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () ->  converterUnderTest.toEntityVariableName(null));
        }
    }

    @Nested
    class BasePathSyntaxTests {

        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "FooBar,        /foobar",
                "FOOBAR,        /foobar",
                "WineGlass,     /wineglass",
                "Wineglass,     /wineglass",
                "/beer,         /beer",
                "/WineGlass,    /wineglass",
                "Wine-Glass,    /wine-glass"
        })
        void shouldReturnBasePathWithStartingSlashAndLowerCase(String sample, String expected) {
            assertThat(converterUnderTest.toBasePathUrl(sample)).isNotNull();
            assertThat(converterUnderTest.toBasePathUrl(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toBasePathUrl(null));
        }
    }

    @Nested
    class SchemaSyntaxTests {
        @Test
        void shouldDisallowNullArgument() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toDatabaseSchemaName(null));
        }

        /**
         * Our current convention is to make schema names lower-case
         */
        @Test
        void shouldReturnSchemaName() {
            assertThat(converterUnderTest.toDatabaseSchemaName("camelCase")).isEqualTo("camelcase");
            assertThat(converterUnderTest.toDatabaseSchemaName("UPPER_CASE")).isEqualTo("upper_case");
            assertThat(converterUnderTest.toDatabaseSchemaName("")).isEqualTo("");
            assertThat(converterUnderTest.toDatabaseSchemaName("lower-case")).isEqualTo("lower-case");
        }
    }

    @Nested
    class Test_getPackageNameForResource {
        @Test
        void shouldTranslatePackageNameToItsEquivalentFilePath() {
            String value = converterUnderTest.toEndpointPackageName("org.example", "Account");
            assertThat(value).isNotEmpty();

            // per the naming convention, it should be this:
            assertThat(value).isEqualTo("org.example.endpoint.account");
        }

        @Test
        void shouldDisallowNullBasePackage() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toEndpointPackageName(null, "widget"));
        }
        @Test
        void shouldDisallowNullResourceName() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toEndpointPackageName("org.example", null));
        }
    }
}
