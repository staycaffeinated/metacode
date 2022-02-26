/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring;

import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.dependency.DependencyCatalog;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.freemarker.ConfigurationFactory;
import mmm.coffee.metacode.common.freemarker.FreemarkerTemplateResolver;
import mmm.coffee.metacode.common.writer.ContentToNullWriter;
import mmm.coffee.metacode.spring.catalog.SpringWebMvcTemplateCatalog;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import mmm.coffee.metacode.spring.project.converter.DescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToRestProjectTemplateModelConverter;
import mmm.coffee.metacode.spring.project.generator.SpringWebMvcCodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;


/**
 * SpringWebMvcCodeGeneratorIT
 */
@Tag("integration")
class SpringWebMvcCodeGeneratorIntegrationTest {

    private static final String DEPENDENCY_FILE = "/spring/dependencies/dependencies.yml";
    private static final String TEMPLATE_DIRECTORY = "/spring/templates/";

    private static final String APP_NAME = "petstore";      // thought: change to 'artifactName'?
    private static final String BASE_PATH = "/petstore";
    private static final String BASE_PKG = "org.acme.petstore";

    SpringWebMvcCodeGenerator generatorUnderTest;

    @BeforeEach
    public void setUp() {
        generatorUnderTest = SpringWebMvcCodeGenerator.builder()
                .collector(new SpringWebMvcTemplateCatalog(new CatalogFileReader()))
                .descriptor2templateModel(new DescriptorToRestProjectTemplateModelConverter())
                .descriptor2Predicate(new DescriptorToPredicateConverter())
                .templateRenderer(new FreemarkerTemplateResolver(ConfigurationFactory.defaultConfiguration(TEMPLATE_DIRECTORY)))
                .outputHandler(new ContentToNullWriter())
                .dependencyCatalog(new DependencyCatalog(DEPENDENCY_FILE))
                .build();
    }

    /**
     * Verify a project with the minimum required properties
     * can be generated.
     */
    @Test
    void whenSimpleProject_shouldRenderTemplate() {
        var spec = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePackage(BASE_PATH)
                .build();

        assertThat(generatorUnderTest.generateCode(spec)).isEqualTo(ExitCodes.OK);
    }

    /**
     * Verify a project that includes Postgres integration can be generated.
     */
    @Test
    void whenPostgresIntegration_shouldRenderTemplate() {
        // given: postgres integration is enabled
        Set<String> integrations = buildIntegrations(WebMvcIntegration.POSTGRES.name());
        var spec = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePackage(BASE_PATH)
                .integrations(integrations)
                .build();

        // when: generating code, expect success
        assertThat(generatorUnderTest.generateCode(spec)).isEqualTo(ExitCodes.OK);
    }

    /**
     * Verify a project that includes TestContainers integration can be generated.
     */
    @Test
    void whenTestContainersIntegration_shouldRenderTemplate() {
        // given: testcontainers integration is enabled
        Set<String> integrations = buildIntegrations(WebMvcIntegration.TESTCONTAINERS.name());
        var spec = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePackage(BASE_PATH)
                .integrations(integrations)
                .build();

        // when: generating the code, expect success
        assertThat(generatorUnderTest.generateCode(spec)).isEqualTo(ExitCodes.OK);
    }

    /**
     * Verify a project that includes Postgres + TestContainers integrations can be generated.
     */
    @Test
    void whenTestContainersAndPostgresIntegration_shouldRenderTemplate() {
        // given: testcontainers and postgres integration is enabled
        Set<String> integrations = buildIntegrations(WebMvcIntegration.TESTCONTAINERS.name(),
                WebMvcIntegration.POSTGRES.name());

        var spec = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePackage(BASE_PATH)
                .integrations(integrations)
                .build();

        // when: generating code, expect success
        assertThat(generatorUnderTest.generateCode(spec)).isEqualTo(ExitCodes.OK);
    }

    /**
     * Verify a project that includes Liquibase integration can be generated.
     */
    @Test
    void whenLiquibaseIntegration_shouldRenderTemplate() {
        // given: liquibase integration is enabled
        Set<String> integrations = buildIntegrations(WebMvcIntegration.LIQUIBASE.name());
        var spec = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePackage(BASE_PATH)
                .integrations(integrations)
                .build();

        // when: generating code, expect success
        assertThat(generatorUnderTest.generateCode(spec)).isEqualTo(ExitCodes.OK);
    }

    /**
     * Verify a project that includes Liquibase integration can be generated.
     */
    @Test
    void whenPostgresAndTestContainersAndLiquibaseIntegration_shouldRenderTemplate() {
        // given: postgres, testcontainers, and liquibase integrations are enabled
        Set<String> integrations = buildIntegrations(WebMvcIntegration.POSTGRES.name(),
                WebMvcIntegration.TESTCONTAINERS.name(),
                WebMvcIntegration.LIQUIBASE.name());
        
        var spec = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePackage(BASE_PATH)
                .integrations(integrations)
                .build();

        // when: generating code, expect success
        assertThat(generatorUnderTest.generateCode(spec)).isEqualTo(ExitCodes.OK);
    }

    // ------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ------------------------------------------------------------------------------------------

    /**
     * Converts the given var-args of integration options into a set of String
     * @param args the integration options
     * @return the equivalent Set
     */
    private Set<String> buildIntegrations(String... args) {
        Set<String> rs = new HashSet<>();
        rs.addAll(Arrays.stream(args).toList());
        return rs;
    }
}
