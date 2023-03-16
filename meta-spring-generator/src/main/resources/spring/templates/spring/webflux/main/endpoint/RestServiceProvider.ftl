<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;
import ${endpoint.basePackage}.exception.ResourceNotFoundException;
import ${endpoint.basePackage}.exception.UnprocessableEntityException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@Service
public class ${endpoint.entityName}ServiceProvider implements ${endpoint.entityName}Service {

    private final ApplicationEventPublisher publisher;
    private final ConversionService conversionService;
    private final ${endpoint.entityName}Repository repository;
    private final SecureRandomSeries secureRandom;

    /*
    * Constructor
    */
    @Autowired
    public ${endpoint.entityName}ServiceProvider(${endpoint.entityName}Repository ${endpoint.entityVarName}Repository,
            @Qualifier("${endpoint.entityVarName}Converter")ConversionService conversionService,
            ApplicationEventPublisher publisher,
            SecureRandomSeries secureRandom) {
        this.repository = ${endpoint.entityVarName}Repository;
        this.conversionService = conversionService;
        this.publisher = publisher;
        this.secureRandom = secureRandom;
    }

    /*
     * findAll
     */
    public Flux<${endpoint.pojoName}> findAll${endpoint.entityName}s() {
        return Flux.from(repository.findAll().map(ejb -> conversionService.convert(ejb, ${endpoint.pojoName}.class)));
    }

    /**
     * findByResourceId
     */
    public Mono<${endpoint.pojoName}> find${endpoint.entityName}ByResourceId(String id) throws ResourceNotFoundException {
        Mono<${endpoint.ejbName}> monoItem = findByResourceId(id);
        return monoItem.flatMap(it -> Mono.just(conversionService.convert(it, ${endpoint.pojoName}.class)));
    }

    /*
     * findAllByText
     */
    public Flux<${endpoint.pojoName}> findAllByText(@NonNull String text) {
        return Flux.from(repository.findAllByText(text).map(ejb -> conversionService.convert(ejb, ${endpoint.pojoName}.class)));
    }

    /**
     * Create
     */
    public Mono<String> create${endpoint.entityName}( @NonNull @Validated(OnCreate.class) ${endpoint.pojoName} resource ) {
        ${endpoint.ejbName} entity = conversionService.convert(resource, ${endpoint.ejbName}.class);
        if (entity == null) {
            log.error("This POJO yielded a null value when converted to an entity bean: {}", resource);
            throw new UnprocessableEntityException();
        }
        entity.setResourceId(secureRandom.nextResourceId());
        return repository.save(entity)
            .doOnSuccess(item -> publishEvent(${endpoint.entityName}Event.CREATED, item))
            .flatMap(item -> Mono.just(item.getResourceId()));
    }

    /**
     * Update
     */
    public void update${endpoint.entityName}( @NonNull @Validated(OnUpdate.class) ${endpoint.pojoName} resource ) {
        Mono<${endpoint.ejbName}> entityBean = findByResourceId(resource.getResourceId());
        entityBean.subscribe(value -> {
        // As fields are added to the entity, this block has to be updated
        value.setText(resource.getText());

        repository.save(value).doOnSuccess(item -> publishEvent(${endpoint.entityName}Event.UPDATED, item)).subscribe();
        });
    }

    /**
     * Delete
     */
    public void delete${endpoint.entityName}ByResourceId(@NonNull String id) {
        repository.deleteByResourceId(id).doOnSuccess(item -> publishDeleteEvent(${endpoint.entityName}Event.DELETED, id)).subscribe();
    }

    /**
     * Find the Document having the given resourceId
     */
    public Mono<${endpoint.ejbName}> findByResourceId(String id) throws ResourceNotFoundException {
        return repository.findByResourceId(id).switchIfEmpty(Mono.defer(() -> Mono.error(
            new ResourceNotFoundException(String.format("Entity not found with the given resourceId: %s", id)))));
    }

    /**
     * Publish events
     */
    private void publishEvent(String event, ${endpoint.ejbName} entity) {
        log.debug("publishEvent: {}, resourceId: {}", event, entity.getResourceId());
        this.publisher.publishEvent(new ${endpoint.entityName}Event(event, conversionService.convert(entity, ${endpoint.pojoName}.class)));
    }

    private void publishDeleteEvent(String event, String resourceId) {
        this.publisher.publishEvent(new ${endpoint.entityName}Event(event, ${endpoint.pojoName}.builder().resourceId(resourceId).build()));
    }
}
