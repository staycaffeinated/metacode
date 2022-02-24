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
package mmm.coffee.metacode.common.dependency;

import lombok.NonNull;
import org.yaml.snakeyaml.Yaml;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * Reads the dependencies.yml file
 */
public class DependencyCatalogReader {
    // These are the keys expected to be defined in the library-versions.yaml file
    private static final String LIBRARY_ROOT = "dependencies";
    private static final String LIBRARY_ENTRY = "dependency";
    private static final String LIBRARY_NAME = "name";
    private static final String LIBRARY_VERSION = "version";

    /**
     * Loads the content of the library-versions.yaml file, returning the content as a list of Library elements.
     *
     * @param resourcePathOfLibraryYaml the resource path of the library-versions.yaml file
     * @return the entries of the catalog, as a list of CatalogEntry
     */
    @SuppressWarnings("unchecked")
    public @NonNull List<Dependency> readLibraryCatalog(@NonNull String resourcePathOfLibraryYaml) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream(resourcePathOfLibraryYaml)) {
            // Fail fast if the library-versions.yaml file isn't found
            Objects.requireNonNull(is, String.format("The file resource, '%s', was not found. Verify the resource exists in the classpath at the given path", resourcePathOfLibraryYaml));

            // Load the yaml content as Library elements
            var yaml = new Yaml();
            Map<String, Object> obj = yaml.load(is);
            List<Map<String, Object>> entries = (List<Map<String, Object>>) obj.get(LIBRARY_ROOT);
            return entries.stream().map(DependencyCatalogReader::readLibraryEntry).toList();
        }
    }

    /**
     * Reads the values loaded by YAML into a Library object
     * @param map these are the values from the yaml file
     */
    @SuppressWarnings("unchecked")
    private static Dependency readLibraryEntry (Map<String,Object> map) {
        var libraryEntry = new Dependency();
        Map<String,Object> values = (Map<String,Object>)map.get(LIBRARY_ENTRY);
        libraryEntry.setName((String)values.get(LIBRARY_NAME));
        libraryEntry.setVersion((String)values.get(LIBRARY_VERSION));
        return libraryEntry;
    }

}
