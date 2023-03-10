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

    @Nested
    class PackageNameToPathTests {

        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "org.acme,          org/acme",
                "org.acme.petstore, org/acme/petstore",
                "acme,              acme"
        })
        void shouldReturnEquivalentPath(String sample, String expected) {
            assertThat(converterUnderTest.packageNameToPath(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.packageNameToPath(null));
        }
    }

    @Nested
    class ToLowerCaseEntityNameTests {
        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "Pet,       pet",
                "PET,       pet",
                "pet,       pet",
                "PetStore,  petstore"
        })
        void shouldBeLowerCaseValue(String testValue, String expectedValue) {
            assertThat(converterUnderTest.toLowerCaseEntityName(testValue)).isEqualTo(expectedValue);
        }

        @Test
        void shouldThrowNpeWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toLowerCaseEntityName(null));
        }
    }

    @Nested
    class ToPojoClassNameTests {
        @ParameterizedTest
        @CsvSource({
                // testValue, classname for its POJO class
                "Pet,       Pet",
                "PET,       PET",
                "pet,       Pet",
                "PetStore,  PetStore"
        })
        void shouldConvertSuccessfully(String resourceName, String expectedValue) {
            assertThat(converterUnderTest.toPojoClassName(resourceName)).isEqualTo(expectedValue);
        }

        @Test
        void shouldThrowNpeWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toPojoClassName(null));
        }
    }

    @Nested
    class ToTableNameTests {
        @ParameterizedTest
        @CsvSource({
                // resourceName,   tableName
                "Pet,               Pet",
                "PET,               PET",
                "pet,               pet",
                "PetStore,          PetStore"
        })
        void shouldConvertSuccessfully(String resourceName, String expectedValue) {
            assertThat(converterUnderTest.toTableName(resourceName)).isEqualTo(expectedValue);
        }

        @Test
        void shouldThrowNpeWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toTableName(null));
        }
    }

    @Nested
    class ToEjbClassNameTests {
        @ParameterizedTest
        @CsvSource({
                // resourceName,   ejbClassName
                "Pet,               PetEntity",
                "PET,               PETEntity",
                "pet,               PetEntity",
                "PetStore,          PetStoreEntity"
        })
        void shouldConvertSuccessfully(String resourceName, String expectedValue) {
            assertThat(converterUnderTest.toEjbClassName(resourceName)).isEqualTo(expectedValue);
        }

        @Test
        void shouldThrowNpeWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toEjbClassName(null));
        }
    }

    @Nested
    class ToDocumentClassNameTests {
        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "pet,          PetDocument",
                "Pet,          PetDocument",
                "acme,         AcmeDocument"
        })
        void shouldHappilyConvertValue(String actual, String expected) {
            assertThat(converterUnderTest.toDocumentClassName(actual)).isEqualTo(expected);
        }

        @Test
        @SuppressWarnings("all")
        void shouldThrowException() {
            assertThrows(NullPointerException.class, () -> converterUnderTest.toDocumentClassName(null));
        }
    }
}
