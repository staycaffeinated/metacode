<#include "/common/Copyright.ftl">

package ${endpoint.packageName};

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ${endpoint.entityName}ResourceToBeanConverterTests {

    ${endpoint.entityName}ResourceToBeanConverter converter = new ${endpoint.entityName}ResourceToBeanConverter();

    @Test
    void shouldReturnNullWhenResourceIsNull() {
        assertThrows (NullPointerException.class, () ->  { converter.convert((${endpoint.pojoName}) null); });
    }

    @Test
    void shouldReturnNullWhenListIsNull() {
        assertThrows (NullPointerException.class, () -> { converter.convert((List<${endpoint.pojoName}>)null); });
    }

    @Test
    void shouldPopulateAllFields() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(100L).text("hello world").build();

        ${endpoint.ejbName} bean = converter.convert(resource);
        assertThat(bean.getResourceId()).isEqualTo(resource.getResourceId());
        assertThat(bean.getText()).isEqualTo(resource.getText());
    }

    @Test
    void shouldCopyList() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(100L).text("hello world").build();
        var pojoList = Lists.list(resource);

        List<${endpoint.ejbName}> ejbList = converter.convert(pojoList);
        assertThat(ejbList.size()).isOne();
        assertThat(ejbList.get(0).getResourceId()).isEqualTo(resource.getResourceId());
        assertThat(ejbList.get(0).getText()).isEqualTo(resource.getText());
    }
}
