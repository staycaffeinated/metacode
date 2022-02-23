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

import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import lombok.NonNull;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.trait.ResolveTrait;

import java.util.Map;

/**
 * Resolves a template, which is to say: replaces tokens within the template with concrete values.
 */
@SuppressWarnings({"java:S1068", "java:S6206"})
// S1068: this is a false positive
// java:S6206: adding using Records to roadmap
public class TemplateResolver implements ResolveTrait<String, TemplateModel, String> {

    private final Configuration configuration;

    /**
     * Constructor
     * @param configuration the Freemarker Configuration used to load and render templates
     */
    public TemplateResolver(@NonNull Configuration configuration) {
        this.configuration = configuration;
    }

    public Configuration getConfiguration() { return configuration; }

    /**
     * Transforms an input of type {@code A} into an output of type {code R},
     * using {@code B} for context information
     *
     * @param templateResourcePath the template
     * @param templateModel this contains the keys that resolve to values
     *                      when the template is rendered. For example,
     *                      if the template contains variable "{{firstName}}",
     *                      templateModel.getFirstName() returns that value. 
     * @return the transformed content, as an instance of {@code R}.
     */
    @Override
    public String resolve(String templateResourcePath, TemplateModel templateModel) {
        return "resolve method is not yet implemented";
    }
}
