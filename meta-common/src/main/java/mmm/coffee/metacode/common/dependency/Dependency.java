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
package mmm.coffee.metacode.common.dependency;

import lombok.Data;
import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * A Dependency maps a 3rd-party library and version.
 * A Java project nearly always uses 3rd-party jars as part of
 * its builds. A collection of Dependency entries enumerates
 * the various 3rd-party libraries (and their versions) that
 * will appear in the templates. For example, template might contain
 * a statement like:
 * <code>
 *     testImplementation ""org.junit.jupiter:junit-jupiter:{{junitVersion}}"
 * </code>
 * A yaml file (usually named "dependencies.yml") defines the values for all
 * these template variables that to be defined. A Dependency object
 * captures each entry from that dependencies.yml file.
 */
@Data
@Generated // exclude this class out from code coverage reports
public class Dependency {

    /**
     * Default constructor
     */
    public Dependency() {}

    /**
     * All-args constructor
     */
    public Dependency(String name, String version) {
        this.name = name;
        this.version = version;
    }

    private String name;
    private String version;
}
