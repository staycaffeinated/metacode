/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.spring.converter.NameConverter;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * RestEndpointDescriptorToTemplateModelConverterTests
 */
class RestEndpointDescriptorToTemplateModelConverterTests {

    private static final String BASE_PATH = "/petstore";
    private static final String BASE_PACKAGE = "org.acme.petstore";
    private static final String WEBFLUX = Framework.SPRING_WEBFLUX.frameworkName();
    private static final String RESOURCE = "Pet";
    private static final String ROUTE = "/pet";

    final RestEndpointDescriptorToTemplateModelConverter converterUnderTest = new RestEndpointDescriptorToTemplateModelConverter(new NameConverter());

    @Test
    void shouldDefineAllTemplateValues() {
        var spec = RestEndpointDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(WEBFLUX)
                .resource(RESOURCE)
                .route(ROUTE)
                .build();

        var model = converterUnderTest.convert(spec);

        // Expect: all values that can come up w/in a template have some value defined
        // The following values are known to be straight copies of the original values
        assertThat(model.getBasePackage()).isEqualTo(BASE_PACKAGE);
        assertThat(model.getResource()).isEqualTo(RESOURCE);
        assertThat(model.getRoute()).isEqualTo(ROUTE);

        // The NameConverter determines _how_ names are converted, and tests of
        // the NameConverter verify the results of the various conversions.
        // Rather than repeat those tests, we've elected here to only check for
        // non-empty values.
        assertThat(model.getBasePackagePath()).isNotEmpty();
        assertThat(model.getEjbName()).isNotEmpty();
        assertThat(model.getEntityName()).isNotEmpty();
        assertThat(model.getEntityVarName()).isNotEmpty();
        assertThat(model.getLowerCaseEntityName()).isNotEmpty();
        assertThat(model.getPojoName()).isNotEmpty();
        assertThat(model.getTableName()).isNotEmpty();
        assertThat(model.getTopLevelVariable()).isEqualTo(MetaTemplateModel.Key.ENDPOINT.value());
    }

}
