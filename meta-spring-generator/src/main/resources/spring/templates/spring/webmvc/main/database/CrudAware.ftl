<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import java.util.List;
import java.util.Optional;

/**
 * A generalized NoSQL data store
 */
public interface DataStore<T> {
    /**
     * Find all documents having a given resourceId
     */
    Optional<T> findByResourceId(String publicId);

    /**
     * Find all documents of a given type. The collections searched is at the
     * implementors discretion.
     */
    List<T> findAll();

    /**
     * Insert a new document
     */
    T create(T object);

    /**
     * Remove the documents having the same resourceId as {@code pojo}.
     */
    long delete(T pojo);

    /**
     * Update the documents having the same resourceId as {@code pojo}.
     *
     * @return a list of the updated records
     */
    List<T> update(T pojo);
}



