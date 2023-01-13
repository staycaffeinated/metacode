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
    void testGetAll${endpoint.entityName}s() {
        this.client.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findAll}).accept(MediaType.APPLICATION_JSON).exchange().expectStatus().isOk()
            .expectHeader().contentType(MediaType.APPLICATION_JSON).expectBody()
            .jsonPath("$.[0].text").isNotEmpty()
            .jsonPath("$.[0].resourceId").isNotEmpty();
    }
    
    @Test
    void testGetSingle${endpoint.entityName}() {
        create${endpoint.entityName}();

        this.client.get().uri(replaceId(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}))
                .accept(MediaType.APPLICATION_JSON)
                .exchange()
   			    .expectStatus().isOk().expectHeader()
                .contentType(MediaType.APPLICATION_JSON)
                .expectBody()
   			    .jsonPath("$.resourceId").isNotEmpty()
                .jsonPath("$.text").isNotEmpty();
    }

    @Test
    void testGet${endpoint.entityName}AsStream() throws Exception {
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

	@Test
	void testCreate${endpoint.entityName}() {
		${endpoint.pojoName} ${endpoint.entityVarName} = ${endpoint.entityName}Generator.generate${endpoint.entityName}();
		${endpoint.entityVarName}.setResourceId(null);

		this.client.post().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
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

		this.client.put().uri(replaceId(${endpoint.entityName}Routes.${endpoint.routeConstants.update}))
                    .contentType(MediaType.APPLICATION_JSON)
				    .body(Mono.just(${endpoint.entityVarName}), ${endpoint.pojoName}.class)
                    .exchange()
                    .expectStatus().isOk();
	}

	@Test
	void testDelete${endpoint.entityName}() {
		create${endpoint.entityName}();

		this.client.delete().uri(replaceId(${endpoint.entityName}Routes.${endpoint.routeConstants.delete})).exchange().expectStatus().isNoContent();
	}

	@Test
	void testResourceNotFoundException() throws Exception {
		this.client.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}.replaceAll("\\{id\\}", "12345")).accept(MediaType.APPLICATION_JSON)
		    .exchange().expectStatus().isNotFound().expectHeader().contentType(MediaType.APPLICATION_JSON)
            .expectBody().jsonPath("$.stackTrace").doesNotExist();
	}

    /* ---------------------------------------------------------------------------------------------------------
     * Helper methods
     * --------------------------------------------------------------------------------------------------------- */

    WebTestClient.ResponseSpec sendFindOnePetRequest(String id) {
        return this.client.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}.replaceAll("\\{id\\}", id))
            .accept(MediaType.APPLICATION_JSON).exchange();
    }

    WebTestClient.ResponseSpec sendFindAllPetsRequest() {
        return this.client.get().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.findAll})
            .accept(MediaType.APPLICATION_JSON).exchange();
    }

    WebTestClient.ResponseSpec sendCreatePetRequest(${endpoint.entityName} pojo) {
        return this.client.post().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.create})
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pojo), ${endpoint.entityName}.class).exchange();
    }

    WebTestClient.ResponseSpec sendUpdatePetRequest(${endpoint.entityName} pojo) {
        return this.client.put().uri(${endpoint.entityName}Routes.${endpoint.routeConstants.update}.replaceAll("\\{id\\}", pojo.getResourceId()))
            .contentType(MediaType.APPLICATION_JSON)
            .body(Mono.just(pojo), ${endpoint.entityName}.class).exchange();
    }

    WebTestClient.ResponseSpec sendDeletePetRequest(String resourceId) {
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

    /**
     * Use this to replace the 'id' parameter in the query string
     * with the resourceId from the instance variable, ${endpoint.entityVarName}
     */
	String replaceId(String path) {
		return path.replaceAll("\\{id\\}", ${endpoint.entityVarName}.getResourceId().toString());
	}
}