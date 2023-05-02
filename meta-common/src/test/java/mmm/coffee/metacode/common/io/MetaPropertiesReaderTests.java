/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;


import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;
import org.mockito.Mockito;

import java.io.File;
import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;

/**
 * MetaPropertiesReaderTests. These tests depend on the MetaPropertiesWriter,
 * since these tests need a file to read to verify the read logic is working. 
 */
class MetaPropertiesReaderTests {

    public String propertyFile;

    private static final String EXPECTED_PATH = MetaPropertiesWriterTests.TestValues.BASE_PATH;
    private static final String EXPECTED_PACKAGE = MetaPropertiesWriterTests.TestValues.BASE_PACKAGE;
    private static final String EXPECTED_FRAMEWORK = MetaPropertiesWriterTests.TestValues.FRAMEWORK;
    private static final String EXPECTED_SCHEMA = MetaPropertiesWriterTests.TestValues.SCHEMA;

    @BeforeEach
    public void createTemporaryMetaPropertiesFile() throws IOException {

        // Given: a temporary folder to work with
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // and given: a hypothetical filename
        propertyFile = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(propertyFile)
                        .configuration(new PropertiesConfiguration()).build();

        // Rather than duplicate code, we'll borrow the WriterTest::buildSampleProperties method
        // to conjure sample data. Take a different direction as needed.
        writer.saveProperties(MetaPropertiesWriterTests.buildSampleProperties());
    }

    @Test
    void shouldRethrowAnyExceptionAsRuntimeApplicationError() throws Exception {
        var mockConfigurations = Mockito.mock(Configurations.class);
        doThrow(ConfigurationException.class).when(mockConfigurations).properties(any(File.class));

        // Given: a Reader that has a "faulty" Configurations that always throws exceptions...
        MetaPropertiesReader reader = MetaPropertiesReader.builder()
                        .propertyFileName(propertyFile).configurations(mockConfigurations).build();

        // Expect: when any exception is thrown within the read() method, that exception is rethrown
        // as a RuntimeApplicationError
        assertThrows(RuntimeApplicationError.class, reader::read);
    }


    /**
     * Verify the basics of adding a property and reading the property back work
     */
    @Test
    void shouldLoadSampleData() throws Exception {
        // when: reading the properties file
        Configuration configuration = MetaPropertiesReader.builder()
                .propertyFileName(propertyFile).configurations(new Configurations()).build().read();

        // expect: the known properties have their expected values
        assertThat(configuration).isNotNull();
        assertThat(configuration.getString(MetaProperties.BASE_PATH)).isEqualTo(EXPECTED_PATH);
        assertThat(configuration.getString(MetaProperties.BASE_PACKAGE)).isEqualTo(EXPECTED_PACKAGE);
        assertThat(configuration.getString(MetaProperties.FRAMEWORK)).isEqualTo(EXPECTED_FRAMEWORK);
        assertThat(configuration.getString(MetaProperties.SCHEMA)).isEqualTo(EXPECTED_SCHEMA);
    }

    @Test
    void shouldSupportAddingProperties() throws Exception {
        var configuration = MetaPropertiesReader.builder()
                .propertyFileName(propertyFile).configurations(new Configurations()).build().read();
        configuration.addProperty("metacode.basePath", "/dashboard");
        assertThat(configuration.getProperty("metacode.basePath")).isEqualTo("/dashboard");
    }

    /*
     * This test only helps with code coverage; it gets us from 77% to 100% for the MetaPropertiesReader class
     */
    @Test
    @Tag("coverage")
    void toStringShouldReturnNonEmptyValue() {
        assertThat(MetaPropertiesReader.builder().toString()).isNotEmpty();
    }
}
