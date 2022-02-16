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

import com.google.common.base.Predicate;

import java.util.Set;
import java.util.stream.Collectors;

/**
 * Predicates applicable to collections of CatalogEntry instances
 */
public class CatalogEntryPredicates {

    /**
     * Returns {@code true} if the CatalogEntry is for a project artifact
     */
    public static Predicate<CatalogEntry> isProjectArtifact() {
        return p -> p.getContext() != null && p.getContext().contains("project");
    }

    /**
     * Returns {@code true} if the CatalogEntry is for an endpoint artifact
     */
    public static Predicate<CatalogEntry> isEndpointArtifact() {
        return p -> p.getContext() != null && p.getContext().contains("endpoint");
    }


    /**
     * Returns {@code true} if the CatalogEntry's tags includes {@code postgres}
     */
    public static Predicate<CatalogEntry> hasPostgresTag() {
        return p -> p.getTags() != null && p.getTags().contains("postgres");
    }

    /**
     * Returns {@code true} if the CatalogEntry's tags includes {@code postgres}
     */
    public static Predicate<CatalogEntry> hasTestContainerTag() {
        return p -> p.getTags() != null && p.getTags().contains("testcontainer");
    }

    /**
     * Returns the subset of entries that fulfil the predicate.
     * @param entries the general population of CatalogEntry's
     * @param predicate essentially, the filter applied to the population
     * @return those entries in the population that satisfy the predicate
     */
    public static Set<CatalogEntry> filterCatalogEntries (Set<CatalogEntry> entries,
                                                           Predicate<CatalogEntry> predicate)
    {
        return entries.stream()
                .filter( predicate )
                .collect(Collectors.<CatalogEntry>toSet());
    }
}
