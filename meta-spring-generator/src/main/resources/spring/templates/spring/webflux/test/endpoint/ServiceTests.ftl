<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.exception.ResourceNotFoundException;
import ${endpoint.basePackage}.exception.UnprocessableEntityException;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

/**
 * Unit tests of the ${endpoint.entityName} service
 */
@ExtendWith(MockitoExtension.class)
@SuppressWarnings({"unused"})
class ${endpoint.entityName}ServiceTests {
    @Mock
    private ${endpoint.entityName}DataStore mockDataStore;

    @InjectMocks
    private ${endpoint.entityName}ServiceProvider serviceUnderTest;

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Test
    void shouldFindAll${endpoint.entityName}s() {
        Flux<${endpoint.pojoName}> itemList = ${endpoint.entityName}TestFixtures.FLUX_ITEMS;
        given(mockDataStore.findAll()).willReturn(itemList);

        Flux<${endpoint.pojoName}> stream = serviceUnderTest.findAll${endpoint.entityName}s();

        StepVerifier.create(stream).expectSubscription().expectNextCount(${endpoint.entityName}TestFixtures.ALL_ITEMS.size()).verifyComplete();
    }


    @Test
    void shouldFind${endpoint.entityName}ByResourceId() {
        /*
         * Mock the repository finding a given ${endpoint.ejbName}
         */
        ${endpoint.pojoName} expectedItem = ${endpoint.entityName}TestFixtures.oneWithResourceId();
        String expectedId = randomSeries.nextResourceId();
        expectedItem.setResourceId(expectedId);
        Mono<${endpoint.pojoName}> rs = Mono.just(expectedItem);
        given(mockDataStore.findByResourceId(any(String.class))).willReturn(rs);

        /*
         * When: the service is asked to find an instance by its resourceId
         */
        Mono<${endpoint.pojoName}> publisher = serviceUnderTest.findByResourceId(expectedId);

        /*
         * Expect: the returned stream to only contain the resource requested
         */
        StepVerifier.create(publisher)
                .expectSubscription()
                .consumeNextWith(item -> assertThat(item.getResourceId()).isEqualTo(expectedId))
                .verifyComplete();
    }


    @Test
    void shouldFindAllByText() {
        /*
         * Mock the repository returning a flux stream of instances that all have matching values
         * in the column being searched.
         */
        final String expectedText = ${endpoint.entityName}TestFixtures.allItemsWithSameText().get(0).getText();
        Flux<${endpoint.pojoName}> expectedRows = Flux.fromIterable(${endpoint.entityName}TestFixtures.allItemsWithSameText());
        given(mockDataStore.findAllByText(expectedText)).willReturn(expectedRows);

        /*
         * When: the service is asked to find all instances having the same value in a particular column
         */
        Flux<${endpoint.pojoName}> publisher = serviceUnderTest.findAllByText(expectedText);

        /*
         * Expect: the result set to contain the same instances found by the repository
         */
        StepVerifier.create(publisher)
                .expectSubscription()
                .consumeNextWith(item -> assertThat(item.getText()).isEqualTo(expectedText))
                .consumeNextWith(item -> assertThat(item.getText()).isEqualTo(expectedText))
                .consumeNextWith(item -> assertThat(item.getText()).isEqualTo(expectedText))
                .verifyComplete();
    }


    @Test
    void shouldCreate${endpoint.entityName}() {
        // Given
        String expectedResourceId = randomSeries.nextResourceId();
        // what the client submits to the service
        ${endpoint.pojoName} expectedPOJO = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
        given(mockDataStore.create${endpoint.entityName}(any(${endpoint.pojoName}.class))).willReturn(Mono.just(expectedResourceId));

        // When
        Mono<String> publisher = serviceUnderTest.create${endpoint.entityName}(expectedPOJO);

        // Then
        StepVerifier.create(publisher.log("testCreate : "))
                .expectSubscription().consumeNextWith(id -> assertThat(id).isEqualTo(expectedResourceId)).verifyComplete();

    }


