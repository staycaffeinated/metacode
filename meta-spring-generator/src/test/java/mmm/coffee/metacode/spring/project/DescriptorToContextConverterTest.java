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
package mmm.coffee.metacode.spring.project;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
class DescriptorToContextConverterTest {

    private static final String APP_NAME = "petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String BASE_PATH = "/petstore";

    RestProjectDescriptor webFluxDescriptor;
    RestProjectDescriptor webMvcDescriptor;

    final DescriptorToContextConverter converterUnderTest = new DescriptorToContextConverter();

    @BeforeEach
    public void createNewDescriptors() {
        webFluxDescriptor = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .build();

        webMvcDescriptor = RestProjectDescriptor.builder()
                .applicationName(APP_NAME)
                .basePackage(BASE_PKG)
                .basePath(BASE_PATH)
                .build();
    }

    @Test
    void shouldConvertToWebFluxContext() {
        // given: a converted object
        SpringProjectContext context = converterUnderTest.convert(webFluxDescriptor);

        // expect: 1:1 match of common fields
        assertThat(context.getApplicationName()).isEqualTo(webFluxDescriptor.getApplicationName());
        assertThat(context.getBasePackage()).isEqualTo(webFluxDescriptor.getBasePackage());
        assertThat(context.getBasePath()).isEqualTo(webFluxDescriptor.getBasePath());
    }

    @Test
    void shouldConvertToWebMvcContext() {
        // given: a converted object
        SpringProjectContext context = converterUnderTest.convert(webMvcDescriptor);

        // expect: a 1:1 match of common fields
        assertThat(context.getApplicationName()).isEqualTo(webMvcDescriptor.getApplicationName());
        assertThat(context.getBasePackage()).isEqualTo(webMvcDescriptor.getBasePackage());
        assertThat(context.getBasePath()).isEqualTo(webMvcDescriptor.getBasePath());
    }
}
