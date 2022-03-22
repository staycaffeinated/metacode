/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.converter;

import lombok.NonNull;

import java.util.Locale;

/**
 * RouteConstantsConverter builds the constants names that appear in the (generated) Routes class
 */
public class RouteConstantsConverter {

    private String resourceNameUpperCase;

    public RouteConstantsConverter() {
        // empty
    }

    public void setResourceName(@NonNull String resourceName) {
        this.resourceNameUpperCase = resourceName.toUpperCase(Locale.ROOT);
    }


    public String basePath() { return "BASE_PATH_" + resourceNameUpperCase; }
    public String create() { return "CREATE_" + resourceNameUpperCase; }
    public String findOne() { return "FIND_ONE_" + resourceNameUpperCase; }
    public String findAll() { return "FIND_ALL_" + resourceNameUpperCase; }
    public String search() { return "SEARCH_" + resourceNameUpperCase; }
    public String idParameter() { return resourceNameUpperCase + "_ID"; }
    public String stream() { return "STREAM_" + resourceNameUpperCase;  }
    public String update() { return "UPDATE_" + resourceNameUpperCase; }
    public String delete() { return "DELETE_" + resourceNameUpperCase; }
    public String events() { return "EVENTS_" + resourceNameUpperCase; }
}
