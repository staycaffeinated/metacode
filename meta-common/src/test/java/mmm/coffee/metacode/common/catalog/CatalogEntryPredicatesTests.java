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
package mmm.coffee.metacode.common.catalog;

import com.google.common.base.Predicate;
import com.google.common.collect.ImmutableSet;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
class CatalogEntryPredicatesTests {
    final Set<CatalogEntry> testData = buildSampleSet();

    @Test
    void shouldReturnPopulatedList() {
        assertThat(testData.size()).isGreaterThan(0);
    }

    @Test
    void shouldFindEntriesHavingPostgresTag() {
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(testData, CatalogEntryPredicates.hasPostgresTag());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldFindEntriesHavingTestContainerTag() {
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(testData, CatalogEntryPredicates.hasTestContainerTag());
        assertThat(result.size()).isEqualTo(1);
    }

    @Test
    void shouldOnlyFindCommonProjectArtifacts() {
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(testData, CatalogEntryPredicates.isCommonProjectArtifact());
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void shouldFindEndpointArtifacts() {
        var endpointData = buildEndpointSampleSet();
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(endpointData, CatalogEntryPredicates.isEndpointArtifact());
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void whenPostgresTagIsPresent_shouldDetectIt() {
        CatalogEntry entry = buildEntry("PostgresConfig.ftl", "PostgresConfig.java", "postgres", "project");
        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasPostgresTag();
        assertThat(predicate.apply(entry)).isTrue();
    }
    @Test
    void whenPostgresTagIsNotPresent_shouldNotDetectIt() {
        // Given a common template unrelated to Postgres integration...
        CatalogEntry entry = buildEntry("Application.ftl","Application.java", null, "project");

        // When the Postgres predicate is applied to this catalog entry, it should return false;
        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasPostgresTag();
        assertThat(predicate.apply(entry)).isFalse();
    }

    @Test
    void whenTestContainerTagIsPresent_shouldBeDetected() {
        CatalogEntry entry = buildEntry("TestContainer.ftl", "TestContainer.java", "testcontainer", "project");
        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasTestContainerTag();
        assertThat(predicate.apply(entry)).isTrue();
    }

    @Test
    void whenTestContainerTagIsNotPresent_shouldNotBeDetected() {
        CatalogEntry entry = buildEntry("Application.ftl", "Application.java", null,"project");
        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasTestContainerTag();
        assertThat(predicate.apply(entry)).isFalse();
    }

    @Test
    void whenLiquibaseTagIsPresent_shouldBeDetected() {
        CatalogEntry entry = buildEntry("Liquibase.ftl", "liquibase-config.yml", "liquibase","project");
        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasLiquibaseTag();
        assertThat(predicate.apply(entry)).isTrue();
    }
    @Test
    void whenLiquibaseTagIsNotPresent_shouldNotBeDetected() {
        CatalogEntry entry = buildEntry("Liquibase.ftl", "liquibase-config.yml", "random-tag","project");
        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasLiquibaseTag();
        assertThat(predicate.apply(entry)).isFalse();
    }

    @Test
    void givenSomeCatalogEntryWithNullContextField_predicatesShouldReturnFalse() {
        CatalogEntry entry = buildEntryWithNullContext("Liquibase.ftl", "liquibase-config.yml", "random-tag","project");

        Predicate<CatalogEntry> predicate = CatalogEntryPredicates.hasLiquibaseTag();
        assertThat(predicate.apply(entry)).isFalse();

        predicate = CatalogEntryPredicates.hasPostgresTag();
        assertThat(predicate.apply(entry)).isFalse();

        predicate = CatalogEntryPredicates.hasTestContainerTag();
        assertThat(predicate.apply(entry)).isFalse();

        predicate = CatalogEntryPredicates.isEndpointArtifact();
        assertThat(predicate.apply(entry)).isFalse();

        predicate = CatalogEntryPredicates.isEndpointArtifact();
        assertThat(predicate.apply(entry)).isFalse();
    }

    @Test
    void shouldOnlyKeepWebFluxTemplates() {
        Set<CatalogEntry> dataSet = buildFrameworkMixtureSampleSet();
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(dataSet, CatalogEntryPredicates.isWebFluxArtifact());
        assertThat(result.size()).isEqualTo(3);
    }

    @Test
    void shouldOnlyKeepWebMvcTemplates() {
        Set<CatalogEntry> dataSet = buildFrameworkMixtureSampleSet();
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(dataSet, CatalogEntryPredicates.isWebMvcArtifact());
        assertThat(result.size()).isEqualTo(3);
    }

    // ------------------------------------------------------------------------------
    //
    // Helper Methods
    //
    // ------------------------------------------------------------------------------

    private static Set<CatalogEntry> buildSampleSet() {
        CatalogEntry e1 = buildEntry("Application.ftl","Application.java", null, "project");
        CatalogEntry e2 = buildEntry("Controller.ftl", "Controller.java", null, "project");
        CatalogEntry e3 = buildEntry("PostgresConfig.ftl", "PostgresConfig.java", "postgres", "project");
        CatalogEntry e4 = buildEntry("ErrorHandler.ftl","ErrorHandler.java", null, "project");
        CatalogEntry e5 = buildEntry("TestContainer.ftl","TestContainer.java", "testcontainer", "project");

        return ImmutableSet.of(e1, e2, e3, e4, e5);
    }

    private static Set<CatalogEntry> buildEndpointSampleSet() {
        CatalogEntry e1 = buildEntry("/webflux/Service.ftl","PetService.java", null, "endpoint");
        CatalogEntry e2 = buildEntry("/webflux/Controller.ftl", "PetController.java", null, "endpoint");
        CatalogEntry e3 = buildEntry("/webmvc/Repository.ftl", "PetRepository.java", null, "endpoint");

        return ImmutableSet.of(e1, e2, e3);
    }

    /**
     * Return a sample set that includes both WebMvc and WebFlux templates
     * to enable verifying the Predicate can tell the difference.
     */
    private static Set<CatalogEntry> buildFrameworkMixtureSampleSet() {
        CatalogEntry e1 = buildEntry("/webflux/Service.ftl","PetService.java", null, "endpoint");
        CatalogEntry e2 = buildEntry("/webflux/Controller.ftl", "PetController.java", null, "endpoint");
        CatalogEntry e3 = buildEntry("/webflux/Repository.ftl", "PetRepository.java", null, "endpoint");
        CatalogEntry e4 = buildEntry("/webmvc/Service.ftl","PetService.java", null, "endpoint");
        CatalogEntry e5 = buildEntry("/webmvc/Controller.ftl", "PetController.java", null, "endpoint");
        CatalogEntry e6 = buildEntry("/webmvc/Repository.ftl", "PetRepository.java", null, "endpoint");

        return ImmutableSet.of(e1, e2, e3, e4, e5, e6);
    }

    /**
     * Builds a single CatalogEntry
     * @param source the template source
     * @param destination the destination of the rendered content, as a file path
     * @param tags optional tags associated to a template
     * @return the populated CatalogEntry
     */
    private static CatalogEntry buildEntry(String source, String destination, @Nullable String tags, String context) {
        CatalogEntry entry = new CatalogEntry();
        entry.setTemplate(source);
        entry.setDestination(destination);
        entry.setTags(tags);
        entry.setContext(context);
        return entry;
    }

    /**
     * Builds a single CatalogEntry with Null context field.
     * This is done for the sake of code coverage and checking the
     * edge case of a CatalogEntry having a null context field
     * 
     * @param source the template source
     * @param destination the destination of the rendered content, as a file path
     * @param tags optional tags associated to a template
     * @return the populated CatalogEntry
     */
    private static CatalogEntry buildEntryWithNullContext(String source, String destination, @Nullable String tags, String context) {
        CatalogEntry entry = new CatalogEntry();
        entry.setTemplate(source);
        entry.setDestination(destination);
        entry.setTags(tags);
        return entry;
    }

}
