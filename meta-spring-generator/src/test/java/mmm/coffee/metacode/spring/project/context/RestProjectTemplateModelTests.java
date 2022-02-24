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
package mmm.coffee.metacode.spring.project.context;

import mmm.coffee.metacode.common.dependency.Dependency;
import mmm.coffee.metacode.common.dependency.DependencyCatalog;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * Unit tests
 */
class RestProjectTemplateModelTests {

    RestProjectTemplateModel modelUnderTest;
    DependencyCatalog mockDependencyCatalog;

    // Completely hypothetical versions of libraries
    private static final String ASSERTJ_VERSION = "1.2.3";
    private static final String SPRINGBOOT_VERSION = "2.6.3";
    private static final String SPRING_CLOUD_VERSION = "2.5.5";
    private static final String SPRING_DM_VERSION = "1.0.1";
    private static final String PROBLEM_VERSION = "2.6";
    private static final String BEN_MANES_VERSION = "1.4.2";
    private static final String JUNIT_RULES_VERSION = "1.0.1b";
    private static final String JUNIT_VERSION = "5.8.1";
    private static final String LIQUIBASE_VERSION = "4.5.6";
    private static final String LOMBOK_VERSION = "3.4.5";
    private static final String LOG4J_VERSION = "2.22.4";
    private static final String TESTCONTAINER_VERSION = "3.33.4";

    @BeforeEach
    public void setUp() {
        modelUnderTest = RestProjectTemplateModel.builder().build();

        // Mock the DependencyCatalog to return sample data
        mockDependencyCatalog = Mockito.mock(DependencyCatalog.class);
        List<Dependency> fakeDependencies = buildFakeDependencies();
        when(mockDependencyCatalog.collect()).thenReturn(fakeDependencies);
    }

    @ParameterizedTest
    @CsvSource( value = {
            "springBoot,setSpringBootVersion",
            "springCloud,setSpringCloudVersion",
            "springDependencyManagement,setSpringDependencyManagementVersion",
            "problemSpringWeb,setProblemSpringWebVersion",
            "assertJ,setAssertJVersion",
            "benManesPlugin,setBenManesPluginVersion",
            "junitSystemRules,setJunitSystemRulesVersion",
            "junit,setJunitVersion",
            "liquibase,setLiquibaseVersion",
            "lombok,setLombokVersion",
            "log4J,setLog4JVersion",
            "testContainers,setTestContainersVersion"
    })
    void shouldConjureSetterName(String field, String expectedMethod) {
        assertThat(modelUnderTest.conjureSetterMethod(field)).isEqualTo(expectedMethod);
    }

    /**
     * The various '**Version' fields are set using reflection.
     * This test confirms the reflection-based setter methods work
     * by verifying the equivalent getter methods return the expected value.
     *
     * The setField method is only package-visible, and only to
     * enable testing it. 
     */
    @Test
    void shouldSetField() {
        final String expectedVersion = "1.2.3";

        modelUnderTest.setField("springBoot", expectedVersion);
        assertThat(modelUnderTest.getSpringBootVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("springCloud", expectedVersion);
        assertThat(modelUnderTest.getSpringCloudVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("springDependencyManagement", expectedVersion);
        assertThat(modelUnderTest.getSpringCloudVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("problemSpringWeb", expectedVersion);
        assertThat(modelUnderTest.getProblemSpringWebVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("assertJ", expectedVersion);
        assertThat(modelUnderTest.getAssertJVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("benManesPlugin", expectedVersion);
        assertThat(modelUnderTest.getBenManesPluginVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("junitSystemRules", expectedVersion);
        assertThat(modelUnderTest.getJunitSystemRulesVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("junit", expectedVersion);
        assertThat(modelUnderTest.getJunitVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("liquibase", expectedVersion);
        assertThat(modelUnderTest.getLiquibaseVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("lombok", expectedVersion);
        assertThat(modelUnderTest.getLombokVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("log4J", expectedVersion);
        assertThat(modelUnderTest.getLombokVersion()).isEqualTo(expectedVersion);

        modelUnderTest.setField("testContainers", expectedVersion);
        assertThat(modelUnderTest.getTestContainersVersion()).isEqualTo(expectedVersion);
    }

    /**
     * The dependencyCatalog.entries() method returns a long list of Dependency entries.
     * Each of those entries causes a field w/in the RestProjectTemplateModel to get updated.
     */
    @Test
    void shouldApplyDependencyData() {
        // when: our sample dependencies are applied to the TemplateModel
        modelUnderTest.apply(mockDependencyCatalog);

        // expect: a 1:1 map of each Dependency to a setter method,
        // with the getter method returning the expected value
        assertThat(modelUnderTest.getAssertJVersion()).isEqualTo(ASSERTJ_VERSION);
        assertThat(modelUnderTest.getSpringBootVersion()).isEqualTo(SPRINGBOOT_VERSION);
        assertThat(modelUnderTest.getSpringCloudVersion()).isEqualTo(SPRING_CLOUD_VERSION);
        assertThat(modelUnderTest.getSpringDependencyManagementVersion()).isEqualTo(SPRING_DM_VERSION);
        assertThat(modelUnderTest.getProblemSpringWebVersion()).isEqualTo(PROBLEM_VERSION);
        assertThat(modelUnderTest.getBenManesPluginVersion()).isEqualTo(BEN_MANES_VERSION);
        assertThat(modelUnderTest.getJunitSystemRulesVersion()).isEqualTo(JUNIT_RULES_VERSION);
        assertThat(modelUnderTest.getJunitVersion()).isEqualTo(JUNIT_VERSION);
        assertThat(modelUnderTest.getLiquibaseVersion()).isEqualTo(LIQUIBASE_VERSION);
        assertThat(modelUnderTest.getLombokVersion()).isEqualTo(LOMBOK_VERSION);
        assertThat(modelUnderTest.getLog4JVersion()).isEqualTo(LOG4J_VERSION);
        assertThat(modelUnderTest.getTestContainersVersion()).isEqualTo(TESTCONTAINER_VERSION);
    }

    // -------------------------------------------------------------------------------------------
    // Helper Methods
    // -------------------------------------------------------------------------------------------

    List<Dependency> buildFakeDependencies() {
        List<Dependency> resultSet = new ArrayList<>();
        resultSet.add(new Dependency("springBoot", SPRINGBOOT_VERSION));
        resultSet.add(new Dependency("springCloud", SPRING_CLOUD_VERSION));
        resultSet.add(new Dependency("springDependencyManagement", SPRING_DM_VERSION));
        resultSet.add(new Dependency("problemSpringWeb", PROBLEM_VERSION));
        resultSet.add(new Dependency("assertJ", ASSERTJ_VERSION));
        resultSet.add(new Dependency("benManesPlugin", BEN_MANES_VERSION));
        resultSet.add(new Dependency("junitSystemRules", JUNIT_RULES_VERSION));
        resultSet.add(new Dependency("junit", JUNIT_VERSION));
        resultSet.add(new Dependency("liquibase", LIQUIBASE_VERSION));
        resultSet.add(new Dependency("lombok", LOMBOK_VERSION));
        resultSet.add(new Dependency("log4J", LOG4J_VERSION));
        resultSet.add(new Dependency("testContainers", TESTCONTAINER_VERSION));
        return resultSet;
    }
}
