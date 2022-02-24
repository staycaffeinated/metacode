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
package mmm.coffee.metacode.spring.project.converter;

import com.google.common.base.Predicate;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test
 */
class DescriptorToPredicateConverterTest {

    private static final String BASE_PATH = "/petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String APP_NAME = "petstore";

    final DescriptorToPredicateConverter converterUnderTest = new DescriptorToPredicateConverter();

    RestProjectDescriptor restProject;

    CatalogEntry commonEntry;
    CatalogEntry postgresEntry;
    CatalogEntry testContainerEntry;
    CatalogEntry liquibaseEntry;

    /*
     * The easiest way to verify a Predicate yields the correct verdict is to
     * throw some sample payloads (CatalogEntries, in our case)
     * at the Predicate and verify the verdict is what we expect.  The
     * CatalogEntries we create in this method are those sample payloads.
     */
    @BeforeEach
    public void setUpTestData() {
        // A template that's applied for common usage, such as error advice, won't have any tag
        commonEntry = buildCatalogEntry("ErrorAdvice.ftl", "ErrorAdvice.java");

        // A template specific to Postgres support should contain the 'postgres' tag
        postgresEntry = buildCatalogEntry("DatabaseConfig.ftl", "DatabaseConfig.java", WebMvcIntegration.POSTGRES.toString());

        // A template specific to testcontainer support should contain the 'testcontainer' tag
        testContainerEntry = buildCatalogEntry("TestContainer.ftl", "TestContainer.java", WebMvcIntegration.TESTCONTAINERS.toString());

        // A template specific to liquibase support should contain the liquibase tag
        liquibaseEntry = buildCatalogEntry("liquibaseTemplate.ftl", "liquibase.yml", WebMvcIntegration.LIQUIBASE.toString());
    }


    /*
     * When building a project that does not have any integrations enabled
     * (such as postgres, testcontainers, liquibase), the uber-predicate
     * should ignore integration-specific templates
     */
    @Test
    void whenNoIntegrations_shouldNotApplyIntegrations() {
        restProject = RestProjectDescriptor.builder()
                        .basePath(BASE_PATH).basePackage(BASE_PKG).applicationName(APP_NAME).build();

        Predicate<CatalogEntry> predicate = converterUnderTest.convert(restProject);

        // Verify the predicate returns the correct verdict for a different CatalogEntries
        assertThat(predicate).isNotNull();
        assertThat(predicate.apply(commonEntry)).isTrue();
        // Verify we don't get any false positives
        assertThat(predicate.apply(postgresEntry)).isFalse();
        assertThat(predicate.apply(testContainerEntry)).isFalse();
        assertThat(predicate.apply(liquibaseEntry)).isFalse();
    }

    /*
     * When building a project that has test container integration enabled,
     * the uber-predicate should include common templates and test container templates,
     * but not other integration-specific templates like postgres templates.
     */
    @Test
    void whenTestContainerSupport_shouldHaveTestContainerPredicate() {
        restProject = RestProjectDescriptor.builder()
                .basePath(BASE_PATH).basePackage(BASE_PKG).applicationName(APP_NAME).build();
        restProject.getIntegrations().add(WebMvcIntegration.TESTCONTAINERS.name());

        Predicate<CatalogEntry> predicate = converterUnderTest.convert(restProject);

        // Verify the predicate returns the correct verdict for a different CatalogEntries
        assertThat(predicate).isNotNull();
        assertThat(predicate.apply(commonEntry)).isTrue();
        assertThat(predicate.apply(testContainerEntry)).isTrue();

        // Verify we don't get any false positives
        assertThat(predicate.apply(postgresEntry)).isFalse();
        assertThat(predicate.apply(liquibaseEntry)).isFalse();
    }

    /*
     * When building a project that has postgres integration enabled,
     * the uber-predicate should include common templates and postgres templates,
     * but not other integration-specific templates like liquibase templates.
     */
    @Test
    void whenPostgresSupport_shouldHavePostgresPredicate() {
        restProject = RestProjectDescriptor.builder()
                .basePath(BASE_PATH).basePackage(BASE_PKG).applicationName(APP_NAME).build();
        restProject.getIntegrations().add(WebMvcIntegration.POSTGRES.name());

        Predicate<CatalogEntry> predicate = converterUnderTest.convert(restProject);

        // Verify the predicate returns the correct verdict for a different CatalogEntries
        assertThat(predicate).isNotNull();
        assertThat(predicate.apply(commonEntry)).isTrue();
        assertThat(predicate.apply(postgresEntry)).isTrue();

        // Verify we don't get any false positives
        assertThat(predicate.apply(testContainerEntry)).isFalse();
        assertThat(predicate.apply(liquibaseEntry)).isFalse();
    }

    /*
     * When building a project that has liquibase integration enabled,
     * the uber-predicate should include common templates and liquibase templates,
     * but not other integration-specific templates like postgres templates.
     */
    @Test
    void whenLiquibaseSupport_shouldHaveLiquibasePredicate() {
        restProject = RestProjectDescriptor.builder()
                .basePath(BASE_PATH).basePackage(BASE_PKG).applicationName(APP_NAME).build();
        restProject.getIntegrations().add(WebMvcIntegration.LIQUIBASE.name());

        Predicate<CatalogEntry> predicate = converterUnderTest.convert(restProject);

        // Verify the predicate returns the correct verdict for a different CatalogEntries
        assertThat(predicate).isNotNull();
        assertThat(predicate.apply(commonEntry)).isTrue();
        assertThat(predicate.apply(liquibaseEntry)).isTrue();

        // Verify we don't get any false positives
        assertThat(predicate.apply(testContainerEntry)).isFalse();
        assertThat(predicate.apply(postgresEntry)).isFalse();
    }

    /*
     * Helper method to build a CatalogEntry w/o any tags
     */
    private CatalogEntry buildCatalogEntry(String source, String destination) {
        return buildCatalogEntry(source, destination, null);
    }

    /*
     * Helper method to build a CatalogEntry with tags
     */
    private CatalogEntry buildCatalogEntry(String source, String destination, String tags) {
        CatalogEntry entry = new CatalogEntry();
        entry.setContext("project");
        entry.setTemplate(source);
        entry.setDestination(destination);
        entry.setTags(tags);
        return entry;
    }

}
