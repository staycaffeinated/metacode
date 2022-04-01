/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import mmm.coffee.metacode.spring.constant.MustacheConstants;
import mmm.coffee.metacode.spring.endpoint.model.RestEndpointTemplateModel;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * RestEndpointTemplateModelToMapConverterTests
 */
class RestEndpointTemplateModelToMapConverterTests {

    private static final String BASE_PATH = "/owner";
    private static final String BASE_PACKAGE = "acme.petstore";
    private static final String BASE_PACKAGE_PATH = "acme/petstore";
    private static final String ENTITY_NAME = "Pet";
    private static final String ENTITY_VAR_NAME = "pet";
    private static final String LOWER_CASE_ENTITY_NAME = "pet";

    final RestEndpointTemplateModelToMapConverter converterUnderTest =  new RestEndpointTemplateModelToMapConverter();

    @Test
    void shouldMapAllValues() {
        // given: some well-defined endpoint template model
        RestEndpointTemplateModel model = RestEndpointTemplateModel.builder()
                .basePackage(BASE_PACKAGE)
                .basePackagePath(BASE_PACKAGE_PATH)
                .basePath(BASE_PATH)
                .entityName(ENTITY_NAME)
                .entityVarName(ENTITY_VAR_NAME)
                .lowerCaseEntityName(LOWER_CASE_ENTITY_NAME)
                .build();

        // when: the endpoint template model is converted to a map
        Map<String,String> mapping = converterUnderTest.convert(model);

        // expect: all the variables that may come up in a Mustache expression have a value
        assertThat(mapping.get(MustacheConstants.BASE_PATH)).isEqualTo(BASE_PATH);
        assertThat(mapping.get(MustacheConstants.BASE_PACKAGE)).isEqualTo(BASE_PACKAGE);
        assertThat(mapping.get(MustacheConstants.BASE_PACKAGE_PATH)).isEqualTo(BASE_PACKAGE_PATH);
        assertThat(mapping.get(MustacheConstants.ENTITY_NAME)).isEqualTo(ENTITY_NAME);
        assertThat(mapping.get(MustacheConstants.ENTITY_NAME_LOWERCASE)).isEqualTo(LOWER_CASE_ENTITY_NAME);
        assertThat(mapping.get(MustacheConstants.ENTITY_VAR_NAME)).isEqualTo(ENTITY_VAR_NAME);
    }
}
