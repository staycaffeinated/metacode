/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.converter;

import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.google.common.truth.Truth.assertThat;

/**
 * ResourceKeyConverterTests
 */
class RouteConstantsConverterTests {

    final String resourceName = "Pet";
    final String upperCaseName = resourceName.toUpperCase(Locale.ROOT);


    RouteConstantsConverter converterUnderTest = new RouteConstantsConverter();

    @Test
    void shouldPopulateAllKeys() {
        converterUnderTest.setResourceName(resourceName);
        
        checkValue(converterUnderTest.basePath());
        checkValue(converterUnderTest.create());
        checkValue(converterUnderTest.delete());
        checkValue(converterUnderTest.events());
        checkValue(converterUnderTest.findAll());
        checkValue(converterUnderTest.findOne());
        checkValue(converterUnderTest.idParameter());
        checkValue(converterUnderTest.search());
        checkValue(converterUnderTest.stream());
        checkValue(converterUnderTest.update());
    }

    private void checkValue(String resourceKey) {
        assertThat(resourceKey).isNotEmpty();
        assertThat(resourceKey).contains(upperCaseName);
    }

}
