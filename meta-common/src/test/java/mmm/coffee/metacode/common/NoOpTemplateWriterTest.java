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
package mmm.coffee.metacode.common;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test
 */
class NoOpTemplateWriterTest {

    final NoOpTemplateWriter writerUnderTest = new NoOpTemplateWriter();
    File scratchFile;

    @BeforeEach
    public void setUp() throws IOException {
        scratchFile = File.createTempFile("delete-me", "java");
    }

    @Test
    void shouldDisallowNullFileArgument() {
        assertThrows(NullPointerException.class,
                () -> writerUnderTest.writeStringToFile(null, null));
    }

    @Test
    void shouldNotGenerateOutput() {
        final String content = "hello world";
        writerUnderTest.writeStringToFile(content, scratchFile);
        // The NoOpWriter intentionally does not write to the file system.
        // The NoOpWriter is designed to supporting testing
        assertThat(scratchFile.length()).isEqualTo(0);
    }
}
