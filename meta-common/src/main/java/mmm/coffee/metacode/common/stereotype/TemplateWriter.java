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
package mmm.coffee.metacode.common.stereotype;

import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;

/**
 * Handles writing String content to a destination.
 * Typically, the destination is a File but, for testing,
 * writing can be a no-op. 
 */
public class TemplateWriter implements WriteOutputTrait<CatalogEntry, String> {

    // Probably need a 

    @Override
    public void writeOutput(CatalogEntry catalogEntry, String content) {
        // -- String fileName = MustacheExpressions.resolve(catalogEntry, context);
        // -- File fOutput = createOutputFile(catalogEntry.getDestination())
        // -- FileUtils.writeStringToFile(content, fOutput)
        System.out.printf("writeOutput: writing %s to-file %s%n", content, catalogEntry.getDestination());
    }
}