<#include "/common/Copyright.ftl">
package ${project.basePackage}.database;

import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

/**
* A generalized NoSQL data store
*/
public interface DataStore<T> {
    /**
     * Find the document having a given resourceId
     */
    Optional<T> findByResourceId(String publicId);

    /**
     * Find all documents of a given type. The collections searched is at the implementors discretion.
     */
    List<T> findAll();

    /**
     * Insert a new document
     * @return a copy of the inserted document
     */
    T create(T object);

    /**
     * Update a document, returning the number of document instances updated
     */
    List<T> update (T pojo);

    Page<T> findByText(@NonNull String text, Pageable pageable);

    /**
     * Delete the documents having this {@code resourceId}.
     * @return the number of documents deleted
     */
    long deleteByResourceId(String resourceId);
}
