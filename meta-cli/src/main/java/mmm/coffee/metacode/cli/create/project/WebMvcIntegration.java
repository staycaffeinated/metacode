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

/**
 * Integrations supported by the spring-webmvc code generator
 */
public enum WebMvcIntegration {
    POSTGRES ("postgres"),
    LIQUIBASE ("liquibase"),
    TESTCONTAINERS ("testcontainers")
            ;

    // This is the value an end-user enters on the command line.
    private final String value;

    WebMvcIntegration(String name) {
        this.value = name;
    }

    @Override
    public String toString() { return value; }

}
