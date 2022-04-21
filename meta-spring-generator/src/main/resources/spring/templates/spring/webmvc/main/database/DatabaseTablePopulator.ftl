<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database;

import ${endpoint.packageName}.${endpoint.ejbName};
import ${endpoint.packageName}.${endpoint.entityName}Repository;
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
public class PetTablePopulator implements ApplicationListener<ApplicationReadyEvent> {

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
        List<${endpoint.ejbName}> sampleData = createSampleData();
        repository.saveAllAndFlush(sampleData);
    }

    /**
     * Creates a collection of sample data
     */
     private List<${endpoint.ejbName}> createSampleData() {
         String[] textSamples = {"One", "Two", "Three", "Four", "Five"};
         List<${endpoint.ejbName}> list = new ArrayList<>();
         for (String s : textSamples) {
             list.add(createOne(s));
         }
         return list;
     }

     /**
       * Creates a single ${endpoint.entityName} entity bean
       */
     private ${endpoint.ejbName} createOne(String text) {
         ${endpoint.ejbName} bean = new ${endpoint.ejbName}();
         bean.setText(text);
         bean.setResourceId(randomSeries.nextResourceId());
         return bean;
     }
}