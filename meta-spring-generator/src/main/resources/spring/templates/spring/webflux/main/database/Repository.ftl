<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ${endpoint.entityName}Repository extends ReactiveSortingRepository<${endpoint.ejbName}, Long>, ReactiveCrudRepository<${endpoint.ejbName}, Long> {

    // Find by the resource ID known by external applications
    Mono<${endpoint.ejbName}> findByResourceId ( String id );

    // Find by the database ID
    Mono<${endpoint.ejbName}> findById ( Long id );

    /* returns the number of entities deleted */
    Mono<Long> deleteByResourceId( String id );

    Flux<${endpoint.ejbName}> findAllByText(String text);
}