    @Test
    void shouldUpdate${endpoint.entityName}() {
        // Given
        // what the client submits
        ${endpoint.pojoName} submittedPOJO = ${endpoint.entityName}TestFixtures.oneWithResourceId();
        Mono<${endpoint.pojoName}> reply = Mono.justOrEmpty(submittedPOJO);
        when(mockDataStore.update${endpoint.entityName}(any(${endpoint.pojoName}.class))).thenReturn(reply);

        // When
        Mono<${endpoint.pojoName}> publisher = serviceUnderTest.update${endpoint.entityName}(submittedPOJO);

        // Then
   		  StepVerifier.create(publisher).expectSubscription().consumeNextWith(
				    v -> assertThat(v.getResourceId()).isEqualTo(submittedPOJO.getResourceId())).verifyComplete();


        verify(mockDataStore, times(1)).update${endpoint.entityName}(any(${endpoint.pojoName}.class));
    }


    @Test
    void shouldDelete${endpoint.entityName}() {
        // The data store returns 1, to indicate 1 row was deleted
        given(mockDataStore.deleteByResourceId(any(String.class))).willReturn(Mono.just(1L));

        final String idToDelete = randomSeries.nextResourceId();
        serviceUnderTest.delete${endpoint.entityName}ByResourceId(idToDelete);

        verify(mockDataStore, times(1)).deleteByResourceId(any(String.class));
    }


	@Test
    @SuppressWarnings("all")
	void whenDeleteNull${endpoint.entityName}_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.delete${endpoint.entityName}ByResourceId((String) null));
	}

	@Test
	void whenFindNonExistingEntity_expectEmptyResultSet() {
		given(mockDataStore.findByResourceId(any())).willReturn(Mono.empty());

		Mono<${endpoint.pojoName}> publisher = serviceUnderTest.findByResourceId(randomSeries.nextResourceId());

		StepVerifier.create(publisher).expectSubscription().expectNextCount(0).verifyComplete();
	}

	@Test
    @SuppressWarnings("all")
	void whenUpdateOfNull${endpoint.entityName}_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.update${endpoint.entityName}(null));
	}

	@Test
    @SuppressWarnings("all")
	void whenFindAllByNullText_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.findAllByText(null));
	}

	@Test
    @SuppressWarnings("all")
	void whenCreateNull${endpoint.entityName}_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.create${endpoint.entityName}(null));
	}

    /**
     * Per its API, a ConversionService::convert method _could_ return null.
     * The scope of this test case is to verify our own code's behavior should a null be returned.
     * In this case, an UnprocessableEntityException is thrown.
     */
	@Test
    @SuppressWarnings("all")
	void whenConversionToEjbFails_expectUnprocessableEntityException() {
        // given
        ${endpoint.entityName}Repository mockRepository = Mockito.mock(${endpoint.entityName}Repository.class);
        ${endpoint.entityName}EntityToPojoConverter mockEjbConverter = Mockito.mock(${endpoint.entityName}EntityToPojoConverter.class);
        ${endpoint.entityName}PojoToEntityConverter dodgyConverter = Mockito.mock(${endpoint.entityName}PojoToEntityConverter.class);
        given(dodgyConverter.convert(any(${endpoint.pojoName}.class))).willReturn(null);

        ${endpoint.entityName}DataStore dodgyDataStore = ${endpoint.entityName}DataStoreProvider.builder()
                .pojoToEjbConverter(dodgyConverter)
                .ejbToPojoConverter(mockEjbConverter)
                .repository(mockRepository)
                .build();

		${endpoint.entityName}Service dodgyService = new ${endpoint.entityName}ServiceProvider(dodgyDataStore);

		${endpoint.pojoName} sample = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();

        // when/then
		assertThrows(UnprocessableEntityException.class, () -> dodgyService.create${endpoint.entityName}(sample));
	}
}