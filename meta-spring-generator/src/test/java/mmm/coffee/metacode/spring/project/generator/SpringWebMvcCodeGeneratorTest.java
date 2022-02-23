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
package mmm.coffee.metacode.spring.project.generator;

import freemarker.template.Configuration;
import freemarker.template.TemplateModel;
import lombok.NonNull;
import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.writer.ContentToNullWriter;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import mmm.coffee.metacode.spring.project.converter.DescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToRestProjectTemplateModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

/**
 * Unit test
 */
class SpringWebMvcCodeGeneratorTest {

    private static final String APP_NAME = "petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String BASE_PATH = "/petstore";

    SpringWebMvcCodeGenerator generatorUnderTest;

    Collector fakeCollector;

    @Mock
    TemplateResolver mockRenderer;
    
    @BeforeEach
    public void setUp() {
        fakeCollector = new FakeCollector();

        mockRenderer = Mockito.mock(TemplateResolver.class);
        when(mockRenderer.resolve(any(), any())).thenReturn("");

        generatorUnderTest = SpringWebMvcCodeGenerator.builder()
                .collector(fakeCollector)
                .descriptor2templateModel(new DescriptorToRestProjectTemplateModelConverter())
                .descriptor2Predicate(new DescriptorToPredicateConverter())
                .outputHandler(new ContentToNullWriter())
                .templateRenderer(mockRenderer)
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
            CatalogEntry e1 = buildEntry("Application.ftl","Application.java", null, "project");
            CatalogEntry e2 = buildEntry("Controller.ftl", "Controller.java", null, "project");
            CatalogEntry e3 = buildEntry("PostgresConfig.ftl", "PostgresConfig.java", "postgres", "project");
            CatalogEntry e4 = buildEntry("ErrorHandler.ftl","ErrorHandler.java", null, "project");
            CatalogEntry e5 = buildEntry("TestContainer.ftl","TestContainer.java", "testcontainer", "project");

            return List.of(e1, e2, e3, e4, e5);
        }

        /**
         * Builds a single CatalogEntry
         */
        private static CatalogEntry buildEntry(String source, String destination, String tags, String context) {
            CatalogEntry entry = new CatalogEntry();
            entry.setTemplate(source);
            entry.setDestination(destination);
            entry.setTags(tags);
            entry.setContext(context);
            return entry;
        }
    }

//    public static class FakeResolver extends TemplateResolver {
//
//        public FakeResolver(@NonNull Configuration configuration) {
//            super(Mockito.mock(Configuration.class));
//        }
//        @Override
//        public String resolve(String templateResourcePath, TemplateModel templateModel) {
//            return "";
//        }
//    }
}
