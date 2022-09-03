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

import com.google.inject.Guice;
import com.google.inject.Injector;
import mmm.coffee.metacode.annotations.guice.SpringBatchProvider;
import mmm.coffee.metacode.annotations.guice.SpringBootProvider;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import static com.google.common.truth.Truth.assertThat;

/**
 * https://stackoverflow.com/questions/8608638/what-techniques-do-you-use-to-debug-complex-guice-bindings
 * https://stackoverflow.com/questions/2716993/hidden-features-of-google-guice/2718802#2718802
 * https://stackoverflow.com/questions/2448013/how-do-i-test-guice-injections
 */
public class GuiceProviderTest {

    Injector injector;

    @BeforeEach
    public void setUp() {
        injector = Guice.createInjector(new SpringGeneratorModule());
    }

    @Disabled
    void verifySpringBatchProviderIsBound() {
        var provider = injector.getProvider(SpringBatchProvider.class);
        assertThat(provider).isNotNull();
    }
    @Disabled
    void verifySpringBootProviderIsBound() {
        var provider = injector.getProvider(SpringBootProvider.class);
        assertThat(provider).isNotNull();
    }
}
