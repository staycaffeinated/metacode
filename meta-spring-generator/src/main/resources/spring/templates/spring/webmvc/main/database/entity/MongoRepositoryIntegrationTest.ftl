<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

/**
* Tests to verify query syntax
*/
@DataMongoTest
class ${endpoint.entityName}RepositoryIntegrationTest extends MongoDbContainerSupport {

    @Autowired
    MongoTemplate mongoTemplate;

    @Autowired
    private ${endpoint.entityName}Repository repository;

    @BeforeEach
    void populateDatabaseWithTestData() {
        repository.saveAll(${endpoint.entityName}DocumentTestFixtures.allItems());
    }

    @AfterEach
    void clearDatabase() {
        repository.deleteAll();
    }

    @Test
    void shouldFindAll() {
        int expectedSize = ${endpoint.entityName}DocumentTestFixtures.allItems().size();
        assertThat((long) repository.findAll().size()).isEqualTo(expectedSize);
    }

    @Test
    void shouldBeAbleToSaveAndFind() {
        var savedDoc = repository.save(${endpoint.entityName}DocumentTestFixtures.getSampleOne());
        assertThat(repository.findById(savedDoc.getId())).isPresent();
    }

    @Test
    void shouldSupportMongoTemplate() {
        mongoTemplate.createCollection(${endpoint.entityName}Document.getCollection());
        mongoTemplate.insert(${endpoint.entityName}DocumentTestFixtures.allItems(), ${endpoint.entityName}Document.getCollection());

        String expectedResourceId = ${endpoint.entityName}DocumentTestFixtures.getSampleOne().getResourceId();
        Query query = new Query();
        query.addCriteria(Criteria.where("resourceId").is(expectedResourceId));

        List<${endpoint.entityName}Document> resultSet = mongoTemplate.find(query, ${endpoint.entityName}Document.class);
        assertThat(resultSet.get(0).getResourceId()).isEqualTo(expectedResourceId);
    }
}