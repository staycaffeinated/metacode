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
    Mono<${endpoint.entityName}> update${endpoint.entityName}(${endpoint.pojoName} resource );

    /**
     * Delete
     */
    Mono<Long> delete${endpoint.entityName}ByResourceId(String id);

    /**
     * Find the POJO having the given resourceId
     */
    Mono<${endpoint.entityName}> findByResourceId(String id) throws ResourceNotFoundException;

}
