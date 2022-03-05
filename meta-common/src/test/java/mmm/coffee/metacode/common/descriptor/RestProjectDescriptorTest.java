/*
 * Copyright 2022 Jon Caulfield
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
package mmm.coffee.metacode.common.descriptor;

import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test
 */
@Tag("coverage") // these tests create code coverage of basic methods
class RestProjectDescriptorTest {

    private static final String BASE_PATH = "/petstore";
    private static final String BASE_PKG = "acme.petstore";
    private static final String APP_NAME = "petstore";
    private static final String GROUP_ID = "org.acme.petstore";


    @Test
    void testBuilderToString() {
        assertThat(RestProjectDescriptor.builder().toString()).isNotNull();
    }

    @Test
    void shouldBuildNonNullObject() {
        assertThat(RestProjectDescriptor.builder().build()).isNotNull();
    }

    @Test
    void shouldDefineWellFormedSpringWebMvcProject() {
        var descriptor = RestProjectDescriptor.builder()
                .basePath(BASE_PATH)
                .basePackage(BASE_PKG)
                .applicationName(APP_NAME)
                .framework(Framework.SPRING_WEBMVC)
                .build();

        assertThat(descriptor.getApplicationName()).isEqualTo(APP_NAME);
        assertThat(descriptor.getBasePackage()).isEqualTo(BASE_PKG);
        assertThat(descriptor.getBasePath()).isEqualTo(BASE_PATH);
        assertThat(descriptor.getFramework()).isEqualTo(Framework.SPRING_WEBMVC.frameworkName());
    }

    @Test
    void shouldDefineWellFormedSpringWebFluxProject() {
        var descriptor = RestProjectDescriptor.builder()
                .basePath(BASE_PATH)
                .basePackage(BASE_PKG)
                .applicationName(APP_NAME)
                .framework(Framework.SPRING_WEBFLUX)
                .build();

        assertThat(descriptor.getApplicationName()).isEqualTo(APP_NAME);
        assertThat(descriptor.getBasePackage()).isEqualTo(BASE_PKG);
        assertThat(descriptor.getBasePath()).isEqualTo(BASE_PATH);
        assertThat(descriptor.getFramework()).isEqualTo(Framework.SPRING_WEBFLUX.frameworkName());
    }

    /*
     * This test only helps with code coverage, hence the tag name
     */
    @Test
    @Tag("coverage")
    void shouldHitCodeCoverage() {
        var foo = RestProjectDescriptor.builder().toString();
        // String should contain some content; we're not picky about what
        // content, for the most part.
        assertThat(foo).isNotEmpty();
    }
    
}
