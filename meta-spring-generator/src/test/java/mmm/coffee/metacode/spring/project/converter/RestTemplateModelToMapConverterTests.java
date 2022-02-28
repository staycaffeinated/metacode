/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.spring.constant.MustacheConstants;
import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointTemplateModelToMapConverter;
import mmm.coffee.metacode.spring.project.context.RestProjectTemplateModel;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * RestTemplateModelToMapConverterTests
 */
class RestTemplateModelToMapConverterTests {

    /*
     * Test values
     */
    private static final String BASE_PATH = "/owner";
    private static final String BASE_PACKAGE = "acme.petstore";
    private static final String BASE_PACKAGE_PATH = "acme/petstore";

    /*
     * The converter instance used for testing
     */
    final RestTemplateModelToMapConverter converterUnderTest = new RestTemplateModelToMapConverter();

    @Test
    void shouldMapAllValues() {
        // given: a well-defined template model
        RestProjectTemplateModel model = RestProjectTemplateModel.builder()
                .basePackage(BASE_PACKAGE)
                .basePackagePath(BASE_PACKAGE_PATH)
                .basePath(BASE_PATH)
                .build();

        // when: the template model is converted to a map
        Map<String,String> map = converterUnderTest.convert(model);

        // expect: the variables that may occur in a Mustache expression have a value defined
        assertThat(map.get(MustacheConstants.BASE_PACKAGE_PATH)).isEqualTo(BASE_PACKAGE);
        assertThat(map.get(MustacheConstants.BASE_PATH)).isEqualTo(BASE_PATH);
        assertThat(map.get(MustacheConstants.BASE_PACKAGE)).isEqualTo(BASE_PACKAGE);
    }

}
