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
import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;

import java.util.List;

/**
 * Loads the Spring WebMvc template catalog
 */
public class SpringWebMvcTemplateCatalog extends SpringTemplateCatalog {

    public static final String WEBMVC_CATALOG = "/spring/catalogs/spring-webmvc.yml";
    public static final String WEBMVC_MONGODB_CATALOG = "/spring/catalogs/spring-webmvc-mongodb.yml";

    private String activeCatalog;

    /**
     * Constructor
     *
     * @param reader the CatalogReader
     */
    public SpringWebMvcTemplateCatalog(@NonNull ICatalogReader reader) {
        super(reader);
    }

    /**
     * Returns the catalog selected for collection.
     * This is exposed to make the state available for unit tests.
     */
    String getActiveCatalog() {
        if (activeCatalog == null)
            return WEBMVC_CATALOG;
        return activeCatalog;
    }

    public Collector beforeCollection(Descriptor descriptor) {
        if (descriptor instanceof RestProjectDescriptor restProjectDescriptor) {
            if (restProjectDescriptor.getIntegrations().contains(SpringIntegrations.MONGODB.name())) {
                activeCatalog = WEBMVC_MONGODB_CATALOG;
            }
            else {
                activeCatalog = WEBMVC_CATALOG;
            }
        }
        return this;
    }

    @Override
    public List<CatalogEntry> collect() {
        return super.collectGeneralCatalogsAndThisOne(getActiveCatalog());
    }
}
