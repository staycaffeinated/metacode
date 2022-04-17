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
package mmm.coffee.metacode.common.dependency;

import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.when;

/**
 * Unit tests
 */
class DependencyCatalogTests {
    private static final String TEST_CATALOG = "/test-dependencies.yml";

    DependencyCatalog catalogUnderTest;

    @BeforeEach
    public void setUpEachTime() {
        catalogUnderTest = new DependencyCatalog(TEST_CATALOG);
    }

    @Test
    void shouldReturnDependencyEntries() {
        List<Dependency> entries = catalogUnderTest.collect();
        assertThat(entries).isNotEmpty();
    }

    @Test
    void shouldDisallowNullCatalogName() {
        assertThrows(NullPointerException.class, () -> new DependencyCatalog(null));
    }

    /**
     * If an IOException occurs when the code attempts to read
     * the dependencies.yml file, the IOException should be wrapped
     * in a RuntimeApplicationError
     */
    @Test
    void shouldThrowRuntimeApplicationError() throws Exception {
        var mockReader = Mockito.mock(DependencyFileReader.class);
        when(mockReader.readDependencyFile(any())).thenThrow(IOException.class);

        DependencyCatalog catalog = new DependencyCatalog(TEST_CATALOG, mockReader);

        assertThrows(RuntimeApplicationError.class, () -> catalog.collect());
    }

    @Test
    void shouldThrowExceptionWhenAnyArgumentIsNull() {
        var mockReader = Mockito.mock(DependencyFileReader.class);
        // If the resourceName is null, expect an NPE
        assertThrows(NullPointerException.class, () -> new DependencyCatalog(null, mockReader));

        // if the reader is null, expect an NPE
        assertThrows(NullPointerException.class, () -> new DependencyCatalog("/catalog/example", null));
    }
}
