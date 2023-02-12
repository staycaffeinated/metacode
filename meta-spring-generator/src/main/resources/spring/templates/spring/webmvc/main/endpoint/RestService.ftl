<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import lombok.NonNull;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.predicate.*;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import java.util.List;
import java.util.Optional;
import java.util.Objects;

@Service
@Transactional
public class ${endpoint.entityName}Service {

    private final ${endpoint.entityName}DataStore ${endpoint.entityVarName}DataStore;

    // private final ${endpoint.entityName}Repository ${endpoint.entityVarName}Repository;

    private final ConversionService conversionService;

    private final SecureRandomSeries secureRandom;

    /*
     * Constructor
     */
    @Autowired
    public ${endpoint.entityName}Service(${endpoint.entityName}DataStore ${endpoint.entityVarName}DataStore,
                                        ConversionService conversionService,
                                        SecureRandomSeries secureRandom)
    {
        this.${endpoint.entityVarName}DataStore = ${endpoint.entityVarName}DataStore;
        this.conversionService = conversionService;
        this.secureRandom = secureRandom;
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
        // @formatter:off
        List<${endpoint.pojoName}> list = ${endpoint.entityVarName}DataStore
                    .findByText(text, pageable)
                    .stream()
                    .map(ejb -> conversionService.convert(ejb, ${endpoint.pojoName}.class)).toList();
        // @formatter:on
        return new PageImpl<>(list, pageable, list.size());
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
