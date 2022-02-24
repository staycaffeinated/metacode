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
@Tag("coverage") // these tests only fulfil code coverage needs
class RestEndpointDescriptorTest {

    private static final String SAMPLE_RESOURCE = "Pet";
    private static final String SAMPLE_ROUTE = "/pet";

    @Test
    void testBuilderToString() {
        assertThat(RestEndpointDescriptor.builder().toString()).isNotNull();
    }

    @Test
    void shouldReturnNonNullObject() {
        assertThat(RestEndpointDescriptor.builder().build()).isNotNull();
    }

    @Test
    void shouldReturnDescriptor() {
        var descriptor = RestEndpointDescriptor.builder()
                .resource(SAMPLE_RESOURCE)
                .route(SAMPLE_ROUTE)
                .build();

        assertThat(descriptor.getResource()).isEqualTo(SAMPLE_RESOURCE);
        assertThat(descriptor.getRoute()).isEqualTo(SAMPLE_ROUTE);
    }
}
