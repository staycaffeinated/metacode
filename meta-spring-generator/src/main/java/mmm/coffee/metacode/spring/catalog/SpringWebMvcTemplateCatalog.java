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
 * Loads the Spring WebMvc template catalog
 */
public class SpringWebMvcTemplateCatalog extends SpringTemplateCatalog {

    public static final String WEBMVC_CATALOG = "/spring/catalogs/spring-webmvc.yml";

    /**
     * Constructor
     *
     * @param reader the CatalogReader
     */
    public SpringWebMvcTemplateCatalog(@NonNull ICatalogReader reader) {
        super(reader);
    }

    @Override
    public List<CatalogEntry> collect() {
        return super.collectGeneralCatalogsAndThisOne(WEBMVC_CATALOG);
    }
}
