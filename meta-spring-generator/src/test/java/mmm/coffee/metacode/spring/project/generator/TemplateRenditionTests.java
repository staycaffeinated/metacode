/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.generator;

import freemarker.template.Configuration;
import mmm.coffee.metacode.common.freemarker.ConfigurationFactory;
import mmm.coffee.metacode.common.freemarker.FreemarkerTemplateResolver;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.spring.project.model.RestProjectTemplateModel;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

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

        //
        // This series confirms behavior when flags _are_ enabled
        //
        @Test
        void whenPostgresFlagIsEnabled_expectUsesPostgresLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.postgresql");
            // make sure startWeb and not starterWebFlux is used
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
        }

        @Test
        void whenTestContainerFlagIsEnabled_expectUsesTestContainerLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithTestContainers(true);
            templateModel.setWebMvc(true);
            
            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.testContainersBom");
            assertThat(content).contains("libs.springCloud");
            assertThat(content).contains("libs.testContainersJupiter");
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
        }

        @Test
        void whenLiquibaseFlagIsEnabled_expectUsesLiquibaseLibrary() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithLiquibase(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.liquibaseCore");
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
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
            // also verify webmvc libs are used, not webflux libs
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
        }

        //
        // This series confirms behavior when flags _are not_ set. For example, when an integration
        // is not requested, the integration library should not be present.
        //
        @Test
        void whenPostgresFlagIsNotEnabled_expectPostgresLibraryIsAbsent() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).doesNotContain("libs.postgresql");
            // check for presence of webmvc libs
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
        }

        @Test
        void whenLiquibaseFlagIsNotEnabled_expectLiquibaseLibraryIsAbsent() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).doesNotContain("libs.liquibaseCore");
            // check for presence of webmvc libs
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
        }

        @Test
        void whenTestContainersFlagIsNotEnabled_expectTestContainersLibraryIsAbsent() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).doesNotContain("libs.testContainersBom");
            assertThat(content).doesNotContain("libs.testContainersJupiter");
            // check for presence of webmvc libs
            assertThat(content).contains("libs.springBootStarterWeb");
            assertThat(content).contains("libs.springBootStarterDataJpa");
        }

        @Test
        void whenWebFlux_expectSpringBootStarterWebFluxIsDependency() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebFlux(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("libs.springBootStarterDataR2dbc");
            assertThat(content).contains("libs.springBootStarterWebFlux");
            // check for presence of webmvc libs
            assertThat(content).contains("libs.problemSpringWebFlux");
            assertThat(content).contains("libs.problemJacksonDataType");
        }

    }

    @Nested
    class ApplicationDotPropertiesTests {
        // This path is relative to TEMPLATE_DIRECTORY
        final String template = "/spring/webmvc/main/resources/ApplicationDotProperties.ftl";

        @Test
        void whenPostgresTrue_expectPostgresJdbcDriver() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            assertThat(content).isNotNull();
            assertThat(content).contains("spring.datasource.url=jdbc:postgresql:");
            assertThat(content).contains("spring.datasource.driver-class-name=org.postgresql.Driver");
        }
        
        @Test
        void whenPostgresFlagIsNotEnabled_expectH2JdbcDriver() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            // When no database is specified, the templates default to the H2 in-memory database
            assertThat(content).isNotNull();
            assertThat(content).contains("spring.datasource.url=jdbc:h2:mem");
            assertThat(content).contains("spring.datasource.driver-class-name=org.h2.Driver");
        }
    }


    @Nested
    class ApplicationTestDotYamlTests {
        // This path is relative to TEMPLATE_DIRECTORY
        final String template = "/spring/webmvc/test/resources/ApplicationTestDotYaml.ftl";

        @Test
        void whenPostgresFlagEnabled_expectPostgresJdbcDriver() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            // these are some strings expected to be found.
            assertThat(content).isNotNull();
            assertThat(content).contains("driver-class-name: org.testcontainers.jdbc.ContainerDatabaseDriver");
            assertThat(content).contains("url: jdbc:tc:postgresql:9.6.8:///testdb?currentSchema=public");
            assertThat(content).contains("dialect: org.hibernate.dialect.PostgreSQLDialect");
            assertThat(content).contains("database: POSTGRESQL");
        }

        @Test
        void whenPostgresFlagIsNotEnabled_expectH2JdbcDriver() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            // When no database is specified, the templates default to the H2 in-memory database
            assertThat(content).isNotNull();
            assertThat(content).contains("driver-class-name: org.h2.Driver");
            assertThat(content).contains("url: jdbc:h2:mem:testdb");
            assertThat(content).contains("database-platform: org.hibernate.dialect.H2Dialect");
        }
    }

    @Nested
    class DockerComposeTests {
        // This path is relative to TEMPLATE_DIRECTORY
        final String template = "/docker/DockerCompose.ftl";

        @Test
        void whenPostgresFlagEnabled_expectPostgresJdbcDriver() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(true);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            // these are some strings expected to be found.
            assertThat(content).isNotNull();
            assertThat(content).contains("SPRING_DATASOURCE_URL=jdbc:postgresql");
            assertThat(content).contains("SPRING_DATASOURCE_USERNAME=postgres");
        }

        @Test
        void whenPostgresFlagIsNotEnabled_expectH2JdbcDriver() {
            RestProjectTemplateModel templateModel = buildBasicModel();
            templateModel.setWithPostgres(false);
            templateModel.setWebMvc(true);

            String content = templateResolver.render(template, templateModel);

            // When Postgres is not specified, the Postgres stanza should not be found.
            // Spot check for various Postgres properties to ensure they are not present.
            assertThat(content).isNotNull();
            assertThat(content).doesNotContain("SPRING_DATASOURCE_URL=jdbc:postgresql");
            assertThat(content).doesNotContain("SPRING_DATASOURCE_USERNAME=postgres");
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
                .coditoryPluginVersion("1.4")
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
