/*
 * Copyright 2020 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.spring.project.converter;

import lombok.NonNull;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import mmm.coffee.metacode.spring.project.context.WebMvcTemplateModel;

/**
 * Creates a TemplateModel from a ProjectDescriptor
 */
public class DescriptorToWebMvcTemplateModel implements ConvertTrait<RestProjectDescriptor, WebMvcTemplateModel> {
    public WebMvcTemplateModel convert(@NonNull RestProjectDescriptor descriptor) {
        return WebMvcTemplateModel.builder()
                .applicationName(descriptor.getApplicationName())
                .basePackage(descriptor.getBasePackage())
                .basePath(descriptor.getBasePath())
                .enableLiquibase(descriptor.getIntegrations().contains(WebMvcIntegration.LIQUIBASE.name()))
                .enablePostgres(descriptor.getIntegrations().contains(WebMvcIntegration.POSTGRES.name()))
                .enableTestContainers(descriptor.getIntegrations().contains(WebMvcIntegration.TESTCONTAINERS.name()))
                .build();
    }

}
