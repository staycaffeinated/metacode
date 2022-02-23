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
package mmm.coffee.metacode.spring.project.generator;

import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.stereotype.Collector;
import mmm.coffee.metacode.common.stereotype.TemplateResolver;
import mmm.coffee.metacode.common.writer.ContentToNullWriter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToPredicateConverter;
import mmm.coffee.metacode.spring.project.converter.DescriptorToRestProjectTemplateModelConverter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test
 */
class SpringWebFluxCodeGeneratorTest {
    SpringWebFluxCodeGenerator generatorUnderTest;

    @Mock
    Collector mockCollector;

    @Mock
    TemplateResolver mockRenderer;

    @Mock
    RestProjectDescriptor mockDescriptor;

    @BeforeEach
    public void setUp() {
        generatorUnderTest = SpringWebFluxCodeGenerator.builder()
                .collector(mockCollector)
                .descriptor2templateModel(new DescriptorToRestProjectTemplateModelConverter())
                .descriptor2predicate(new DescriptorToPredicateConverter())
                .outputHandler(new ContentToNullWriter())
                .templateRenderer(mockRenderer)
                .build();
    }

    @Test
    void shouldSucceed() {
        assertThat(generatorUnderTest.generateCode(mockDescriptor)).isEqualTo(0);
    }

}
