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
package mmm.coffee.metacode.common.descriptor;

import lombok.Builder;
import lombok.Data;
import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * Meta-data of a REST project. This object captures the
 * command-line arguments and is passed onto the CodeGenerator.
 * Anything the Code
 */
@Data
@Builder
@Generated // exclude from code coverage reports
public class RestProjectDescriptor implements Descriptor {
    private String basePackage;
    private String applicationName;
    private String basePath;
}
