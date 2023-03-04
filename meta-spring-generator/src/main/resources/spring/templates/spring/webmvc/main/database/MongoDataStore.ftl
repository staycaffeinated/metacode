<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import ${project.basePackage}.exception.*;

import java.util.List;

/**
* A generalized NoSQL data store
*/
public interface DataStore<T> {
    /**
     * Find all documents having a given resourceId
     */
    List<T> findByResourceId(String publicId) throws ResourceNotFoundException;

    /**
     * Find all documents of a given type. The collections searched is at the implementors discretion.
     */
    List<T> findAll();

    /**
     * Insert a new document
     */
    T create(T object);

    /**
     * Remove a document
     */
    long delete (T pojo);

    /**
     * Update a document, returning the number of document instances updated
     */
    List<T> update (T pojo);
}
