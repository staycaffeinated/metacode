/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.io;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.io.MetaProperties;
import mmm.coffee.metacode.common.io.MetaPropertiesReader;
import org.apache.commons.configuration2.Configuration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.mockito.Mockito;

import static com.google.common.truth.Truth.assertThat;
import static org.mockito.Mockito.when;

/**
 * SpringEndpointMetaPropertiesHandlerTest
 */
class SpringEndpointMetaPropertiesHandlerTest {

    public static final String BASE_PKG = "org.acme.petstore";
    public static final String BASE_PATH = "/petstore";
    public static final String FRAMEWORK = Framework.SPRING_WEBMVC.frameworkName();


    /*
     * The handler under test
     */
    SpringEndpointMetaPropertiesHandler handlerUnderTest;

    @BeforeEach
    public void setUp() {
        handlerUnderTest = setUpMetaPropertiesHandler();
    }

    @ParameterizedTest
    @CsvSource( value = {
            MetaProperties.BASE_PACKAGE + "," + BASE_PKG,
            MetaProperties.BASE_PATH + "," + BASE_PATH
    })
    void shouldReadValues(String propName, String expectedValue) {
        assertThat(handlerUnderTest.readMetaProperties().getString(propName)).isEqualTo(expectedValue);
    }

    @Test
    void shouldReadFramework() {
        assertThat(handlerUnderTest.readMetaProperties().getString(MetaProperties.FRAMEWORK)).isEqualTo(FRAMEWORK);
    }
    
    // --------------------------------------------------------------------------------------------------------
    //
    // Helper methods
    //
    // --------------------------------------------------------------------------------------------------------

    private SpringEndpointMetaPropertiesHandler setUpMetaPropertiesHandler() {
        Configuration mockConfiguration = Mockito.mock(Configuration.class);
        when(mockConfiguration.getString(MetaProperties.FRAMEWORK)).thenReturn(Framework.SPRING_WEBMVC.frameworkName());
        when(mockConfiguration.getString(MetaProperties.BASE_PACKAGE)).thenReturn("org.acme.petstore");
        when(mockConfiguration.getString(MetaProperties.BASE_PATH)).thenReturn("/petstore");

        MetaPropertiesReader mockReader = Mockito.mock(MetaPropertiesReader.class);
        when(mockReader.read()).thenReturn(mockConfiguration);

        return SpringEndpointMetaPropertiesHandler.builder().reader(mockReader).build();
    }
}
