<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;
import jakarta.validation.Valid;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;

import java.util.List;
import java.util.Optional;

public interface ${endpoint.entityName}Service {

    /*
     * findAll
     */
    List<${endpoint.entityName}> findAll${endpoint.entityName}s();

    /**
     * findByResourceId
     */
    Optional<${endpoint.entityName}> find${endpoint.entityName}ByResourceId(String id);

    /*
     * findByText
     */
    Page<${endpoint.entityName}> findByText(@NonNull String text, Pageable pageable);

    /**
     * Persists a new resource
     */
    ${endpoint.entityName} create${endpoint.entityName}(@NonNull @Validated(OnCreate.class) ${endpoint.entityName} resource);

    /**
     * Updates an existing resource
     */
    List<${endpoint.entityName}> update${endpoint.entityName}(@NonNull @Validated(OnUpdate.class) @Valid ${endpoint.entityName} resource);

    /**
     * delete
     */
    void delete${endpoint.entityName}ByResourceId(@NonNull String id);
}