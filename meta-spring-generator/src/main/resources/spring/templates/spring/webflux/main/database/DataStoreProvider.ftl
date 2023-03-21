<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.exception.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;

/**
 * An Adapter for the persistence of ${endpoint.entityName}s
 */
@Component
@Builder
@RequiredArgsConstructor
@Slf4j
public class ${endpoint.entityName}DataStoreProvider implements ${endpoint.entityName}DataStore {
    private final ${endpoint.entityName}Repository repository;
    private final ${endpoint.entityName}EntityToPojoConverter ejbToPojoConverter;
    private final ${endpoint.entityName}PojoToEntityConverter pojoToEjbConverter;
    private final SecureRandomSeries secureRandom;

    /**
     * create
     */
    public Mono<String> create${endpoint.entityName}(${endpoint.pojoName} pojo) {
        ${endpoint.ejbName} entity = pojoToEjbConverter.convert(pojo);
        if (entity == null) {
            log.error("This POJO yielded a null value when converted to an entity bean: {}", pojo);
            throw new UnprocessableEntityException();
        }
        entity.setResourceId(secureRandom.nextResourceId());
        return repository.save(entity).flatMap(item -> Mono.just(item.getResourceId()));
    }

    /**
     * update
     */
    public Mono<${endpoint.entityName}> update${endpoint.entityName}(${endpoint.pojoName} resource) {
		    return repository.findByResourceId(resource.getResourceId())
				    .switchIfEmpty(Mono.error(new ResourceNotFoundException()))
    				.flatMap(found -> repository.save(found.copyMutableFieldsFrom(resource)))
		    		.mapNotNull(ejbToPojoConverter::convert);
    }

    /**
     * findByResourceId
     */
    @Override
    public Mono<${endpoint.pojoName}> findByResourceId(String id) {
        Mono<${endpoint.ejbName}> monoItem = repository.findByResourceId(id) .switchIfEmpty(Mono.defer(() -> Mono.error(
                    new ResourceNotFoundException(String.format("Entity not found with the given resource ID: %s", id)))));
        return monoItem.flatMap(it -> Mono.just(Objects.requireNonNull(ejbToPojoConverter.convert(it))));
    }

    /**
     * findById
     */
    @Override
    public Mono<${endpoint.pojoName}> findById(Long id) {
        Mono<${endpoint.ejbName}> monoItem = repository.findById(id).switchIfEmpty(Mono.defer(() -> Mono.error(
            new ResourceNotFoundException(String.format("Entity not found with the given database ID: %s", id)))));
        return monoItem.flatMap(it -> Mono.just(Objects.requireNonNull(ejbToPojoConverter.convert(it))));
    }

    /**
     * deleteByResourceId 
     * @return the number of rows deleted
     */
    @Override
    public Mono<Long> deleteByResourceId(String id) {
        return repository.deleteByResourceId(id);
    }

    /**
     * findAllByText
     */
    @Override
    public Flux<${endpoint.pojoName}> findAllByText(String text) {
        return Flux.from(repository.findAllByText(text).mapNotNull(ejbToPojoConverter::convert));
    }

    /**
     * findAll
     */
    public Flux<${endpoint.pojoName}> findAll() {
        return Flux.from(repository.findAll().mapNotNull(ejbToPojoConverter::convert));
    }
}
