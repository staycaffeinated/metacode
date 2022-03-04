/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import lombok.Builder;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.apache.commons.configuration2.PropertiesConfiguration;
import org.apache.commons.configuration2.ex.ConfigurationException;

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
        try {
            // Only copy properties needed for endpoint generation.
            // The incoming {@code properties} could contain many values,
            // but only a handful of values need to be persisted
            // to the {@code metacode.properties} file.
            configuration.setHeader("This property file was created by the MetaCode code generator.\nThese values are consumed when creating endpoints");
            configuration.setProperty(MetaProperties.BASE_PATH, properties.get(MetaProperties.BASE_PATH));
            configuration.setProperty(MetaProperties.BASE_PACKAGE, properties.get(MetaProperties.BASE_PACKAGE));
            configuration.setProperty(MetaProperties.FRAMEWORK, properties.get(MetaProperties.FRAMEWORK));

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
