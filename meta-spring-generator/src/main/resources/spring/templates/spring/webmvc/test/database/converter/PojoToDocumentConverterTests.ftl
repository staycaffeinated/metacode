<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import ${endpoint.basePackage}.math.SecureRandomSeries;

import org.assertj.core.util.Lists;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SuppressWarnings("all")
class ${endpoint.entityName}PojoToDocumentConverterTests {

    ${endpoint.entityName}PojoToDocumentConverter converter = new ${endpoint.entityName}PojoToDocumentConverter();

    final SecureRandomSeries randomSeries = new SecureRandomSeries();

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
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(randomSeries.nextResourceId()).text("hello world").build();

        ${endpoint.documentName} bean = converter.convert(resource);
        assertThat(bean.getResourceId()).isEqualTo(resource.getResourceId());
        assertThat(bean.getText()).isEqualTo(resource.getText());
    }

    @Test
    void shouldCopyList() {
        ${endpoint.pojoName} resource = ${endpoint.pojoName}.builder().resourceId(randomSeries.nextResourceId()).text("hello world").build();
        var pojoList = Lists.list(resource);

        List<${endpoint.documentName}> ejbList = converter.convert(pojoList);
        assertThat(ejbList.size()).isOne();
        assertThat(ejbList.get(0).getResourceId()).isEqualTo(resource.getResourceId());
        assertThat(ejbList.get(0).getText()).isEqualTo(resource.getText());
    }
}
