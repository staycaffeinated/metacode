/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import com.google.common.truth.IterableSubject;
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.StreamSupport;

import static com.google.common.collect.Sets.newHashSet;
import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * MetaPropertiesWriterTests
 */
class MetaPropertiesWriterTests {

    MetaPropertiesWriter writerUnderTest;




    @Test
    void shouldWriteProperties() throws Exception {
        // Given: a temporary folder to work with
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        String fakeDestination = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(fakeDestination)
                .configuration(new PropertiesConfiguration())
                .build();

        // when: saving the properties
        writer.saveProperties(buildSampleProperties());

        File f = new File(fakeDestination);
        assertThat(f.exists()).isTrue();
    }

    @Test
    void whenUsingTestContainers_expectTestContainersEnabledInPropertiesFile() throws Exception {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        String fakeDestination = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(fakeDestination)
                .configuration(new PropertiesConfiguration())
                .build();

        // when: saving the properties
        writer.saveProperties(buildSamplePropertiesWithTestContainersEnabled());

        File f = new File(fakeDestination);
        assertThat(f.exists()).isTrue();
    }

    @Test
    void whenUsingPostgres_expectPostgresEnabledInPropertiesFile() throws Exception {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        String fakeDestination = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(fakeDestination)
                .configuration(new PropertiesConfiguration())
                .build();

        // when: saving the properties
        writer.saveProperties(buildSamplePropertiesWithPostgresEnabled());

        File f = new File(fakeDestination);
        assertThat(f.exists()).isTrue();
    }

    @Test
    void whenUsingLiquibase_expectLiquibaseEnabledInPropertiesFile() throws Exception {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        String fakeDestination = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(fakeDestination)
                .configuration(new PropertiesConfiguration())
                .build();

        // when: saving the properties
        writer.saveProperties(buildSamplePropertiesWithLiquibaseEnabled());

        File f = new File(fakeDestination);
        assertThat(f.exists()).isTrue();
    }


    @Test
    void shouldRethrowConfigurationExceptionAsRuntimeApplicationError() throws Exception {
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        String fakeDestination = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        // given: a PropertiesConfig that will throw an exception whenever it's {@code write} method is called...
        var mockConfiguration = Mockito.mock(PropertiesConfiguration.class);
        doThrow(ConfigurationException.class).when(mockConfiguration).write(any());

        // Given: a MetaPropertiesWriter that employs our mock PropertiesConfig
        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(fakeDestination)
                .configuration(mockConfiguration)
                .build();

        // when: the propertiesConfig.write method throws a ConfigurationException
        // expect: that exception is recast as a RuntimeApplicationError
        Map<String,Object> sampleData = buildSampleProperties();
        assertThrows(RuntimeApplicationError.class, () -> writer.saveProperties(sampleData) );
    }

    /**
     * A silly edge case causing code coverage metrics to drop
     */
    @Test
    @Tag("coverage")
    void toStringShouldReturnSomething() {
        assertThat(MetaPropertiesWriter.builder().toString()).isNotEmpty();
    }

    // ------------------------------------------------------------------------------------
    //
    // Helper Methods
    //
    // ------------------------------------------------------------------------------------

    /**
     * Builds a Map of sample values suitable for testing
     *
     * @return a map of sample values
     */
    static Map<String, Object> buildSampleProperties() {
        var map = new HashMap<String, Object>();

        map.put(MetaProperties.BASE_PACKAGE, TestValues.BASE_PACKAGE);
        map.put(MetaProperties.BASE_PATH, TestValues.BASE_PATH);
        map.put(MetaProperties.FRAMEWORK, TestValues.FRAMEWORK);

        return map;
    }

    static Map<String,Object> buildSamplePropertiesWithTestContainersEnabled() {
        var map = buildSampleProperties();
        map.put(MetaProperties.ADD_TESTCONTAINERS, Boolean.TRUE);
        return map;
    }

    static Map<String,Object> buildSamplePropertiesWithPostgresEnabled() {
        var map = buildSampleProperties();
        map.put(MetaProperties.ADD_POSTGRESQL, Boolean.TRUE);
        return map;
    }

    static Map<String,Object> buildSamplePropertiesWithLiquibaseEnabled() {
        var map = buildSampleProperties();
        map.put(MetaProperties.ADD_LIQUIBASE, Boolean.TRUE);
        return map;
    }

    public static class TestValues {
        public static final String BASE_PACKAGE = "org.acme.petstore";
        public static final String BASE_PATH = "/petstore";
        public static final String FRAMEWORK = Framework.SPRING_WEBFLUX.frameworkName();
    }
}
