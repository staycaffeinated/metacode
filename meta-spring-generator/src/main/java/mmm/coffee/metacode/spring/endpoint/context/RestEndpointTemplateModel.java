/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.context;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.spring.model.SpringTemplateModel;

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
    private final String topLevelVariable = Key.PROJECT.value();

    private String resource;

    /**
     * The relative path to this endpoint (relative to the base path)
     * For example, the basePath may be "/petstore", with a route to the
     * Pet resource being "/pet", with the fully-qualified path being "/petstore/pet".
     */
    private String route;

    private String basePackage;

    private String basePackagePath;

    // The base path of the parent,
    private String basePath;

    private String entityName;

    private String entityVarName;

    private String lowerCaseEntityName;
}
