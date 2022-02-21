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

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/**
 * Unit tests
 */
class DescriptorToContextConverterTest {

    private static final String APP_NAME = "petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String BASE_PATH = "/petstore";

    RestProjectDescriptor webFluxRestProject;
    RestProjectDescriptor webMvcRestProject;

    final DescriptorToRestProjectTemplateModelConverter converterUnderTest = new DescriptorToRestProjectTemplateModelConverter();

    @BeforeEach
    public void createNewDescriptors() {
        webFluxRestProject = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .build();

        webMvcRestProject = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .build();
    }

    @Test
    void shouldConvertToWebFluxContext() {
        // given: a converted object
        RestProjectTemplateModel context = converterUnderTest.convert(webFluxRestProject);

        // expect: 1:1 match of common fields
        assertThat(context.getApplicationName()).isEqualTo(webFluxRestProject.getApplicationName());
        assertThat(context.getBasePackage()).isEqualTo(webFluxRestProject.getBasePackage());
        assertThat(context.getBasePath()).isEqualTo(webFluxRestProject.getBasePath());
    }

    @Test
    void shouldConvertToWebMvcContext() {
        // given: a converted object
        RestProjectTemplateModel context = converterUnderTest.convert(webMvcRestProject);

        // expect: a 1:1 match of common fields
        assertThat(context.getApplicationName()).isEqualTo(webMvcRestProject.getApplicationName());
        assertThat(context.getBasePackage()).isEqualTo(webMvcRestProject.getBasePackage());
        assertThat(context.getBasePath()).isEqualTo(webMvcRestProject.getBasePath());
    }

    @Disabled("Not implemented yet")
    void shouldConvertWebMvcProjectWithPostgresIntegration() {
        // Given: a WebMvc project with Postgres integration enabled
        webMvcRestProject.getIntegrations().add(WebMvcIntegration.POSTGRES.name());

        // When: converting to a template model...
        RestProjectTemplateModel templateModel = converterUnderTest.convert(webMvcRestProject);

        // Expect: the template model shows that postgres integration is enabled
        // TODO: how do we need to model this for Freemarker?
        // templateModel.integrations().postgres() is true
        // For now, fail because we need to implement this
        assertWithMessage("Not implemented yet").that(false).isTrue();
    }
}
