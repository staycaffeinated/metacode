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
package mmm.coffee.metacode.common.writer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import java.io.File;
import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test
 */
class ContentToNullWriterTest {

    ContentToNullWriter writerUnderTest;

    @BeforeEach
    public void setUp() {
        writerUnderTest = new ContentToNullWriter();
    }

    /*
     * The ContentToNullWriter's writeOutput method is empty,
     * so no File should be created when writeOutput is called.
     */
    @Test
    void shouldNotCreateOutputFile() throws IOException {
        // Given: a temporary folder to work with
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        String fakeDestination = temporaryFolder.getRoot().getAbsolutePath() + "/sample.txt";

        // when: writing to that destination
        writerUnderTest.writeOutput(fakeDestination, "hello, world");

        // expect: no File was created; writeOutput is a no-op method
        File f = new File(fakeDestination);
        assertThat(f.exists()).isFalse();
    }
}
