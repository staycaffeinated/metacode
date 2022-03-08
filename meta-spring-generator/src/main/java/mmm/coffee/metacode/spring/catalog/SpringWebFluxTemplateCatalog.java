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

import java.util.List;

/**
 * Reads the catalogs of Spring WebFlux templates
 */
public class SpringWebFluxTemplateCatalog extends SpringTemplateCatalog {

    static final String WEBFUX_CATALOG = "/spring/catalogs/spring-webflux.yml";

    /**
     * Constructor
     *
     * @param reader the CatalogReader
     */
    public SpringWebFluxTemplateCatalog(@NonNull ICatalogReader reader) {
        super(reader);
    }

    @Override
    public List<CatalogEntry> collect() {
        return super.collectGeneralCatalogsAndThisOne(WEBFUX_CATALOG);
    }
}
