/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.descriptor;

import lombok.NonNull;
import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * An enumeration of the frameworks currently supported
 * by the MetaCode code generator
 */
@Generated // exclude from code coverage
public enum Framework {
    SPRING_WEBMVC("WEBMVC"),
    SPRING_WEBFLUX("WEBFLUX"),
    SPRING_BOOT("SPRING-BOOT"),
    SPRING_BATCH("SPRING-BATCH"),
    UNDEFINED("UNDEFINED");
    // Future frameworks include: Micronaut, Quarkus, VueJS, and React

    private String frameworkName;
    Framework(@NonNull String frameworkName) {
        this.frameworkName = frameworkName;
    }
    public final String frameworkName() { return frameworkName; }

    public static final Framework toFramework(String s) {
        if (s == null) return UNDEFINED;
        if (s.equals("WEBMVC")) return SPRING_WEBMVC;
        if (s.equals("WEBFLUX")) return SPRING_WEBFLUX;
        return UNDEFINED;
    }

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
