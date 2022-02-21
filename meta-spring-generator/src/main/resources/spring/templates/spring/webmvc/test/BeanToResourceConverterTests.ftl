<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ${endpoint.entityName}BeanToResourceConverterTests {

    ${endpoint.entityName}BeanToResourceConverter converter = new ${endpoint.entityName}BeanToResourceConverter();

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
        ${endpoint.ejbName} bean = new ${endpoint.ejbName}();
        bean.setResourceId(12345L);
        bean.setText("hello, world");

        ${endpoint.pojoName} resource = converter.convert(bean);
        assertThat(resource.getResourceId()).isEqualTo(bean.getResourceId());
        assertThat(resource.getText()).isEqualTo(bean.getText());
    }
}
