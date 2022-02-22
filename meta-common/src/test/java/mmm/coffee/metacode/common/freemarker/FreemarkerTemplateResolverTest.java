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
package mmm.coffee.metacode.common.freemarker;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * Unit tests
 */
class FreemarkerTemplateResolverTest {

    FreemarkerTemplateResolver resolverUnderTest;

    @BeforeEach
    public void setUp() {
        resolverUnderTest = new FreemarkerTemplateResolver(ConfigurationFactory.defaultConfiguration("/sample/templates"));
    }

    @Test
    void shouldRenderSimpleTemplate() {
        // Given: a map of variables used by the template
        HashMap<String,String> templateModel = new HashMap<>();
        // and given: a freemarker template to render
        final String template = "/common/Copyright.ftl";

        // when: the template is rendered
        String content = resolverUnderTest.render(template, templateModel);

        // expect: at least a non-empty string
        assertThat(content).isNotEmpty();
    }

    @Test
    void shouldThrowNullPointerExceptionWhenConfigArgIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new FreemarkerTemplateResolver(null);
        });
    }
}
