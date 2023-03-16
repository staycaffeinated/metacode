<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import lombok.NonNull;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.${endpoint.pojoName};
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.${endpoint.entityName}DataStore;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;

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
public class ${endpoint.entityName}ServiceProvider implements ${endpoint.entityName}Service {

    private final ${endpoint.entityName}DataStore ${endpoint.entityVarName}DataStore;

    /*
     * Constructor
     */
    @Autowired
    public ${endpoint.entityName}ServiceProvider(${endpoint.entityName}DataStore ${endpoint.entityVarName}DataStore)
    {
        this.${endpoint.entityVarName}DataStore = ${endpoint.entityVarName}DataStore;
    }

    /*
     * findAll
     */
    public List<${endpoint.pojoName}> findAll${endpoint.entityName}s() {
        return ${endpoint.entityVarName}DataStore.findAll();
    }

    /**
     * findByResourceId
     */
    public Optional<${endpoint.pojoName}> find${endpoint.entityName}ByResourceId(String id) {
        return ${endpoint.entityVarName}DataStore.findByResourceId ( id );
    }

    /*
     * findByText
     */
    public Page<${endpoint.pojoName}> findByText(@NonNull Optional<String> text, Pageable pageable) {
        return ${endpoint.entityVarName}DataStore .findByText(text, pageable);
    }

    /**
     * Persists a new resource
     */
    public ${endpoint.pojoName} create${endpoint.entityName}( @NonNull @Validated(OnCreate.class) ${endpoint.pojoName} resource ) {
        return ${endpoint.entityVarName}DataStore.save(resource);
    }

    /**
     * Updates an existing resource
     */
    public Optional<${endpoint.pojoName}> update${endpoint.entityName}( @NonNull @Validated(OnUpdate.class) ${endpoint.pojoName} resource ) {
        return ${endpoint.entityVarName}DataStore.update(resource);
    }

    /**
     * delete
     */
    public void delete${endpoint.entityName}ByResourceId(@NonNull String id) {
        ${endpoint.entityVarName}DataStore.deleteByResourceId(id);
    }
}
