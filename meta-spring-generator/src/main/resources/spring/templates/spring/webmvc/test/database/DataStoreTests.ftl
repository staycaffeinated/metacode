<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;

/**
* Unit test the ${endpoint.entityName}DataStore
*/
@ExtendWith(MockitoExtension.class)
public class ${endpoint.entityName}DataStoreTests {

    @Mock
    ${endpoint.entityName}Repository mockRepository;

    ${endpoint.entityName}EntityToPojoConverter ejbToPojoConverter = new ${endpoint.entityName}EntityToPojoConverter();
    ${endpoint.entityName}PojoToEntityConverter pojoToEjbConverter = new ${endpoint.entityName}PojoToEntityConverter();
    SecureRandomSeries randomSeries = new SecureRandomSeries();

    ${endpoint.entityName}DataStoreProvider dataStoreUnderTest;

    @BeforeEach
    void setUp() {
        dataStoreUnderTest = new ${endpoint.entityName}DataStoreProvider(mockRepository, ejbToPojoConverter, pojoToEjbConverter, randomSeries);
    }

    @Nested
    class FindByResourceId {
        @Test
        void shouldFindOne() {
            // scenario: an item is know to exist in the database
            Optional<${endpoint.ejbName}> optional = Optional.of(${endpoint.entityName}EntityTestFixtures.oneWithResourceId());
            String expectedResourceId = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId().getResourceId();
            when(mockRepository.findByResourceId(any(String.class))).thenReturn(optional);

            // when: the dataStore is asked to find the known-to-exist item by its publicId...
            Optional<${endpoint.entityName}> actualResult = dataStoreUnderTest.findByResourceId(expectedResourceId);

            assertThat(actualResult).isNotNull().isPresent();
            assertThat(actualResult.get().getResourceId()).isEqualTo(expectedResourceId);
        }

        @Test
        void shouldReturnEmptyWhenNoMatchingRecord() {
            // scenario: the repository cannot find any such record...
            Optional<${endpoint.ejbName}> optional = Optional.empty();
            given(mockRepository.findByResourceId(any(String.class))).willReturn(optional);

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
            // Create a Mock ejbToPojoConverter to enable testing a particular branch of code
            ${endpoint.entityName}EntityToPojoConverter mockEjbToPojoConverter = Mockito.mock(${endpoint.entityName}EntityToPojoConverter.class);

            // Create a DataStore that uses the mock converter
            ${endpoint.entityName}DataStore storeUnderTest = new ${endpoint.entityName}DataStoreProvider(mockRepository, mockEjbToPojoConverter,
            pojoToEjbConverter, randomSeries);

            // given: the repository is able to find a particular entity
            // but when the entity is converted to a POJO, a NULL value is returned
            ${endpoint.ejbName} targetEntity = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId();
            given(mockRepository.findByResourceId(any(String.class))).willReturn(Optional.of(targetEntity));
            given(mockEjbToPojoConverter.convert(any(${endpoint.ejbName}.class))).willReturn(null);

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
            given(mockRepository.findAll()).willReturn(${endpoint.entityName}EntityTestFixtures.allItems());

            // when:
            List<${endpoint.entityName}> items = dataStoreUnderTest.findAll();

            // expect: the resultSet contains the same number of items as were found in the
            // repository
            assertThat(items).isNotNull().isNotEmpty().hasSize(${endpoint.entityName}EntityTestFixtures.allItems().size());
        }

        @Test
        void shouldHaveEmptyResults() {
            // scenario: the repository contains no ${endpoint.entityName} entities
            given(mockRepository.findAll()).willReturn(List.of());

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
            // scenario: a ${endpoint.entityName} is successfully saved in the repository, and assigned a resourceId.
            // Under the covers, the EJB is assigned a resourceId, then saved, so the record
            // returned by the repository should also have a resourceId. When the EJB is
            // transformed into a POJO, the POJO should retain the resourceId
            ${endpoint.ejbName} savedEntity = ${endpoint.entityName}EntityTestFixtures.oneWithResourceId();
            given(mockRepository.save(any(${endpoint.ejbName}.class))).willReturn(savedEntity);
            ${endpoint.entityName} pojoToSave = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();

            // when: the item is saved
            ${endpoint.entityName} actualPojo = dataStoreUnderTest.save(pojoToSave);

            // expect: a non-null POJO with an assigned resourceId
            assertThat(actualPojo).isNotNull();
            assertThat(actualPojo.getResourceId()).isNotEmpty();
        }

        @Test
        void shouldThrowExceptionOnAttemptToSaveNullItem() {
            assertThrows(NullPointerException.class, () -> {
                dataStoreUnderTest.save(null);
            });
        }

