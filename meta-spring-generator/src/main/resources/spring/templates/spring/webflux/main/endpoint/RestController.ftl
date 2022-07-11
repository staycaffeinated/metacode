<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.exception.*;
import ${endpoint.basePackage}.common.ResourceIdentity;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;
import ${endpoint.basePackage}.validation.ResourceId;

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
    @GetMapping (value=${endpoint.entityName}Routes.${endpoint.routeConstants.findAll}, produces = MediaType.APPLICATION_JSON_VALUE )
    public Flux<${endpoint.pojoName}> getAll${endpoint.entityName}s() {
        return ${endpoint.entityVarName}Service.findAll${endpoint.entityName}s();
    }

    /*
     * Get one by resourceId
     *
     */
    @GetMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, produces = MediaType.APPLICATION_JSON_VALUE )
    public Mono<${endpoint.pojoName}> get${endpoint.entityName}ById(@PathVariable @ResourceId String id) {
        return ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(id);
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
    @PostMapping (value=${endpoint.entityName}Routes.${endpoint.routeConstants.create}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<ResponseEntity<ResourceIdentity>> create${endpoint.entityName}(@RequestBody @Validated(OnCreate.class) ${endpoint.pojoName} resource ) {
        Mono<String> id = ${endpoint.entityVarName}Service.create${endpoint.entityName}(resource);
        return id.map(value -> ResponseEntity.status(HttpStatus.CREATED).body(new ResourceIdentity(value)));
    }
    
    /*
     * Update by resourceId
     */
    @PutMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.update}, produces = MediaType.APPLICATION_JSON_VALUE )
    public void update${endpoint.entityName}(@PathVariable @ResourceId String id, @RequestBody @Validated(OnUpdate.class) ${endpoint.pojoName} ${endpoint.entityVarName}) {
        if (!Objects.equals(id, ${endpoint.entityVarName}.getResourceId())) {
            log.error("Update declined: mismatch between query string identifier, {}, and resource identifier, {}", id, ${endpoint.entityVarName}.getResourceId());
            throw new UnprocessableEntityException("Mismatch between the identifiers in the URI and the payload");
        }
        ${endpoint.entityVarName}Service.update${endpoint.entityName}(${endpoint.entityVarName});
    }

    /*
     * Delete one
     */
    @DeleteMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.delete})
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete${endpoint.entityName}(@PathVariable @ResourceId String id) {
        Mono<${endpoint.pojoName}> resource = ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(id);
        resource.subscribe(value -> ${endpoint.entityVarName}Service.delete${endpoint.entityName}ByResourceId(id));
    }
}