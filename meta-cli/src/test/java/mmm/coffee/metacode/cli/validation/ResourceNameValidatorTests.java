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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
class ResourceNameValidatorTests {

    @ParameterizedTest
    @ValueSource( strings = { "Employee", "employee", "Test_Suite", "xyzzy", "_foobar", "$foo" })
    void shouldRecognizeValidIdentifiers(String identifier) {
        assertThat( ResourceNameValidator.isValid(identifier)).isTrue();
    }

    @ParameterizedTest
    @ValueSource( strings = { "abstract", "null", "const", "float", "'Hello'", "/Hello" })
    void shouldRecognizeInvalidIdentifiers(String identifier) {
        assertThat( ResourceNameValidator.isValid(identifier)).isFalse();
    }

    @Test
    void shouldRecognizeReservedWord() {
        assertThat(ResourceNameValidator.isValid("static")).isFalse();
    }

    @Test
    void shouldRecognizeUnreservedWord() {
        assertThat(ResourceNameValidator.isValid("widget")).isTrue();
    }

    @Test
    void shouldRejectNullValues() {
        assertThat(ResourceNameValidator.isValid(null)).isFalse();
    }

    @Test
    void shouldRejectEmptyString() {
        assertThat(ResourceNameValidator.isValid("")).isFalse();
    }

    @Test
    void shouldRejectInvalidClassNames() {
        assertThat(ResourceNameValidator.isValid("Treasure#!Map")).isFalse();
    }
}