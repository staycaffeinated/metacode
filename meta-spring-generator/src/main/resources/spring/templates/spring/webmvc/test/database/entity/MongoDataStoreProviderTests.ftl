<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.${endpoint.documentName}ToPojoConverter;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.${endpoint.entityName}PojoToDocumentConverter;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import org.bson.BsonValue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.AutoConfigureDataMongo;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Query;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/**
 * Unit test the ${endpoint.entityName}DataStore
 */
@ExtendWith(MockitoExtension.class)
@AutoConfigureDataMongo
@SuppressWarnings("all")
public class ${endpoint.entityName}DataStoreProviderTests {

    ${endpoint.documentName}ToPojoConverter documentToPojoConverter = new ${endpoint.documentName}ToPojoConverter();
    ${endpoint.entityName}PojoToDocumentConverter pojoToDocumentConverter = new ${endpoint.entityName}PojoToDocumentConverter();
    SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Mock
    MongoTemplate mockMongoTemplate;

    @Mock
    ${endpoint.entityName}Repository mockRepository;

    ${endpoint.entityName}DataStoreProvider dataStoreUnderTest;

    @BeforeEach
    void setUp() {
        // @formatter:off
        dataStoreUnderTest = ${endpoint.entityName}DataStoreProvider.builder()
            .documentConverter(documentToPojoConverter)
            .pojoConverter(pojoToDocumentConverter)
            .resourceIdGenerator(randomSeries)
            .mongoTemplate(mockMongoTemplate)
            .repository(mockRepository)
            .build();
        // @formatter:ono
    }

    @Nested
    class FindByResourceId {
        @Test
        void shouldFindOne() {
            // scenario: an item is know to exist in the database
            ${endpoint.documentName} expectedDocument = ${endpoint.documentName}TestFixtures.oneWithResourceId();
            String expectedResourceId = ${endpoint.documentName}TestFixtures.oneWithResourceId().getResourceId();
            when(mockMongoTemplate.findOne(any(), any(), any())).thenReturn(expectedDocument);

            // when: the dataStore is asked to find the known-to-exist item by its
            // publicId...
            Optional<${endpoint.entityName}> actualResult = dataStoreUnderTest.findByResourceId(expectedResourceId);

            assertThat(actualResult).isNotNull().isPresent();
            assertThat(actualResult.get().getResourceId()).isEqualTo(expectedResourceId);
        }

        @Test
        void shouldReturnEmptyWhenNoMatchingRecord() {
            // scenario: the repository cannot find any such record...
            ${endpoint.documentName} expectedDocument = null;
            given(mockMongoTemplate.findOne(any(), any(), any())).willReturn(expectedDocument);

            // when: the dataStore is asked to find an unknown record...
            Optional<${endpoint.entityName}> actualResult = dataStoreUnderTest.findByResourceId("12345");

            // expect: an empty Optional is returned
            assertThat(actualResult).isNotNull().isEmpty();
        }

        @Test
        void shouldThrowExceptionWhenKeyIsNull() {
            assertThrows(NullPointerException.class, () -> dataStoreUnderTest.findByResourceId(null));
        }

        /*
         * This verifies the edge case that, when the converter attempts to transform an
         * entity into a POJO, a Null value is returned.
         */
        @Test
        void shouldReturnEmptyResultWhenConverterReturnsNull() {
            // Create a Mock documentToPojoConverter to enable testing a particular branch of code
            ${endpoint.documentName}ToPojoConverter mockDocumentToPojoConverter = Mockito.mock(${endpoint.documentName}ToPojoConverter.class);

            // Create a DataStore that uses the mock converter
            ${endpoint.entityName}DataStore storeUnderTest = new ${endpoint.entityName}DataStoreProvider(mockDocumentToPojoConverter, pojoToDocumentConverter,
                randomSeries, mockMongoTemplate, mockRepository);

            // given: the repository is able to find a particular entity
            // but when the entity is converted to a POJO, a NULL value is returned
            ${endpoint.documentName} targetEntity = ${endpoint.documentName}TestFixtures.oneWithResourceId();
            given(mockMongoTemplate.findOne(any(), any(), any())).willReturn(targetEntity);
            given(mockDocumentToPojoConverter.convert(any(${endpoint.documentName}.class))).willReturn(null);

            // when:
            Optional<${endpoint.entityName}> actual = storeUnderTest.findByResourceId("12345");

            // expect
            assertThat(actual).isNotNull().isEmpty();
        }
    }

