/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.trait.ConvertTrait;

import java.util.HashMap;
import java.util.Map;

/**
 * DescriptorToMetaProperties
 */
public class DescriptorToMetaProperties implements ConvertTrait<RestProjectDescriptor, Map<String,Object>> {

    public Map<String,Object> convert(RestProjectDescriptor descriptor) {
        Map<String,Object> map = new HashMap<>();
        map.put(MetaProperties.BASE_PACKAGE, descriptor.getBasePackage());
        map.put(MetaProperties.BASE_PATH, descriptor.getBasePath());
        map.put(MetaProperties.FRAMEWORK, descriptor.getFramework());
        return map;
    }
}
