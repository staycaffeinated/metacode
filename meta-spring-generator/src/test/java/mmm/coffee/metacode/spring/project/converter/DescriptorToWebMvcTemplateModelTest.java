/*
 * Copyright 2020 Jon Caulfield
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package mmm.coffee.metacode.spring.project.converter;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests
 */
class DescriptorToWebMvcTemplateModelTest {

    private static final String APP_NAME = "petstore";
    private static final String BASE_PATH = "/petstore";
    private static final String BASE_PKG = "acme.petstore";

    final DescriptorToWebMvcTemplateModel converterUnderTest = new DescriptorToWebMvcTemplateModel();

    @Test
    void shouldContainBasicFields() {
        // given: basic project configuration...
        RestProjectDescriptor descriptor = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .build();

        var model = converterUnderTest.convert(descriptor);

        assertThat(model.getApplicationName()).isEqualTo(APP_NAME);
        assertThat(model.getBasePackage()).isEqualTo(BASE_PKG);
        assertThat(model.getBasePath()).isEqualTo(BASE_PATH);
        assertThat(model.isWebMvc()).isTrue();
        assertThat(model.isWebFlux()).isFalse(); // if its webmvc, its _not_ webflux
    }

    @Test
    void shouldThrowException_whenConvertingNullObject() {
        assertThrows(NullPointerException.class, () -> {
            converterUnderTest.convert(null);
        }) ;
    }
}
