/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.spring.project.model.SpringTemplateModel;

/**
 * RestEndpointTemplateModel
 */
@Data
@SuperBuilder
@EqualsAndHashCode(callSuper=false)
@Generated // Ignore code coverage for this class
@SuppressWarnings({"java:S125","java:S116"})
public class RestEndpointTemplateModel extends SpringTemplateModel {
    // When this object is passed into Freemarker,
    // it's assigned a name referred to in Freemarker lingo
    // as the "top level variable".
    private final String topLevelVariable = Key.ENDPOINT.value();

    // The resource name as given by the end-user on the command line.
    private String resource;

    /**
     * The relative path to this endpoint (relative to the base path)
     * For example, the basePath may be "/petstore", with a route to the
     * Pet resource being "/pet", with the fully-qualified path being "/petstore/pet".
     */
    private String route;

    /**
     * The base java package of the application, such as 'org.acme.petstore'
     */
    private String basePackage;

    /**
     * The file system path equivalent of the base package; such as 'org/acme/petstore'
     */
    private String basePackagePath;

    /**
     * The base URL path of the application, such as '/petstore'
     */
    private String basePath;

    /**
     * The resource's base class name, such as 'Pet'
     */
    private String entityName;

    /**
     * The variable name used in source code for instances of the resource
     */
    private String entityVarName;

    /**
     * The entity name in lower case letters. This is used in package names.
     * For example, with a base package 'org.acme.petstore' and a 'Pet' resource,
     * one package name is 'org.acme.petstore.endpoint.pet'; this is the use case
     * where the lower-case version of the entity name gets used.
     */
    private String lowerCaseEntityName;

    /**
     * The name of the POJO class for the resource. The generated code generates
     * a class for the database model (ejbName) and the business model (pojoName).
     */
    private String pojoName;

    /**
     * The name of the database class name for the resource.
     */
    private String ejbName;

    /**
     * The package name into which this endpoint's classes are placed.
     * For example, this might look like 'org.acme.petstore.endpoint.pet',
     * with all Pet-related classes placed there, or 'org.acme.petstore.endpoint.owner'
     * with all Owner-related classes placed there.
     */
    private String packageName;

    /**
     * The file system path equivalent of the package name, say,
     * 'org/acme/petstore/endpoint/pet/'
     */
    private String packagePath;

    /**
     * JPA allows a TableName to be specified with an annotation, when the
     * default table name used by JPA isn't what's wanted. This property
     * sets the value used in the JPA annotation. 
     */
    private String tableName;

    /**
     * Provides the constant names used in the Routes class.
     * The constant names are intentionally sensitive to the
     * entity name to avoid DuplicatedBlocks errors from Sonarqube,
     * which, by default, are Major severity.
     */
    private RouteConstants routeConstants;
}
