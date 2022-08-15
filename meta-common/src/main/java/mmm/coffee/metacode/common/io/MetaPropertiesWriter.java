/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import lombok.Builder;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;
import org.apache.commons.lang3.ObjectUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

/**
 * MetaPropertiesWriter
 */
@Builder
public class MetaPropertiesWriter {

    private final String destinationFile;
    private final PropertiesConfiguration configuration;

    public void saveProperties(Map<String, Object> properties) {
        // FOR DEBUGGING
        try {
            throw new RuntimeException("In saveProperties");
        }
        catch (RuntimeException e) {
            e.printStackTrace();
        }
        try {
            // Only copy properties needed for endpoint generation.
            // The incoming {@code properties} could contain many values,
            // but only a handful of values need to be persisted
            // to the {@code metacode.properties} file.
            configuration.setHeader("This property file was created by the MetaCode code generator.\nThese values are consumed when creating endpoints");
            configuration.setProperty(MetaProperties.BASE_PATH, properties.get(MetaProperties.BASE_PATH));
            configuration.setProperty(MetaProperties.BASE_PACKAGE, properties.get(MetaProperties.BASE_PACKAGE));
            configuration.setProperty(MetaProperties.FRAMEWORK, properties.get(MetaProperties.FRAMEWORK));

            if (ObjectUtils.isNotEmpty(properties.get(MetaProperties.ADD_LIQUIBASE))) {
                configuration.setProperty(MetaProperties.ADD_LIQUIBASE, "true");
            }
            if (ObjectUtils.isNotEmpty(properties.get(MetaProperties.ADD_POSTGRESQL))) {
                configuration.setProperty(MetaProperties.ADD_POSTGRESQL, "true");
            }
            if (ObjectUtils.isNotEmpty(properties.get(MetaProperties.ADD_TESTCONTAINERS))) {
                configuration.setProperty(MetaProperties.ADD_TESTCONTAINERS, "true");
            }

            writeConfiguration(configuration);
        } catch (IOException | ConfigurationException e) {
            throw new RuntimeApplicationError(e.getMessage(), e);
        }
    }

    /**
     * Write the the configuration to the mojo.properties file
     */
    private void writeConfiguration(PropertiesConfiguration configuration) throws IOException, ConfigurationException {
        var file = new File(destinationFile);
        try (var fw = new FileWriter(file)) {
            configuration.write(fw);
            fw.flush();
        }
    }
}
