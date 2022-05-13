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
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test
 */
class SpringWebMvcTemplateCatalogTest {

    SpringWebMvcTemplateCatalog catalogUnderTest;

    @BeforeEach
    public void setUp() {
        CatalogFileReader catalogFileReader = new CatalogFileReader();
        catalogUnderTest = new SpringWebMvcTemplateCatalog(catalogFileReader);
    }

    @Test
    void shouldReadTemplates() {
        List<CatalogEntry> resultSet = catalogUnderTest.collect();
        assertThat(resultSet).isNotNull();
        assertThat(resultSet.size()).isGreaterThan(0);
    }

    /*
     * This is a white box test of the
     * {@code collectGeneralCatalogsAndThisOne} method.
     * This test calls that method with a {@code null}
     * argument to ensure an NPE is thrown.
     */
    @Test
    void shouldThrowExceptionWhenCatalogPathIsNull() {
        var obj = new FakeTemplateCatalog(new CatalogFileReader());
        assertThrows(NullPointerException.class, obj::invokeCollectGeneralCatalogsAndThisOne);
    }

    @Test
    void shouldThrowExceptionWhenReaderArgIsNull() {
        assertThrows(NullPointerException.class, () -> new SpringWebMvcTemplateCatalog(null));
    }

    @Test
    void shouldInstantiateSuccessfully() {
        var mockCatalogReader = Mockito.mock(ICatalogReader.class);
        var obj = new SpringWebMvcTemplateCatalog(mockCatalogReader);
        assertThat(obj).isNotNull();
    }


    @Nested
    class ConstructorTests {
        @Test
        void shouldThrowExceptionWhenReaderArgIsNull() {
            assertThrows(NullPointerException.class, () -> new SpringWebMvcTemplateCatalog(null));
        }

        @Test
        void shouldInstantiateSuccessfully() {
            var mockCatalogReader = Mockito.mock(ICatalogReader.class);
            var obj = new SpringWebMvcTemplateCatalog(mockCatalogReader);
            assertThat(obj).isNotNull();
        }

        /**
         * The signature of the SpringWebMvcTemplateCatalog is:
         * <code>
         *  SpringWebMvcTemplateCatalog (@NotNull ICatalogReader reader)
         * </code>
         * Naturally, we want to test two conditions:
         * <li>(1) The reader is null</li>
         * <li>(2) The reader is not null</li>
         *
         * The two tests, <code>shouldThrowExceptionWhenReaderArgIsNull</code>
         * and <code>shouldInstantiateSuccessfully</code> are designed to test those two
         * conditions.  However, Jacoco's code coverage report shows that the first
         * condition is not being tested.
         *
         * To address the Jacoco finding, this alternative test of condition one has
         * been added. Jacoco concurs that _this_ test covers condition one, leaving
         * us with a slightly cleaner report.
         */
        @Test
        void thisTestFixesHoleInJacoco() {
            try {
                // Send in a Null argument to test the @NotNull annotation
                var foo = new SpringWebMvcTemplateCatalog(null);
            }
            catch (NullPointerException e) {
                return;
            }
            fail("Expected NPE to be thrown");
        }
    }

    // -----------------------------------------------------------------------------
    //
    // Helper methods
    //
    // -----------------------------------------------------------------------------

    private static class FakeTemplateCatalog extends SpringWebMvcTemplateCatalog {

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

        public void invokeCollectGeneralCatalogsAndThisOne() {
            super.collectGeneralCatalogsAndThisOne(null);
        }
    }
}
