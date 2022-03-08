/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.io;

import lombok.AllArgsConstructor;
import lombok.Builder;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.io.MetaPropertiesHandler;
import mmm.coffee.metacode.common.io.MetaPropertiesReader;
import org.apache.commons.configuration2.Configuration;

/**
 * SpringEndpointMetaPropertiesHandler.
 *
 * To generate the various classes for endpoints, we need a way to read metacode.properties,
 * but we never need to write/update metacode.properties. Since writing isn't a use case we
 * need to support, this class not implement the {@code writeMetaProperties} (the default
 * behavior defined in {@code MetaPropertiesHandler} is to do nothing, which suits our needs).
 */
@Builder
@AllArgsConstructor
public class SpringEndpointMetaPropertiesHandler implements MetaPropertiesHandler<RestEndpointDescriptor> {
    private MetaPropertiesReader reader;

    /**
     * Returns a Configuration object containing the content of the metacode.properties file
     *
     * @return a Configuration containing the metacode.properties key/value pairs
     */
    @Override
    public Configuration readMetaProperties() {
        return reader.read();
    }
}
