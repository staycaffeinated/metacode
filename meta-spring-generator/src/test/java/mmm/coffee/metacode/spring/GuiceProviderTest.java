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

import com.google.inject.Binding;
import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Key;
import mmm.coffee.metacode.annotations.guice.SpringBatchProvider;
import mmm.coffee.metacode.annotations.guice.SpringBootProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static com.google.common.truth.Truth.assertThat;

/**
 * https://stackoverflow.com/questions/8608638/what-techniques-do-you-use-to-debug-complex-guice-bindings
 * https://stackoverflow.com/questions/2716993/hidden-features-of-google-guice/2718802#2718802
 * https://stackoverflow.com/questions/2448013/how-do-i-test-guice-injections
 */
class GuiceProviderTest {

    Injector injector;

    @BeforeEach
    public void setUp() {
        injector = Guice.createInjector(new SpringGeneratorModule());
    }

    /**
     * The point of this test is to verify everything Guice needs to bind together
     * a SpringBatchProvider is defined and configured. With deep dependency graphs,
     * it can be difficult to track down unbound items. This test is used as a starting
     * point. If the SpringBatchProvider cannot be bound, then some IoC dependency of
     * the SpringBatchProvider couldn't be discovered or materialized.
     */
    @Test
    void verifySpringBatchProviderIsBound() {
        verifyBindingOf(SpringBatchProvider.class);
    }
    /**
     * The point of this test is to verify everything Guice needs to bind together
     * a SpringBootProvider is well-defined and configured.
     */

    @Test
    void verifySpringBootProviderIsBound() {
        verifyBindingOf(SpringBootProvider.class);

    }

    /**
     * Checks the Bindings that are available and confirms `klass` is one of them.
     */
    private void verifyBindingOf (Class<?> klass) {
        Map<Key<?>, Binding<?>> map = injector.getBindings();
        map.keySet().forEach(key -> {
            if (key.getAnnotationType() != null) {
                if (key.getAnnotationType().equals(klass)) {
                    Binding<?> binding = injector.getExistingBinding(key);
                    assertThat(binding).isNotNull();
                }
            }
        });
    }
}
