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

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import org.junit.jupiter.api.Test;

import javax.annotation.Nullable;
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
    void shouldFindProjectArtifacts() {
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(testData, CatalogEntryPredicates.isProjectArtifact());
        assertThat(result.size()).isEqualTo(5);
    }

    @Test
    void shouldFindEndpointArtifacts() {
        var endpointData = buildEndpointSampleSet();
        Set<CatalogEntry> result = CatalogEntryPredicates.filterCatalogEntries(endpointData, CatalogEntryPredicates.isEndpointArtifact());
        assertThat(result.size()).isEqualTo(3);
    }

    /**
     * Helper method to build test data
     * @return the test data
     */
    private static Set<CatalogEntry> buildSampleSet() {
        CatalogEntry e1 = buildEntry("Application.ftl","Application.java", null, "project");
        CatalogEntry e2 = buildEntry("Controller.ftl", "Controller.java", null, "project");
        CatalogEntry e3 = buildEntry("PostgresConfig.ftl", "PostgresConfig.java", "postgres", "project");
        CatalogEntry e4 = buildEntry("ErrorHandler.ftl","ErrorHandler.java", null, "project");
        CatalogEntry e5 = buildEntry("TestContainer.ftl","TestContainer.java", "testcontainer", "project");

        return ImmutableSet.of(e1, e2, e3, e4, e5);
    }

    private static Set<CatalogEntry> buildEndpointSampleSet() {
        CatalogEntry e1 = buildEntry("Service.ftl","PetService.java", null, "endpoint");
        CatalogEntry e2 = buildEntry("Controller.ftl", "PetController.java", null, "endpoint");
        CatalogEntry e3 = buildEntry("Repository.ftl", "PetRepository.java", null, "endpoint");

        return ImmutableSet.of(e1, e2, e3);
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
        entry.setTemplate(tags);
        entry.setContext(context);
        return entry;
    }

}
