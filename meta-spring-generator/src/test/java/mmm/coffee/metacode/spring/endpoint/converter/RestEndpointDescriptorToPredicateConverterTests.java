/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.converter;

import com.google.common.base.Predicate;
import mmm.coffee.metacode.common.catalog.CatalogEntry;
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * RestEndpointDescriptorToPredicateConverterTests
 */
class RestEndpointDescriptorToPredicateConverterTests {

    final RestEndpointDescriptorToPredicateConverter converterUnderTest = new RestEndpointDescriptorToPredicateConverter();

    RestEndpointDescriptor webFluxDescriptor;
    RestEndpointDescriptor webMvcDescriptor;

    CatalogEntry webFluxEndpointCE;
    CatalogEntry webMvcEndpointCE;
    CatalogEntry webFluxProjectCE;
    CatalogEntry webMvcProjectCE;

    @BeforeEach
    public void setUp() {
        final String basePath = "/petstore";
        final String route = "/pet";
        final String resource = "Pet";
        final String basePackage = "org.acme.petstore";

        // An example WebFlux endpoint
        webFluxDescriptor = RestEndpointDescriptor.builder()
                .basePath(basePath)
                .basePackage(basePackage)
                .resource(resource)
                .route(route)
                .framework(Framework.SPRING_WEBFLUX.frameworkName())
                .build();

        // An example WebMvc endpoint
        webMvcDescriptor = RestEndpointDescriptor.builder()
                .basePath(basePath)
                .basePackage(basePackage)
                .resource(resource)
                .route(route)
                .framework(Framework.SPRING_WEBMVC.frameworkName())
                .build();

        // An example entry from the spring-webflux catalog
        webFluxEndpointCE = new CatalogEntry();
        webFluxEndpointCE.setContext("endpoint");
        webFluxEndpointCE.setTemplate("/spring/webflux/endpoint/Controller.ftl");

        // An example entry from the spring-webmvc catalog
        webMvcEndpointCE = new CatalogEntry();
        webMvcEndpointCE.setContext("endpoint");
        webMvcEndpointCE.setTemplate("/spring/webmvc/endpoint/Controller.ftl");

        // An example project-level entry from the spring-webflux catalog
        webFluxProjectCE = new CatalogEntry();
        webFluxProjectCE.setContext("project");
        webFluxProjectCE.setTemplate("/spring/webflux/Application.ftl");

        // An example project-level entry from the spring-webmvc catalog
        webMvcProjectCE = new CatalogEntry();
        webMvcProjectCE.setContext("project");
        webMvcProjectCE.setTemplate("/spring/webmvc/Application.ftl");
    }


    @Test
    void shouldOnlyAcceptWebFluxEndpointTemplates() {
        Predicate<CatalogEntry> predicate = converterUnderTest.convert(webFluxDescriptor);

        // expect: the predicate accepts a webflux template
        assertThat(predicate.apply(webFluxEndpointCE)).isTrue();

        // expect: the predicate rejects CatalogEntry's that
        // are not for WebFlux endpoints
        assertThat(predicate.apply(webMvcEndpointCE)).isFalse();
        assertThat(predicate.apply(webMvcProjectCE)).isFalse();
        assertThat(predicate.apply(webFluxProjectCE)).isFalse();
    }

    @Test
    void shouldOnlyAcceptWebMvcEndpointTemplates() {
        Predicate<CatalogEntry> predicate = converterUnderTest.convert(webMvcDescriptor);

        // expect: the predicate accepts a webmvc template
        assertThat(predicate.apply(webMvcEndpointCE)).isTrue();

        // expect: the predicate rejects any CatalogEntry
        // that is not for a WebMvc endpoint
        assertThat(predicate.apply(webFluxEndpointCE)).isFalse();
        assertThat(predicate.apply(webFluxProjectCE)).isFalse();
        assertThat(predicate.apply(webMvcProjectCE)).isFalse();
    }
}
