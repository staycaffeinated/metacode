/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.trait.ConvertTrait;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;

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
        
        if (descriptor.getIntegrations().contains(SpringIntegrations.POSTGRES.name())) {
            map.put(MetaProperties.ADD_POSTGRESQL, Boolean.TRUE);
        }
        if (descriptor.getIntegrations().contains(SpringIntegrations.TESTCONTAINERS.name())) {
            map.put(MetaProperties.ADD_TESTCONTAINERS, Boolean.TRUE);
        }
        if (descriptor.getIntegrations().contains(SpringIntegrations.LIQUIBASE.name())) {
            map.put(MetaProperties.ADD_LIQUIBASE, Boolean.TRUE);
        }

        return map;
    }
}
