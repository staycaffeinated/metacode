<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import java.util.Set;

/**
 * Common methods to be supported by the implementor of a DataStore
 *
 * {@code B} is the Document type (the NoSQL equivalent of an entity bean;
 * for example {@code CrudAware<PetDocument>}, or {@code CrudAware<PersonDocument>}.
 */
public interface CrudAware<B> {
    /**
     * Insert a new document into the database
     */
    B create(B document);

    /**
     * Updates the matching documents; returns the number of documents updated
     */
    long update(B document);

    /**
     * Fetch the entity having the given {@code resourceId}
     */
    Set<B> findByResourceId(String resourceId);

    /**
     * Find all of a particular kind of entity
     */
    Set<B> findAll();

    /**
     * Remove the given {@code entity}, if it exists.
     * Requests to delete an entity that does not exist are quietly ignored.
     */
    void delete(B document);
}