/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

/**
 * MetaPropertiesWriterTests
 */
class MetaPropertiesWriterTests {

    MetaPropertiesWriter writerUnderTest;

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

    public static class TestValues {
        public static final String BASE_PACKAGE = "org.acme.petstore";
        public static final String BASE_PATH = "/petstore";
        public static final String FRAMEWORK = Framework.SPRING_WEBFLUX.frameworkName();
    }
}
