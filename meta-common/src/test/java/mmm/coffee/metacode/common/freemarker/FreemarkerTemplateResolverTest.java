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
package mmm.coffee.metacode.common.freemarker;

import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import mmm.coffee.metacode.common.exception.RuntimeApplicationError;
import mmm.coffee.metacode.common.stereotype.MetaTemplateModel;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.io.IOException;

import static com.google.common.truth.Truth.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

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
        MetaTemplateModel mockTemplateModel = Mockito.mock(MetaTemplateModel.class);
        when(mockTemplateModel.getTopLevelVariable()).thenReturn("project");
        // and given: a freemarker template to render
        final String template = "/common/Copyright.ftl";

        // when: the template is rendered
        String content = resolverUnderTest.render(template, mockTemplateModel);

        // expect: at least a non-empty string
        assertThat(content).isNotEmpty();
    }

    @Test
    void shouldThrowNullPointerExceptionWhenConfigArgIsNull() {
        assertThrows(NullPointerException.class, () -> {
            new FreemarkerTemplateResolver(null);
        });
    }

    @Test
    void shouldThrowRuntimeApplicationErrorWhenTemplateExceptionOccurs() throws IOException {
        var mockConfig = Mockito.mock(freemarker.template.Configuration.class);
        // ReflectionTestUtils.setField(mockConfig, "lock", new Object());

        when(mockConfig.getTemplate(anyString(), anyString())).thenThrow(IOException.class);

        FreemarkerTemplateResolver resolver = new FreemarkerTemplateResolver(mockConfig);
        // The resolver is a synchronized class that uses a 'lock' instance variable for locking.
        // Cannot enter synchronized block because "this.lock" is null
        // ReflectionTestUtils.setField(resolver, "lock", new Object());

        MetaTemplateModel mockTemplateModel = Mockito.mock(MetaTemplateModel.class);
        assertThrows(RuntimeApplicationError.class, () -> resolver.render("someTemplate", mockTemplateModel));

    }
}
