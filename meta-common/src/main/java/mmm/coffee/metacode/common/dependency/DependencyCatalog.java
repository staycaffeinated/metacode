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
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.stereotype.DependencyCollector;

import java.io.IOException;
import java.util.List;

/**
 * Abstracts the contents of a library-versions.yaml file. Provides
 * a helper method to filter the catalog entries.
 */
public class DependencyCatalog implements DependencyCollector {

    private final String resourceName;

    /**
     * Constructor
     */
    public DependencyCatalog(@NonNull String resourceName) {
        this.resourceName = resourceName;
    }

    /**
     * Reads the YAML resource file that contains Dependency entries
     * and returns those Dependency entries.
     *
     * Typically, the YAML file is a file named 'dependencies.yml',
     * but that's just by convention within this project.
     *
     * @return the Dependency entries
     */
    @Override
    public List<Dependency> collect() {
        try {
            return new DependencyCatalogReader().readLibraryCatalog(resourceName);
        } catch (IOException e) {
            throw new RuntimeApplicationError(e.getMessage(), e);
        }
    }
}