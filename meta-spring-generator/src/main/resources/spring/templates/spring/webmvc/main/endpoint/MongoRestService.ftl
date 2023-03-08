<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;
import jakarta.validation.Valid;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@Slf4j
@Validated
public class ${endpoint.entityName}Service {

    private final ${endpoint.entityName}DataStore ${endpoint.lowerCaseEntityName}DataStore;

    /*
     * Constructor
     */
    @Autowired
    public ${endpoint.entityName}Service(${endpoint.entityName}DataStore dataStore) {
        this.${endpoint.lowerCaseEntityName}DataStore = dataStore;
    }

    /*
     * findAll
     */
    public List<${endpoint.entityName}> findAll${endpoint.entityName}s() {
        return ${endpoint.lowerCaseEntityName}DataStore.findAll();
    }

    /**
     * findByResourceId
     */
    public Optional<${endpoint.entityName}> find${endpoint.entityName}ByResourceId(String id) {
        return ${endpoint.lowerCaseEntityName}DataStore.findByResourceId(id);
    }

    /*
     * findByText
     */
    public Page<${endpoint.entityName}> findByText(@NonNull String text, Pageable pageable) {
        return ${endpoint.lowerCaseEntityName}DataStore.findByText(text, pageable);
    }

    /**
     * Persists a new resource
     */
    public ${endpoint.entityName} create${endpoint.entityName}(@NonNull @Validated(OnCreate.class) ${endpoint.entityName} resource) {
        return ${endpoint.lowerCaseEntityName}DataStore.create(resource);
    }

    /**
     * Updates an existing resource
     */
    public List<${endpoint.entityName}> update${endpoint.entityName}(@NonNull @Validated(OnUpdate.class) @Valid ${endpoint.entityName} resource) {
        return ${endpoint.lowerCaseEntityName}DataStore.update(resource);
    }

    /**
     * delete
     */
    public void delete${endpoint.entityName}ByResourceId(@NonNull String id) {
        ${endpoint.lowerCaseEntityName}DataStore.deleteByResourceId(id);
    }
}