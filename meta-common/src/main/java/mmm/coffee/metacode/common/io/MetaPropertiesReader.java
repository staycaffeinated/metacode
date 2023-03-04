/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import org.apache.commons.configuration2.Configuration;
import org.apache.commons.configuration2.builder.fluent.Configurations;
import org.apache.commons.configuration2.ex.ConfigurationException;

import java.io.File;

/**
 * MetaPropertiesReader reads the {@code metacode.properties} file
 * and returns a {@code Configuration} instance.
 */
@Builder
@Slf4j
public class MetaPropertiesReader {
    /*
     * the file to which the properties are written
     */
    private final String propertyFileName;

    /*
     * Handles the work of writing the property values
     */
    private final Configurations configurations;

    
    /**
     * Returns the content of the {@code metacode.properties} file as a {@code Configuration}
     * instance. Each invocation of {@code read} causes the file to be read.
     * @return the content o
     */
    public Configuration read() {
        log.debug("[read]", new Exception("Who called me?"));
        try {
            return configurations.properties(new File(propertyFileName));
        }
        catch (ConfigurationException ex) {
            throw new RuntimeApplicationError(ex.getMessage(), ex);
        }
    }
}
