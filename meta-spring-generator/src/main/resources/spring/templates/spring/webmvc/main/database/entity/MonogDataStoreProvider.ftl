<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Component;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
* Default implementation of the PetDataStore.
*/
@Component
@RequiredArgsConstructor
public class ${endpoint.entityName}DataStoreProvider implements ${endpoint.entityName}DataStore {

    private final ${endpoint.documentName}ToPojoConverter documentConverter;
    private final ${endpoint.entityName}PojoToDocumentConverter pojoConverter;
    private final SecureResourceIdGenerator resourceIdGenerator;
    private final MongoTemplate mongoTemplate;

    private static final String RESOURCE_ID = "resourceId";

    @Override
    public Set<${endpoint.entityName}> findByResourceId(String publicId)  {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(publicId));
        // @formatter:off
        return mongoTemplate.find(query, ${endpoint.documentName}.class, ${endpoint.documentName}.collectionName())
                            .stream()
                            .map(documentConverter::convert)
                            .collect(Collectors.toSet());
        // @formatter:on
    }

    @Override
    public Set<${endpoint.entityName}> findAll() {
        // @formatter:off
        return mongoTemplate.findAll(${endpoint.documentName}.class, ${endpoint.documentName}.collectionName())
                            .stream()
                            .map(documentConverter::convert)
                            .collect(Collectors.toSet());
        // @formatter:on
    }

    @Override
    public ${endpoint.pojoName} create(${endpoint.pojoName} pojo) {
        ${endpoint.documentName} document = pojoConverter.convert(pojo);
        Objects.requireNonNull(document);
        document.setResourceId(resourceIdGenerator.nextResourceId());
        PetDocument managed = mongoTemplate.save(document, ${endpoint.documentName}.collectionName());
        return documentConverter.convert(managed);
    }

    @Override
    public void delete(${endpoint.pojoName} pojo) {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(pojo.getResourceId()));
        mongoTemplate.remove(query, ${endpoint.documentName}.collectionName());
    }

    @Override
    public long update(${endpoint.pojoName} pojo) {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(pojo.getResourceId()));
        UpdateDefinition updateDefinition = Update.update("text", pojo.getText());
        UpdateResult updateResult = mongoTemplate.updateFirst(query, updateDefinition, ${endpoint.documentName}.collectionName());
        return updateResult.getModifiedCount();
    }
}