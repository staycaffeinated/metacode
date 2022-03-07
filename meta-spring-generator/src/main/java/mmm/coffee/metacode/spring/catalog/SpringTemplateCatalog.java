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

import lombok.NonNull;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.ICatalogReader;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.stereotype.Collector;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Base class for loading the catalog entries
 */
@SuppressWarnings( { "java:S125" })
// S125: we're OK with comments that look like code
public abstract class SpringTemplateCatalog implements Collector {

    private static final String[] SPRING_CATALOGS = {
            "/spring/catalogs/common-stuff.yml",
            "/spring/catalogs/spring-gradle.yml"
    };
    
    final ICatalogReader reader;

    /*
     * Most likely, reader is-a CatalogFileReader
     */
    protected SpringTemplateCatalog(@NonNull ICatalogReader reader) {
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
                throw new RuntimeApplicationError("Error when reading Spring template catalogs", e);
            }
        }
        return resultSet;
    }
}
