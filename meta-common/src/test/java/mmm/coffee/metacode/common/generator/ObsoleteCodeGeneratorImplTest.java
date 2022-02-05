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
package mmm.coffee.metacode.common.generator;

import com.google.inject.AbstractModule;
import com.google.inject.Guice;
import com.google.inject.Injector;
import mmm.coffee.metacode.common.catalog.TemplateCatalog;
import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.trait.MetaSpecification;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.spi.AbstractResourceBundleProvider;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test
 */
class ObsoleteCodeGeneratorImplTest {

    public static final int SUCCESS = 0;

    TemplateCatalog mockCatalog;
    MetaSpecification mockSpec;
    RestProjectDescriptor mockProjectDescriptor;

    CodeGeneratorImpl generator;
    Injector injector;

    @BeforeEach
    public void setUp() {
        mockCatalog = Mockito.mock(TemplateCatalog.class);
        mockSpec = Mockito.mock(MetaSpecification.class);
        mockProjectDescriptor = Mockito.mock(RestProjectDescriptor.class);

        injector = Guice.createInjector(new AbstractModule() {
            @Override
            protected void configure() {
                bind(TemplateCatalog.class).toInstance(mockCatalog);
                bind(Descriptor.class).toInstance(mockProjectDescriptor);
            }
        });

        generator = injector.getInstance(CodeGeneratorImpl.class);
    }

    @Test
    void shouldPass() {
        int rc = generator.generateCode();
        assertThat(rc).isEqualTo(SUCCESS);
    }
}
