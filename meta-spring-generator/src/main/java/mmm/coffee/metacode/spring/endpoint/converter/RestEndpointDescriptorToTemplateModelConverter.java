/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import lombok.Builder;
import lombok.RequiredArgsConstructor;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.converter.NameConverter;
import mmm.coffee.metacode.spring.endpoint.context.RestEndpointTemplateModel;

/**
 * RestEndpointDescriptorToTemplateModelConverter
 */
@Builder
@RequiredArgsConstructor
public class RestEndpointDescriptorToTemplateModelConverter implements ConvertTrait<RestEndpointDescriptor, RestEndpointTemplateModel> {

    final NameConverter nameConverter;

    /**
     * Converts an instance of class {@code FROM} into an instance of class {@code TO}.
     * We let the implementer decide how to handle {@code nulls}
     *
     * @param fromType some instance to convert
     * @return the transformed object
     */
    @Override
    public RestEndpointTemplateModel convert(RestEndpointDescriptor fromType) {
        return RestEndpointTemplateModel.builder()
                .basePackage(fromType.getBasePackage())
                .basePath(fromType.getBasePath())
                .framework(fromType.getFramework())
                .entityName(nameConverter.toEntityName(fromType.getResource()))
                .entityVarName(nameConverter.toEntityVariableName(fromType.getResource()))
                .lowerCaseEntityName(nameConverter.toLowerCaseEntityName(fromType.getResource()))
                .build();         
    }
}
