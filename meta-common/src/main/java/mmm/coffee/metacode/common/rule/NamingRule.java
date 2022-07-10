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
package mmm.coffee.metacode.common.rule;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * The syntax rules we apply to resource names, entity names, base paths, and such
 * to provide consistent naming conventions
 */
public class NamingRule {
    /**
     * This is the suffix applied to the entity bean class.
     * For example, for the 'Pet' object, the entity bean class is 'PetEntity'.
     */
    public static final String EJB_SUFFIX = "Entity";

    // Hidden constructor
    private NamingRule() {}

    /**
     * Returns the syntax convention for the given resource value
     * @param resource the name of some resource or entity
     * @return the resource name, with syntax conventions applied
     */
    public static @NonNull String toEntityName(@NonNull String resource) {
        // Capitalize the first char; leave others as-is
        return StringUtils.capitalize(resource);
    }

    public static @NonNull String toEntityNameUpperCase(@NonNull String resource) {
        // Capitalize everything
        return resource.toUpperCase(Locale.ROOT);
    }
    public static @NonNull String toEntityVariableName(@NonNull String resource) {
        return StringUtils.uncapitalize(resource);
    }

    public static String toBasePackagePath(@NonNull String basePackage) {
        return StringUtils.replace(basePackage, ".", "/");
    }

    /**
     * Base path must begin with a forward-slash ('/'), and the route must be
     * all lower-case characters.
     *
     * @param route the suggested base path to the resource, probably input by an end-user
     * @return the basePath, with formatting conventions applied.
     */
    public static @NonNull String toBasePathUrl(@NonNull String route) {
        if (!route.startsWith("/")) {
            route = "/" + route;
        }
        return StringUtils.toRootLowerCase(route);
    }

    /**
     * The dabase schema for JPA projects. Use this if a schema wasn't explicitly
     * defined on the command line
     * @param projectName the project name
     * @return the default schema name
     */
    public static @NonNull String toDatabaseSchemaName(@NonNull String projectName) {
        return StringUtils.toRootLowerCase(projectName);
    }

    public static String toPojoClassName(String resourceName) {
        return StringUtils.capitalize(resourceName);
    }

    public static String toEjbClassName(String resourceName) {
        return StringUtils.capitalize(resourceName) + EJB_SUFFIX;
    }

    public static String toTableName(String resourceName) {
        return resourceName;
    }

    /**
     * Conjures the package name to which the code for the given RESTful resource (aka Entity) will be written.
     * The convention followed is: {basePackage}.endpoint.{lowercaseVersionOfResourceName}.
     * For example, if the basePackage is ```org.example.greeting_service``` and the resource is ```Greeting```,
     * the package name returned will be ```
     * ```org.example.greeting_service.endpoint.greeting```.
     * (where ```org.example.greeting_service``` is the assumed base package, and ```Greeting``` is the resource/entity.
     *
     * @param basePackage the base package of the project
     * @param resourceOrEntityName the name of the RESTful resource/entity, for example ```Student``` or ```Account```
     * @return the package name into which the assets of this resource will be placed
     */
    @NonNull
    public static String toEndpointPackageName (@NonNull String basePackage, @NonNull String resourceOrEntityName) {
        String packageName = basePackage + ".endpoint." + StringUtils.toRootLowerCase(resourceOrEntityName);
        return StringUtils.toRootLowerCase(packageName);
    }
}
