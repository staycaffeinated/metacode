<#include "/common/Copyright.ftl">

package ${endpoint.packageName};


import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.core.convert.ConversionService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.BDDAssertions.then;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

/**
 * Unit tests of {@link ${endpoint.entityName}Service }
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unused"})
class ${endpoint.entityName}ServiceTests {

    @InjectMocks
    private ${endpoint.entityName}Service ${endpoint.entityVarName}Service;

    @Mock
    private ${endpoint.entityName}Repository ${endpoint.entityVarName}Repository;

    @Mock
    private SecureRandomSeries mockRandomSeries;

    final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Spy
    private final ${endpoint.entityName}PojoToEntityConverter ${endpoint.entityVarName}PojoToEntityConverter = new ${endpoint.entityName}PojoToEntityConverter();

    @Spy
    private final ${endpoint.entityName}EntityToPojoConverter ${endpoint.entityVarName}EntityToPojoConverter = new ${endpoint.entityName}EntityToPojoConverter();

    @Spy
    private final ConversionService conversionService = FakeConversionService.build();

    private List<${endpoint.ejbName}> ${endpoint.entityVarName}List;
    private Page<${endpoint.ejbName}> pageOfData;

    @BeforeEach
    void setUpEachTime() {
        ${endpoint.entityVarName}List = new ArrayList<>();
        ${endpoint.entityVarName}List.addAll(${endpoint.ejbName}TestFixtures.allItems());
    }

    /**
     * Unit tests of the findAll method
     */
    @Nested
    class FindAllTests {

        @Test
        void shouldReturnAllRowsWhenRepositoryIsNotEmpty() {
            given(${endpoint.entityVarName}Repository.findAll() ).willReturn( ${endpoint.entityVarName}List );

            List<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.findAll${endpoint.entityName}s();

            then(result).isNotNull();       // must never return null
            then(result.size()).isEqualTo( ${endpoint.entityVarName}List.size()); // must return all rows
        }

        @Test
        void shouldReturnEmptyListWhenRepositoryIsEmpty() {
            given( ${endpoint.entityVarName}Repository.findAll() ).willReturn( new ArrayList<>() );

            List<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.findAll${endpoint.entityName}s();

            then(result).isNotNull();       // must never get null back
            then(result.size()).isZero();   // must have no content for this edge case
        }
    }

    @Nested
    class FindByTextTests {
        /*
         * Happy path - some rows match the given text
         */
        @Test
        @SuppressWarnings("unchecked")
        void shouldReturnRowsWhenRowsWithTextExists() {
            // given
            Pageable pageable = PageRequest.of(0,25);
            Page<${endpoint.ejbName}> page = new PageImpl<>(${endpoint.entityVarName}List, pageable, ${endpoint.entityVarName}List.size());

            // we're not validating what text gets passed to the repo, only that a result set comes back
            given(${endpoint.entityVarName}Repository.findAll(any(Specification.class), any(Pageable.class))).willReturn(page);

            // when/then
            Page<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.findByText(Optional.of("text"), pageable);

            then(result).isNotNull();       // must never return null

            // depending on which is smaller, the sample size or the page size, we expect that many rows back
            then(result.getContent().size()).isEqualTo(Math.min(${endpoint.entityVarName}List.size(), pageable.getPageSize()));
        }

        @Test
        @SuppressWarnings("unchecked")
        void shouldReturnEmptyListWhenNoDataFound() {
            given( ${endpoint.entityVarName}Repository.findAll(any(Specification.class), any(Pageable.class))).willReturn( Page.empty() );

            Pageable pageable = PageRequest.of(1,10);
            Page<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.findByText(Optional.of("foo"), pageable);

            then(result).isNotNull();       // must never get null back
            then(result.hasContent()).isFalse();   // must have no content for this edge case
        }

        @Test
        @SuppressWarnings("all")
        void shouldThrowNullPointerExceptionWhen${endpoint.entityName}IsNull() {
            Pageable pageable = PageRequest.of(0,20);
            assertThrows (NullPointerException.class, () ->  ${endpoint.entityVarName}Service.findByText(null, pageable));
        }
    }

    @Nested
    class Find${endpoint.entityName}ByResourceIdTests {
        /*
         * Happy path - finds the entity in the database
         */
        @Test
        void shouldReturnOneWhenRepositoryContainsMatch() {
            // given
            String expectedId = randomSeries.nextResourceId();
            Optional<${endpoint.ejbName}> expected = Optional.of(new ${endpoint.ejbName}(1L, expectedId, "sample"));
            given(${endpoint.entityVarName}Repository.findByResourceId(any())).willReturn(expected);

            // when/then
            Optional<${endpoint.pojoName}> actual = ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(expectedId);

            assertThat(actual).isNotNull().isPresent();
            assertThat(actual.get().getResourceId()).isEqualTo(expectedId);
        }

        /*
         * Test scenario when no such entity exists in the database
         */
        @Test
        void shouldReturnEmptyWhenRepositoryDoesNotContainMatch() {
            given(${endpoint.entityVarName}Repository.findByResourceId(any())).willReturn(Optional.empty());

            // when/then
            Optional<${endpoint.pojoName}> actual = ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(randomSeries.nextResourceId());

            assertThat(actual).isNotNull().isNotPresent();
        }
    }

    @Nested
    class Create${endpoint.entityName}Tests {

        /*
         * happy path should create a new entity
         */
        @Test
        void shouldCreateOneWhen${endpoint.entityName}IsWellFormed() {
            // given
            final ${endpoint.ejbName} expectedEJB = ${endpoint.ejbName}TestFixtures.oneWithResourceId();
            final String sampleText = expectedEJB.getText();

            given(${endpoint.entityVarName}Repository.save(any())).willReturn(expectedEJB);

            // when/then
            ${endpoint.pojoName} sampleData = convertToPojo(expectedEJB);
            ${endpoint.pojoName} actual = ${endpoint.entityVarName}Service.create${endpoint.entityName}(sampleData);

            assertThat(actual).isNotNull();
            assertThat(actual.getResourceId()).isNotNull();
            assertThat(actual.getText()).isEqualTo(sampleText);
        }

        @Test
        @SuppressWarnings("all")
        void shouldThrowNullPointerExceptionWhen${endpoint.entityName}IsNull() {
            Exception exception = assertThrows (NullPointerException.class, () ->  ${endpoint.entityVarName}Service.create${endpoint.entityName}(null));
        }
    }

    @Nested
    class Update${endpoint.entityName}Tests {
        /*
         * Happy path - updates an existing entity
         */
        @Test
        void shouldUpdateWhenEntityIsFound() {
            // given
            ${endpoint.pojoName} changedVersion =${endpoint.entityName}TestFixtures.oneWithResourceId();
            String resourceId = changedVersion.getResourceId();

            ${endpoint.ejbName} originalEJB = convertToEntity(changedVersion);
            ${endpoint.ejbName} updatedEJB = convertToEntity(changedVersion);
            given(${endpoint.entityVarName}Repository.findByResourceId(any())).willReturn(Optional.of(originalEJB));
            given(${endpoint.entityVarName}Repository.save(any())).willReturn(updatedEJB);

            // when/then
            Optional<${endpoint.pojoName}> optional = ${endpoint.entityVarName}Service.update${endpoint.entityName}(changedVersion);
            then(optional).isNotNull();
            then(optional.isPresent()).isTrue();
            if (optional.isPresent()) {
               then (optional.get().getResourceId()).isEqualTo(resourceId);
               then (optional.get().getText()).isEqualTo(changedVersion.getText());
            }
        }

        /*
         * When no database record is found, the update should return an empty result
         */
        @Test
        void shouldReturnEmptyOptionWhenEntityIsNotFound() {
            // given no such entity exists in the database...
            given(${endpoint.entityVarName}Repository.findByResourceId(any())).willReturn(Optional.empty());

            // then/when
            ${endpoint.pojoName} changedVersion = ${endpoint.entityName}TestFixtures.oneWithResourceId();
            Optional<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.update${endpoint.entityName}(changedVersion);
            then(result.isEmpty()).isTrue();
        }

        @Test
        @SuppressWarnings("all")
        void shouldThrowExceptionWhenArgumentIsNull() {
            assertThrows(NullPointerException.class, () -> ${endpoint.entityVarName}Service.update${endpoint.entityName}(null));
        }
    }

    @Nested
    class Delete${endpoint.entityName}Tests {
        /*
         * Happy path - deletes an entity
         */
        @Test
        void shouldDeleteWhenEntityExists()  {
            // given one matching one is found
            given(${endpoint.entityVarName}Repository.deleteByResourceId(any())).willReturn(1L);

            ${endpoint.entityVarName}Service.delete${endpoint.entityName}ByResourceId(randomSeries.nextResourceId());

            // Verify the deleteByResourceId method was invoked
            verify(${endpoint.entityVarName}Repository, times(1)).deleteByResourceId(any());
        }

        /*
         * When deleting a non-existing EJB, silently return
         */
        @Test
        void shouldSilentlyReturnWhenEntityDoesNotExist() {
            // given no matching record is found
            given(${endpoint.entityVarName}Repository.deleteByResourceId(any())).willReturn(0L);

            ${endpoint.entityVarName}Service.delete${endpoint.entityName}ByResourceId(randomSeries.nextResourceId());

            // Verify the deleteByResourceId method was invoked
            verify(${endpoint.entityVarName}Repository, times(1)).deleteByResourceId(any());
        }

        @Test
        @SuppressWarnings("all")
        void shouldThrowExceptionWhenArgumentIsNull() {
            assertThrows(NullPointerException.class, () -> ${endpoint.entityVarName}Service.delete${endpoint.entityName}ByResourceId(null));
        }
    }
    
    
    /* -------------------------------------------
	   * Helper methods
	   * ------------------------------------------- */
	  protected ${endpoint.entityName} convertToPojo(${endpoint.ejbName} entity) {
  	    return ${endpoint.entityVarName}EntityToPojoConverter.convert(entity);
	  }
	  
	  protected ${endpoint.ejbName} convertToEntity(${endpoint.entityName} pojo) {
        return ${endpoint.entityVarName}PojoToEntityConverter.convert(pojo);
	  }
    
}