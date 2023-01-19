<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("all")
class ${endpoint.entityName}EntityToPojoConverterTests {

    ${endpoint.entityName}EntityToPojoConverter converter = new ${endpoint.entityName}EntityToPojoConverter();

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Test
    void shouldReturnNullWhenResourceIsNull() {
        assertThrows (NullPointerException.class, () ->  { converter.convert((${endpoint.ejbName}) null); });
    }

    @Test
    void shouldReturnNullWhenListIsNull() {
        assertThrows (NullPointerException.class, () -> { converter.convert((List<${endpoint.ejbName}>)null); });
    }

    /**
     * Verify that properties of the EJB that must not shared outside
     * the security boundary of the service are not copied into
     * the RESTful resource.  For example, the database ID
     * assigned to an entity bean must not be exposed to
     * external applications, thus the database ID is never
     * copied into a RESTful resource.
     */
    @Test
    void shouldCopyOnlyExposedProperties() {
        ${endpoint.ejbName} bean = ${endpoint.ejbName}TestFixtures.oneWithResourceId();

        ${endpoint.pojoName} resource = converter.convert(bean);
        assertThat(resource.getResourceId()).isEqualTo(bean.getResourceId());
        assertThat(resource.getText()).isEqualTo(bean.getText());
    }

    @Test
    void shouldCopyList() {
        var ejbList = ${endpoint.ejbName}TestFixtures.allItems();

        List<${endpoint.pojoName}> pojoList = converter.convert(ejbList);
        assertThat(pojoList.size()).isEqualTo(${endpoint.ejbName}TestFixtures.allItems().size());
    }

}
