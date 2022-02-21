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
package mmm.coffee.metacode.spring.project.context;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * This is the 'Model' object used by Freemarker to resolve template variables.
 * Any variable that might be used in a project-related template must have a
 * corresponding field within this POJO.
 */
@Data
@SuperBuilder
@Generated // Ignore code coverage for this class
public class RestProjectTemplateModel {
    private String applicationName;
    private String basePath;
    private String basePackage;

    private boolean isWebFlux;
    private boolean isWebMvc;
}
