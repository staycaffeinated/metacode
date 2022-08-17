/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.spring.endpoint.mustache;

import mmm.coffee.metacode.spring.endpoint.converter.RestEndpointTemplateModelToMapConverter;
import mmm.coffee.metacode.spring.endpoint.model.RestEndpointTemplateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * MustachEndpointDecoderTests
 */
class MustachEndpointDecoderTests {

    MustacheEndpointDecoder decoderUnderTest;

    @BeforeEach
    public void setUp() {
        decoderUnderTest = MustacheEndpointDecoder.builder()
                .converter(new RestEndpointTemplateModelToMapConverter())
                .build();
    }

    @Test
    void shouldWork() {
        RestEndpointTemplateModel model = RestEndpointTemplateModel.builder()
                .basePath("/petstore")
                .basePackage("org.acme.petstore")
                .basePackagePath("org/acme/petstore")
                .ejbName("PetEJB")
                .entityName("Pet")
                .entityVarName("pet")
                .lowerCaseEntityName("pet")
                .pojoName("Pet")
                .isWebFlux(true)
                .build();

        decoderUnderTest.configure(model);

        assertThat(decoderUnderTest.decode("{{basePath}}")).isEqualTo("/petstore");
        assertThat(decoderUnderTest.decode("{{basePackage}}")).isEqualTo("org.acme.petstore");
        assertThat(decoderUnderTest.decode("{{ejbName}}")).isEqualTo("PetEJB");
        assertThat(decoderUnderTest.decode("{{pojoName}}")).isEqualTo("Pet");
    }
}
