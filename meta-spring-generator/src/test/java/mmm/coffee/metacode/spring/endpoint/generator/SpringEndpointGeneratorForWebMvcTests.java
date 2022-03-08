/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.generator;

import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.freemarker.ConfigurationFactory;
import mmm.coffee.metacode.common.freemarker.FreemarkerTemplateResolver;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.io.MetaPropertiesHandler;
import mmm.coffee.metacode.common.io.MetaPropertiesReader;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.writer.ContentToNullWriter;
import mmm.coffee.metacode.spring.catalog.SpringWebMvcTemplateCatalog;
import mmm.coffee.metacode.spring.converter.NameConverter;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointDescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointDescriptorToTemplateModelConverter;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointTemplateModelToMapConverter;
import mmm.coffee.metacode.spring.endpoint.function.MustacheEndpointDecoder;
import mmm.coffee.metacode.spring.endpoint.io.SpringEndpointMetaPropertiesHandler;
import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * The objective of these integration tests is to exercise the
 * parsing of the templates to ensure all template variables are
 * defined.  The production code templates are read and rendered,
 * with the rendered content written to /dev/null.
 */
@Tag("integration")
class SpringEndpointGeneratorForWebMvcTests {

    private static final String TEMPLATE_DIRECTORY = "/spring/templates/";

    /*
     * the object subjected to integration testing
     */
    SpringEndpointGenerator generatorUnderTest;


    /*
     * Initialization
     */
    @BeforeEach
    public void setUp() {
        MetaPropertiesHandler<RestEndpointDescriptor> mockMetaPropertiesHandler = setUpMetaPropertiesHandler();
        MustacheEndpointDecoder decoder = setUpMustacheDecoder();
        Collector templateCollector = setUpTemplateCollector();
        TemplateResolver<MetaTemplateModel> templateResolver = setUpTemplateResolver();

        generatorUnderTest = SpringEndpointGenerator.builder()
                .metaPropertiesHandler(mockMetaPropertiesHandler)
                .mustacheDecoder(decoder)
                .collector(templateCollector)
                .descriptor2templateModel(new RestEndpointDescriptorToTemplateModelConverter(new NameConverter()))
                .descriptor2predicate(new RestEndpointDescriptorToPredicateConverter())
                .outputHandler(new ContentToNullWriter())
                .templateRenderer(templateResolver)
                .build();
    }

    @Test
    void shouldRenderAllTemplates() {
        RestEndpointDescriptor descriptor = RestEndpointDescriptor.builder()
                                .resource("Pet").route("/pet").build();

        int exitCode = generatorUnderTest.doPreprocessing(descriptor).generateCode(descriptor);
        assertThat(exitCode).isEqualTo(ExitCodes.OK);
    }
    
    // ------------------------------------------------------------------------------------------------------------
    //
    // Helper classes
    //
    // ------------------------------------------------------------------------------------------------------------

    private MetaPropertiesHandler<RestEndpointDescriptor> setUpMetaPropertiesHandler() {
        Configuration mockConfiguration = Mockito.mock(Configuration.class);
        when(mockConfiguration.getString(MetaProperties.FRAMEWORK)).thenReturn(Framework.SPRING_WEBMVC.frameworkName());
        when(mockConfiguration.getString(MetaProperties.BASE_PACKAGE)).thenReturn("org.acme.petstore");
        when(mockConfiguration.getString(MetaProperties.BASE_PATH)).thenReturn("/petstore");

        MetaPropertiesReader mockReader = Mockito.mock(MetaPropertiesReader.class);
        when(mockReader.read()).thenReturn(mockConfiguration);

        return SpringEndpointMetaPropertiesHandler.builder().reader(mockReader).build();
    }

    private MustacheEndpointDecoder setUpMustacheDecoder() {
        return MustacheEndpointDecoder.builder().converter(new RestEndpointTemplateModelToMapConverter()).build();
    }

    private Collector setUpTemplateCollector() {
        return new SpringWebMvcTemplateCatalog(new CatalogFileReader());
    }

    private TemplateResolver<MetaTemplateModel> setUpTemplateResolver() {
        return new FreemarkerTemplateResolver(ConfigurationFactory.defaultConfiguration(TEMPLATE_DIRECTORY));
    }
}
