<#include "/common/Copyright.ftl">
package ${endpoint.packageName};

import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * unit tests
 */
class ${endpoint.entityName}EventTests {

    final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Test
    void shouldReturnEventTypeOfCreated() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(randomSeries.nextResourceId()).text("Hello world").build();
        ${endpoint.entityName}Event event = new ${endpoint.entityName}Event(${endpoint.entityName}Event.CREATED, resource);

   	    assertThat(event.getEventType()).isEqualTo(${endpoint.entityName}Event.CREATED);
    }

    @Test
    void shouldReturnEventTypeOfUpdated() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(randomSeries.nextResourceId()).text("Hello world").build();
        ${endpoint.entityName}Event event = new ${endpoint.entityName}Event(${endpoint.entityName}Event.UPDATED, resource);

   	    assertThat(event.getEventType()).isEqualTo(${endpoint.entityName}Event.UPDATED);
    }

    @Test
    void shouldReturnEventTypeOfDeleted() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(randomSeries.nextResourceId()).text("Hello world").build();
        ${endpoint.entityName}Event event = new ${endpoint.entityName}Event(${endpoint.entityName}Event.DELETED, resource);

   	    assertThat(event.getEventType()).isEqualTo(${endpoint.entityName}Event.DELETED);
    }
}