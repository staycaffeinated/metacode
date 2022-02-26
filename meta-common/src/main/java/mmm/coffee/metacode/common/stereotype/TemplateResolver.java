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
package mmm.coffee.metacode.common.stereotype;

/**
 * Resolves a template, which is to say: replaces tokens within the template with concrete values.
 */
@SuppressWarnings({"java:S1068", "java:S6206"})
// S1068: this is a false positive
// java:S6206: adding using Records to roadmap
public interface TemplateResolver<T> {


    /**
     * Renders the {@code template}, using {@code dataModel} to resolve template variables
     * @param templateClassPath the resource path to the template file
     * @param dataModel the data model processed by the template engine to resolve variables
     * @param key the root key expected by the template (this is applied when resolving template variables)
     * @return the rendered content
     */
    String render(String templateClassPath, T dataModel);
}
