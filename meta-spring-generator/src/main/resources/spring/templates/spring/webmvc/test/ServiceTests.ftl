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

    @BeforeEach
    void setUpEachTime() {
        ${endpoint.ejbName} item1 = new ${endpoint.ejbName}(1L, randomSeries.nextResourceId(), "text 1");
        ${endpoint.ejbName} item2 = new ${endpoint.ejbName}(2L, randomSeries.nextResourceId(), "text 2");
        ${endpoint.ejbName} item3 = new ${endpoint.ejbName}(3L, randomSeries.nextResourceId(), "text 3");

        ${endpoint.entityVarName}List = new LinkedList<>();
        ${endpoint.entityVarName}List.add( item1 );
        ${endpoint.entityVarName}List.add( item2 );
        ${endpoint.entityVarName}List.add( item3 );
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
            List<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.findByText("text", 1, 20);

            then(result).isNotNull();       // must never return null

            // depending on which is smaller, the sample size or the page size, we expect that many rows back
            then(result.size()).isEqualTo(Math.min(${endpoint.entityVarName}List.size(), pageable.getPageSize()));
        }

        @Test
        @SuppressWarnings("unchecked")
        void shouldReturnEmptyListWhenNoDataFound() {
            given( ${endpoint.entityVarName}Repository.findAll(any(Specification.class), any(Pageable.class))).willReturn( Page.empty() );

            List<${endpoint.pojoName}> result = ${endpoint.entityVarName}Service.findByText("foo", 1, 100);

            then(result).isNotNull();       // must never get null back
            then(result.size()).isZero();   // must have no content for this edge case
        }

        @Test
        @SuppressWarnings("all")
        void shouldThrowNullPointerExceptionWhen${endpoint.entityName}IsNull() {
            assertThrows (NullPointerException.class, () ->  ${endpoint.entityVarName}Service.findByText(null, 1, 100));
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
            final String sampleText = "sample text";
            final ${endpoint.ejbName} expectedEJB = new ${endpoint.ejbName}(1L, randomSeries.nextResourceId(), sampleText);
            given(${endpoint.entityVarName}Repository.save(any())).willReturn(expectedEJB);

            // when/then
            ${endpoint.pojoName} sampleData = ${endpoint.entityName}.builder().text(sampleText).build();
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
            String resourceId = randomSeries.nextResourceId();
            ${endpoint.pojoName} changedVersion = ${endpoint.pojoName}.builder().resourceId(resourceId).text("new text").build();
            ${endpoint.ejbName} originalEJB = new ${endpoint.ejbName} (1L, resourceId, "old text");
            ${endpoint.ejbName} updatedEJB = ${endpoint.entityVarName}PojoToEntityConverter.convert (changedVersion);
            given(${endpoint.entityVarName}Repository.findByResourceId(any())).willReturn(Optional.of(originalEJB));
            given(${endpoint.entityVarName}Repository.save(any())).willReturn(updatedEJB);

            // when/then
            Optional<${endpoint.pojoName}> optional = ${endpoint.entityVarName}Service.update${endpoint.entityName}(changedVersion);
            then(optional).isNotNull();
            then(optional.isPresent()).isTrue();
            if (optional.isPresent()) {
               then (optional.get().getResourceId()).isEqualTo(resourceId);
               then (optional.get().getText()).isEqualTo("new text");
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
            ${endpoint.pojoName} changedVersion = ${endpoint.entityName}.builder().resourceId(randomSeries.nextResourceId()).text("new text").build();
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
}