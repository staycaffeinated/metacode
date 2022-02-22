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
package mmm.coffee.metacode.cli.create.project;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
public class RestProjectDescriptorImplTests {

    private static final String APPNAME = "petstore";
    private static final String BASEPKG = "acme.petstore";
    private static final String BASEPATH = "/petstore/v1";
    private static final String GROUPID = "org.acme.products";

    @Test
    void shouldBuildWellFormedObject() {
        RestProjectDescriptor obj = RestProjectDescriptor.builder()
                .basePackage(BASEPKG)
                .basePath(BASEPATH)
                .applicationName(APPNAME)
                .groupId(GROUPID)
                .build();

        assertThat(obj.getBasePackage()).isEqualTo(BASEPKG);
        assertThat(obj.getBasePath()).isEqualTo(BASEPATH);
        assertThat(obj.getApplicationName()).isEqualTo(APPNAME);
        assertThat(obj.getGroupId()).isEqualTo(GROUPID);
        
        assertThat(obj.toString()).isNotEmpty();
        assertThat(obj.equals(null)).isFalse();
        assertThat(obj.equals(obj)).isTrue();
        assertThat(obj.hashCode()).isEqualTo(obj.hashCode());
    }

    @Test
    @Tag("coverage")
    void shouldHitCodeCoverage() {
        var foo = RestProjectDescriptor.builder().toString();
        // String should contain some content; we're not picky about what
        // content, for the most part. 
        assertThat(foo).isNotEmpty();
    }
}
