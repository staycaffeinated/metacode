<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.CrudAware;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

public interface ${endpoint.entityName}Repository extends MongoRepository<${endpoint.documentName}, String> {
    @Query("{resourceId:?}")
    Optional<endpoint.documentName> findByResourceId(String resourceId);

    @Query("{text:?}")
    List<endpoint.documentName> findAllByText(String text);
}
