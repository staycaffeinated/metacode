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
package mmm.coffee.metacode.common.dependency;

import lombok.NonNull;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;

import java.io.IOException;
import java.util.List;
import java.util.Map;

/**
 * Abstracts the contents of a library-versions.yaml file. Provides
 * a helper method to filter the catalog entries.
 */
public class DependencyCatalog {

    private final List<Dependency> entries;   // libraryVersionReader.readCatalog guarantees a non-null list

    /**
     * Constructor
     */
    public DependencyCatalog(@NonNull String resourceName) {
        try {
            entries = new DependencyCatalogReader().readLibraryCatalog(resourceName);
        }
        catch (IOException e) {
            throw new RuntimeApplicationError(e.getMessage(), e);
        }
    }

    public void loadTemplateKeys(@NonNull Map<String,Object> templateKeys) {
        entries.forEach( library -> templateKeys.put(library.getName()+"Version", library.getVersion()));
    }

    public List<Dependency> entries()  { return entries; }
}