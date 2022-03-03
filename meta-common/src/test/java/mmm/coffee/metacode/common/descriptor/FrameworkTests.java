/*
 * Copyright (c) 2022 Jon Caulfield.
 */
package mmm.coffee.metacode.common.descriptor;

import org.junit.jupiter.api.Test;

import java.awt.*;

import static com.google.common.truth.Truth.assertThat;

/**
 * FrameworkTests
 */
class FrameworkTests {

    @Test
    void shouldBeWebMvc() {
        assertThat(Framework.SPRING_WEBMVC.isWebMvc()).isTrue();

        // test against false positives
        assertThat(Framework.SPRING_WEBFLUX.isWebMvc()).isFalse();
    }

    @Test
    void shouldBeWebFlux() {
        assertThat(Framework.SPRING_WEBFLUX.isWebFlux()).isTrue();

        // test against false positives
        assertThat(Framework.SPRING_WEBMVC.isWebFlux()).isFalse();
    }

    @Test
    void whenUndefined_shouldNotBeSupportedFramework() {
        assertThat(Framework.UNDEFINED.isWebFlux()).isFalse();
        assertThat(Framework.UNDEFINED.isWebMvc()).isFalse();
    }

}
