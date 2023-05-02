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
package mmm.coffee.metacode.common.descriptor;

import lombok.*;
import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * RestEndpointDescriptor
 */
@Data
@Builder
@Generated // exclude from code coverage reports
public class RestEndpointDescriptor implements Descriptor {

    /**
     * The resource provided by this endpoint, such as Pet or Owner
     */
    private String resource;

    /**
     * The URL path of this endpoint (relative to the REST service's base path).
     * For example, {@code "/pets"} or {@code "/owners"}.
     */
    private String route;

    /**
     * The base package of the project; for example, 'acme.petstore'
     */
    private String basePackage;

    /**
     * The base path of the root application
     */
    private String basePath;

    /**
     * Indicates whether this is webflux or webmvc
     */
    private String framework;

    /**
     * Optional: the database schema
     */
    private String schema;

    private boolean withPostgres;
    private boolean withTestContainers;
    private boolean withLiquibase;
    private boolean withMongoDb;


    public final String getFramework() {
        if (framework != null) {
            return framework;
        }
        return Framework.UNDEFINED.frameworkName();
    }

    public final boolean isWebMvc() {
        return framework != null && Framework.toFramework(framework).isWebMvc();
    }
    public final boolean isWebFlux() {
        return framework != null && Framework.toFramework(framework).isWebFlux();
    }
}
