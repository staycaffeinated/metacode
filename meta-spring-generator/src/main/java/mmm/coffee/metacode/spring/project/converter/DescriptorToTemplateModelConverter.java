/*
 * Copyright 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import mmm.coffee.metacode.spring.project.model.RestProjectTemplateModel;

/**
 * Converts a RestProjectDescriptor (which wraps the command-line args into a POJO)
 * into a SpringProjectContext (which provides the Model state for Freemarker templates).
 * Separate object types are used to obtain separation of concern between the command-line module
 * and the code-generation module.
 */
public class DescriptorToTemplateModelConverter implements ConvertTrait<RestProjectDescriptor, RestProjectTemplateModel> {
    public RestProjectTemplateModel convert(RestProjectDescriptor descriptor) {
        return RestProjectTemplateModel.builder()
                .applicationName(descriptor.getApplicationName())
                .basePackage(descriptor.getBasePackage())
                .basePath(descriptor.getBasePath())
                .isWebFlux(descriptor.getFramework().equals(Framework.SPRING_WEBFLUX.frameworkName()))
                .isWebMvc(descriptor.getFramework().equals(Framework.SPRING_WEBMVC.frameworkName()))
                .isSpringBatch(descriptor.getFramework().equals(Framework.SPRING_BATCH.frameworkName()))
                .isSpringBoot(descriptor.getFramework().equals(Framework.SPRING_BOOT.frameworkName()))
                .withPostgres(descriptor.getIntegrations().contains(SpringIntegrations.POSTGRES.name()))
                .withTestContainers(descriptor.getIntegrations().contains(SpringIntegrations.TESTCONTAINERS.name()))
                .withLiquibase(descriptor.getIntegrations().contains(SpringIntegrations.LIQUIBASE.name()))
                .build();
    }
}
