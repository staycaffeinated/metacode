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
package mmm.coffee.metacode.common.catalog;

import lombok.NonNull;

import java.util.List;
import java.util.Set;

/**
 * Stereotype for a Reader of catalog files. Catalog files in YAML format
 * with defined fields. A catalog file contains a list of {@code CatalogEntry}'s.  
 */
public interface ICatalogReader {
    /**
     * Reads the {@code CatalogEntry}'s from the YAML file found at {@code catalogResourcePath}.
     * @param catalogResourcePath the resource path (classpath) to the yaml file to read.
     *                            For example, "/spring/catalogs/common-stuff.yml" or
     *                            "/spring/catalogs/spring-boot.yml"
     * @return
     * @throws java.io.IOException
     */
    List<CatalogEntry> readCatalogFile(@NonNull String catalogResourcePath) throws java.io.IOException;
}
