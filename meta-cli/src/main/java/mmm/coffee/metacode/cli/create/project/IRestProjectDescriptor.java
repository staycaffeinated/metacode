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
package mmm.coffee.metacode.cli.create.project;

import mmm.coffee.metacode.common.descriptor.Descriptor;

/**
 * Captures the command line inputs provided
 * when creating a REST project
 */
public interface IRestProjectDescriptor extends Descriptor {
    /**
     * the name of the application
     */
    String getApplicationName();
    void setApplicationName(String name);

    /**
     * The groupId, as part of the library coordinates.
     * This defaults to the base package.
     */
    String getGroupId();
    void setGroupId(String groupId);

    /**
     * The base Java package for the generated Java code
     * @return the base Java package
     */
    String getBasePackage();
    void setBasePackage(String basePackage);

    /**
     * The base URL of the REST service.
     * For example, "/petstore/v1"
     * @return  the base URL of the REST service
     */
    String getBasePath();
    void setBasePath(String path);
}
