<#-- @ftlroot "../../../.." -->
<#include "/common/Copyright.ftl">
package ${project.basePackage}.endpoint.root;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;

/**
 * The default implementation of this controller merely returns Http:200 responses to GET requests.
 */
@RestController
<#noparse>
@RequestMapping("${spring.webflux.base-path}")
</#noparse>
@Slf4j
public class RootController {

    private final RootService rootService;

    /*
     * Constructor
     */
    public RootController(RootService service) {
        this.rootService = service;
    }

    /*
     * An example Mono stream
     */
    @GetMapping (value= "/mono", produces = MediaType.APPLICATION_JSON_VALUE )
    public Mono<String> getHome() {
        rootService.doNothing();
        return Mono.just("OK").log();
    }
    
    /**
	 * An example Flux stream
	 */
	@GetMapping(value="/flux", produces = MediaType.APPLICATION_JSON_VALUE)
	public Flux<String> getFlux() {
		return Flux.just("OK").delayElements(Duration.ofMillis(10)).log();
	}
}