/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.descriptor;

import lombok.NonNull;

/**
 * An enumeration of the frameworks currently supported
 * by the MetaCode code generator
 */
public enum Framework {
    SPRING_WEBMVC("WEBMVC"),
    SPRING_WEBFLUX("WEBFLUX"),
    UNDEFINED("UNDEFINED");
    // Future frameworks include: Micronaut, Quarkus, VueJS, and React

    private String frameworkName;
    Framework(@NonNull String frameworkName) {
        this.frameworkName = frameworkName;
    }
    public final String frameworkName() { return frameworkName; }

    public boolean isWebMvc() {
        return SPRING_WEBMVC.frameworkName.equals(frameworkName);
    }
    public boolean isWebFlux() {
        return SPRING_WEBFLUX.frameworkName.equals(frameworkName);
    }
    public boolean isUndefined() {
        return UNDEFINED.frameworkName.equals(frameworkName);
    }
}
