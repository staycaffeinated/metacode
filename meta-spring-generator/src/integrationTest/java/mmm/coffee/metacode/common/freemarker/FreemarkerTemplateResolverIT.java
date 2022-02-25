/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.freemarker;

import com.google.common.base.Predicate;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.dependency.DependencyCatalog;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.spring.catalog.SpringWebMvcTemplateCatalog;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;
import mmm.coffee.metacode.spring.project.converter.DescriptorToPredicateConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * FreemarkerTemplateResolverIT
 */
class FreemarkerTemplateResolverIT {

    private static final String TEMPLATE_FOLDER = "/spring/templates";
    private static final String APP_NAME = "petstore";
    private static final String BASE_PATH = "/petstore";
    private static final String BASE_PKG = "acme.petstore";

    private static final String DEPENDENCY_FILE = "/spring/dependencies/dependencies.yml";

    final FreemarkerTemplateResolver resolverUnderTest = new FreemarkerTemplateResolver(ConfigurationFactory.defaultConfiguration(TEMPLATE_FOLDER));

    RestProjectTemplateModel templateModel;

    final DependencyCatalog dependencyCatalog = new DependencyCatalog(DEPENDENCY_FILE);

    /*
     * Use the Spring templates for test coverage
     */
    final Collector templateCatalog = new SpringWebMvcTemplateCatalog(new CatalogFileReader());

    RestProjectDescriptor projectDescriptor;

    @BeforeEach
    public void setUpTemplateModel() {
        templateModel = RestProjectTemplateModel.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .framework(TemplateResolver.Framework.WEBMVC.value())
                .build();

        // Set up our base project model
        projectDescriptor = RestProjectDescriptor.builder()
                .applicationName("petstore")
                .basePackage("acme.petstore")
                .basePath("/petstore")
                .build();
    }

    /**
     * This test takes a RestTemplateModel and uses it to render every template.
     * This test will tell us if a template has an undefined variable, which needs
     * to be added to the template model. Freemarker throws an exception whenever
     * an undefined variable is encountered in a template. To ensure our template model
     * defines every variable that might be consumed by a template, we have this
     * test case try every template.
     */
    @Test
    void shouldRenderAllProjectTemplates() {
        // prelim: create a Predicate to include project templates
        // and exclude endpoint templates.
        var converter = new DescriptorToPredicateConverter();
        Predicate<CatalogEntry> keepThese = converter.convert(projectDescriptor);
        templateModel.apply(dependencyCatalog);

        templateCatalog.collect().stream().filter(keepThese).forEach(it -> {
            var content = resolverUnderTest.render(it.getTemplate(), templateModel, TemplateResolver.Key.PROJECT);
            assertThat(content).isNotEmpty();
        });
    }
}
