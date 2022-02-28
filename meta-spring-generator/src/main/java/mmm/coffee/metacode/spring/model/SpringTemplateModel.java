/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.model;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;


/**
 * SpringTemplateModel
 */
@SuperBuilder
@Generated // exclude this class from code coverage reports
public abstract class SpringTemplateModel implements MetaTemplateModel {

    // This field has a custom setter method, so tell lombok not to create a setter
    @Setter(AccessLevel.PROTECTED)
    private boolean isWebFlux;

    // This field has a custom setter method, so tell lombok not to create a setter
    @Setter(AccessLevel.PROTECTED)
    private boolean isWebMvc;

    @Setter(AccessLevel.PROTECTED)
    @Getter(AccessLevel.NONE)       // we provide a custom getter
    private String framework;

    /**
     * Toggle the Spring WebFlux flag which, when true,
     * directs the code generator to use spring-webflux libraries
     */
    public void isWebFlux(boolean value) {
        this.isWebFlux = value;
        if (value) setFramework(Framework.WEBFLUX.value());
    }

    public boolean isWebFlux() {
        return this.isWebFlux;
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
