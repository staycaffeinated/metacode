/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.io;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.io.MetaPropertiesHandler;
import mmm.coffee.metacode.common.io.MetaPropertiesReader;
import mmm.coffee.metacode.common.io.MetaPropertiesWriter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToMetaProperties;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.rules.TemporaryFolder;

import static com.google.common.truth.Truth.assertThat;

/**
 * SpringMetaPropertiesHandlerTests
 */
class SpringMetaPropertiesHandlerTests {

    static final String EXPECTED_BASE_PACKAGE = "org.acme.petstore";
    static final String EXPECTED_BASE_PATH = "/petstore";

    MetaPropertiesHandler<RestProjectDescriptor> handlerUnderTest;

    @BeforeEach
    public void setUp() throws Exception {
        // Set up a temporary folder
        TemporaryFolder temporaryFolder = new TemporaryFolder();
        temporaryFolder.create();

        // Conjure a temporary file we'll use
        String scratchPropertyFileName = temporaryFolder.getRoot().getAbsolutePath() + "/" + MetaProperties.DEFAULT_FILENAME;

        // Build a Writer to write to the scratch file
        MetaPropertiesWriter writer = MetaPropertiesWriter.builder()
                .destinationFile(scratchPropertyFileName)
                .configuration(new PropertiesConfiguration()).build();

        // Build a Reader to read from the scratch file
        MetaPropertiesReader reader = MetaPropertiesReader.builder()
                .configurations(new Configurations())
                .propertyFileName(scratchPropertyFileName)
                .build();

        // Build a handler that uses the above Reader and Writer
        handlerUnderTest = SpringMetaPropertiesHandler.builder()
                .converter(new DescriptorToMetaProperties())
                .reader(reader)
                .writer(writer)
                .build();
    }

    /**
     * This test exercises writing some data to a temporary properties file,
     * then reading back that same temporary file, and ensuring the property
     * values that are read back match the property values originally written.
     */
    @Test
    void shouldWorkRoundTrip() {
        RestProjectDescriptor data = RestProjectDescriptor.builder()
                .basePackage(EXPECTED_BASE_PACKAGE)
                .basePath(EXPECTED_BASE_PATH)
                .framework(Framework.SPRING_WEBFLUX).build();

        // Write the properties
        handlerUnderTest.writeMetaProperties(data);
        // Read the properties back
        Configuration config = handlerUnderTest.readMetaProperties();

        // Verify the values read back match the values that were written
        assertThat(config.getString(MetaProperties.BASE_PACKAGE)).isEqualTo(EXPECTED_BASE_PACKAGE);
        assertThat(config.getString(MetaProperties.BASE_PATH)).isEqualTo(EXPECTED_BASE_PATH);
        assertThat(config.getString(MetaProperties.FRAMEWORK)).isEqualTo(Framework.SPRING_WEBFLUX.frameworkName());
    }

    /**
     * This test repeats {@code shouldWorkRoundTrip}, with a change to the
     * {@code Framework} for the same of testing a different {@code Framework} value
     */
    @Test
    void shouldDecodeWebMvcFramework() {
        RestProjectDescriptor data = RestProjectDescriptor.builder()
                .basePackage(EXPECTED_BASE_PACKAGE)
                .basePath(EXPECTED_BASE_PATH)
                .framework(Framework.SPRING_WEBMVC).build();

        // Write the properties
        handlerUnderTest.writeMetaProperties(data);
        // Read the properties back
        Configuration config = handlerUnderTest.readMetaProperties();

        // Verify the values read back match the values that were written
        assertThat(config.getString(MetaProperties.BASE_PACKAGE)).isEqualTo(EXPECTED_BASE_PACKAGE);
        assertThat(config.getString(MetaProperties.BASE_PATH)).isEqualTo(EXPECTED_BASE_PATH);
        assertThat(config.getString(MetaProperties.FRAMEWORK)).isEqualTo(Framework.SPRING_WEBMVC.frameworkName());
    }
}
