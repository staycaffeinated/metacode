<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("all")
class ${endpoint.entityName}DocumentToPojoConverterTests {

    ${endpoint.entityName}DocumentToPojoConverter converter = new ${endpoint.entityName}DocumentToPojoConverter();

    private final SecureRandomSeries randomSeries = new SecureRandomSeries();

    @Test
    void shouldReturnNullWhenResourceIsNull() {
        assertThrows (NullPointerException.class, () ->  { converter.convert((${endpoint.documentName}) null); });
    }

    @Test
    void shouldReturnNullWhenListIsNull() {
        assertThrows (NullPointerException.class, () -> { converter.convert((List<${endpoint.documentName}>)null); });
    }

    /**
     * Verify that properties of the Document that must not be shared outside
     * the security boundary of the service are not copied into
     * the RESTful resource.  For example, the database ID
     * assigned to an entity bean must not be exposed to
     * external applications, thus the database ID is never
     * copied into a RESTful resource.
     */
    @Test
    void shouldCopyOnlyExposedProperties() {
        ${endpoint.documentName} bean = ${endpoint.documentName}TestFixtures.oneWithResourceId();

        ${endpoint.pojoName} resource = converter.convert(bean);
        assertThat(resource.getResourceId()).isEqualTo(bean.getResourceId());
        assertThat(resource.getText()).isEqualTo(bean.getText());
    }

    @Test
    void shouldCopyList() {
        var ejbList = ${endpoint.documentName}TestFixtures.allItems();

        List<${endpoint.pojoName}> pojoList = converter.convert(ejbList);
        assertThat(pojoList.size()).isEqualTo(${endpoint.documentName}TestFixtures.allItems().size());
    }

}
