/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.stereotype;

/**
 * Stereotype for a template model
 */
public interface MetaTemplateModel {
    
    /*
     * These are the valid values for the top level variable.
     * Template's have variables that look like '${project.someField}'
     * or '${endpoint.someField}'.
     */
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
     * variable in the template models.
     */
    enum Framework {
        WEBMVC ("WEBMVC"),
        WEBFLUX ("WEBFLUX");

        private final String value;
        Framework(String value) { this.value = value; }
        public String value() { return value; }
    }


    String getTopLevelVariable();
}
