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
package mmm.coffee.metacode.cli.create.project;

import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
class SpringWebMvcProjectDescriptorTests {

    private static final String BASEPATH = "/petstore";
    private static final String BASEPKG = "io.acme.petstore";
    private static final String APPNAME = "petstore";
    private static final String GROUPID = "io.acme.petstore";
    private static Set<WebMvcIntegration> FEATURES = new HashSet<>();

    @BeforeEach
    public void setUp() {
        FEATURES.clear();
        FEATURES.add(WebMvcIntegration.POSTGRES);
        FEATURES.add(WebMvcIntegration.TESTCONTAINERS);
    }

    /**
     * A simple exercise of the SpringWebMvcProjectDescriptor api
     */
    @Test
    void shouldBuildWellFormedObject() {
        var descriptor = SpringWebMvcProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .build();

        descriptor.getIntegrations().add(WebMvcIntegration.POSTGRES.name());
        descriptor.getIntegrations().add(WebMvcIntegration.TESTCONTAINERS.name());
        String[] expectedFeatures = {
                WebMvcIntegration.POSTGRES.name(),
                WebMvcIntegration.TESTCONTAINERS.name()
        };

        assertThat(descriptor.getApplicationName()).isEqualTo(APPNAME);
        assertThat(descriptor.getBasePackage()).isEqualTo(BASEPKG);
        assertThat(descriptor.getBasePath()).isEqualTo(BASEPATH);
        assertThat(descriptor.getGroupId()).isEqualTo(GROUPID);
        assertThat(descriptor.getIntegrations()).containsExactlyElementsIn(expectedFeatures);

        assertThat(descriptor.toString()).isNotEmpty();
        assertThat(descriptor.equals(null)).isFalse();
        assertThat(descriptor.equals(descriptor)).isTrue();
        assertThat(descriptor.hashCode()).isEqualTo(descriptor.hashCode());
    }

    @Test
    void shouldAllowAddingIntegrationsAfterBuildIsCalled() {
        var descriptor = SpringWebMvcProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .build();

        descriptor.getIntegrations().add(WebMvcIntegration.POSTGRES.name());
        descriptor.getIntegrations().add(WebMvcIntegration.TESTCONTAINERS.name());
        
        assertThat(descriptor.getIntegrations().size()).isEqualTo(2);
        assertThat(descriptor.getIntegrations()).containsExactly(WebMvcIntegration.POSTGRES.name(), WebMvcIntegration.TESTCONTAINERS.name());
    }
    
    @Test
    void shouldAddIntegrationsDuringBuildPhase() {
        var descriptor = SpringWebMvcProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .build();

        // calling {@code '.integrations(FEATURES)'} does not work - we end up with an empty list.
        // that's either a lombok issue or a misunderstanding in how lombok sets List values
        FEATURES.forEach(f -> descriptor.getIntegrations().add(f.name()));

        assertThat(descriptor.getIntegrations().size()).isEqualTo(2);
        assertThat(descriptor.getIntegrations()).containsExactly(WebMvcIntegration.POSTGRES.name(), WebMvcIntegration.TESTCONTAINERS.name());
    }

    /*
     * This test only increases code coverage
     */
    @Test
    @Tag("coverage")
    void testToString() {
        var descriptor = SpringWebMvcProjectDescriptor.builder().build();
        assertThat(descriptor.toString()).isNotEmpty();
    }

    /*
     * This test only increases code coverage
     */
    @Test
    @Tag("coverage")
    void testBuilderToString() {
        var descriptor = SpringWebMvcProjectDescriptor.builder().toString();
        assertThat(descriptor.toString()).isNotEmpty();
    }
}
