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
package mmm.coffee.metacode.cli.create;

import com.google.inject.*;
import mmm.coffee.metacode.annotations.guice.SpringWebFlux;
import mmm.coffee.metacode.annotations.guice.SpringWebMvc;
import mmm.coffee.metacode.cli.Application;
import mmm.coffee.metacode.cli.ExitCodes;
import mmm.coffee.metacode.cli.StringHelper;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.google.common.truth.Truth.assertThat;

/**
 * Unit test
 */
class CreateCommandTest {

    final CommandLine.IFactory guiceFactory = new GuiceFactory();
    final Application app = new Application();
    final CommandLine commandLine = new CommandLine(app, guiceFactory);

    @BeforeEach
    public void setUp() {
        commandLine.clearExecutionResults();
    }

    @Test
    void shouldAcceptCreateHelpFlag() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("create --help");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    /**
     * The following classes configure Guice to use fake classes for testing
     */
    // From: https://picocli.info/#_guice_example
    final static class GuiceFactory implements CommandLine.IFactory {
        private final Injector injector = Guice.createInjector(new SpringTestModule());

        @Override
        public <K> K create(Class<K> aClass) throws Exception {
            try {
                return injector.getInstance(aClass);
            }
            catch (ConfigurationException e) {
                return CommandLine.defaultFactory().create(aClass);
            }
        }
    }

    final static class SpringTestModule extends AbstractModule {
        @Provides
        @SpringWebMvc
        ICodeGenerator<RestProjectDescriptor> provideSpringWebMvcGenerator() {
            return new FakeCodeGenerator();
        }

        @Provides
        @SpringWebFlux
        ICodeGenerator<RestProjectDescriptor> providesSpringWebFluxGenerator() {
            return new FakeCodeGenerator();
        }
    }

    /**
     * A fake code generator suitable for testing
     */
    public static class FakeCodeGenerator implements ICodeGenerator<RestProjectDescriptor> {
        /**
         * Returns the exit code from the generator.
         * 0 = success
         * 1 = general error
         *
         * @return the exit code, with zero indicating success.
         */
        @Override
        public int generateCode(RestProjectDescriptor ignored) { return 0; }
    }
}
