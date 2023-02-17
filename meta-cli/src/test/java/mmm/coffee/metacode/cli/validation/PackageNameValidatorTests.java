/*
 * Copyright 2020 Jon Caulfield
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
package mmm.coffee.metacode.cli.validation;

import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static com.google.common.truth.Truth.assertThat;

/**
 * Test PackageNameValidator
 *
 * The package name validator makes its best effort to ensure the package name
 * is allowed by the Java compiler.  Guidelines can be found online for how one
 * _ought_ to name their packages, such as:
 *
 * https://www.oracle.com/java/technologies/javase/codeconventions-namingconventions.html
 *
 */
class PackageNameValidatorTests {

    /**
     * Check whether the PackageNameValidator detects legal and illegal
     * package names.  At the moment, this test "kicks the tires" and
     * does not try to be an exhaustive test of all package name scenarios.
     */
    @ParameterizedTest
    @CsvSource( value = {
            "org.example.warehouse",    // valid
            "com.example.bookstore",    // valid
            "_abc_.example.com",        // valid
            "abc._example_.com",        // valid
            "abc.lesson01.com",         // valid
            "org.camelCase.foobar",     // packageName may contain upper-case letters
    })
    void shouldAcceptValidPackageNames(String packageName) {
        assertThat( PackageNameValidator.of(packageName).isValid() ).isTrue();
    }

    @ParameterizedTest
    @CsvSource( value = {
            "static.org.example.widget",        // packageName cannot contain a reserved word
            "abstract.org.example.widget",
            "org.example.widget.synchronized",
            "01lesson.example.org",             // packageName cannot start with a digit
            "org-kebob-case"                    // packageName cannot contain dashes
    })
    void shouldDetectInvalidPackageNames(String packageName) {
        assertThat( PackageNameValidator.of(packageName).isValid()).isFalse();
    }

    @Test
    void shouldDetectInvalidPackageName() {
        // a comma is not allowed in the package name
        assertThat(PackageNameValidator.of("org.acme,warehouse").isInvalid()).isTrue();

        // the root package must be alpha
        assertThat(PackageNameValidator.of("123.acme.app").isInvalid()).isTrue();
        assertThat(PackageNameValidator.of("123.acme.app").isValid()).isFalse();

        // the package name cannot be blank
        assertThat(PackageNameValidator.of("").isValid()).isFalse();

        // the package name cannot be null
        assertThat(PackageNameValidator.of(null).isValid()).isFalse();
    }

    @Test
    void shouldDetectValidPackageName() {
        assertThat(PackageNameValidator.of("acme.app").isValid()).isTrue();
        assertThat(PackageNameValidator.of("acme.app").isValid()).isTrue();
        assertThat(PackageNameValidator.of("acme.app.v1").isValid()).isTrue();
    }

    @Test
    void shouldAcceptLongPackageNames() {
        assertThat(PackageNameValidator.of("aa.bb.cc.dd.xx.yy.zz").isValid()).isTrue();
    }

    @Nested
    class IsLowerCaseLetterTests {
        @Test
        void verifyIsLowerCaseLetterEdgeCases() {
            assertThat(PackageNameValidator.isLowerCaseLetter('A')).isFalse();
            assertThat(PackageNameValidator.isLowerCaseLetter('a')).isTrue();
            assertThat(PackageNameValidator.isLowerCaseLetter('z')).isTrue();
            assertThat(PackageNameValidator.isLowerCaseLetter('{')).isFalse();
        }
    }
}
