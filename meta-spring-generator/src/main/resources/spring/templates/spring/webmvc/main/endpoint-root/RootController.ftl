<#-- @ftlroot "../../../.." -->
<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * The default implementation of this controller merely returns Http:200 responses to GET requests.
 */
@RestController
@RequestMapping("/")
@Slf4j
public class RootController {

    RootService rootService;

    /*
     * Constructor
     */
    public RootController(RootService service) {
        this.rootService = service;
    }

    /*
     * The root path
     */
    @GetMapping (value= "", produces = MediaType.APPLICATION_JSON_VALUE )
    public ResponseEntity<String> getHome() {
        rootService.doNothing();
        return ResponseEntity.ok().build();
    }
}