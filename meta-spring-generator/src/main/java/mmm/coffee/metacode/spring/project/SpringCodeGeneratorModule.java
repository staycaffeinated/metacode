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
package mmm.coffee.metacode.spring.project;

import com.google.inject.AbstractModule;
import com.google.inject.Provides;
import freemarker.template.Configuration;
import mmm.coffee.metacode.annotations.guice.FreemarkerConfigurationProvider;
import mmm.coffee.metacode.annotations.guice.SpringWebFlux;
import mmm.coffee.metacode.annotations.guice.SpringWebMvc;
import mmm.coffee.metacode.annotations.guice.WriteOutputProvider;
import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.common.catalog.CatalogFileReader;
import mmm.coffee.metacode.common.freemarker.ConfigurationFactory;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;
import mmm.coffee.metacode.common.writer.ContentToFileWriter;
import mmm.coffee.metacode.spring.catalog.SpringWebFluxTemplateCatalog;
import mmm.coffee.metacode.spring.catalog.SpringWebMvcTemplateCatalog;
import mmm.coffee.metacode.spring.project.converter.DescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToRestProjectTemplateModelConverter;
import mmm.coffee.metacode.spring.project.generator.SpringWebFluxCodeGenerator;
import mmm.coffee.metacode.spring.project.generator.SpringWebMvcCodeGenerator;

/**
 * Module for SpringWebMvc Project generator
 */
@SuppressWarnings("java:S1452") // S1452: allow generic wildcards for the moment
@Generated // Ignore code coverage for this class
public final class SpringCodeGeneratorModule extends AbstractModule {
    
    @Provides
    @SpringWebMvc
    ICodeGenerator<?> provideSpringWebMvcGenerator() {
        return SpringWebMvcCodeGenerator.builder()
                .collector(new SpringWebMvcTemplateCatalog(new CatalogFileReader()))
                .descriptor2context(new DescriptorToRestProjectTemplateModelConverter())
                .descriptor2Predicate(new DescriptorToPredicateConverter())
                .templateRenderer(new TemplateResolver(ConfigurationFactory.defaultConfiguration("/spring/templates")))
                .outputHandler(new ContentToFileWriter())
                .build();
    }

    @Provides
    @SpringWebFlux
    ICodeGenerator<?> providesSpringWebFluxGenerator() {
        return SpringWebFluxCodeGenerator.builder()
                .collector(new SpringWebFluxTemplateCatalog(new CatalogFileReader()))
                .descriptor2templateModel(new DescriptorToRestProjectTemplateModelConverter())
                .descriptor2predicate(new DescriptorToPredicateConverter())
                .templateRenderer(new TemplateResolver(ConfigurationFactory.defaultConfiguration("/spring/templates")))
                .outputHandler(new ContentToFileWriter())
                .build();
    }

    @Provides
    @FreemarkerConfigurationProvider
    Configuration providesFreemarkerConfiguration() {
        // The resource path to the Freemarker templates will be different for each
        // code generator that's plugged in. For instance, Micronaut templates will
        // be in a different folder than the Spring templates.  When the Configuration
        // object is created, the base folder used by the Configuration instance varies.
        return ConfigurationFactory.defaultConfiguration("/spring/templates/"); }

    /**
     * The code generator needs a class that will handle writing content to a file.
     * Specifically, once a template is parsed and rendered as a String, that String
     * gets written to a file (such as a .java source file). The WriteOutputProvider
     * provides the class that handles that.
     *
     * The WriteOutputProvider is made "pluggable" so that, during testing, a NullWriter
     * can be used to exercise the code w/o producing files that need to be cleaned up
     * after the tests finish.
     */
    @Provides
    @WriteOutputProvider
    WriteOutputTrait providesWriteOutput() { return new ContentToFileWriter(); }

}
