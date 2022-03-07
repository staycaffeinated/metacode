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
        final String resourceName = fromType.getResource();
        final String packageName = buildPackageName(fromType);
        final String packagePath = nameConverter.packageNameToPath(packageName);

        return RestEndpointTemplateModel.builder()
                .basePackage(fromType.getBasePackage())
                .basePackagePath(nameConverter.packageNameToPath(fromType.getBasePackage()))
                .basePath(fromType.getBasePath())
                .ejbName(nameConverter.toEntityName(resourceName))
                .entityName(nameConverter.toEntityName(resourceName))
                .entityVarName(nameConverter.toEntityVariableName(resourceName))
                .framework(fromType.getFramework())
                .lowerCaseEntityName(nameConverter.toLowerCaseEntityName(resourceName))
                .packageName(packageName)
                .packagePath(packagePath)
                .pojoName(nameConverter.toPojoClassName(resourceName))
                .resource(fromType.getResource())
                .route(fromType.getRoute())
                .tableName(nameConverter.toTableName(resourceName))
                .build();
    }

    /**
     * Returns the package name into which classes (and sub-packages) specific to this endpoint
     * are placed. For better cohesion of the classes, all classes that support and endpoint,
     * such as the Controller, Repository, Service classes, are all placed in a package named
     * something like: '{{basePackage}}.endpoint.pet' or '{{basePackage}}.endpoint.owner'.
     *
     * @param descriptor contains the command-line values entered by the end-user
     * @return the package name for this endpoint's classes and sub-packages.
     */
    private String buildPackageName(RestEndpointDescriptor descriptor) {
        final String resourceName = descriptor.getResource();
        final String lowerCaseName = nameConverter.toLowerCaseEntityName(resourceName);

        StringBuilder sb = new StringBuilder();
        sb.append(descriptor.getBasePackage()).append(".endpoint.").append(lowerCaseName);
        return sb.toString();
    }
}
