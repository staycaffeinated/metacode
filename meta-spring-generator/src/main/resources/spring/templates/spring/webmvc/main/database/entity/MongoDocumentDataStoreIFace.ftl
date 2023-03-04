<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.DataStore;
import lombok.NonNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * DataStore for ${endpoint.entityName} domain objects. This interface extends the basic
 * {@code DataStore} interface, adding a ${endpoint.entityName}-specific search API.
*/
public interface ${endpoint.entityName}DataStore extends DataStore<${endpoint.entityName}> {
    Page<${endpoint.entityName}> findByText(@NonNull String text, Pageable pageable);

    void deleteByResourceId(String resourceId);
}