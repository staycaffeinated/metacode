/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.io;

import mmm.coffee.metacode.annotations.jacoco.Generated;
import org.apache.commons.configuration2.Configuration;

/**
 * MetaPropertiesHandler
 */
@Generated // exclude interfaces from code coverage reporting
public interface MetaPropertiesHandler<T> {
    /**
     * Writes metacode.properties file, using {@code data} as the source of property values
     * @param data provides the property values of the keys written to the metacode.properties file
     */
    default void writeMetaProperties(T data) {}

    /**
     * Returns a Configuration object containing the content of the metacode.properties file
     * @return a Configuration containing the metacode.properties key/value pairs
     */
    Configuration readMetaProperties();
}