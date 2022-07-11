<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ${endpoint.entityName}EntityToPojoConverter implements Converter<${endpoint.ejbName}, ${endpoint.pojoName}> {

    /**
     * Convert the source object of type {@code ${endpoint.entityName}} to target type {@code ${endpoint.entityName}Resource}.
     *
     * @param source the source object to convert, which must be an instance of {@code ${endpoint.entityName}} (never {@code null})
     * @return the converted object, which must be an instance of {@code ${endpoint.entityName}Resource} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ${endpoint.pojoName} convert(@NonNull ${endpoint.ejbName} source) {
        return ${endpoint.pojoName}.builder().resourceId(source.getResourceId()).text(source.getText()).build();
    }

    /**
     * Convert a list of EJBs into POJOs
     */
    public List<${endpoint.pojoName}> convert (@NonNull List<${endpoint.ejbName}> sourceList) {
        return sourceList.stream().map(this::convert).toList();
    }
}