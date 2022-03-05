/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.converter;

import lombok.NonNull;
import org.apache.commons.lang3.StringUtils;

import java.util.Locale;

/**
 * A collection of methods for converting names
 * of items, such as converting a resource name to
 * an entity-bean name. The conversions are used to
 * name classes, instance variables, and sundry items
 * that come up during code generation.
 *
 * This is a stateless object (there are no instance variables).
 * As such, every method _could_ be defined as a static method.
 * To enable dependency injection, non-static methods are used instead.
 */
public class NameConverter {

    private static final String EJB_SUFFIX = "EntityBean";

    /**
     * Default constructor
     */
    public NameConverter() {
        // empty
    }

    /**
     * Returns the syntax convention for the given resource value
     * @param resource the name of some resource or entity
     * @return the resource name, with syntax conventions applied
     */
    public String toEntityName(@NonNull String resource) {
        // Capitalize the first char; leave others as-is
        return StringUtils.capitalize(resource);

    }
    public String toEntityVariableName(@NonNull String resource) {
        return StringUtils.uncapitalize(resource);
    }

    public String toLowerCaseEntityName(@NonNull String resource) { return resource.toLowerCase(Locale.ROOT); }

    /**
     * Base path must begin with a forward-slash ('/'), and the route must be
     * all lower-case characters.
     *
     * @param route the suggested base path to the resource, probably input by an end-user
     * @return the basePath, with formatting conventions applied.
     */
    public String toBasePathUrl(@NonNull String route) {
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
    public String toDatabaseSchemaName(@NonNull String projectName) {
        return StringUtils.toRootLowerCase(projectName);
    }

    public String toPojoClassName(String resourceName) {
        return StringUtils.capitalize(resourceName);
    }

    public String toEjbClassName(String resourceName) {
        return StringUtils.capitalize(resourceName) + EJB_SUFFIX;
    }

    public String toTableName(String resourceName) {
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
    public  String toEndpointPackageName (@NonNull String basePackage, @NonNull String resourceOrEntityName) {
        String packageName = basePackage + ".endpoint." + StringUtils.toRootLowerCase(resourceOrEntityName);
        return StringUtils.toRootLowerCase(packageName);
    }

    /**
     * Converts a package name to its equivalent file system path. This method is used to enable
     * writing Java files in the correct directory.
     * 
     * @param packageName a Java package, such as 'org.acme.petstore'
     * @return the equivalent file system path, such as 'org/example/petstore'
     */
    public String packageNameToPath(@NonNull String packageName) { return packageName.replace(".", "/"); }
}
