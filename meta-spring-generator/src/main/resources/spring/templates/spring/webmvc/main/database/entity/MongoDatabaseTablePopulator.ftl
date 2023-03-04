<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * This component loads sample data into the ${endpoint.tableName} database table.
 * This is suitable for testing and demonstration, but probably not
 * for production.
 */
@Component
@Slf4j
public class ${endpoint.entityName}TablePopulator implements ApplicationListener<ApplicationReadyEvent> {

    private final ${endpoint.entityName}Repository repository;
    private final SecureRandomSeries randomSeries;

    /**
     * Constructor
     */
    public ${endpoint.entityName}TablePopulator(${endpoint.entityName}Repository repository, SecureRandomSeries randomSeries) {
        this.repository = repository;
        this.randomSeries = randomSeries;
    }

    /**
     * Inserts sample data into the ${endpoint.tableName} table
     */
    @Override
    public void onApplicationEvent(ApplicationReadyEvent event) {
        repository.deleteAll();
        List<${endpoint.documentName}> sampleData = createSampleData();
        repository.saveAllAndFlush(sampleData);
    }

    /**
     * Creates a collection of sample data
     */
     private List<${endpoint.documentName}> createSampleData() {
         String[] textSamples = {"One", "Two", "Three", "Four", "Five"};
         List<${endpoint.documentName}> list = new ArrayList<>();
         for (String s : textSamples) {
             list.add(createOne(s));
         }
         return list;
     }

     /**
       * Creates a single ${endpoint.entityName} Document
       */
     private ${endpoint.documentName} createOne(String text) {
         return  ${endpoint.documentName}.builder()
            .text(text)
            .resourceId(randomSeries.nextResourceId())
            .build();
     }
}