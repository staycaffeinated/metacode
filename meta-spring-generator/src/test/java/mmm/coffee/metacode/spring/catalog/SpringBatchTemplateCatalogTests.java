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
package mmm.coffee.metacode.spring.catalog;

import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.catalog.ICatalogReader;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Nested;
import org.mockito.Mockito;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit test the SpringBatchTemplateCatalog
 */
class SpringBatchTemplateCatalogTests {

    SpringBatchTemplateCatalog catalogUnderTest;

    @BeforeEach
    public void setUp() {
        CatalogFileReader catalogFileReader = new CatalogFileReader();
        catalogUnderTest = new SpringBatchTemplateCatalog(catalogFileReader);
    }

    @Nested
    class CollectMethod {
        @Test
        void shouldCollectCatalogEntries() {
            List<CatalogEntry> resultSet = catalogUnderTest.collect();
            assertThat(resultSet).isNotNull();
            assertThat(resultSet.size()).isGreaterThan(0);
        }
    }


    @Test
    void shouldThrowExceptionWhenCatalogPathIsNull() {
        var obj = new FakeTemplateCatalog(new CatalogFileReader());
        assertThrows(NullPointerException.class, obj::invokeCollectGeneralCatalogsAndThisOne);
    }


    @Nested
    class ConstructorTests {
        @Test
        @SuppressWarnings("all")
        void shouldThrowExceptionWhenReaderArgIsNull() {
            assertThrows(NullPointerException.class, () -> new SpringBatchTemplateCatalog(null));
        }

        @Test
        void shouldConstructAnInstanceWhenReaderIsNotNull() {
            var mockCatalogReader = Mockito.mock(ICatalogReader.class);
            var obj = new SpringBatchTemplateCatalog(mockCatalogReader);
            assertThat(obj).isNotNull();
        }
    }

    // ======================================================================================
    // Helper Methods
    // ======================================================================================
    
    private static class FakeTemplateCatalog extends SpringBatchTemplateCatalog {
        public FakeTemplateCatalog(ICatalogReader reader) {
            super(reader);
        }

        /**
         * Collects items, honoring the conditions set with {@code setConditions}
         *
         * @return the items meeting the conditions
         */
        @Override
        public List<CatalogEntry> collect() {
            return null;
        }

        @SuppressWarnings("all")
        public void invokeCollectGeneralCatalogsAndThisOne() {
            // The catalog is intentionally null to test handling of null values
            super.collectGeneralCatalogsAndThisOne(null );
        }
    }

}
