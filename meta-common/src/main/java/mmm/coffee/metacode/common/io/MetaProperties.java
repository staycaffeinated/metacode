/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

/**
 * MetaProperties
 */
public class MetaProperties {
    public static final String DEFAULT_FILENAME = "metacode.properties";

    public static final String BASE_PATH = "basePath";
    public static final String BASE_PACKAGE = "basePackage";
    public static final String FRAMEWORK = "framework";
    public static final String ADD_TESTCONTAINERS = "add.testcontainers";
    public static final String ADD_POSTGRESQL = "add.postgres";
    public static final String ADD_LIQUIBASE = "add.liquibase";

    // hidden constructor
    private MetaProperties() {
        // empty
    }
}
