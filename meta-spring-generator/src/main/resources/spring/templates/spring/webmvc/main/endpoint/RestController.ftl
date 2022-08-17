<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter.*;
import ${endpoint.basePackage}.exception.UnprocessableEntityException;
import ${endpoint.basePackage}.validation.OnCreate;
import ${endpoint.basePackage}.validation.OnUpdate;
import ${endpoint.basePackage}.validation.ResourceId;
import ${endpoint.basePackage}.validation.SearchText;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.data.web.SortDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.List;
import java.util.Optional;
import java.net.URI;

@RestController
@RequestMapping
@Slf4j
@Validated
public class ${endpoint.entityName}Controller {

    private static final int DEFAULT_PAGE_NUMBER = 0;
    private static final int DEFAULT_PAGE_SIZE = 25;

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
    public List<${endpoint.pojoName}> getAll${endpoint.entityName}s() {
        return ${endpoint.entityVarName}Service.findAll${endpoint.entityName}s();
    }

    /*
     * Get one by resourceId
     *
     */
    @GetMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<${endpoint.pojoName}> get${endpoint.entityName}ById(@PathVariable @ResourceId String id) {
        return ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     * Create one
     */
    @PostMapping (value=${endpoint.entityName}Routes.${endpoint.routeConstants.create}, produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<${endpoint.pojoName}> create${endpoint.entityName}(@RequestBody @Validated(OnCreate.class) ${endpoint.pojoName} resource ) {
        try {
            ${endpoint.pojoName} savedResource = ${endpoint.entityVarName}Service.create${endpoint.entityName} ( resource );
            URI uri = ServletUriComponentsBuilder
                            .fromCurrentRequest()
                            .path("/{id}")
                            .buildAndExpand(savedResource.getResourceId())
                            .toUri();
            return ResponseEntity.created(uri).body(savedResource);
        }
        // if, for example, a database constraint prevents writing the data...
        catch (org.springframework.transaction.TransactionSystemException e) {
            log.error(e.getMessage());
            throw new UnprocessableEntityException();
        }
    }
    
    /*
     * Update one
     */
    @PutMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.findOne}, produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<${endpoint.pojoName}> update${endpoint.entityName}(@PathVariable @ResourceId String id, @RequestBody @Validated(OnUpdate.class) ${endpoint.pojoName} ${endpoint.entityVarName}) {
        if (!id.equals(${endpoint.entityVarName}.getResourceId())) {
            throw new UnprocessableEntityException("The identifier in the query string and request body do not match");
        }
        Optional<${endpoint.pojoName}> optional = ${endpoint.entityVarName}Service.update${endpoint.entityName}( ${endpoint.entityVarName} );
        return optional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     * Delete one
     */
    @DeleteMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.findOne})
    public ResponseEntity<${endpoint.pojoName}> delete${endpoint.entityName}(@PathVariable @ResourceId String id) {
        return ${endpoint.entityVarName}Service.find${endpoint.entityName}ByResourceId(id)
                .map(${endpoint.entityVarName} -> {
                    ${endpoint.entityVarName}Service.delete${endpoint.entityName}ByResourceId(id);
                    return ResponseEntity.ok(${endpoint.entityVarName});
                })
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    /*
     * Find by text
     */
    @GetMapping(value=${endpoint.entityName}Routes.${endpoint.routeConstants.search}, produces = MediaType.APPLICATION_JSON_VALUE)
    public Page<${endpoint.pojoName}> searchByText (
                        @RequestParam(name="text", required = true) @SearchText Optional<String> text,
                        @PageableDefault(page = DEFAULT_PAGE_NUMBER, size = DEFAULT_PAGE_SIZE)
                        @SortDefault.SortDefaults(
                            {@SortDefault(sort = "text", direction = Sort.Direction.ASC)}) Pageable pageable)
    {
        return ${endpoint.entityVarName}Service.findByText(text, pageable);
    }
}
