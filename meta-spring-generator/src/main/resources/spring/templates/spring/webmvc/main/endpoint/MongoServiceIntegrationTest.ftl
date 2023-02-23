<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.database.MongoDbContainerSupport;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.PetDocumentToPojoConverter;
import ${endpoint.basePackage}.endpoint.${endpoint.lowerCaseEntityName}.PetService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.util.Set;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
@Slf4j
class ${endpoint.entityName}ServiceIntegrationTest extends MongoDbContainerSupport {

    @Autowired
    ${endpoint.entityName}DataStore ${endpoint.lowerCaseEntityName}DataStore;

    // The repository is directly accessed to enable removing all test data
    @Autowired
    ${endpoint.entityName}Repository ${endpoint.lowerCaseEntityName}Repository;

    ${endpoint.documentName}ToPojoConverter ${endpoint.lowerCaseEntityName}DocumentToPojoConverter = new ${endpoint.documentName}ToPojoConverter();

    @Autowired
    MongoTemplate mongoTemplate;

    private ${endpoint.entityName}Service serviceUnderTest;

    @BeforeEach
    void setUp() {
        ${endpoint.lowerCaseEntityName}Repository.deleteAll();
        serviceUnderTest = new ${endpoint.entityName}Service(${endpoint.lowerCaseEntityName}DataStore);
        mongoTemplate.insertAll(${endpoint.documentName}TestFixtures.allItems());
    }

    @AfterEach
    void cleanUp() {
        mongoTemplate.remove(${endpoint.entityName}Document.class);
    }

    @Nested
    public class FindAll {
        @Test
        void shouldFindAll() {
            Set<${endpoint.entityName}> results = serviceUnderTest.findAll${endpoint.entityName}s();
            assertThat(results).isNotNull();
        }
    }
}