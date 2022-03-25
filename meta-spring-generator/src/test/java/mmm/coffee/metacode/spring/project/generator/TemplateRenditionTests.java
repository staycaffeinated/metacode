/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.generator;

import freemarker.cache.StatefulTemplateLoader;
import freemarker.template.Configuration;
import mmm.coffee.metacode.common.freemarker.ConfigurationFactory;
import mmm.coffee.metacode.common.freemarker.FreemarkerTemplateResolver;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.sql.Connection;

import static com.google.common.truth.Truth.assertThat;

/**
 * This collection of tests help us verify the content
 * rendered by the TemplateResolver contains expected content.
 *
 * These tests are helpful for verifying the expression syntax in
 * the templates, especially relative to the template model.
 * For example, if the templateModel has the field 'testContainer'
 * but the template is expecting 'templatecontainer', this group of
 * tests can catch those errors.
 *
 */
class TemplateRenditionTests {

    private static final String TEMPLATE_DIRECTORY = "/spring/templates/";

    private final Configuration configuration = ConfigurationFactory.defaultConfiguration(TEMPLATE_DIRECTORY);

    private final TemplateResolver<MetaTemplateModel> templateResolver = new FreemarkerTemplateResolver(configuration);


    @Nested
    class BuildDotGradleTests {

        // This path is relative to TEMPLATE_DIRECTORY
        final String template = "/gradle/BuildDotGradle.ftl";

        @Test
        void whenPostgresFlagIsEnabled_expectUsesPostgresLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.postgresql");
        }

        @Test
        void whenTestContainsFlagIsEnabled_expectUsesTestContainerLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithTestContainers(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.testContainersBom");
            assertThat(content).contains("libs.springCloud");
            assertThat(content).contains("libs.testContainersJupiter");
        }

        @Test
        void whenLiquibaseFlagIsEnabled_expectUsesLiquibaseLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithTestContainers(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.liquibaseCore");
        }

        @Test
        void whenLiquibaseAndPostgresAreEnabled_expectUsesPostgresTestContainerLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithTestContainers(true);
            templateModel.setWithPostgres(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.testContainersPostgres");
        }

    }

    // ------------------------------------------------------------------------------------------------
    //
    // Helper Methods
    //
    // ------------------------------------------------------------------------------------------------

    /**
     * Builds a RestProjectTemplateModel populated with properties
     * likely to be needed across several tests
     */
    private RestProjectTemplateModel buildBasicModel() {
        // the version numbers here are hypothetical
        return RestProjectTemplateModel.builder()
                .applicationName("petstore")
                .benManesPluginVersion("1.0")
                .javaVersion("11")
                .springBootVersion("2.5")
                .springDependencyManagementVersion("1.1")
                .sonarqubeVersion("3.3")
                .jibPluginVersion("1.0")
                .spotlessVersion("1.0")
                .lombokPluginVersion("1.3")
                .build();


    }




}
