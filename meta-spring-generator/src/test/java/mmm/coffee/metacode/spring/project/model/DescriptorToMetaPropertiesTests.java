/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.model;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import mmm.coffee.metacode.spring.project.converter.DescriptorToMetaProperties;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * DescriptorToMetaPropertiesTests
 */
class DescriptorToMetaPropertiesTests {
    static final String BASE_PACKAGE = "org.acme.petstore";
    static final String BASE_PATH = "/petstore";

    final DescriptorToMetaProperties converterUnderTest = new DescriptorToMetaProperties();

    @Test
    void shouldIndicateWebMvcFramework() {
        RestProjectDescriptor descriptor = RestProjectDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(Framework.SPRING_WEBMVC).build();

        Map<String, Object> map = converterUnderTest.convert(descriptor);

        assertThat(map.get(MetaProperties.BASE_PACKAGE)).isEqualTo(BASE_PACKAGE);
        assertThat(map.get(MetaProperties.BASE_PATH)).isEqualTo(BASE_PATH);
        assertThat(map.get(MetaProperties.FRAMEWORK)).isEqualTo(Framework.SPRING_WEBMVC.frameworkName());
    }

    @Test
    void shouldIndicateWebFluxFramework() {
        RestProjectDescriptor descriptor = RestProjectDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(Framework.SPRING_WEBFLUX).build();

        Map<String, Object> map = converterUnderTest.convert(descriptor);

        assertThat(map.get(MetaProperties.BASE_PACKAGE)).isEqualTo(BASE_PACKAGE);
        assertThat(map.get(MetaProperties.BASE_PATH)).isEqualTo(BASE_PATH);
        assertThat(map.get(MetaProperties.FRAMEWORK)).isEqualTo(Framework.SPRING_WEBFLUX.frameworkName());
    }

    @Test
    void whenIntegrationWithPostgres_expectPostgresPropertyIsSet() {
        // Given: a RestProject with Postgres integration added
        RestProjectDescriptor descriptor = newWebMvcProject(SpringIntegrations.POSTGRES);

        // When: the RestProjectDescriptor is converted to a Map
        Map<String, Object> map = converterUnderTest.convert(descriptor);

        // Expect: the Map contains an property for Postgres that's set to 'true'
        assertThat(map.get(MetaProperties.ADD_POSTGRESQL)).isEqualTo(Boolean.TRUE);
    }

    @Test
    void whenIntegrationWithLiquibase_expectLiquibasePropertyIsSet() {
        // Given: a RestProject with Liquibase integration added
        RestProjectDescriptor descriptor = newWebMvcProject(SpringIntegrations.LIQUIBASE);

        // When: the RestProjectDescriptor is converted to a Map
        Map<String, Object> map = converterUnderTest.convert(descriptor);

        // Expect: the Map contains an property for Liquibase that's set to 'true'
        assertThat(map.get(MetaProperties.ADD_LIQUIBASE)).isEqualTo(Boolean.TRUE);
    }

    @Test
    void whenIntegrationWithTestContainers_expectTestContainersPropertyIsSet() {
        // Given: a RestProject with TestContainers integration added
        RestProjectDescriptor descriptor = newWebMvcProject(SpringIntegrations.TESTCONTAINERS);

        // When: the RestProjectDescriptor is converted to a Map
        Map<String, Object> map = converterUnderTest.convert(descriptor);

        // Expect: the Map contains an property for TestContainers that's set to 'true'
        assertThat(map.get(MetaProperties.ADD_TESTCONTAINERS)).isEqualTo(Boolean.TRUE);
    }

    @Test
    void whenIntegrationWithMultipleTools_expectPropertyIsSetForEachTool() {
        // Given: a RestProject with TestContainers integration added
        RestProjectDescriptor descriptor = newWebMvcProject(SpringIntegrations.TESTCONTAINERS, SpringIntegrations.POSTGRES, SpringIntegrations.LIQUIBASE);

        // When: the RestProjectDescriptor is converted to a Map
        Map<String, Object> map = converterUnderTest.convert(descriptor);

        // Expect: the Map contains an property for each tool that's set to 'true'
        assertThat(map.get(MetaProperties.ADD_TESTCONTAINERS)).isEqualTo(Boolean.TRUE);
        assertThat(map.get(MetaProperties.ADD_POSTGRESQL)).isEqualTo(Boolean.TRUE);
        assertThat(map.get(MetaProperties.ADD_LIQUIBASE)).isEqualTo(Boolean.TRUE);
    }
    
    // ---------------------------------------------------------------------------------------------------------
    // Helper methods
    // ---------------------------------------------------------------------------------------------------------

    private RestProjectDescriptor newWebMvcProject(SpringIntegrations ...integrations) {
        RestProjectDescriptor descriptor = RestProjectDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(Framework.SPRING_WEBMVC).build();

        for (SpringIntegrations springIntegration : integrations) {
            descriptor.getIntegrations().add(springIntegration.name());
        }
        return descriptor;
    }

}
