<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public interface ${endpoint.entityName}Service {
    /*
     * findAll
     */
    List<${endpoint.pojoName}> findAll${endpoint.entityName}s();

    /**
     * findByResourceId
     */
    Optional<${endpoint.pojoName}> find${endpoint.entityName}ByResourceId(String id);

    /*
     * findByText
     */
    Page<${endpoint.pojoName}> findByText(Optional<String> text, Pageable pageable);

    /**
     * Persists a new resource
     */
    ${endpoint.pojoName} create${endpoint.entityName}(${endpoint.pojoName} resource );

    /**
     * Updates an existing resource
     */
    Optional<${endpoint.pojoName}> update${endpoint.entityName}(${endpoint.pojoName} resource );

    /**
     * delete
     */
    void delete${endpoint.entityName}ByResourceId( String id );
}
