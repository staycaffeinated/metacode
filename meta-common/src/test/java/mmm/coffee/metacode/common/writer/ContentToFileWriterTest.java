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
package mmm.coffee.metacode.common.writer;

import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.io.FileSystem;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;
import org.mockito.stubbing.OngoingStubbing;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Unit test
 */
class ContentToFileWriterTest {

    ContentToFileWriter writerUnderTest;

    @BeforeEach
    public void setUp() {
        writerUnderTest = new ContentToFileWriter();
    }


    @Test
    void shouldWriteToFile() throws IOException  {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        File tempFile = temporaryFolder.newFile("sample.txt");
        
        // Given: a destination file and some content to be written
        final String expectedContent = "This is some sampel output";

        // when: the content is written to that destination
        writerUnderTest.writeOutput(tempFile.getCanonicalPath(), expectedContent);

        // expect: that file contains the expected content
        String actualContent = FileUtils.readFileToString(tempFile, StandardCharsets.UTF_8);
        assertThat(actualContent).isEqualTo(expectedContent);
    }

    @Test
    void shouldThrowNullPointerExceptionWhenDestinationIsNull() {
        assertThrows(NullPointerException.class, () -> {
            writerUnderTest.writeOutput(null, "hello, world");
        });         
    }

    @Test
    void shouldNotWriteFileWhenContentIsEmptyr() throws IOException {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        File tempFile = temporaryFolder.newFile("sample.txt");

        writerUnderTest.writeOutput(tempFile.getCanonicalPath(), null);

        // When no content is written, the temp file may exist
        // but should be empty
        assertThat(tempFile.exists()).isTrue();
        assertThat(FileUtils.sizeOf(tempFile)).isEqualTo(0);
    }

    @Test
    void shouldHandleIoException() throws Exception {
        FileSystem mockFS = Mockito.mock(FileSystem.class);
        doThrow(IOException.class).when(mockFS).forceMkdir(any(File.class));

        ContentToFileWriter localWriterUnderTest = new ContentToFileWriter(mockFS);

        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();
        File tempFile = temporaryFolder.newFile("sample.txt");

        String canonicalPath = tempFile.getCanonicalPath();

        assertThrows(RuntimeApplicationError.class,
                () -> localWriterUnderTest.writeOutput(canonicalPath, "some content"));
    }
}
