/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.context;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;

/**
 * RestEndpointTemplateModel
 */
@Data
@SuperBuilder
@Generated // Ignore code coverage for this class
@SuppressWarnings({"java:S125","java:S116"})
public class RestEndpointTemplateModel implements MetaTemplateModel {
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

    // This field has a custom setter method, so tell lombok not to create a setter
    @Setter(AccessLevel.NONE)
    private boolean isWebFlux;

    // This field has a custom setter method, so tell lombok not to create a setter
    @Setter(AccessLevel.NONE)
    private boolean isWebMvc;

    @Setter(AccessLevel.PRIVATE)
    @Getter(AccessLevel.NONE)   // we provide a custom getter
    private String framework;

    /**
     * Toggle the Spring WebFlux flag which, when true,
     * directs the code generator to use spring-webflux libraries
     */
    public void isWebFlux(boolean value) {
        this.isWebFlux = value;
        if (value) setFramework(Framework.WEBFLUX.value());
    }

    /**
     * Toggle the Spring WebMvc flag which, when true,
     * directs the code generator to use spring-webmvc libraries
     */
    public void isWebMvc(boolean value) {
        this.isWebMvc = value;
        if (value) setFramework (Framework.WEBMVC.value());
    }
    /**
     * Returns the framework (WebMvc or WebFlux).
     * WebFlux is the default framework
     * @return the framework, with WebFlux as the default
     */
    public final String getFramework() {
        if (isWebMvc) return Framework.WEBMVC.value();
        return Framework.WEBFLUX.value();
    }
}