        @Test
        void shouldReturnNullWhenConverterReturnsNull() {
            ${endpoint.entityName}PojoToEntityConverter mockPojoToEntityConverter = Mockito.mock(${endpoint.entityName}PojoToEntityConverter.class);

            // Create a DataStore that uses the mock converter
            ${endpoint.entityName}DataStore edgeCaseDataStore = new ${endpoint.entityName}DataStoreProvider(mockRepository, ejbToPojoConverter,
                                                                      mockPojoToEntityConverter, randomSeries);

            // given: the converter returns a null value
            given(mockPojoToEntityConverter.convert(any(${endpoint.entityName}.class))).willReturn(null);

            // when:
            ${endpoint.entityName} result = edgeCaseDataStore.save(${endpoint.entityName}TestFixtures.oneWithoutResourceId());

            // expect: when the conversion goes side-ways, save() returns a null
            assertThat(result).isNull();
        }
    }

    @Nested
    class Update {
        @Test
        void shouldReturnUpdatedVersionWhenItemExists() {
            // scenario: the repository contains the item being updated,
            // so the repository can find it and save it
            ${endpoint.pojoName} testSubject = ${endpoint.entityName}TestFixtures.oneWithResourceId();
            ${endpoint.ejbName} persistedSubject = pojoToEjbConverter.convert(testSubject);
            assert persistedSubject != null;
            given(mockRepository.findByResourceId(any(String.class))).willReturn(Optional.of(persistedSubject));
            given(mockRepository.save(any(${endpoint.ejbName}.class))).willReturn(persistedSubject);

            // given
            Optional<${endpoint.entityName}> option = dataStoreUnderTest.update(testSubject);

            // expect: the Optional contains the updated item.
            // Since resourceIds are immutable, expect a match on the value before and after
            assertThat(option).isNotNull().isPresent();
            assertThat(option.get().getResourceId()).isEqualTo(testSubject.getResourceId());
        }

        @Test
        void shouldReturnEmptyOptionWhenUpdatingNonExistentItem() {
            // scenario: an attempt is made to update an item that does not exist in the
            // repository
            given(mockRepository.findByResourceId(any(String.class))).willReturn(Optional.empty());

            // given:
            Optional<${endpoint.entityName}> option = dataStoreUnderTest.update(${endpoint.entityName}TestFixtures.oneWithResourceId());

            // expect: a non-null, but empty, result
            assertThat(option).isNotNull().isEmpty();
        }

        @Test
        void shouldThrowExceptionOnAttemptToUpdateNullItem() {
            assertThrows(NullPointerException.class, () -> {
                dataStoreUnderTest.update(null);
            });
        }

        @Test
        void shouldReturnEmptyWhenConversionIsNull() {
            // Create a Mock ejbToPojoConverter to enable testing a particular branch of code
            ${endpoint.entityName}EntityToPojoConverter mockEjbToPojoConverter = Mockito.mock(${endpoint.entityName}EntityToPojoConverter.class);
            given(mockEjbToPojoConverter.convert(any(${endpoint.ejbName}.class))).willReturn(null);
            // Create a DataStore that uses the mock converter
            ${endpoint.entityName}DataStore edgeCaseDataStore = new ${endpoint.entityName}DataStoreProvider(mockRepository, mockEjbToPojoConverter,
                                     pojoToEjbConverter, randomSeries);

            // given: the repository finds the requested record, but the conversion to a
            // POJO yields a null value
            ${endpoint.pojoName} targetPojo = ${endpoint.entityName}TestFixtures.oneWithResourceId();
            ${endpoint.ejbName} targetEjb = pojoToEjbConverter.convert(targetPojo);
            assert targetEjb != null;
            given(mockRepository.findByResourceId(any(String.class))).willReturn(Optional.of(targetEjb));
            given(mockRepository.save(any(${endpoint.ejbName}.class))).willReturn(targetEjb);

            // when: an update is attempted
            Optional<${endpoint.entityName}> optional = edgeCaseDataStore.update(targetPojo);

            // expect: an empty Optional is returned
            assertThat(optional).isNotNull().isEmpty();
        }
    }

    @Nested
    class DeleteByResourceId {
        @Test
        void shouldQuietlyRemoveExistingItem() {
            ${endpoint.ejbName} targetEntity = ${endpoint.ejbName}TestFixtures.oneWithResourceId();
            // scenario: the repository contains the record being deleted.
            // the first call to findBy is successful; the second call to findBy
            // comes up empty (the second call occurs after the delete operation;
            // attempts to find deleted records should return empty results)
            // @formatter:off
            given(mockRepository.findByResourceId(any(String.class)))
                                .willReturn(Optional.of(targetEntity)) // happens when delete is called
                                .willReturn(Optional.empty()); // happens after delete is called
            // @formatter:on
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
            given(mockRepository.findByResourceId(any(String.class))).willReturn(Optional.empty());

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
            List<${endpoint.ejbName}> content = ${endpoint.ejbName}TestFixtures.allItems();
            Page<${endpoint.ejbName}> pageResult = new PageImpl<>(content, Pageable.unpaged(), content.size());
            given(mockRepository.findAll(any(Specification.class), any(Pageable.class))).willReturn(pageResult);

            // when: attempting a findByText
            Page<${endpoint.entityName}> actual = dataStoreUnderTest.findByText(Optional.of("anything"), PageRequest.of(1, 10));

            // expect: the result to contain as many items as were found in the repository.
            assertThat(actual).isNotNull().hasSize(content.size());
        }
    }
}
