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
    private String framework;       // This is a String because the templates expect a string
                                    // Future task: refactor to use the Enum here, and have
                                    // templates use 'framework.isWebFlux', 'framework.isWebMvc'

    /**
     * Returns {@code true} if {@code framework} is spring-webflux
     */
    public final boolean isWebFlux() {
        return Framework.WEBFLUX.value().equals(framework);
    }

    /**
     * Returns {@code true} if {@code framework} is spring-webmvc
     */
    public final boolean isWebMvc() {
        return Framework.WEBMVC.value().equals(framework);
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
