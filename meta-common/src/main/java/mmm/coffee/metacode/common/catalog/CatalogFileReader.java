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

import lombok.NonNull;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * The {@code CatalogFileReader} reads the catalog files containing
 * the inventory of templates to render.
 *
 * To get from a template to a rendered file, we need to know
 * two things: the location of the template and the destination
 * of the rendered content.  These two pieces of information are
 * captured in a {@code CatalogEntry}.
 *
 * To create an inventory of all the templates to render, we
 * have the notion of "catalogs", where a catalog contains a list
 * of CatalogEntry's. We also support multiple catalogs, since
 * being able to organize CatalogEntry's into different files is useful.
 *
 * Catalogs are stored as YAML files, saved as resource bundles.
 * By convention, the catalog files are saved in a folder named "catalogs".
 * Thus, the classpath to the catalogs is something like "/restapi/catalogs/",
 * which can be found in a module's "src/main/resources" folder.
 *
 * Likewise, the Freemarker templates are TFL files saved as resource bundles,
 * with a classpath something like "/restapi/templates/".
 *
 * From the point of view of a CodeGenerator, the generator needs the (primary)
 * catalog of templates to process. (Some filtering is supported since a code
 * generator can exclude templates not to render, based on command-line options.)
 *
 * Idea on adding Keywords attribute:
 * https://github.com/helm/helm/issues/7771
 */
public class CatalogFileReader implements ICatalogReader {

    /*
     * These are the keys expected to be defined in the catalog.yaml file
     */
    private static final String CATALOG_KEY = "catalog";
    private static final String CATALOG_ENTRY_KEY = "entry";
    private static final String CONTEXT_KEY = "context";
    private static final String TEMPLATE_KEY = "template";
    private static final String DESTINATION_KEY = "destination";
    private static final String FEATURE_KEY = "feature";


    /**
     * Constructor
     */
    public CatalogFileReader() {
        // empty; no instance variables are needed
    }


    /**
     * Reads the given catalog file, returning the content
     * as CatalogEntry's.  The {@code catalogClassPath} will
     * look something like "/spring/catalogs/spring-boot.yml"
     * or "/spring/catalogs/spring-gradle.yml".
     *
     * @param catalogClassPath the resource path of the catalog.yaml file
     * @return the entries of the catalog, as a list of CatalogEntry
     */
    public List<CatalogEntry> readCatalogFile(@NonNull String catalogClassPath) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream(catalogClassPath)) {
            // Fail fast if the file isn't found
            Objects.requireNonNull(is, String.format("The catalog file, '%s', was not found. Verify the resource exists at the given path.", catalogClassPath));

            // Load the yaml content as CatalogEntry items
            var yaml = new Yaml();
            Map<String, Object> obj = yaml.load(is);
            List<Map<String, Object>> entries = (List<Map<String, Object>>) obj.get(CATALOG_KEY);
            return entries.stream().map(CatalogFileReader::readCatalogEntry).toList();
        }
    }

    @SuppressWarnings("unchecked")
    /**
     * Reads the values loaded by YAML into a CatalogEntry object
     * @param map these are the values from the yaml file
     */
    private static CatalogEntry readCatalogEntry (Map<String,Object> map) {
        var catalogEntry = new CatalogEntry();
        Map<String,Object> values = (Map<String,Object>)map.get(CATALOG_ENTRY_KEY);
        
        catalogEntry.setDestination((String)values.get(DESTINATION_KEY));
        catalogEntry.setTemplate((String)values.get(TEMPLATE_KEY));
        catalogEntry.setContext((String)values.get(CONTEXT_KEY));   // context is either: project or endpoint

        // NB: In the YAML file, the field is currently named 'features'
        // We want to rename this field to 'tags'.  In the CatalogEntry,
        // get/setTags is used; the YAML files have not yet been updated.
        catalogEntry.setTags((String)values.get(FEATURE_KEY));

        return catalogEntry;
    }
}
