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
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.fail;

/**
 * Unit test
 *
 * NB: There are some redundant tests. Jacoco is having trouble
 * mapping tests within nested classes to line coverage metrics.
 * When you come across a test within a nested class that covers
 * the same condition as some other test, that's from my attempts
 * to figure out Jacoco's idiosyncratic behavior.
 */
class SpringBootTemplateCatalogTest {

    SpringBootTemplateCatalog catalogUnderTest;

    @BeforeEach
    public void setUp() {
        CatalogFileReader catalogFileReader = new CatalogFileReader();
        catalogUnderTest = new SpringBootTemplateCatalog(catalogFileReader);
    }

    /**
     * The mmm.coffee.metacode.annotations.guice.SpringBootTemplateCatalog class contains a hard-coded
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

    // See this for another idea:
    // https://stackoverflow.com/questions/63124975/lombok-nonnull-annotation-with-builder-not-reflecting-in-test-coverage
    // Technically, _our_ code is covered, but the lombok-generated code is not. 
    @Test
    void shouldThrowExceptionWhenReaderArgIsNull() {
        assertThrows(NullPointerException.class, () -> new SpringBootTemplateCatalog(null));
    }

    @Test
    void shouldConstructAnInstance() {
        var mockCatalogReader = Mockito.mock(ICatalogReader.class);
        var obj = new SpringBootTemplateCatalog(mockCatalogReader);
        assertThat(obj).isNotNull();
    }

    @Nested
    class ConstructorTests {
        /**
         * Note:  Jacoco seems to have trouble detecting null-handling tests in constructors.
         * If the constructor method is, say, `public Widget(@NonNull String value) {..}`, there are
         * two scenarios to cover: `value` is null and `value` is not null. The code to test
         * these two scenarios is trivial; for instance:
         * <code>
         *     var scenario1 = new Widget(null);
         *     var scenario2 = new Widget("something");
         * </code>
         * Even with this, Jacoco will report that the first condition, `value` is null, was never tested.
         * <p>
         * This lapse in line coverage analysis seems to be specific to constructor methods.
         * Instance methods that use `@NonNull` are not a victim of this bug. Take this method,
         * for example:
         * <code>
         *     public void setName(@NonNull String name);
         * </code>
         * Again, we write two tests, one for `name` is null, and one where `name` is not null.
         * Jacoco will report that _both_ conditions were indeed covered by tests. It seems to me
         * that only constructor methods are tripping up Jacoco.
         * </p><p>
         * Of course, _technically_, those null/not-null tests are exercising Lombok's code, not ours.
         * Since its rather trivial to write those null/not-null tests, my tendency is to go ahead and
         * add them, even if the improvement in code coverage is only cosmetic.
         */
        @Test
        void shouldThrowExceptionWhenReaderArgIsNull() {
            assertThrows(NullPointerException.class, () -> new SpringBootTemplateCatalog(null));
        }

        @Test
        void shouldInstantiateSuccessfully() {
            var mockCatalogReader = Mockito.mock(ICatalogReader.class);
            var obj = new SpringBootTemplateCatalog(mockCatalogReader);
            assertThat(obj).isNotNull();
        }

        /**
         * The signature of the SpringBootTemplateCatalog is:
         * <code>
         *  SpringBootTemplateCatalog (@NotNull ICatalogReader reader)
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
                var foo = new SpringBootTemplateCatalog(null);
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

    private static class FakeTemplateCatalog extends SpringBootTemplateCatalog {

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
