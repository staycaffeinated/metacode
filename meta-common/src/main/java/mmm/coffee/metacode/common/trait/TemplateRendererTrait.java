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
package mmm.coffee.metacode.common.trait;

/**
 * Functional interface for template resolution.
 */
public interface TemplateRendererTrait<T> {
    /**
     * Renders a Template.
     * @param templateClassPath the path to the template file (a freemarker FTL file, for example)
     * @param dataModelObject The dataModel used by the Template instance to resolve variables.
     *
     * @return
     */
    String render(String templateClassPath, T dataModelObject);
}
