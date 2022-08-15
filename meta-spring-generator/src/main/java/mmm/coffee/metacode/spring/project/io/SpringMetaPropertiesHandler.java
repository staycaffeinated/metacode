/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.io;

import lombok.Builder;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.io.MetaPropertiesHandler;
import mmm.coffee.metacode.common.io.MetaPropertiesReader;
import mmm.coffee.metacode.common.io.MetaPropertiesWriter;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import org.apache.commons.configuration2.Configuration;

import java.util.Map;

/**
 * SpringMetaPropertiesHandler
 */
@Builder
public class SpringMetaPropertiesHandler implements MetaPropertiesHandler<RestProjectDescriptor> {

    private MetaPropertiesWriter writer;
    private MetaPropertiesReader reader;
    private ConvertTrait<RestProjectDescriptor,Map<String,Object>> converter;

    /**
     * Writes metacode.properties file, using {@code data} as the source of property values
     *
     * @param data provides the property values of the keys written to the metacode.properties file
     */
    @Override
    public void writeMetaProperties(RestProjectDescriptor data) { writer.saveProperties(converter.convert(data)); }


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
