<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.when;



/**
 * Unit test the ${endpoint.entityName}Controller
 */
@ExtendWith(SpringExtension.class)
@WebFluxTest(controllers = ${endpoint.entityName}Controller.class)
class ${endpoint.entityName}ControllerTests {

    @MockBean
    private ${endpoint.entityName}Service mock${endpoint.entityName}Service;

    @Autowired
    private WebTestClient webClient;
<#noparse>
    @Value("${spring.webflux.base-path}")
</#noparse>
    String applicationBasePath;

    final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        webClient = WebTestClient.bindToApplicationContext(context).configureClient().build();
    }


    @Test
    void shouldGetOne${endpoint.entityName}() {
        /*
         * Create a mock-up of the service.findByResourceId returning a known instance
         */
        final String expectedResourceID = randomSeries.nextResourceId();
        ${endpoint.pojoName} pojo = ${endpoint.pojoName}TestFixtures.oneWithResourceId();
        // because the EJB and POJO should look the same...
        ${endpoint.ejbName} ejb = ${endpoint.ejbName}.builder().resourceId(expectedResourceID).text(pojo.getText()).build();

        /*
         * When a REST call is made to fetch a ${endpoint.entityName} by its ID, expect to get back the mocked instance
         */
        when(mock${endpoint.entityName}Service.findByResourceId(expectedResourceID)).thenReturn(Mono.just(ejb));
        when(mock${endpoint.entityName}Service.find${endpoint.entityName}ByResourceId(expectedResourceID)).thenReturn(Mono.just(pojo));

        // @formatter:off
        sendFindOne${endpoint.entityName}Request(expectedResourceID)
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.resourceId").isNotEmpty()
                .jsonPath("$.text").isNotEmpty();
        // @formatter:on         

        Mockito.verify(mock${endpoint.entityName}Service, times(1)).find${endpoint.entityName}ByResourceId(expectedResourceID);
    }

    @Test
    void shouldGetAll${endpoint.entityName}s() {
        /*
         * Mock the service.findAll returning a known list of instances
         */
        Flux<${endpoint.pojoName}> flux = ${endpoint.pojoName}TestFixtures.FLUX_ITEMS;
        when(mock${endpoint.entityName}Service.findAll${endpoint.entityName}s()).thenReturn(flux);

        /*
         * Expect: the REST call to return the same instances the service.findAll fetched
         */
        // @formatter:off 
        sendFindAll${endpoint.entityName}sRequest()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                .jsonPath("$.[0].text").isNotEmpty()
                .jsonPath("$.[0].resourceId").isNotEmpty();
        // @formatter:on        
    }

    @Test
    void shouldCreate${endpoint.entityName}() {
        /*
         * Mock the service returning a ${endpoint.pojoName} and returning a predetermined
         * resourceId (since resourceIds are assigned server-side).
         */
        ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();
        String expectedId = randomSeries.nextResourceId();

        when(mock${endpoint.entityName}Service.create${endpoint.entityName}(any(${endpoint.pojoName}.class))).thenReturn(Mono.just(expectedId));

        // @formatter:off
        sendCreate${endpoint.entityName}Request(pojo)
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
        // @formatter:on           
    }

    @Test
    void shouldUpdate${endpoint.entityName}() {
        ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithResourceId();
        
        sendUpdate${endpoint.entityName}Request(pojo).expectStatus().isOk();
    }

	@Test
	void whenMismatchOfResourceIds_expectUnprocessableEntityException() {
		/*
         * Mock an attempt to update a ${endpoint.pojoName}, but the ID in the request body
         * does not match the ID in the query string.
         */
		${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithResourceId();
        String idInParameter = randomSeries.nextResourceId();

        /*
         * Expect: data validation to detect the mismatch between the resourceId in the body and
         * the resourceId in the query string, and return a client error
         */
		webClient.put().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.update}, idInParameter).contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(pojo), ${endpoint.pojoName}.class).exchange().expectStatus().is4xxClientError();
	}

    @Test
    void shouldDelete${endpoint.entityName}() {
        /*
         * Mock the service finding the ${endpoint.pojoName} to be deleted,
         * to emulate deleting a ${endpoint.pojoName} that does exist.
         */
        ${endpoint.pojoName} pojo = ${endpoint.entityName}TestFixtures.oneWithResourceId();
        when(mock${endpoint.entityName}Service.find${endpoint.entityName}ByResourceId(pojo.getResourceId())).thenReturn(Mono.just(pojo));

        /*
         * When deleting a Pet, expect the response code to be OK. Nothing else is expected;
         * the endpoint does not reveal whether the deleted item was found, or if the delete was successful.
         */
        // @formatter:off
        sendDelete${endpoint.entityName}Request(pojo.getResourceId()).expectStatus().isNoContent(); 
        // @formatter:on
    }

 	@Test
	void shouldGet${endpoint.entityName}sAsStream() throws Exception {
		// Given
		Flux<${endpoint.pojoName}> fluxOfResources = ${endpoint.entityName}TestFixtures.FLUX_ITEMS;
		given(mock${endpoint.entityName}Service.findAll${endpoint.entityName}s()).willReturn(fluxOfResources);

		// When
		FluxExchangeResult<${endpoint.pojoName}> result = webClient.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.stream})
				.accept(MediaType.TEXT_EVENT_STREAM).exchange().expectStatus().isOk()
				.returnResult(${endpoint.pojoName}.class);

		// Then
		Flux<${endpoint.pojoName}> events = result.getResponseBody();
		StepVerifier.create(events).expectSubscription().consumeNextWith(p -> {
			assertThat(p.getResourceId()).isNotNull();
			assertThat(p.getText()).isNotEmpty();
		}).consumeNextWith(p -> {
			assertThat(p.getResourceId()).isNotNull();
			assertThat(p.getText()).isNotEmpty();
		}).thenCancel().verify();
	}
		
    /* -----------------------------------------------------------------------
     * Helper methods
     * ----------------------------------------------------------------------- */

    WebTestClient.ResponseSpec sendFindOne${endpoint.entityName}Request(String id) {
        return webClient.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}.replaceAll("\\{id}", id))
            .accept(MediaType.APPLICATION_JSON).exchange();
    }

    WebTestClient.ResponseSpec sendFindAll${endpoint.entityName}sRequest() {
        return webClient.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findAll})
            .accept(MediaType.APPLICATION_JSON).exchange();
    }

    WebTestClient.ResponseSpec sendCreate${endpoint.entityName}Request(${endpoint.entityName} pojo) {
        return webClient.post().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pojo), ${endpoint.entityName}.class).exchange();
    }

    WebTestClient.ResponseSpec sendUpdate${endpoint.entityName}Request(${endpoint.entityName} pojo) {
        return webClient.put().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.update}.replaceAll("\\{id}", pojo.getResourceId()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pojo), ${endpoint.entityName}.class).exchange();
    }

    WebTestClient.ResponseSpec sendDelete${endpoint.entityName}Request(String resourceId) {
        return webClient.delete().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.update}.replaceAll("\\{id}", resourceId)).exchange();
    }
	
}