    @Nested
    class FindAll {
        @Test
        void shouldFindMultipleItems() {
            // scenario: the repository contains multiple ${endpoint.entityName} entities
            List<${endpoint.documentName}> resultSet = ${endpoint.documentName}TestFixtures.allItems();
            given(mockMongoTemplate.findAll(any(), any(String.class))).willReturn(List.copyOf(resultSet));

            // when:
            List<${endpoint.entityName}> items = dataStoreUnderTest.findAll();

            // expect: the resultSet contains the same number of items as were found in the
            // repository
            assertThat(items).isNotNull().isNotEmpty().hasSize(${endpoint.documentName}TestFixtures.allItems().size());
        }

        @Test
        void shouldHaveEmptyResults() {
            // scenario: the repository contains no ${endpoint.entityName} entities
            given(mockMongoTemplate.findAll(any(), any(String.class))).willReturn(List.of());

            // when:
            List<${endpoint.entityName}> items = dataStoreUnderTest.findAll();

            // expect: the resultSet is empty
            assertThat(items).isNotNull().isEmpty();
        }
    }

    @Nested
    class Save {
        /*
         * Context: when a new record is created in the database, a resourceId is
         * assigned to that record. The scope of this test is to verify that resourceId
         * is assigned and is part of the return value.
         */
        @Test
        void shouldAssignResourceIdToSavedItem() {
            // scenario: a ${endpoint.entityName} is successfully saved in the repository, and assigned a
            // resourceId.
            // Under the covers, the EJB is assigned a resourceId, then saved, so the record
            // returned by the repository should also have a resourceId. When the EJB is
            // transformed into a POJO, the POJO should retain the resourceId
                    ${endpoint.documentName} savedEntity = ${endpoint.documentName}TestFixtures.oneWithResourceId();
            given(mockMongoTemplate.save(any(), any())).willReturn(savedEntity);
            ${endpoint.entityName} pojoToSave = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();

            // when: the item is saved
            ${endpoint.entityName} actualPojo = dataStoreUnderTest.create(pojoToSave);

            // expect: a non-null POJO with an assigned resourceId
            assertThat(actualPojo).isNotNull();
            assertThat(actualPojo.getResourceId()).isNotEmpty();
        }

        @Test
        void shouldThrowExceptionOnAttemptToSaveNullItem() {
            assertThrows(NullPointerException.class, () -> {
            dataStoreUnderTest.create(null);
            });
        }

        /**
         * Scenario: when {@code convert} is called to convert the POJO to a Document,
         * and the {@code convert} method returns null, expect a {@code NullPointerException}.
         */
        @Test
        void shouldThrowNullPointerExceptionWhenConversionFails() {
            ${endpoint.entityName}PojoToDocumentConverter mockPojoToDocumentConverter = Mockito.mock(${endpoint.entityName}PojoToDocumentConverter.class);

            // Create a DataStore that uses the mock converter
            ${endpoint.entityName}DataStore edgeCaseDataStore = ${endpoint.entityName}DataStoreProvider.builder()
                        .documentConverter(documentToPojoConverter)
                        .pojoConverter(mockPojoToDocumentConverter)
                        .resourceIdGenerator(randomSeries)
                        .mongoTemplate(mockMongoTemplate)
                        .repository(mockRepository)
                        .build();

            // given: the converter returns a null value
            given(mockPojoToDocumentConverter.convert(any(${endpoint.entityName}.class))).willReturn(null);

            assertThrows(NullPointerException.class, () -> {
                ${endpoint.entityName} result = edgeCaseDataStore.create(${endpoint.entityName}TestFixtures.oneWithoutResourceId());
            });
        }
    }

