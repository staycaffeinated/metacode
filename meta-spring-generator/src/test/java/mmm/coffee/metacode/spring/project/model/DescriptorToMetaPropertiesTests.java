/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.model;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.spring.project.converter.DescriptorToMetaProperties;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * DescriptorToMetaPropertiesTests
 */
class DescriptorToMetaPropertiesTests {
    static final String BASE_PACKAGE = "org.acme.petstore";
    static final String BASE_PATH = "/petstore";

    final DescriptorToMetaProperties converterUnderTest = new DescriptorToMetaProperties();

    @Test
    void shouldIndicateWebMvcFramework() {
        RestProjectDescriptor descriptor = RestProjectDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(Framework.SPRING_WEBMVC).build();

        Map<String, Object> map = converterUnderTest.convert(descriptor);

        assertThat(map.get(MetaProperties.BASE_PACKAGE)).isEqualTo(BASE_PACKAGE);
        assertThat(map.get(MetaProperties.BASE_PATH)).isEqualTo(BASE_PATH);
        assertThat(map.get(MetaProperties.FRAMEWORK)).isEqualTo(Framework.SPRING_WEBMVC.frameworkName());
    }

    @Test
    void shouldIndicateWebFluxFramework() {
        RestProjectDescriptor descriptor = RestProjectDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(Framework.SPRING_WEBFLUX).build();

        Map<String, Object> map = converterUnderTest.convert(descriptor);

        assertThat(map.get(MetaProperties.BASE_PACKAGE)).isEqualTo(BASE_PACKAGE);
        assertThat(map.get(MetaProperties.BASE_PATH)).isEqualTo(BASE_PATH);
        assertThat(map.get(MetaProperties.FRAMEWORK)).isEqualTo(Framework.SPRING_WEBFLUX.frameworkName());
    }

}
