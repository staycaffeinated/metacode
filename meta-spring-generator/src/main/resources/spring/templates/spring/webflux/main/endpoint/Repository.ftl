<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ${endpoint.entityName}Repository extends ReactiveSortingRepository<${endpoint.ejbName}, Long> {

    // Find by the resource ID known by external applications
    Mono<${endpoint.ejbName}> findByResourceId ( Long id );

    // Find by the database ID
    Mono<${endpoint.ejbName}> findById ( Long id );


    /* returns the number of entities deleted */
    Mono<Long> deleteByResourceId( Long id );
    

    Flux<${endpoint.ejbName}> findAllByText(String text);
}

