/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.constant;

import com.samskivert.mustache.Mustache;

/**
 * MustacheConstants
 */
public class MustacheConstants {
    /*
     * These are the variables found in mustache expressions, which are used by CatalgoEntry's
     * Within the sundry catalog files, mustache expressions are used so the destination file
     * to which a template is written can be flexible to the command line inputs.
     * For instance, if the base package is, say, 'acme.petstore', the base package needs to
     * be resolved to an equivalent file directory (say, 'src/main/java/acme/petstore').
     * We accomplish that flexibility  with mustach expressions.  The variables used in any
     * mustache expression are defined here so we can make sure values are assigned to each variable.
     */
    public static final String BASE_PACKAGE_PATH = "basePackagePath";
    public static final String BASE_PACKAGE = "basePackage";
    public static final String BASE_PATH = "basePath";

    // These only apply to endpoint assets; these remain undefined for project assets.
    public static final String ENTITY_NAME = "entityName";
    public static final String ENTITY_VAR_NAME = "entityVarName";
    public static final String ENTITY_NAME_LOWERCASE = "lowerCaseEntityName";


    // Hidden constructor
    private MustacheConstants() {}

}
