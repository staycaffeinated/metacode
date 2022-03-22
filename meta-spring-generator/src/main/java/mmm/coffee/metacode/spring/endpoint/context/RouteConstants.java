/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.context;

import lombok.Data;
import lombok.experimental.SuperBuilder;
import mmm.coffee.metacode.annotations.jacoco.Generated;

/**
 * Defines the constant names plugged into the {whatever}Routes.java class.
 *
 * Consider: if an endpoint for a 'Pet' resource is created, the
 * generator produces a PetRoutes.java class; that class contains constants
 * for the different URL routes, like '/pet/findAll' or '/pet/{id}'
 * or '/pet/ws/events'. This, the PetRoutes class will include constants like:
 * <code>
 *  BASE_PATH_PET = "/pet";
 *  FIND_ALL_PET = "/pet/findAll";
 * </code>
 * and so on.
 *
 * Creating constants whose names include the resourceName may seem like
 * overkill, but if all the constants in each of the **Routes.java classes
 * use the same name, say
 * <code>
 *  (from a hypothetical PetRoutes.java...)
 *      PetRoutes.BASE_PATH = "/pet";
 *      PetRouts.FIND_ALL = "/pet/findAll";
 *
 *  (from a hypothetical StoreRoutes.java...)
 *      StoreRoutes.BASE_PATH = "/store";
 *      StoreRoutes.FIND_ALL = "/store/findAll"
 * </code>
 * SonarQube will flag those lines as duplicate code blocks, since the constant names
 * are the same _and_ despite the constant names being in different classes and having
 * different values. By default, Sonarqube treats duplicate blocks as a Major error
 * which leads to a "Failed" grade from Sonarqube. Using a @SuppressWarnings does not
 * work in the case; by design, Sonarqube will not ignore duplicate blocks unless
 * it's instructed to do so in from the Sonarqube UI.
 *
 */
@SuperBuilder
@Data
@Generated  // code coverage not needed for a lombok-generated POJO
public class RouteConstants {
    String basePath;    // BASE_PATH_entityNameUpperCase
    String create;      // CREATE_entityNameUpperCase
    String findOne;     // FIND_ONE_entityNameUpperCase
    String findAll;     // FIND_ALL_entityNameUpperCase
    String search;      // SEARCH_entityNameUpperCase
    String idParameter; // entityNameUpperCase_ID
    String stream;      // STREAM_entityNameUpperCase
    String update;      // UPDATE_entityNameUpperCase
    String delete;      // DELETE_entityNameUpperCase
    String events;      // GET_entityNameUpperCase_EVENTS
}
