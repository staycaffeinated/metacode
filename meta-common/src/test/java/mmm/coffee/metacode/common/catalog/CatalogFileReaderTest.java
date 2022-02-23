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
package mmm.coffee.metacode.common.catalog;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test
 */
class CatalogFileReaderTest {

    CatalogFileReader readerUnderTest;

    @BeforeEach
    public void setUp() {
        readerUnderTest = new CatalogFileReader();
    }

    @Test
    void shouldReadCatalog() throws Exception {
        List<CatalogEntry> entries = readerUnderTest.readCatalogFile("/test-catalog.yml");
        assertThat(entries).isNotEmpty();
        assertThat(entries.size()).isEqualTo(1);    // the test-catalog contains one entry

        // Let's verify the CatalogEntry fields match the yaml fields.
        // We want to ensure the code that transforms the YAML into a POJO works.
        CatalogEntry entry = entries.get(0);
        assertThat(entry.getTemplate()).isEqualTo( "/gradle/BuildDotGradle.ftl");
        assertThat(entry.getContext()).isEqualTo("project");
        assertThat(entry.getDestination()).isEqualTo("build.gradle");
    }

    @Test
    void shouldThrowException_whenRequiredArgIsNull() {
        assertThrows (NullPointerException.class, () -> {
            readerUnderTest.readCatalogFile(null);
        });
    }
}
