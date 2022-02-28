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
package mmm.coffee.metacode.spring.project.generator;

import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.dependency.Dependency;
import mmm.coffee.metacode.common.dependency.DependencyCatalog;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.writer.ContentToNullWriter;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import mmm.coffee.metacode.spring.project.converter.DescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToRestProjectTemplateModelConverter;
import mmm.coffee.metacode.spring.project.converter.RestTemplateModelToMapConverter;
import mmm.coffee.metacode.spring.project.function.MustacheDecoder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

/**
 * Unit test
 */
@SuppressWarnings("unchecked")
class SpringCodeGeneratorTest {

    private static final String APP_NAME = "petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String BASE_PATH = "/petstore";

    SpringCodeGenerator generatorUnderTest;

    Collector fakeCollector;

    @Mock
    TemplateResolver<MetaTemplateModel> mockRenderer;

    @Mock
    DependencyCatalog mockDependencyCollector;
    
    @BeforeEach
    public void setUp() {
        // create a Collector that'll return some test data
        fakeCollector = new FakeCollector();

        // In the TemplateResolver, we just need the
        // {@code render} method to return a non-null String.
        // For these tests, we want to confirm the Generator's
        // 'pipeline' works.
        mockRenderer = Mockito.mock(TemplateResolver.class);
        when(mockRenderer.render(any(), any())).thenReturn("");

        mockDependencyCollector = Mockito.mock(DependencyCatalog.class);
        when(mockDependencyCollector.collect()).thenReturn(buildFakeDependencies());

        generatorUnderTest = SpringCodeGenerator.builder()
                .collector(fakeCollector)
                .descriptor2templateModel(new DescriptorToRestProjectTemplateModelConverter())
                .descriptor2predicate(new DescriptorToPredicateConverter())
                .outputHandler(new ContentToNullWriter())
                .templateRenderer(mockRenderer)
                .dependencyCatalog(mockDependencyCollector)
                .mustacheDecoder(
                        MustacheDecoder.builder()
                                .converter(new RestTemplateModelToMapConverter()).build())
                .build();
    }

    @Test
    void givenBasicOptions_shouldReturnOK() {
        // Given: a basic project w/o any integrations enabled
        var descriptor = buildSampleDescriptor();

        // expect: code generation is successful
        assertThat(generatorUnderTest.generateCode(descriptor)).isEqualTo(ExitCodes.OK);
    }

    @Test
    void givenPostgresSupport_shouldReturnOK() {
        // Given: a project that wants Postgres integration enabled
        var descriptor = buildSampleDescriptor();
        descriptor.getIntegrations().add(WebMvcIntegration.POSTGRES.name());

        // Expect: code generation is successful
        assertThat(generatorUnderTest.generateCode(descriptor)).isEqualTo(ExitCodes.OK);
    }

    @Test
    void givenPostgresAndTestContainerSupport_shouldReturnOK() {
        // given: a project with Postgres and TestContainer integration enabled
        var descriptor = buildSampleDescriptor();
        descriptor.getIntegrations().add(WebMvcIntegration.POSTGRES.name());
        descriptor.getIntegrations().add(WebMvcIntegration.TESTCONTAINERS.name());

        //  expect: code generation is successful
        assertThat(generatorUnderTest.generateCode(descriptor)).isEqualTo(ExitCodes.OK);
    }

    @Test
    void givenPostgresAndTestContainerAndLiquibaseSupport_shouldReturnOK() {
        // given: a project with Postgres and TestContainer integration enabled
        var descriptor = buildSampleDescriptor();
        descriptor.getIntegrations().add(WebMvcIntegration.POSTGRES.name());
        descriptor.getIntegrations().add(WebMvcIntegration.TESTCONTAINERS.name());
        descriptor.getIntegrations().add(WebMvcIntegration.LIQUIBASE.name());

        //  expect: code generation is successful
        assertThat(generatorUnderTest.generateCode(descriptor)).isEqualTo(ExitCodes.OK);
    }

    @Test
    void givenLiquibaseSupport_shouldReturnOK() {
        // given: a project with Postgres and TestContainer integration enabled
        var descriptor = buildSampleDescriptor();
        descriptor.getIntegrations().add(WebMvcIntegration.LIQUIBASE.name());

        //  expect: code generation is successful
        assertThat(generatorUnderTest.generateCode(descriptor)).isEqualTo(ExitCodes.OK);
    }

    // ------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // ------------------------------------------------------------------------------------

    /**
     * Builds a sample RestProjectDescriptor
     */
    private RestProjectDescriptor buildSampleDescriptor() {
        return RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .build();
    }


    public static class FakeCollector implements Collector {

        /**
         * Collects items, honoring the conditions set with {@code setConditions}
         *
         * @return the items meeting the conditions
         */
        @Override
        public List<CatalogEntry> collect() {
            return buildSampleSet();
        }

        /**
         * Builds a data set of CatalogEntry's
         */
        private static List<CatalogEntry> buildSampleSet() {
            CatalogEntry e1 = buildEntry("Application.ftl","Application.java", null);
            CatalogEntry e2 = buildEntry("Controller.ftl", "Controller.java", null);
            CatalogEntry e3 = buildEntry("PostgresConfig.ftl", "PostgresConfig.java", "postgres");
            CatalogEntry e4 = buildEntry("ErrorHandler.ftl","ErrorHandler.java", null);
            CatalogEntry e5 = buildEntry("TestContainer.ftl","TestContainer.java", "testcontainer");

            return List.of(e1, e2, e3, e4, e5);
        }

        /**
         * Builds a single CatalogEntry
         */
        private static CatalogEntry buildEntry(String source, String destination, String tags) {
            CatalogEntry entry = new CatalogEntry();
            entry.setTemplate(source);
            entry.setDestination(destination);
            entry.setTags(tags);
            entry.setContext(MetaTemplateModel.Key.PROJECT.value());
            return entry;
        }
    }

    /*
     * This is a static method to enable re-use by SpringWebFluxCodeGeneratorTest
     */
    static List<Dependency> buildFakeDependencies() {
        List<Dependency> resultSet = new ArrayList<>();
        // These are completely hypothetical versions for these libraries.
        // The names here should match those found in the dependencies.yml file
        // for the sake of finding name mismatches early. 
        resultSet.add(new Dependency("springBoot", "2.6"));
        resultSet.add(new Dependency("springCloud", "2.5"));
        resultSet.add(new Dependency("springDependencyManagement", "1.2.3"));
        resultSet.add(new Dependency("problemSpringWeb", "2.6.1"));
        resultSet.add(new Dependency("assertJ", "4.0.1"));
        resultSet.add(new Dependency("benManesPlugin", "0.44.1"));
        resultSet.add(new Dependency("junitSystemRules", "2.8"));
        resultSet.add(new Dependency("junit", "5.8.1"));
        resultSet.add(new Dependency("liquibase", "2.7"));
        resultSet.add(new Dependency("lombok", "22.5"));
        resultSet.add(new Dependency("log4j", "2.24.5"));
        resultSet.add(new Dependency("testContainers", "6.5"));
        return resultSet;
    }
}
