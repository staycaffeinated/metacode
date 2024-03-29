<#include "/common/Copyright.ftl">
package ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.converter;

import ${endpoint.basePackage}.database.${endpoint.lowerCaseEntityName}.*;
import lombok.NonNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ${endpoint.documentName}ToPojoConverter implements Converter<${endpoint.documentName}, ${endpoint.pojoName}> {

    /**
     * Convert the source object of type {@code ${endpoint.documentName}} to target type {@code ${endpoint.pojoName}}.
     *
     * @param source the source object to convert, which must be an instance of {@code ${endpoint.documentName}} (never {@code null})
     * @return the converted object, which must be an instance of {@code ${endpoint.pojoName}} (potentially {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @Override
    public ${endpoint.pojoName} convert(@NonNull ${endpoint.documentName} source) {
        return ${endpoint.pojoName}.builder()
                        .resourceId( source.getResourceId() )
                        .text( source.getText() )
                        .build();
    }

    /**
     * Convert a list of EJBs into POJOs
     */
    public List<${endpoint.pojoName}> convert (@NonNull List<${endpoint.documentName}> sourceList) {
        return sourceList.stream().map(this::convert).toList();
    }
}