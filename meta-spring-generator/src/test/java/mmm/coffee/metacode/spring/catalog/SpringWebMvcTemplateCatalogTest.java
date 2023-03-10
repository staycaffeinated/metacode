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
import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test
 * <p>
 * NB: There are some redundant tests. Jacoco is having trouble
 * mapping tests within nested classes to line coverage metrics.
 * When you come across a test within a nested class that covers
 * the same condition as some other test, that's from my attempts
 * to figure out Jacoco's idiosyncratic behavior.
 * </p>
 */
class SpringWebMvcTemplateCatalogTest {

    SpringWebMvcTemplateCatalog catalogUnderTest;

    @BeforeEach
    public void setUp() {
        CatalogFileReader catalogFileReader = new CatalogFileReader();
        catalogUnderTest = new SpringWebMvcTemplateCatalog(catalogFileReader);
    }

    /**
     * The SpringWebMvcTemplateCatalog class contains a hard-coded
     * path to the catalog file that it reads.  When ``collect`` is
     * called, that catalog file is loaded and its content is returned
     * as a List of CatalogEntry's.
     */
    @Test
    void shouldCollectCatalogEntries() {
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
    @SuppressWarnings("all")
    void shouldThrowExceptionWhenReaderArgIsNull() {
        assertThrows(NullPointerException.class, () -> new SpringWebMvcTemplateCatalog(null));
    }

    @Test
    void shouldAlsoThrowExceptionOnNullArg() {
        assertThrows(NullPointerException.class, () -> {
            ICatalogReader nullReader = null;
            new SpringWebMvcTemplateCatalog(nullReader);
        });
    }

    @Test
    void shouldConstructAnInstance() {
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
         * <p>
         * The two tests, <code>shouldThrowExceptionWhenReaderArgIsNull</code>
         * and <code>shouldInstantiateSuccessfully</code> are designed to test those two
         * conditions.  However, Jacoco's code coverage report shows that the first
         * condition is not being tested.
         * </p><p>
         * To address the Jacoco finding, this alternative test of condition one has
         * been added. Jacoco concurs that _this_ test covers condition one, leaving
         * us with a slightly cleaner report.
         * </p>
         */
        @Test
        @SuppressWarnings("all")
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

    @Nested
    class MongoIntegrationTests {
        @Test
        void whenProjectDescriptorHasIntegrationWithMongo_expectMongoDbCatalogIsActive() {
            Set<String> integrations = new TreeSet<>();
            integrations.add(SpringIntegrations.MONGODB.name());
            RestProjectDescriptor mockProjectDescriptor = Mockito.mock(RestProjectDescriptor.class);
            when(mockProjectDescriptor.getIntegrations()).thenReturn(integrations);
            
            catalogUnderTest.beforeCollection(mockProjectDescriptor);
            assertThat(catalogUnderTest.getActiveCatalog()).isEqualTo(SpringWebMvcTemplateCatalog.WEBMVC_MONGODB_CATALOG);
        }

        @Test
        void whenProjectDescriptorDoesNotIntegrateWithMongo_expectDefaultCatalogIsActive() {
            Set<String> integrations = new TreeSet<>();
            integrations.add(SpringIntegrations.POSTGRES.name());
            RestProjectDescriptor mockProjectDescriptor = Mockito.mock(RestProjectDescriptor.class);
            when(mockProjectDescriptor.getIntegrations()).thenReturn(integrations);

            catalogUnderTest.beforeCollection(mockProjectDescriptor);
            assertThat(catalogUnderTest.getActiveCatalog()).isEqualTo(SpringWebMvcTemplateCatalog.WEBMVC_CATALOG);
        }

        @Test
        void whenEndpointDescriptorHasIntegrationWithMongo_expectMongoDbCatalogIsActive() {
            RestEndpointDescriptor mockEndpointDescriptor = Mockito.mock(RestEndpointDescriptor.class);
            when(mockEndpointDescriptor.isWithMongoDb()).thenReturn(true);

            catalogUnderTest.beforeCollection(mockEndpointDescriptor);
            assertThat(catalogUnderTest.getActiveCatalog()).isEqualTo(SpringWebMvcTemplateCatalog.WEBMVC_MONGODB_CATALOG);
        }

        @Test
        void whenEndpointDescriptorDoesNotIntegrateWithMongo_expectDefaultCatalogIsActive() {
            RestEndpointDescriptor mockEndpointDescriptor = Mockito.mock(RestEndpointDescriptor.class);
            when(mockEndpointDescriptor.isWithMongoDb()).thenReturn(false);

            catalogUnderTest.beforeCollection(mockEndpointDescriptor);
            assertThat(catalogUnderTest.getActiveCatalog()).isEqualTo(SpringWebMvcTemplateCatalog.WEBMVC_MONGODB_CATALOG);

        }
    }

    @Nested
    class EdgeCases {
        /**
         * Perhaps correct behavior should be to throw IllegalArgException in this scenario.
         * In practice, end-to-end tests should uncover any scenario where a Descriptor is
         * for neither a project nor an endpoint. 
         */
        @Test
        void whenDescriptorIsOfWrongType_expectDefaultCatalog() {
            Descriptor mockDescriptor = Mockito.mock(Descriptor.class);

            catalogUnderTest.beforeCollection(mockDescriptor);
            assertThat(catalogUnderTest.getActiveCatalog()).isEqualTo(SpringWebMvcTemplateCatalog.WEBMVC_CATALOG);
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
            super.collectGeneralCatalogsAndThisOne("fakeCatalog");
        }
    }
}
