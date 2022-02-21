<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.common.ResourceIdentity;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests of ${endpoint.entityName}Controller
 */
@Slf4j
   @SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
   @DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class ${endpoint.entityName}ControllerTest {

   @LocalServerPort
   int port;
<#noparse>
   @Value("${spring.webflux.base-path}")
</#noparse>
   String applicationBasePath;
   private WebTestClient client;
   private ${endpoint.pojoName} ${endpoint.entityVarName};

   @Autowired
   private ${endpoint.entityName}Service ${endpoint.entityVarName}Service;

   	@Autowired
    public void setApplicationContext(ApplicationContext context) {
        this.client = WebTestClient.bindToApplicationContext(context).configureClient().baseUrl(applicationBasePath).build();
    }

    @Test
    void testGetAll${endpoint.entityName}s() {
        this.client.get().uri(${endpoint.entityName}Routes.GET_ALL).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
            .jsonPath("$.[0].text").isNotEmpty()
            .jsonPath("$.[0].resourceId").isNotEmpty();
    }
    
    @Test
    void testGetSingle${endpoint.entityName}() {
        create${endpoint.entityName}();

        this.client.get().uri(replaceId(${endpoint.entityName}Routes.GET_ONE))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
   			    .expectStatus().isOk().expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
   			    .jsonPath("$.resourceId").isNotEmpty()
                .jsonPath("$.text").isNotEmpty();
    }

    @Test
    void testGetCatalogItemsStream() throws Exception {
        FluxExchangeResult<${endpoint.pojoName}> result
                = this.client.get()
                      .uri(${endpoint.entityName}Routes.GET_STREAM)
                      .accept(MediaType.TEXT_EVENT_STREAM)
                      .exchange()
                      .expectStatus().isOk()
                      .returnResult(${endpoint.pojoName}.class);


        Flux<${endpoint.pojoName}> events = result.getResponseBody();

        StepVerifier.create(events)
                    .expectSubscription()
                    .expectNextMatches(p -> p.getResourceId() != null)
   			        .expectNextMatches(p -> p.getResourceId() != null)
                    .expectNextMatches(p -> p.getResourceId() != null)
   			        .thenCancel()
                    .verify();
    }

	@Test
	void testCreate${endpoint.entityName}() {
		${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.entityName}Generator.generate${endpoint.entityName}();
		${endpoint.entityVarName}.setResourceId(null);

		this.client.post().uri(${endpoint.entityName}Routes.CREATE)
                    .contentType(MediaType.APPLICATION_JSON)
				    .body(Mono.just( ${endpoint.entityVarName} ), ${endpoint.pojoName}.class)
                    .exchange()
                    .expectStatus().isCreated()
                    .expectHeader().contentType(MediaType.APPLICATION_JSON);
	}

    @Test
	void testUpdate${endpoint.entityName}() {
		create${endpoint.entityName}();

		${endpoint.entityVarName}.setText("my new text");

		this.client.put().uri(replaceId(${endpoint.entityName}Routes.UPDATE))
                    .contentType(MediaType.APPLICATION_JSON)
				    .body(Mono.just(${endpoint.entityVarName}), ${endpoint.pojoName}.class)
                    .exchange()
                    .expectStatus().isOk();
	}

	@Test
	void testDelete${endpoint.entityName}() {
		create${endpoint.entityName}();

		this.client.delete().uri(replaceId(${endpoint.entityName}Routes.DELETE)).exchange().expectStatus().isNoContent();
	}

	@Test
	void testResourceNotFoundException() throws Exception {
		this.client.get().uri(${endpoint.entityName}Routes.GET_ONE.replaceAll("\\{id\\}", "12345")).accept(MediaType.APPLICATION_JSON)
		    .exchange().expectStatus().isNotFound().expectHeader().contentType(MediaType.APPLICATION_JSON);
	}

    /**
     * Creates a new ${endpoint.entityName} then updates the resourceId of the instance variable, ${endpoint.entityVarName},
     * with the resourceId of the added ${endpoint.entityName}.
     */
	void create${endpoint.entityName}() {
        ${endpoint.entityVarName} = ${endpoint.entityName}Generator.generate${endpoint.entityName}();
		${endpoint.entityVarName}.setResourceId(null);

		EntityExchangeResult<ResourceIdentity> result = this.client.post().uri(${endpoint.entityName}Routes.CREATE)
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(${endpoint.entityVarName}), ${endpoint.pojoName}.class).exchange().expectStatus()
				.isCreated().expectBody(ResourceIdentity.class).returnResult();

		// After the ${endpoint.entityVarName} is created, the endpoint returns the resourceId of the
		// created book. Here, the resourceId of the instance variable, ${endpoint.entityVarName}, is updated
		// to enable the current test to acquire the new ${endpoint.entityName}'s resourceId.
		Long resourceId = result.getResponseBody().getResourceId();
		${endpoint.entityVarName}.setResourceId(resourceId);
	}

    /**
     * Use this to replace the 'id' parameter in the query string
     * with the resourceId from the instance variable, ${endpoint.entityVarName}
     */
	String replaceId(String path) {
		return path.replaceAll("\\{id\\}", ${endpoint.entityVarName}.getResourceId().toString());
	}
}