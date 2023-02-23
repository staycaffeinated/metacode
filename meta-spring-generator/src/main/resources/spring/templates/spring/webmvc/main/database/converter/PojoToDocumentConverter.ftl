<#include "/common/Copyright.ftl">

package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * Converts Drink entity beans into DrinkResource objects
 */
@Component
public class ${endpoint.entityName}PojoToDocumentConverter implements Converter<${endpoint.pojoName}, ${endpoint.documentName}> {
    /**
     * Convert the source object of type {@code ${endpoint.pojoName}} to target type {@code ${endpoint.documentName}}.
     *
     * @param resource the source object to convert, which must be an instance of {@code ${endpoint.pojoName}} (never {@code null})
     * @return the converted object, which must be an instance of {@code ${endpoint.documentName}} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ${endpoint.documentName} convert (@NonNull ${endpoint.pojoName} resource) {
        ${endpoint.documentName} target = new ${endpoint.documentName}();
        target.setResourceId (resource.getResourceId());
        target.setText (resource.getText());
        return target;
    }

    /**
     * Convert a list of POJOs into EJBs
     */
    public List<${endpoint.documentName}> convert (@NonNull List<${endpoint.pojoName}> sourceList) {
        return sourceList.stream().map(this::convert).toList();
    }
}
