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

import org.junit.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
class ReservedWordsTests {

    @ParameterizedTest
    @ValueSource( strings = { "Employee", "employee", "Test_Suite", "xyzzy" })
    void shouldRecognizeNonReservedWords(String identifier) {
        assertThat( ReservedWords.isReservedWord(identifier)).isFalse();
    }

    @ParameterizedTest
    @ValueSource( strings = { "abstract", "null", "const", "float" })
    void shouldRecognizeReservedWords(String identifier) {
        assertThat( ReservedWords.isReservedWord(identifier)).isTrue();
    }
}