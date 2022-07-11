<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;

/**
 * This component populates the ${endpoint.tableName} database table with sample data.
 * This is suitable for testing and demonstration, but probably not
 * what you want in production. 
 */
@Component
@Slf4j
public class ${endpoint.entityName}TablePopulator implements ApplicationListener<ApplicationReadyEvent> {

	private final ${endpoint.entityName}Repository repository;
	private final SecureRandomSeries randomSeries;

	/**
	 * Constructor
	 */
	public ${endpoint.entityName}TablePopulator (${endpoint.entityName}Repository repository, SecureRandomSeries secureRandom) {
	    this.repository = repository;
	    this.randomSeries = secureRandom;
	}

	@Override
	public void onApplicationEvent(ApplicationReadyEvent event) {
	    repository.deleteAll()
	              .thenMany(Flux.just("One", "Two", "Three", "Four", "Five").map(this::buildSampleRecord)
 	              .flatMap(repository::save))
	              .thenMany(repository.findAll()).subscribe(pet -> log.info("Saving " + pet.toString()));
	}

	/**
	 * Creates a sample database record
	 */
	private ${endpoint.ejbName} buildSampleRecord(String text) {
	    return ${endpoint.ejbName}.builder().resourceId(randomSeries.nextResourceId()).text(text).build();
	}
}