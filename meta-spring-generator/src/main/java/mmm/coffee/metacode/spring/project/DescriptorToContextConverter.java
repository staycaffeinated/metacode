/*
 * Copyright 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;

/**
 * Converts a RestProjectDescriptor (which wraps the command-line args into a POJO)
 * into a SpringProjectContext (which provides the Model state for Freemarker templates).
 * Separate object types are used to obtain separation of concern between the command-line module
 * and the code-generation module.
 */
public class DescriptorToContextConverter implements ConvertTrait<RestProjectDescriptor, SpringProjectContext> {
    public SpringProjectContext convert(RestProjectDescriptor descriptor) {
        var result = SpringProjectContext.builder()
                .applicationName(descriptor.getApplicationName())
                .basePackage(descriptor.getBasePackage())
                .basePath(descriptor.getBasePath())
                .build();
        return result;         
    }
}
