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
     * This code base passes a POJO to Freemarker templates
     * as the approach to passing template variables. In the
     * template variable names, we use property names that
     * are either `${project.whateverPropertyName}` or `${endpoint.whateverPropertyName}`.
     * The two `Key` values shown in this class are those two prefixes:
     * `project` or `endpoint`. In template-speak, these are
     * `top-level variables`, since they are the first token.
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

    String getTopLevelVariable();
}
