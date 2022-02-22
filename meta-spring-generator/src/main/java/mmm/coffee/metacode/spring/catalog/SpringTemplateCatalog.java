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

import com.google.inject.Inject;
import lombok.NonNull;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.ICatalogReader;
import mmm.coffee.metacode.common.stereotype.Collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for loading the catalog entries
 */
public abstract class SpringTemplateCatalog implements Collector {

    private static final String[] SPRING_CATALOGS = {
            "/spring/catalogs/common-stuff.yml",
            "/spring/catalogs/spring-boot.yml",
            "/spring/catalogs/spring-gradle.yml",
    };
    private static final String WEBFLUX_CATALOG = "/spring/catalogs/spring-webflux.yml";

    final ICatalogReader reader;

    /*
     * Most likely, reader is-a CatalogFileReader
     */
    @Inject
    public SpringTemplateCatalog(@NonNull ICatalogReader reader) {
        this.reader = reader;
    }

    /**
     * Reads the general catalog files and the {@code specificCatalog}
     * @param specificCatalog a specific file to include
     * @return the collection of CatalogEntry's 
     */
    protected List<CatalogEntry> collectGeneralCatalogsAndThisOne(@NonNull String specificCatalog) {
        List<CatalogEntry> resultSet = new ArrayList<>();

        for ( String catalog : SPRING_CATALOGS ) {
            try {
                resultSet.addAll(reader.readCatalogFile(catalog));
                resultSet.addAll(reader.readCatalogFile(specificCatalog));
            }
            catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return resultSet;
    }


    public List<CatalogEntry> __collect() {
        // read all the CatalogEntry's from all catalogs
        // filter ( allCatalogs, isCommonUsage() ).collect(resultSet.addAll())
        // if (descriptor.isWebFlux())
        //    filter ( allCatalogs, isWebFlux() ).collect(resultSet.addAll())
        // else if (descriptor.isWebMvc() )
        //    filter ( allCatalogs, isWebMvc() ).collect(resultSet.addAll())
        // filter (allCatalogs, isPostgres() ).collect(resultSet.addAll())
        // filter (allCatalogs, isTestContainer()).collect(resultSet.addAll())

        // Set<Predicate> predicates = DescriptorToPredicatesConverter.convert(descriptor);
        // eg:
        //      addPredicate( CommonUsagePredicate() )
        //      addPredicate( WebFluxPredicate() )
        //      addPredicate( PostgresPredicate() )
        //      addPredicate( TestContainerPredicate() )
        // predicates.forEach(p ->
        //    filter (allCatalogs, p ).collect(resultSet.addAll())
        //
        // something like this might work nicely:
        // -- this combines all the Predicate's into an Or chain of predicates
        //    (that is, a.or.b.or.c.or.d; e.g.: commonUsage.or.webFlux.or.postgres.or.testcontainer
        // -- got this from baeldung site. 
        // allCatalogs.filter( allPredicates.stream().reduce(x->false, Predicate::or) ).collect(Collectors.toList());
        //
        // Also look at: https://stackoverflow.com/questions/22845574/how-to-dynamically-do-filtering-in-java-8
        // We could define a Criteria, which is a chain of Predicates.
        // allowing us to do something like:
        //    allCriteria.apply ( allCatalogs.stream() ).forEach ( resultSet::add );

        return null;
    }
}
