<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.exception.ResourceNotFoundException;
import ${endpoint.basePackage}.exception.UnprocessableEntityException;


import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ${endpoint.entityName}Service {

    /*
     * findAll
     */
    Flux<${endpoint.pojoName}> findAll${endpoint.entityName}s();

    /**
     * findByResourceId
     */
    Mono<${endpoint.pojoName}> find${endpoint.entityName}ByResourceId(String id) throws ResourceNotFoundException;

    /*
     * findAllByText
     */
    Flux<${endpoint.pojoName}> findAllByText(String text);

    /**
     * Create
     */
    Mono<String> create${endpoint.entityName}(${endpoint.pojoName} resource );

    /**
     * Update
     */
    void update${endpoint.entityName}(${endpoint.pojoName} resource );

    /**
     * Delete
     */
    void delete${endpoint.entityName}ByResourceId(String id);

    /**
     * Find the EJB having the given resourceId
     */
    Mono<${endpoint.ejbName}> findByResourceId(String id) throws ResourceNotFoundException;

}
