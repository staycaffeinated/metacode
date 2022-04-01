/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import mmm.coffee.metacode.spring.converter.NameConverter;
import mmm.coffee.metacode.spring.converter.RouteConstantsConverter;
import mmm.coffee.metacode.spring.endpoint.model.RouteConstants;
import org.junit.jupiter.api.Tag;
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

    final RestEndpointDescriptorToTemplateModelConverter converterUnderTest = new RestEndpointDescriptorToTemplateModelConverter(new NameConverter(), new RouteConstantsConverter());

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

        // This test explicitly set the framework to webflux, so test the toggles
        assertThat(model.isWebFlux()).isTrue();
        assertThat(model.isWebMvc()).isFalse();

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

    @Test
    @Tag("coverage")
    void shouldCreateInstanceWithBuilderAPI() {
        var converter = RestEndpointDescriptorToTemplateModelConverter
                .builder().nameConverter(new NameConverter()).build();

        assertThat(converter).isNotNull();
    }

    /**
     * If the route entered on the command line does not
     * begin with a forward slash, the converter should add
     * to avoid errors at runtime that can be difficult to identify.
     */
    @Test
    void shouldPrefaceRouteWithForwardSlash() {
        var spec = RestEndpointDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(WEBFLUX)
                .resource("Owner")
                .route("owner")
                .build();

        var model = converterUnderTest.convert(spec);

        assertThat(model.getRoute()).startsWith("/");
    }

    @Test
    void shouldPopulateRouteConstants() {
        var spec = RestEndpointDescriptor.builder()
                .basePackage(BASE_PACKAGE)
                .basePath(BASE_PATH)
                .framework(WEBFLUX)
                .resource("Owner")
                .route("owner")
                .build();

        var model = converterUnderTest.convert(spec);

        RouteConstants routeConstants = model.getRouteConstants();

        // Verify a value is defined for each constant name.
        // We do some white-box testing and verify the returned name
        // looks like what is expected. Since a converter basically provides
        // a mapping, copying instance variables from one object into the
        // instance variables of another object, the following tests verify
        // the name looks like what is expected, to make sure each instance
        // variable was mapped correctly.
        //
        // The pattern for the name isn't a hard contract. If the pattern changes,
        // just update these tests accordingly.
        assertThat(routeConstants).isNotNull();
        assertThat(routeConstants.getBasePath()).isNotEmpty();
        
        assertThat(routeConstants.getCreate()).isNotEmpty();
        assertThat(routeConstants.getCreate()).contains("CREATE");

        assertThat(routeConstants.getDelete()).isNotEmpty();
        assertThat(routeConstants.getDelete()).contains("DELETE");

        assertThat(routeConstants.getEvents()).isNotEmpty();
        assertThat(routeConstants.getEvents()).contains("EVENT");

        assertThat(routeConstants.getFindAll()).isNotEmpty();
        assertThat(routeConstants.getFindAll()).contains("FIND_ALL");

        assertThat(routeConstants.getFindOne()).isNotEmpty();
        assertThat(routeConstants.getFindOne()).contains("FIND");

        assertThat(routeConstants.getIdParameter()).isNotEmpty();
        assertThat(routeConstants.getIdParameter()).contains("ID");

        assertThat(routeConstants.getSearch()).isNotEmpty();
        assertThat(routeConstants.getSearch()).contains("SEARCH");

        assertThat(routeConstants.getStream()).isNotEmpty();
        assertThat(routeConstants.getStream()).contains("STREAM");

        assertThat(routeConstants.getUpdate()).isNotEmpty();
        assertThat(routeConstants.getUpdate()).contains("UPDATE");
    }

}
