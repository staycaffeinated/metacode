<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import lombok.NonNull;

import java.util.List;
import java.util.Optional;

/**
 * A DataStore encapsulates the business rules that concern
 * reading and writing Domain objects to the database.
 * The DataStore passes Domain objects through its API; the
 * underlying Repository and EntityBeans that typically come into play
 * when persisting data are encapsulated.
 *
 * {@code T} is the Domain object type; for instance, {@code DataStore<$lt;>Pet>}
 */
public interface DataStore<T> {
    /**
     * Find the Domain object having the given {@code resourceId}
     */
    Optional<T> findByResourceId(@NonNull String resourceId);

    /**
     * Retrieve all instances of {@code T}.
     */
    List<T> findAll();

    /**
     * Insert {@code item} into the database, returning the persisted representation
     * (for instance, a resourceId is assigned to an object when its persisted; that
     * resourceId is passed back to the client to enable the client to recall the object).
     */
    T save(T item);

    /**
     * Update {@code item} in the database, returning the persisted representation.
     * If an attempt is made to update a non-existent item, the returned {@code Optional} is empty.
     */
    Optional<T> update(T item);

    /**
     * Delete the Domain object that has the given {@code resourceId}.
     * Attempts to delete a non-existent object are quietly ignored.
     */
    void deleteByResourceId(@NonNull String resourceId);
}