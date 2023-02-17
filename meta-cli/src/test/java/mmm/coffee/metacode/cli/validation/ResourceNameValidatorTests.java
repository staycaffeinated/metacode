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
        assertThat( ResourceNameValidator.of(identifier).isValid()).isTrue();
    }

    @ParameterizedTest
    @ValueSource( strings = { "abstract", "null", "const", "float", "'Hello'", "/Hello" })
    void shouldRecognizeInvalidIdentifiers(String identifier) {
        assertThat( ResourceNameValidator.of(identifier).isValid()).isFalse();
    }

    /**
     * Some class names are not allowed because they lead to compile-time errors.
     * Case in point, a class named 'Test' will end up conflicting with JUnit's 'Test' class
     * and the generated code will not compile due to the name conflict.  A class named
     * 'User' typically causes runtime errors due to Hibernate/H2 conflicts.
     */
    @ParameterizedTest
    @ValueSource( strings = { "test", "Test" })
    void shouldRecognizeDisallowedWords(String proposedResourceName) {
        assertThat( ResourceNameValidator.of(proposedResourceName).isValid()).isFalse();
    }


    @Test
    void shouldRecognizeReservedWord() {
        assertThat(ResourceNameValidator.of("static").isValid()).isFalse();
    }

    @Test
    void shouldRecognizeUnreservedWord() {
        assertThat(ResourceNameValidator.of("widget").isValid()).isTrue();
    }

    @Test
    void shouldRejectNullValues() {
        assertThat(ResourceNameValidator.of(null).isValid()).isFalse();
    }

    @Test
    void shouldRejectEmptyString() {
        assertThat(ResourceNameValidator.of("").isValid()).isFalse();
    }

    @Test
    void shouldRejectInvalidClassNames() {
        assertThat(ResourceNameValidator.of("Treasure#!Map").isValid()).isFalse();
    }
}