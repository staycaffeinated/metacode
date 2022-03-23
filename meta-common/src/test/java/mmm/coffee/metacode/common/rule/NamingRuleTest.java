/*
 * Copyright 2022 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.common.rule;

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
 * Unit tests
 */
public class NamingRuleTest {
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();


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
            assertThat(NamingRule.toEntityName(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> NamingRule.toEntityName(null));
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
            assertThat(NamingRule.toEntityVariableName(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () ->  NamingRule.toEntityVariableName(null));
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
            assertThat(NamingRule.toBasePathUrl(sample)).isNotNull();
            assertThat(NamingRule.toBasePathUrl(sample)).isEqualTo(expected);
        }

        @Test
        void shouldThrowNullPointerExceptionWhenArgIsNull() {
            assertThrows(NullPointerException.class, () -> NamingRule.toBasePathUrl(null));
        }
    }

    @Nested
    class SchemaSyntaxTests {
        @Test
        void shouldDisallowNullArgument() {
            assertThrows(NullPointerException.class, () -> NamingRule.toDatabaseSchemaName(null));
        }

        /**
         * Our current convention is to make schema names lower-case
         */
        @Test
        void shouldReturnSchemaName() {
            assertThat(NamingRule.toDatabaseSchemaName("camelCase")).isEqualTo("camelcase");
            assertThat(NamingRule.toDatabaseSchemaName("UPPER_CASE")).isEqualTo("upper_case");
            assertThat(NamingRule.toDatabaseSchemaName("")).isEqualTo("");
            assertThat(NamingRule.toDatabaseSchemaName("lower-case")).isEqualTo("lower-case");
        }
    }

    @Nested
    class Test_getPackageNameForResource {
        @Test
        void shouldTranslatePackageNameToItsEquivalentFilePath() {
            String value = NamingRule.toEndpointPackageName("org.example", "Account");
            assertThat(value).isNotEmpty();

            // per the naming convention, it should be this:
            assertThat(value).isEqualTo("org.example.endpoint.account");
        }

        @Test
        void shouldDisallowNullBasePackage() {
            assertThrows(NullPointerException.class, () -> NamingRule.toEndpointPackageName(null, "widget"));
        }
        @Test
        void shouldDisallowNullResourceName() {
            assertThrows(NullPointerException.class, () -> NamingRule.toEndpointPackageName("org.example", null));
        }
    }

    @Nested
    class BasePackagePathTests {
        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "org.acme.petstore, org/acme/petstore",
                "acme,              acme",
                "acme.petstore,     acme/petstore"
        })
        void shouldConvertJavaPackageToFileSystemPath(String testValue, String expectedResult) {
            assertThat(NamingRule.toBasePackagePath(testValue)).isEqualTo(expectedResult);
        }

        @Test
        void shouldThrowExceptionWhenArgumentIsNull() {
            assertThrows(NullPointerException.class, () -> NamingRule.toBasePackagePath(null));
        }
    }

    @Nested
    class PojoClassNameTests {
        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "pet, Pet",
                "owner, Owner",
                "accountNumber, AccountNumber"
        })
        void shouldConvertJavaPackageToFileSystemPath(String testValue, String expectedResult) {
            assertThat(NamingRule.toPojoClassName(testValue)).isEqualTo(expectedResult);
        }
    }

    @Nested
    class EjbClassNameTests {
        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "pet, PetEntityBean",
                "petOwner, PetOwnerEntityBean",
                "account, AccountEntityBean"
        })
        void shouldConvertJavaPackageToFileSystemPath(String testValue, String expectedResult) {
            assertThat(NamingRule.toEjbClassName(testValue)).isEqualTo(expectedResult);
        }
    }

    @Nested
    class TableNameTests {
        @ParameterizedTest
        @CsvSource({
                // testValue,   expectedResult
                "pet, pet",
                "petOwner, petOwner",
                "account, account"
        })
        void shouldConvertJavaPackageToFileSystemPath(String testValue, String expectedResult) {
            assertThat(NamingRule.toTableName(testValue)).isEqualTo(expectedResult);
        }
    }

    @Nested
    class EntityNameUpperCaseTests {
        @ParameterizedTest
        @CsvSource({
                // actual,   expected
                "pet,           PET",
                "owner,         OWNER",
                "accountNumber, ACCOUNTNUMBER"
        })
        void shouldReturnUpperCaseString(String actual, String expected) {
            assertThat(NamingRule.toEntityNameUpperCase(actual)).isEqualTo(expected);
        }

        @Test
        void shouldThrowExceptionWhenArgumentIsNull() {
            assertThrows(NullPointerException.class, () -> NamingRule.toEntityNameUpperCase(null));
        }
    }
}
