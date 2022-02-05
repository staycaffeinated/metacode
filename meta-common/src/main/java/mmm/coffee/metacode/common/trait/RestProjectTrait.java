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
package mmm.coffee.metacode.common.trait;

import lombok.NonNull;
import lombok.experimental.NonFinal;

/**
 * Basic traits of a REST web service project
 */
public interface RestProjectTrait extends MetaSpecification, DryRunTrait {

    /**
     * The application name; sometimes doubles as the project name
     */
    String getApplicationName();
    void setApplicationName(@NonNull String applicationName);

    /**
     * The base URL of the deployed web service. For example: {@code /petstore}
     * Other endpoints within the web service will be relative to this base URL.
     */
    String getBasePath();
    void setBasePath(@NonFinal String basePath);

    /**
     * The base Java package of the generated code. For example: {@code com.acme.petstore}
     */
    String getBasePackage();
    void setBasePackage(@NonNull String basePackage);
}