    @Nested
    class DeleteByResourceId {
        @Test
        void shouldQuietlyRemoveExistingItem() {
            // scenario: the repository contains the record being deleted.
            // the first call to findBy is successful; the second call to findBy
            // comes up empty (the second call occurs after the delete operation;
            // attempts to find deleted records should return empty results)
            ${endpoint.documentName} targetEntity = ${endpoint.documentName}TestFixtures.oneWithResourceId();
            DeleteResult mockResult = Mockito.mock(DeleteResult.class);
            given(mockMongoTemplate.remove(any(Query.class), any(String.class))).willReturn(mockResult);
            given(mockResult.getDeletedCount()).willReturn(1L);

            // when
            String targetItem = targetEntity.getResourceId();
            dataStoreUnderTest.deleteByResourceId(targetItem);

            // expect: a non-null result.
            Optional<${endpoint.entityName}> result = dataStoreUnderTest.findByResourceId(targetItem);
            assertThat(result).isNotNull().isEmpty();
        }

        @Test
        void shouldQuietlyIgnoreAttemptToDeleteNonExistentItem() {
            // scenario: the repository does not contain the record being deleted
            DeleteResult mockResult = Mockito.mock(DeleteResult.class);
            given(mockMongoTemplate.remove(any(Query.class), any(String.class))).willReturn(mockResult);
            given(mockResult.getDeletedCount()).willReturn(0L);

            String targetItem = "12345AbCdE56789xYz";
            // when
            dataStoreUnderTest.deleteByResourceId(targetItem);

            // expect
            Optional<${endpoint.entityName}> result = dataStoreUnderTest.findByResourceId(targetItem);
            assertThat(result).isNotNull().isEmpty();
        }

        @Test
        void shouldThrowExceptionWhenResourceIdIsNull() {
            assertThrows(NullPointerException.class, () -> {
                dataStoreUnderTest.deleteByResourceId(null);
            });
        }
    }

    @Nested
    class FindByText {
        @Test
        void shouldThrowExceptionWhenSearchTextIsNull() {
            Pageable page = PageRequest.of(0, 10);
            assertThrows(NullPointerException.class, () -> dataStoreUnderTest.findByText(null, page));
        }

        @Test
        void shouldReturnPageOfResults() {
            // given: the repository retrieves a page of entities that meet some criteria
            List<${endpoint.documentName}> content = ${endpoint.documentName}TestFixtures.allItems();
            Page<${endpoint.documentName}> pageResult = new PageImpl<>(content, Pageable.unpaged(), content.size());
            given(mockRepository.findByTextContainingIgnoreCase(any(String.class), any(Pageable.class)))
                    .willReturn(pageResult);

            // when: attempting a findByText
            Page<${endpoint.entityName}> actual = dataStoreUnderTest.findByText("anything", PageRequest.of(1, 10));

            //
            // expect: the result to contain as many items as were found in the repository.
            assertThat(actual).isNotNull().hasSize(content.size());
        }
    }

    @Nested
    class Delete {
        @Test
        void shouldDeleteSuccessfully() {
            // Simulate the dataStore containing an item, such that requests
            // to delete the item are successful
            DeleteResult mockResult = Mockito.mock(DeleteResult.class);
            when(mockResult.getDeletedCount()).thenReturn(1L);
            when(mockMongoTemplate.remove(any(Query.class), any(String.class))).thenReturn(mockResult);

            // when: said item is deleted
            long count = dataStoreUnderTest.deleteByResourceId(${endpoint.entityName}TestFixtures.sampleOne().getResourceId());

            // expect: the delete count is 1
            assertThat(count).isEqualTo(1);
        }
    }

    /**
     * Builds a Provider that uses a DocumentToPojo converter whose {@code convert}
     * method returns null.  Used for edge-case testing.
     */
    private ${endpoint.entityName}DataStoreProvider aProviderWithAnIffyDocumentConverter() {
        ${endpoint.documentName}ToPojoConverter mockConverter = Mockito.mock(${endpoint.documentName}ToPojoConverter.class);
        when(mockConverter.convert(any(List.class))).thenReturn(null);

        return ${endpoint.entityName}DataStoreProvider.builder()
            .documentConverter(mockConverter)
            .pojoConverter(pojoToDocumentConverter)
            .resourceIdGenerator(randomSeries)
            .mongoTemplate(mockMongoTemplate)
            .repository(mockRepository)
            .build();
    }
}