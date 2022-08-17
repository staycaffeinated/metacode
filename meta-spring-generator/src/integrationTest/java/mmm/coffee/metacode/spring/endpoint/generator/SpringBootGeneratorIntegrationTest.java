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
package mmm.coffee.metacode.spring.endpoint.generator;

import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.exception.CreateEndpointUnsupportedException;
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
import mmm.coffee.metacode.spring.converter.RouteConstantsConverter;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointDescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointDescriptorToTemplateModelConverter;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointTemplateModelToMapConverter;
import mmm.coffee.metacode.spring.endpoint.io.SpringEndpointMetaPropertiesHandler;
import mmm.coffee.metacode.spring.endpoint.mustache.MustacheEndpointDecoder;
import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

/**
 * Integration tests covering the `create project spring-boot` command
 */
class SpringBootGeneratorIntegrationTest {


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
                .descriptor2templateModel(new RestEndpointDescriptorToTemplateModelConverter(new NameConverter(), new RouteConstantsConverter()))
                .descriptor2predicate(new RestEndpointDescriptorToPredicateConverter())
                .outputHandler(new ContentToNullWriter())
                .templateRenderer(templateResolver)
                .build();
    }

    @Test
    void shouldFailBecauseEndpointsNotSupportedUnderVanillaSpringBootProject() {
        RestEndpointDescriptor descriptor = RestEndpointDescriptor.builder()
                .resource("Pet").route("/pet").build();

        // We don't chain together `doPreprocessing().generateCode()` to verify `CreateEndpointUnsupportedException`
        // is thrown, for this test case, when `generateCode` is invoked.
        generatorUnderTest.doPreprocessing(descriptor);
        assertThrows(CreateEndpointUnsupportedException.class, () -> generatorUnderTest.generateCode(descriptor));
    }

    // ------------------------------------------------------------------------------------------------------------
    //
    // Helper classes
    //
    // ------------------------------------------------------------------------------------------------------------

    private MetaPropertiesHandler<RestEndpointDescriptor> setUpMetaPropertiesHandler() {
        //
        // Mock-up what happens when the user enters: `create project spring-boot -n petstore -p org.acme.petstore --base-path /petstore`
        // 
        Configuration mockConfiguration = Mockito.mock(Configuration.class);
        when(mockConfiguration.getString(MetaProperties.FRAMEWORK)).thenReturn(Framework.SPRING_BOOT.frameworkName());
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
