/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.stereotype;

import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * The template model captures any data that will be consumed
 * by the template rendering process to resolve template variables.
 */
@Generated
@SuppressWarnings("java:S125")
// S125: we're OK with comments that look like code since comments may contain code examples
public interface MetaTemplateModel {
    
    /*
     * These are the valid values for the top level variable
     * (returned by {@code getTopLevelVariable()}, below).
     * Template's have variables that look like '${project.someField}'
     * or '${endpoint.someField}'. The top-level variable, in template-speak,
     * is that first token, 'project' or 'endpoint'.
     *
     * This {@code Key} class serves as the source-of-record
     * for any top-level variables that may be defined in any
     * of the templates. This list can grow as needed.
     *
     */
    @Generated
    enum Key {
        PROJECT ("project"),
        ENDPOINT ("endpoint");

        private final String value;
        Key(String value) {
            this.value = value;
        }
        public String value() { return value; }
    }

    /*
     * These are the legal values for the 'framework'
     * variable in the template models. Our current templates
     * use expressions like "if (project.framework == 'WEBFLUX') ...";
     * This {@code Framework} class serves as the source-of-record
     * of the values used by templates.
     */
    @Generated
    enum Framework {
        WEBMVC ("WEBMVC"),
        WEBFLUX ("WEBFLUX");

        private final String value;
        Framework(String value) { this.value = value; }
        public String value() { return value; }

        public final boolean isWebFlux() { return WEBFLUX.value.equals(this.value); }
        public final boolean isWebMvc() { return WEBMVC.value.equals(this.value); }
    }


    String getTopLevelVariable();
}
