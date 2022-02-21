<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

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

    @Autowired
    public void setApplicationContext(ApplicationContext context) {
        webClient = WebTestClient.bindToApplicationContext(context).configureClient().baseUrl(applicationBasePath).build();
    }


    @Test
    void shouldGetOne${endpoint.entityName}() {
        final Long expectedResourceID = 1000L;
        ${endpoint.pojoName} pojo = ${endpoint.pojoName}.builder().text("testGetOne").resourceId(expectedResourceID).build();
        ${endpoint.ejbName} ejb = ${endpoint.ejbName}.builder().resourceId(expectedResourceID).text("testGetOne").build();

        when(mock${endpoint.entityName}Service.findByResourceId(expectedResourceID)).thenReturn(Mono.just(ejb));
        when(mock${endpoint.entityName}Service.find${endpoint.entityName}ByResourceId(expectedResourceID)).thenReturn(Mono.just(pojo));

        webClient.get().uri(${endpoint.entityName}Routes.GET_ONE, expectedResourceID)
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
                .expectStatus().isOk()
                .expectBody()
                .jsonPath("$.resourceId").isNotEmpty().jsonPath("$.text").isNotEmpty();

        Mockito.verify(mock${endpoint.entityName}Service, times(1)).find${endpoint.entityName}ByResourceId(expectedResourceID);
    }

    @Test
    void shouldGetAll${endpoint.entityName}s() {
        List<${endpoint.pojoName}> list = create${endpoint.entityName}List();
        Flux<${endpoint.pojoName}> flux = Flux.fromIterable(list);

        when(mock${endpoint.entityName}Service.findAll${endpoint.entityName}s()).thenReturn(flux);

        webClient.get().uri(${endpoint.entityName}Routes.GET_ALL).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
                .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody().jsonPath("$.[0].text").isNotEmpty()
                .jsonPath("$.[0].resourceId").isNotEmpty();
    }

    @Test
    void shouldCreate${endpoint.entityName}() {
        ${endpoint.pojoName} pojo = create${endpoint.entityName}();
        pojo.setResourceId(null);
        Long expectedId = 5000L;

        when(mock${endpoint.entityName}Service.create${endpoint.entityName}(any(${endpoint.pojoName}.class))).thenReturn(Mono.just(expectedId));

        webClient.post().uri(${endpoint.entityName}Routes.CREATE).contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(pojo), ${endpoint.pojoName}.class).exchange().expectStatus().isCreated().expectHeader()
                .contentType(MediaType.APPLICATION_JSON);
    }

    @Test
    void shouldUpdate${endpoint.entityName}() {
        ${endpoint.pojoName} pojo = create${endpoint.entityName}();
        webClient.put().uri(${endpoint.entityName}Routes.UPDATE, pojo.getResourceId())
                .contentType(MediaType.APPLICATION_JSON)
                .body(Mono.just(pojo), ${endpoint.pojoName}.class).exchange().expectStatus().isOk();
    }

	@Test
	void whenMismatchOfResourceIds_expectUnprocessableEntityException() {
		// Given
		${endpoint.pojoName} pojo = create${endpoint.entityName}();
		pojo.setResourceId(5000L);

		// when the ID in the URL is a mismatch to the ID in the POJO, the request should fail
		webClient.put().uri(${endpoint.entityName}Routes.UPDATE, 1000L).contentType(MediaType.APPLICATION_JSON)
				.body(Mono.just(pojo), ${endpoint.pojoName}.class).exchange().expectStatus().is4xxClientError();
	}

    @Test
    void shouldDelete${endpoint.entityName}() {
        ${endpoint.pojoName} pojo = create${endpoint.entityName}();
        when(mock${endpoint.entityName}Service.find${endpoint.entityName}ByResourceId(pojo.getResourceId())).thenReturn(Mono.just(pojo));

        webClient.delete().uri(${endpoint.entityName}Routes.DELETE, pojo.getResourceId()).exchange().expectStatus().isNoContent();
    }

 	@Test
	void shouldGet${endpoint.entityName}sAsStream() throws Exception {
		// Given
		List<${endpoint.pojoName}> resourceList = create${endpoint.entityName}List();
		given(mock${endpoint.entityName}Service.findAll${endpoint.entityName}s()).willReturn(Flux.fromIterable(resourceList));

		// When
		FluxExchangeResult<${endpoint.pojoName}> result = webClient.get().uri(${endpoint.entityName}Routes.GET_STREAM)
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


    /**
     * Generates a list of sample test data
     */
    private List<${endpoint.pojoName}> create${endpoint.entityName}List() {
        ${endpoint.pojoName} w1 = ${endpoint.pojoName}.builder().resourceId(1000L).text("Lorim ipsum dolor imit").build();
        ${endpoint.pojoName} w2 = ${endpoint.pojoName}.builder().resourceId(2000L).text("Hodor Hodor Hodor Hodor").build();
        ${endpoint.pojoName} w3 = ${endpoint.pojoName}.builder().resourceId(3000L).text("Now is the time to fly").build();

        ArrayList<${endpoint.pojoName}> list = new ArrayList<>();
        list.add(w1);
        list.add(w2);
        list.add(w3);

        return list;
    }

    /**
     * Generates a single test item
     */
    private ${endpoint.pojoName} create${endpoint.entityName}() {
        return ${endpoint.pojoName}.builder().resourceId(5000L).text("Duis aute irure dolor in reprehenderit.").build();
    }
}
