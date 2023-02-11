<#include "/common/Copyright.ftl">

package ${project.basePackage}.database;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

/**
 * Common methods to be supported by the implementor of a DataStore
 *
 * {@code B} is the EntityBean type; for example {@code CrudAware<PetEntity>}.
 */
public interface CrudAware<B> {
    /**
     * Save the entity to the database
     */
    B save(B object);

    /**
     * Fetch the entity having the given {@code resourceId}
     */
    Optional<B> findByResourceId(String resourceId);

    /**
     * Find all of a particular kind of entity
     */
    List<B> findAll();

    /**
     * Find all entities that satisfy the {@code specification}
     */
    Page<B> findAll(Specification<B> specification, Pageable pageable);

    /**
     * Remove the given {@code entity}, if it exists.
     * Requests to delete an entity that does not exist are quietly ignored.
     */
    void delete(B entity);
}


