<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import ${endpoint.basePackage}.common.ResourceIdentity;

import ${endpoint.basePackage}.database.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.context.ApplicationContext;
import org.springframework.http.MediaType;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.reactive.server.EntityExchangeResult;
import org.springframework.test.web.reactive.server.FluxExchangeResult;
import org.springframework.test.web.reactive.server.WebTestClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.Duration;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Integration tests of ${endpoint.entityName}Controller
 */
@Slf4j
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
<#if (endpoint.isWithPostgres() && endpoint.isWithTestContainers())>
class ${endpoint.entityName}ControllerIntegrationTest extends PostgresTestContainer {
<#else>
class ${endpoint.entityName}ControllerIntegrationTest {
</#if>
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
   private ${endpoint.entityName}Repository repository;

   /*
    * Use this to fetch a record known to exist. The underlying database record
    * is created in the @BeforeEach method.
    */
   private String knownResourceId;

   	@Autowired
    public void setApplicationContext(ApplicationContext context) {
        this.client = WebTestClient.bindToApplicationContext(context).configureClient().build();
    }

    @BeforeEach
    void insertTestRecordsIntoDatabase() {
        repository.saveAll(${endpoint.entityName}EntityTestFixtures.allItems()).blockLast(Duration.ofSeconds(10));
        knownResourceId = ${endpoint.entityName}EntityTestFixtures.allItems().get(1).getResourceId();
    }

    @Test
    void shouldGetAll${endpoint.entityName}s() {
        // @formatter:off
        sendFindAll${endpoint.entityName}sRequest()
            .expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.[0].text")
            .isNotEmpty().jsonPath("$.[0].resourceId").isNotEmpty();
        // @formatter:on
    }

    @Test
    void shouldGetExisting${endpoint.entityName}() {
        // formatter:off
        sendFindOne${endpoint.entityName}Request(knownResourceId).expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
            .jsonPath("$.resourceId").isNotEmpty()
            .jsonPath("$.text").isNotEmpty();
        // formatter:on
    }

    @Test
    void shouldCreateNewPet() {
        ${endpoint.entityName} pojo = ${endpoint.entityName}TestFixtures.oneWithoutResourceId();

        // @formatter:off
        sendCreatePetRequest(pojo)
            .expectStatus().isCreated()
            .expectHeader().contentType(MediaType.APPLICATION_JSON);
        // @formatter:on
    }

    @Test
    void shouldUpdateAnExisting${endpoint.entityName}() {
        // Pick one of the persisted instances for an update.
        // Any one will do, as long as it's been persisted.
        ${endpoint.entityName}Entity targetEntity = ${endpoint.entityName}EntityTestFixtures.allItems().get(1);

        // Create an empty POJO and set the fields to update
        // (in this example, the text field).
        // The resourceId indicates which instance to update.
        ${endpoint.entityName} updatedItem = ${endpoint.entityName}.builder().build();
        updatedItem.setText("My new text");
        updatedItem.setResourceId(targetEntity.getResourceId());

        sendUpdate${endpoint.entityName}Request(updatedItem).expectStatus().isOk();
    }

    @Test
    void shouldQuietlyDeleteExistingEntity() {
        // Pick one of the persisted instances to delete
        ${endpoint.ejbName} existingItem = ${endpoint.ejbName}TestFixtures.allItems().get(1);
        sendDeletePetRequest(existingItem.getResourceId()).expectStatus().isNoContent();
    }

    @Test
    void shouldReturnNotFoundWhenResourceDoesNotExist() {
        // @formatter:off
        sendFindOne${endpoint.entityName}Request("ThisIdDoesNotExist").expectStatus().isNotFound()
            .expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody()
                // I've seen stack traces could come back with both labels
                .jsonPath("$.stackTrace").doesNotExist()
                .jsonPath("$.trace").doesNotExist();
        // @formatter:on
    }

    @Test
    void shouldGet${endpoint.entityName}AsStream() throws Exception {
        FluxExchangeResult<${endpoint.pojoName}> result
                = this.client.get()
                      .uri(${endpoint.entityName}Routes.${endpoint.routeConstants.stream})
                      .accept(MediaType.TEXT_EVENT_STREAM)
                      .exchange()
                      .expectStatus().isOk()
                      .returnResult(${endpoint.pojoName}.class);


        Flux<${endpoint.pojoName}> events = result.getResponseBody();

        StepVerifier.create(events)
                    .expectSubscription()
                    .expectNextMatches(p -> p.getResourceId() != null)
   			        .thenCancel().verify();
    }

    /* ---------------------------------------------------------------------------------------------------------
     * Helper methods
     * --------------------------------------------------------------------------------------------------------- */

    WebTestClient.ResponseSpec sendFindOne${endpoint.entityName}Request(String id) {
        return this.client.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}.replaceAll("\\{id\\}", id))
            .accept(MediaType.APPLICATION_JSON).exchange();
    }

    WebTestClient.ResponseSpec sendFindAll${endpoint.entityName}sRequest() {
        return this.client.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findAll})
            .accept(MediaType.APPLICATION_JSON).exchange();
    }

    WebTestClient.ResponseSpec sendCreate${endpoint.entityName}Request(${endpoint.entityName} pojo) {
        return this.client.post().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pojo), ${endpoint.entityName}.class).exchange();
    }

    WebTestClient.ResponseSpec sendUpdate${endpoint.entityName}Request(${endpoint.entityName} pojo) {
        return this.client.put().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.update}.replaceAll("\\{id\\}", pojo.getResourceId()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pojo), ${endpoint.entityName}.class).exchange();
    }

    WebTestClient.ResponseSpec sendDelete${endpoint.entityName}Request(String resourceId) {
        return this.client.delete().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.update}.replaceAll("\\{id\\}", resourceId)).exchange();
    }

    /**
     * Creates a new ${endpoint.entityName} then updates the resourceId of the instance variable, ${endpoint.entityVarName},
     * with the resourceId of the added ${endpoint.entityName}.
     */
	void create${endpoint.entityName}() {
        ${endpoint.entityVarName} = ${endpoint.entityName}Generator.generate${endpoint.entityName}();
		${endpoint.entityVarName}.setResourceId(null);

		EntityExchangeResult<ResourceIdentity> result = this.client.post().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
				.contentType(MediaType.APPLICATION_JSON).body(Mono.just(${endpoint.entityVarName}), ${endpoint.pojoName}.class).exchange().expectStatus()
				.isCreated().expectBody(ResourceIdentity.class).returnResult();

		// After the ${endpoint.entityVarName} is created, the endpoint returns the resourceId of the
		// created book. Here, the resourceId of the instance variable, ${endpoint.entityVarName}, is updated
		// to enable the current test to acquire the new ${endpoint.entityName}'s resourceId.
		String resourceId = result.getResponseBody().getResourceId();
		${endpoint.entityVarName}.setResourceId(resourceId);
	}

}