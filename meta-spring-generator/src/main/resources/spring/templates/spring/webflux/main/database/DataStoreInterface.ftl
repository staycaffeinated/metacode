<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.DataStore;
import reactor.core.publisher.Mono;

/**
 * This is the SPI for the ${endpoint.entityName} database adapter
 */
public interface ${endpoint.entityName}DataStore extends DataStore<${endpoint.entityName}> {
    Mono<String> create${endpoint.entityName}(${endpoint.entityName} pojo);
    Mono<${endpoint.entityName}> update${endpoint.entityName}(${endpoint.entityName} pojo);
}
