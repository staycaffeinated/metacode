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

    private final String frameworkName;
    Framework(@NonNull String frameworkName) {
        this.frameworkName = frameworkName;
    }
    public final String frameworkName() { return frameworkName; }

    public static Framework toFramework(String s) {
        if (s == null) return UNDEFINED;
        return switch (s) {
            case "WEBMVC" -> SPRING_WEBMVC;
            case "WEBFLUX" -> SPRING_WEBFLUX;
            case "SPRING-BATCH" -> SPRING_BATCH;
            case "SPRING-BOOT" -> SPRING_BOOT;
            default -> UNDEFINED;
        };
    }

    public boolean isWebMvc() {
        return SPRING_WEBMVC.frameworkName.equals(frameworkName);
    }
    public boolean isWebFlux() {
        return SPRING_WEBFLUX.frameworkName.equals(frameworkName);
    }

    public boolean isSpringBatch() { return SPRING_BATCH.frameworkName.equals(frameworkName); }
    public boolean isSpringBoot() { return SPRING_BOOT.frameworkName.equals(frameworkName); }
    
    public boolean isUndefined() {
        return UNDEFINED.frameworkName.equals(frameworkName);
    }
}
