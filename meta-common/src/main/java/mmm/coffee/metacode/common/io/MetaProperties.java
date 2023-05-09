/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

/**
 * MetaProperties
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class MetaProperties {
    public static final String DEFAULT_FILENAME = "metacode.properties";

    public static final String BASE_PATH = "basePath";
    public static final String BASE_PACKAGE = "basePackage";
    public static final String FRAMEWORK = "framework";
    public static final String SCHEMA = "schema";
    public static final String ADD_TESTCONTAINERS = "add.testcontainers";
    public static final String ADD_POSTGRESQL = "add.postgres";
    public static final String ADD_LIQUIBASE = "add.liquibase";
    public static final String ADD_MONGODB = "add.mongodb";
    public static final String ADD_OPENAPI = "add.openapi";
}
