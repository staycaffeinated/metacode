<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.exception.ResourceNotFoundException;
import ${endpoint.basePackage}.exception.UnprocessableEntityException;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.convert.ConversionService;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

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
    private ${endpoint.entityName}Repository mockRepository;

    @Mock
    private ApplicationEventPublisher publisher;

    @Mock
    private SecureRandomSeries mockSecureRandom;

    @InjectMocks
    private ${endpoint.entityName}Service serviceUnderTest;

    @Spy
    private final ConversionService conversionService = FakeConversionService.build();

    final SecureRandomSeries randomSeries = new SecureRandomSeries();


    @Test
    void shouldFindAll${endpoint.entityName}s() {
        Flux<${endpoint.ejbName}> ejbList = ${endpoint.ejbName}TestFixtures.FLUX_ITEMS;
        given(mockRepository.findAll()).willReturn(ejbList);

        Flux<${endpoint.pojoName}> stream = serviceUnderTest.findAll${endpoint.entityName}s();

        StepVerifier.create(stream).expectSubscription().expectNextCount(3).verifyComplete();
    }


    @Test
    void shouldFind${endpoint.entityName}ByResourceId() {
        /*
         * Mock the repository finding a given ${endpoint.ejbName}
         */
        ${endpoint.ejbName} expectedEJB = ${endpoint.ejbName}TestFixtures.oneWithResourceId();
        String expectedId = randomSeries.nextResourceId();
        expectedEJB.setResourceId(expectedId);
        Mono<${endpoint.ejbName}> rs = Mono.just(expectedEJB);
        given(mockRepository.findByResourceId(any(String.class))).willReturn(rs);

        /*
         * When: the service is asked to find an instance by its resourceId
         */
        Mono<${endpoint.pojoName}> publisher = serviceUnderTest.find${endpoint.entityName}ByResourceId(expectedId);

        /*
         * Expect: the returned stream to only contain the resource requested
         */
        StepVerifier.create(publisher)
                .expectSubscription()
                .consumeNextWith(item -> assertThat(Objects.equals(item.getResourceId(), expectedId)))
                .verifyComplete();
    }


    @Test
    void shouldFindAllByText() {
        /*
         * Mock the repository returning a flux stream of instances that all have matching values
         * in the column being searched.
         */
        final String expectedText = ${endpoint.ejbName}TestFixtures.ALL_WITH_SAME_TEXT.get(0).getText();
        Flux<${endpoint.ejbName}> expectedRows = Flux.fromIterable(${endpoint.ejbName}TestFixtures.ALL_WITH_SAME_TEXT);
        given(mockRepository.findAllByText(expectedText)).willReturn(expectedRows);

        /*
         * When: the service is asked to find all instances having the same value in a particular column
         */
        Flux<${endpoint.pojoName}> publisher = serviceUnderTest.findAllByText(expectedText);

        /*
         * Expect: the result set to contain the same instances found by the repository
         */
        StepVerifier.create(publisher)
                .expectSubscription()
                .consumeNextWith(item -> assertThat(Objects.equals(item.getText(), expectedText)))
                .consumeNextWith(item -> assertThat(Objects.equals(item.getText(), expectedText)))
                .consumeNextWith(item -> assertThat(Objects.equals(item.getText(), expectedText)))
                .verifyComplete();
    }


    @Test
    void shouldCreate${endpoint.entityName}() {
        // Given
        String expectedResourceId = randomSeries.nextResourceId();
        // what the client submits to the service
        ${endpoint.pojoName} expectedPOJO = ${endpoint.pojoName}.builder().text("Lorim ipsum dolor amount").build();
        // what the persisted version looks like
        ${endpoint.ejbName} persistedObj = conversionService.convert(expectedPOJO, ${endpoint.ejbName}.class);
        persistedObj.setResourceId(expectedResourceId);
        persistedObj.setId(1L);
        given(mockRepository.save(any(${endpoint.ejbName}.class))).willReturn(Mono.just(persistedObj));

        // When
        Mono<String> publisher = serviceUnderTest.create${endpoint.entityName}(expectedPOJO);

        // Then
        StepVerifier.create(publisher.log("testCreate : "))
                .expectSubscription().consumeNextWith(item -> assertThat(Objects.equals(item, expectedResourceId))).verifyComplete();

    }


    @Test
    void shouldUpdate${endpoint.entityName}() {
        // Given
        // what the client submits
        ${endpoint.pojoName} submittedPOJO = ${endpoint.pojoName}TestFixtures.oneWithResourceId();
        // what the new persisted value looks like
        ${endpoint.ejbName} persistedObj = conversionService.convert(submittedPOJO, ${endpoint.ejbName}.class);
        Mono<${endpoint.ejbName}> dataStream = Mono.just(persistedObj);
        given(mockRepository.findByResourceId(any(String.class))).willReturn(dataStream);
        given(mockRepository.save(persistedObj)).willReturn(dataStream);

        // When
        serviceUnderTest.update${endpoint.entityName}(submittedPOJO);

        // Then
        // verify publishEvent was invoked
        verify(publisher, times(1)).publishEvent(any());
    }


    @Test
    void shouldDelete${endpoint.entityName}() {
        String deletedId = randomSeries.nextResourceId();
        // The repository returns 1, to indicate 1 row was deleted
        given(mockRepository.deleteByResourceId(deletedId)).willReturn(Mono.just(1L));

        serviceUnderTest.delete${endpoint.entityName}ByResourceId(deletedId);

        verify(publisher, times(1)).publishEvent(any());
    }


	@Test
	void whenDeleteNull${endpoint.entityName}_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.delete${endpoint.entityName}ByResourceId((String) null));
	}

	@Test
	void whenFindNonExistingEntity_expectResourceNotFoundException() {
		given(mockRepository.findByResourceId(any())).willReturn(Mono.empty());

		Mono<${endpoint.ejbName}> publisher = serviceUnderTest.findByResourceId(randomSeries.nextResourceId());

		StepVerifier.create(publisher).expectSubscription().expectError(ResourceNotFoundException.class).verify();
	}

	@Test
	void whenUpdateOfNull${endpoint.entityName}_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.update${endpoint.entityName}(null));
	}

	@Test
	void whenFindAllByNullText_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.findAllByText(null));
	}

	@Test
	void whenCreateNull${endpoint.entityName}_expectNullPointerException() {
		assertThrows(NullPointerException.class, () -> serviceUnderTest.create${endpoint.entityName}(null));
	}

    /**
     * Per its API, a ConversionService::convert method _could_ return null.
     * The scope of this test case is to verify our own code's behavior should a null be returned.
     * In this case, an UnprocessableEntityException is thrown.
     */
	@Test
	void whenConversionToEjbFails_expectUnprocessableEntityException() {
        // given
		ConversionService mockConversionService = Mockito.mock(ConversionService.class);
		${endpoint.entityName}Service localService = new ${endpoint.entityName}Service(mockRepository, mockConversionService, publisher, new SecureRandomSeries());
        given(mockConversionService.convert(any(${endpoint.pojoName}.class), eq(${endpoint.ejbName}.class)))
                 .willReturn((${endpoint.ejbName}) null);

		${endpoint.pojoName} sample = ${endpoint.pojoName}.builder().text("sample").build();

        // when/then
		assertThrows(UnprocessableEntityException.class, () -> localService.create${endpoint.entityName}(sample));
	}
}