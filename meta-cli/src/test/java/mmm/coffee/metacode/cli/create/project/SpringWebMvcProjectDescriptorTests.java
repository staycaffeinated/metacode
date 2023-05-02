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

import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.spring.constant.SpringIntegrations;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests of RestProjectDescriptor in the context of defining spring-webmvc projects.
 *
 * This test case lives in the {@code meta-cli} module because {@code meta-cli} has dependencies
 * on {@code meta-common} and {@code meta-spring}.  This test uses some {@code meta-cli} classes.
 * If this test were moved to {@code meta-common}, then {@code meta-common} would require a
 * dependency on {@code meta-cli}, causing a circular dependency between {@code meta-cli} and {@code meta-common}.
 * Thus, this test lives in {@code meta-cli}.
 */
class SpringWebMvcProjectDescriptorTests {

    private static final String BASEPATH = "/petstore";
    private static final String BASEPKG = "io.acme.petstore";
    private static final String APPNAME = "petstore";
    private static final String GROUPID = "io.acme.petstore";
    private static final String SCHEMA = "petstore";
    private static Set<SpringIntegrations> FEATURES = new HashSet<>();

    @BeforeEach
    public void setUp() {
        FEATURES.clear();
        FEATURES.add(SpringIntegrations.POSTGRES);
        FEATURES.add(SpringIntegrations.TESTCONTAINERS);
    }

    /**
     * A simple exercise of the SpringWebMvcProjectDescriptor api
     */
    @Test
    @SuppressWarnings("all")
    void shouldBuildWellFormedObject() {
        var descriptor = RestProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .framework(Framework.SPRING_WEBMVC)
                .build();

        descriptor.getIntegrations().add(SpringIntegrations.POSTGRES.name());
        descriptor.getIntegrations().add(SpringIntegrations.TESTCONTAINERS.name());
        String[] expectedFeatures = {
                SpringIntegrations.POSTGRES.name(),
                SpringIntegrations.TESTCONTAINERS.name()
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
    void shouldBuildWellFormedProjectWithSchema() {
        var descriptor = RestProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .schema(SCHEMA)
                .framework(Framework.SPRING_WEBMVC)
                .build();

        descriptor.getIntegrations().add(SpringIntegrations.POSTGRES.name());
        descriptor.getIntegrations().add(SpringIntegrations.TESTCONTAINERS.name());
        String[] expectedFeatures = {
                SpringIntegrations.POSTGRES.name(),
                SpringIntegrations.TESTCONTAINERS.name()
        };

        assertThat(descriptor.getApplicationName()).isEqualTo(APPNAME);
        assertThat(descriptor.getBasePackage()).isEqualTo(BASEPKG);
        assertThat(descriptor.getBasePath()).isEqualTo(BASEPATH);
        assertThat(descriptor.getGroupId()).isEqualTo(GROUPID);
        assertThat(descriptor.getSchema()).isEqualTo(SCHEMA);
        assertThat(descriptor.getIntegrations()).containsExactlyElementsIn(expectedFeatures);

        assertThat(descriptor.toString()).isNotEmpty();
        assertThat(descriptor.equals(null)).isFalse();
        assertThat(descriptor.equals(descriptor)).isTrue();
        assertThat(descriptor.hashCode()).isEqualTo(descriptor.hashCode());
    }

    @Test
    void shouldAllowAddingIntegrationsAfterBuildIsCalled() {
        var descriptor = RestProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .framework(Framework.SPRING_WEBMVC)
                .build();

        descriptor.getIntegrations().add(SpringIntegrations.POSTGRES.name());
        descriptor.getIntegrations().add(SpringIntegrations.TESTCONTAINERS.name());
        
        assertThat(descriptor.getIntegrations().size()).isEqualTo(2);
        assertThat(descriptor.getIntegrations()).containsExactly(SpringIntegrations.POSTGRES.name(), SpringIntegrations.TESTCONTAINERS.name());
    }
    
    @Test
    void shouldAddIntegrationsDuringBuildPhase() {
        var descriptor = RestProjectDescriptor.builder()
                .applicationName(APPNAME)
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .groupId(GROUPID)
                .framework(Framework.SPRING_WEBMVC)
                .build();

        // calling {@code '.integrations(FEATURES)'} does not work - we end up with an empty list.
        // that's either a lombok issue or a misunderstanding in how lombok sets List values
        FEATURES.forEach(f -> descriptor.getIntegrations().add(f.name()));

        assertThat(descriptor.getIntegrations().size()).isEqualTo(2);
        assertThat(descriptor.getIntegrations()).containsExactly(SpringIntegrations.POSTGRES.name(), SpringIntegrations.TESTCONTAINERS.name());
    }

    /*
     * This test only increases code coverage
     */
    @Test
    @Tag("coverage")
    void testToString() {
        var descriptor = RestProjectDescriptor.builder().build();
        assertThat(descriptor.toString()).isNotEmpty();
    }

    /*
     * This test only increases code coverage
     */
    @Test
    @Tag("coverage")
    @SuppressWarnings("all")
    void testBuilderToString() {
        var descriptor = RestProjectDescriptor.builder().toString();
        assertThat(descriptor.toString()).isNotEmpty();
    }
}
