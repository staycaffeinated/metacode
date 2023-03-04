<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import com.mongodb.client.result.UpdateResult;
import lombok.Builder;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.data.mongodb.core.query.UpdateDefinition;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Default implementation of the ${endpoint.entityName}DataStore.
 */
@Component
@RequiredArgsConstructor
public class ${endpoint.entityName}DataStoreProvider implements ${endpoint.entityName}DataStore {

    private final ${endpoint.documentName}ToPojoConverter documentConverter;
    private final ${endpoint.entityName}PojoToDocumentConverter pojoConverter;
    private final SecureRandomSeries resourceIdGenerator;
    private final MongoTemplate mongoTemplate;
    private final ${endpoint.entityName}Repository repository;

    private static final String RESOURCE_ID = "resourceId";

    @Override
    public Optional<${endpoint.entityName}> findByResourceId(String publicId)  {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(publicId));
        ${endpoint.documentName} document = mongoTemplate.findOne(query, ${endpoint.documentName}.class, ${endpoint.documentName}.collectionName());
        if (document == null)
            return Optional.empty();
        else {
            ${endpoint.pojoName} pojo = documentConverter.convert(document);
            if (pojo == null)
                return Optional.empty();
            else
                return Optional.of(pojo);
        }
    }

    @Override
    public List<${endpoint.entityName}> findAll() {
        // @formatter:off
        return mongoTemplate.findAll(${endpoint.documentName}.class, ${endpoint.documentName}.collectionName())
                            .stream()
                            .map(documentConverter::convert)
                            .toList();
        // @formatter:on
    }

    @Override
    public ${endpoint.pojoName} create(${endpoint.pojoName} pojo) {
        ${endpoint.documentName} document = pojoConverter.convert(pojo);
        Objects.requireNonNull(document);
        document.setResourceId(resourceIdGenerator.nextResourceId());
        ${endpoint.documentName} managed = mongoTemplate.save(document, ${endpoint.documentName}.collectionName());
        return documentConverter.convert(managed);
    }

    @Override
    public long delete(${endpoint.pojoName} pojo) {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(pojo.getResourceId()));
        mongoTemplate.remove(query, ${endpoint.documentName}.collectionName()).getDeletedCount();
    }

    @Override
    public long update(${endpoint.pojoName} pojo) {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(pojo.getResourceId()));
        // By default, this is only updating the 'text' column.
        // You will want to decide how you want this to actually work and change this.
        UpdateDefinition updateDefinition = Update.update("text", pojo.getText());
        UpdateResult updateResult = mongoTemplate.updateMulti(query, updateDefinition, ${endpoint.documentName}.collectionName());
        if (updateResult.getModifiedCount() > 0) {
            List<${endpoint.documentName}> modified = mongoTemplate.find(query, ${endpoint.documentName}.class, ${endpoint.documentName}.collectionName());
            if (!modified.isEmpty()) {
                List<${endpoint.entityName}> pojoList = documentConverter.convert(modified);
                if (pojoList != null) {
                    return pojoList;
                }
            }
        }
        return List.of();
    }

    @Override
    public Page<${endpoint.entityName}> findByText(@NonNull String text, Pageable pageable) {
        Page<${endpoint.documentName}> rs = repository.findByTextContainingIgnoreCase(text, pageable);
        List<${endpoint.entityName}> list = rs.stream().map(documentConverter::convert).toList();
        return new PageImpl<>(list, pageable, list.size());
    }

    @Override
    public void deleteByResourceId(@NonNull String resourceId) {
        Query query = Query.query(Criteria.where(RESOURCE_ID).is(resourceId));
        mongoTemplate.remove(query, ${endpoint.documentName}.collectionName());
    }
                
}