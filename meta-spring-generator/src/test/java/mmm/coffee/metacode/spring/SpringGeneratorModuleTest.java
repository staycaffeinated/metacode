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
package mmm.coffee.metacode.spring;

import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test the SpringGeneratorModule
 */
public class SpringGeneratorModuleTest {

    SpringGeneratorModule moduleUnderTest = new SpringGeneratorModule();

    /**
     * This test exercises the `provides` methods. This is mainly a smoke test
     * to catch any issues with the builders within the `provides` methods.
     */
    @Test
    void provideMethodsShouldNotReturnNull() {
        assertThat(moduleUnderTest.providesDependencyCatalog()).isNotNull();
        assertThat(moduleUnderTest.providesEndpointMetaPropertiesHandler()).isNotNull();
        assertThat(moduleUnderTest.providesFreemarkerConfiguration()).isNotNull();
        assertThat(moduleUnderTest.providesMetaPropertiesHandler()).isNotNull();
        assertThat(moduleUnderTest.providesSpringWebMvcGenerator()).isNotNull();
        assertThat(moduleUnderTest.providesSpringWebFluxGenerator()).isNotNull();
        assertThat(moduleUnderTest.providesRestEndpointGenerator()).isNotNull();
        assertThat(moduleUnderTest.providesSpringBootGenerator()).isNotNull();
        assertThat(moduleUnderTest.providesWriteOutput()).isNotNull();
    }
}
