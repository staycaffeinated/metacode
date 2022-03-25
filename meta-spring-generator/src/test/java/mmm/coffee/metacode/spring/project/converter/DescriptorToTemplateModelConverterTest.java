/*
 * Copyright 2022 Jon Caulfield
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
import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

/**
 * Unit tests
 */
class DescriptorToTemplateModelConverterTest {

    private static final String APP_NAME = "petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String BASE_PATH = "/petstore";

    RestProjectDescriptor webFluxRestProject;
    RestProjectDescriptor webMvcRestProject;

    final DescriptorToTemplateModelConverter converterUnderTest = new DescriptorToTemplateModelConverter();

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
    void shouldConvertToWebFluxTemplateModel() {
        // given: a converted object
        RestProjectTemplateModel context = converterUnderTest.convert(webFluxRestProject);

        // expect: 1:1 match of common fields
        assertThat(context.getApplicationName()).isEqualTo(webFluxRestProject.getApplicationName());
        assertThat(context.getBasePackage()).isEqualTo(webFluxRestProject.getBasePackage());
        assertThat(context.getBasePath()).isEqualTo(webFluxRestProject.getBasePath());
    }

    @Test
    void shouldConvertToWebMvcTemplateModel() {
        // given: a converted object
        RestProjectTemplateModel context = converterUnderTest.convert(webMvcRestProject);

        // expect: a 1:1 match of common fields
        assertThat(context.getApplicationName()).isEqualTo(webMvcRestProject.getApplicationName());
        assertThat(context.getBasePackage()).isEqualTo(webMvcRestProject.getBasePackage());
        assertThat(context.getBasePath()).isEqualTo(webMvcRestProject.getBasePath());
    }

    @Test
    void shouldSetPostgresIntegrationFlag() {
        // Given: a project with Postgres integration enabled
        webMvcRestProject.getIntegrations().add(SpringIntegrations.POSTGRES.name());

        // When: converting to a template model...
        RestProjectTemplateModel templateModel = converterUnderTest.convert(webMvcRestProject);

        // the 'withPostgres' flag should be 'true'
        assertThat(templateModel.isWithPostgres()).isTrue();
    }

    @Test
    void shouldSetTestContainersIntegrationFlag() {
        // Given: a project with TestContainers integration enabled
        webMvcRestProject.getIntegrations().add(SpringIntegrations.TESTCONTAINERS.name());

        // When: converting to a template model...
        RestProjectTemplateModel templateModel = converterUnderTest.convert(webMvcRestProject);

        // the 'withTestcontainers' flag should be 'true'
        assertThat(templateModel.isWithTestContainers()).isTrue();
    }

    @Test
    void shouldSetLiquibaseIntegrationFlag() {
        // Given: a project with Liquibase integration enabled
        webMvcRestProject.getIntegrations().add(SpringIntegrations.LIQUIBASE.name());

        // When: converting to a template model...
        RestProjectTemplateModel templateModel = converterUnderTest.convert(webMvcRestProject);

        // the 'withLiquibase' flag should be 'true'
        assertThat(templateModel.isWithLiquibase()).isTrue();
    }

    @Test
    void shouldSetPostgresAndTestContainersIntegrationFlags() {
        // Given: a project with Postgres & TestContainers integration enabled
        webMvcRestProject.getIntegrations().add(SpringIntegrations.POSTGRES.name());
        webMvcRestProject.getIntegrations().add(SpringIntegrations.TESTCONTAINERS.name());

        // When: converting to a template model...
        RestProjectTemplateModel templateModel = converterUnderTest.convert(webMvcRestProject);

        // expect: withTestContainers and withPostgres flags are both 'true'
        assertThat(templateModel.isWithTestContainers()).isTrue();
        assertThat(templateModel.isWithPostgres()).isTrue();
    }

    @Test
    void shouldSetPostgresTestContainersAndLiquibaseIntegrationFlags() {
        // Given: a project with Postgres, Liquibase, and TestContainers integration enabled
        webMvcRestProject.getIntegrations().add(SpringIntegrations.POSTGRES.name());
        webMvcRestProject.getIntegrations().add(SpringIntegrations.LIQUIBASE.name());
        webMvcRestProject.getIntegrations().add(SpringIntegrations.TESTCONTAINERS.name());


        // When: converting to a template model...
        RestProjectTemplateModel templateModel = converterUnderTest.convert(webMvcRestProject);

        // expect: the corresponding flags are set to 'true'
        assertThat(templateModel.isWithLiquibase()).isTrue();
        assertThat(templateModel.isWithPostgres()).isTrue();
        assertThat(templateModel.isWithTestContainers()).isTrue();
    }
}
