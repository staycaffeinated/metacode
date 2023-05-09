<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.exception.*;
import ${endpoint.basePackage}.common.ResourceIdentity;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;
import ${endpoint.basePackage}.validation.ResourceId;

<#if endpoint.isWithOpenApi()>
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
</#if>
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Objects;
import java.time.Duration;

@RestController
<#noparse>
@RequestMapping("")
</#noparse>
@Slf4j
@Validated
public class ${endpoint.entityName}Controller {

    private final ${endpoint.entityName}Service ${endpoint.entityVarName}Service;

    /*
     * Constructor
     */
     @Autowired
     public ${endpoint.entityName}Controller(${endpoint.entityName}Service ${endpoint.entityVarName}Service) {
        this.${endpoint.entityVarName}Service = ${endpoint.entityVarName}Service;
     }

    /*
     * Get all
     */
<#if endpoint.isWithOpenApi()>
    @Operation(summary = "Retrieve all ${endpoint.entityName}s")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Found all ${endpoint.entityName}s")})
</#if>
    @GetMapping (value=${endpoint.entityName}Routes.${endpoint.routeConstants.findAll}, produces = MediaType.APPLICATION_JSON_VALUE )
    public Flux<${endpoint.pojoName}> getAll${endpoint.entityName}s() {
        return ${endpoint.entityVarName}Service.findAll${endpoint.entityName}s();
    }

    /*
     * Get one by resourceId
     *
     */
<#if endpoint.isWithOpenApi()>
    @Operation(summary = "Retrieve a single${endpoint.entityName} based on its public identifier")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Found the ${endpoint.entityName}", content = {
                @Content(mediaType = "application/json", schema = @Schema(implementation = ${endpoint.pojoName}.class))}),
        @ApiResponse(responseCode = "400", description = "An invalid ID was supplied")})
</#if>
    @GetMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, produces = MediaType.APPLICATION_JSON_VALUE )
    public Mono<${endpoint.pojoName}> get${endpoint.entityName}ById(@PathVariable @ResourceId String id) {
        return ${endpoint.entityVarName}Service.findByResourceId(id);
    }
    
    /**
     * If api needs to push items as Streams to ensure Backpressure is applied, we
     * need to set produces to MediaType.TEXT_EVENT_STREAM_VALUE
     *
     * MediaType.TEXT_EVENT_STREAM_VALUE is the official media type for Server Sent
     * Events (SSE) MediaType.APPLICATION_STREAM_JSON_VALUE is for server to
     * server/http client communications.
     *
	 * https://stackoverflow.com/questions/52098863/whats-the-difference-between-text-event-stream-and-application-streamjson
	 *
	 */
    @GetMapping(value = ${endpoint.entityName}Routes.${endpoint.routeConstants.stream}, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    @ResponseStatus(value = HttpStatus.OK)
    public Flux<${endpoint.pojoName}> get${endpoint.entityName}Stream() {
	    // This is only an example implementation. Modify this line as needed.
        return ${endpoint.entityVarName}Service.findAll${endpoint.entityName}s().delayElements(Duration.ofMillis(250));
    }

    /*
     * Create
     */
<#if endpoint.isWithOpenApi()>
    @Operation(summary = "Create a new ${endpoint.entityName} entry and persist it")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Add a ${endpoint.entityName}", content = {
            @Content(mediaType = "application/json", schema = @Schema(implementation = ${endpoint.entityName}.class))}),
    @ApiResponse(responseCode = "400", description = "An invalid ID was supplied")})
</#if>
    @PostMapping (value=${endpoint.entityName}Routes.${endpoint.routeConstants.create}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Validated(OnCreate.class) 
    public Mono<ResponseEntity<ResourceIdentity>> create${endpoint.entityName}(@RequestBody ${endpoint.pojoName} resource ) {
        Mono<String> id = ${endpoint.entityVarName}Service.create${endpoint.entityName}(resource);
        return id.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(new ResourceIdentity(value)));
    }
    
    /*
     * Update by resourceId
     */
<#if endpoint.isWithOpenApi()>
    @Operation(summary = "Update an existing ${endpoint.entityName}")
        @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Updated the ${endpoint.entityName}"),
            @ApiResponse(responseCode = "400", description = "Incorrect data was submitted")})
</#if>
    @PutMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.update}, produces = MediaType.APPLICATION_JSON_VALUE )
    @Validated(OnUpdate.class) 
    public Mono<${endpoint.entityName}> update${endpoint.entityName}(@PathVariable @ResourceId String id, @RequestBody ${endpoint.pojoName} ${endpoint.entityVarName}) {
        if (!Objects.equals(id, ${endpoint.entityVarName}.getResourceId())) {
            log.error("Update declined: mismatch between query string identifier, {}, and resource identifier, {}", id, ${endpoint.entityVarName}.getResourceId());
            return Mono.error(new UnprocessableEntityException("Mismatch between the identifiers in the URI and the payload"));
        }
        return ${endpoint.entityVarName}Service.update${endpoint.entityName}(${endpoint.entityVarName});
    }

    /*
     * Delete one
     */
<#if endpoint.isWithOpenApi()>
    @Operation(summary = "Delete an existing ${endpoint.entityName}")
    @ApiResponses(value = {@ApiResponse(responseCode = "200", description = "Removed the ${endpoint.entityName}"),
        @ApiResponse(responseCode = "400", description = "An incorrect identifier was submitted")})
</#if>
    @DeleteMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.delete})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete${endpoint.entityName}(@PathVariable @ResourceId String id) {
        Mono<${endpoint.pojoName}> resource = ${endpoint.entityVarName}Service.findByResourceId(id);
        resource.subscribe(value -> ${endpoint.entityVarName}Service.delete${endpoint.entityName}ByResourceId(id));
    }
}