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

import java.util.List;

/**
 * TemplateCatalog is a collection of templates that are processed
 * to produce source code.
 */
public interface TemplateCatalog {
    /**
     * Returns the list of templates of this catalog.
     * A {@code CatalogEntry} defines the template source and
     * the destination of the code emitted by the template.
     */
    List<CatalogEntry> getEntries();
}
