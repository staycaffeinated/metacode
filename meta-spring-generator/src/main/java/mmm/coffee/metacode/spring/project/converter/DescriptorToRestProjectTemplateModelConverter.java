/*
 * Copyright 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;

/**
 * Converts a RestProjectDescriptor (which wraps the command-line args into a POJO)
 * into a SpringProjectContext (which provides the Model state for Freemarker templates).
 * Separate object types are used to obtain separation of concern between the command-line module
 * and the code-generation module.
 */
public class DescriptorToRestProjectTemplateModelConverter implements ConvertTrait<RestProjectDescriptor, RestProjectTemplateModel> {
    public RestProjectTemplateModel convert(RestProjectDescriptor descriptor) {
        return RestProjectTemplateModel.builder()
                .applicationName(descriptor.getApplicationName())
                .basePackage(descriptor.getBasePackage())
                .basePath(descriptor.getBasePath())
                .build();
    }
}
