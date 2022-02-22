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
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;

import java.util.HashSet;
import java.util.Set;

/**
 * Meta-data of a REST project. This object captures the
 * command-line arguments and is passed onto the CodeGenerator.
 * Anything the Code
 */
@Data
@SuperBuilder
@Generated // exclude from code coverage reports
public class RestProjectDescriptor implements Descriptor {
    private String basePackage;
    private String applicationName;
    private String basePath;
    private String groupId;

    /*
     * The integrations to support in the generated code.
     * For example, if integration with Postgres is selected,
     * then Postgres-specific properties are included in the
     * generated application.properties file.
     *
     * If integration with Liquibase is selected, then default
     * liquibase files are generated.
     *
     * NB: Lombok allows @Singular annotation and @Builder.Default.
     * The @Singular creates an immutable list; the collection must be
     * populated before build() is called; after build() is called, the
     * collection becomes immutable.  With @Builder.Default, we
     * initialize the collection, and lobmok does not generate methods
     * to add to the collection while building the object. After build()
     * is called, we can call getIntegration().add(...), and we do
     * have a mutable collection.
     *
     * See https://projectlombok.org/features/Builder#singular
     */
    @Builder.Default private Set<String> integrations = new HashSet<>();
}
