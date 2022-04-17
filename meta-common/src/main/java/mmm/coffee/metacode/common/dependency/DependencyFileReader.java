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

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import lombok.NonNull;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

/**
 * Reads the dependencies.yml file
 */
public class DependencyFileReader {

    /**
     * Reads the dependencies file and returns a POJO that mirrors it content.
     * @param resourcePathOfDependenciesYaml the YAML file to read, usually the dependencies.yml
     * @return a Library object that mirrors the content of the file
     * @throws IOException if the dependencyFile cannot be read
     */
    public Library readDependencyFile(@NonNull String resourcePathOfDependenciesYaml) throws IOException {
        try (InputStream is = this.getClass().getResourceAsStream(resourcePathOfDependenciesYaml)) {
            // This null check only ever fails during testing, since the file location will be fixed in production
            Objects.requireNonNull(is, String.format("The file resource, '%s', was not found. Verify the resource exists on the classpath in the given directory", resourcePathOfDependenciesYaml));
            ObjectMapper mapper = new ObjectMapper(new YAMLFactory());
            return mapper.readValue(is, Library.class);
        }
    }
}
