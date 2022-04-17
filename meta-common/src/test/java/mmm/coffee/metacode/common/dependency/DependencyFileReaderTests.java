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
package mmm.coffee.metacode.common.dependency;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.Assert.assertThrows;

/**
 * Unit tests
 */
class DependencyFileReaderTests {

    private static final String TEST_FILE = "/test-dependencies.yml";

    DependencyFileReader readerUnderTest = new DependencyFileReader();

    @Test
    void shouldReadEntriesFromTheFile() throws Exception {
        Library library = readerUnderTest.readDependencyFile(TEST_FILE);

        assertThat(library).isNotNull();
        assertThat(library.getDependencies().size()).isAtLeast(12);
    }

    @Test
    void shouldThrowExceptionIfFileNameIsNull() {
        assertThrows(NullPointerException.class, () -> readerUnderTest.readDependencyFile(null));
    }
}
