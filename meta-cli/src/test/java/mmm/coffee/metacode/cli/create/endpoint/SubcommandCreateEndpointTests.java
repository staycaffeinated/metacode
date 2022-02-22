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
package mmm.coffee.metacode.cli.create.endpoint;

import com.google.inject.*;
import mmm.coffee.metacode.annotations.guice.SpringWebFlux;
import mmm.coffee.metacode.annotations.guice.SpringWebMvc;
import mmm.coffee.metacode.cli.Application;
import mmm.coffee.metacode.cli.StringHelper;
import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.google.common.truth.Truth.assertThat;
import static mmm.coffee.metacode.cli.ExitCodes.INVALID_INPUT;
import static mmm.coffee.metacode.cli.ExitCodes.OK;

/**
 * Unit test
 */
class SubcommandCreateEndpointTests {

    final CommandLine.IFactory guiceFactory = new GuiceFactory();
    final Application app = new Application();
    final CommandLine commandLine = new CommandLine(app, guiceFactory);

    @BeforeEach
    public void setUp() {
        commandLine.clearExecutionResults();
    }

    @Test
    void shouldAcceptCreateProjectHelpFlag() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("create endpoint --help");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(OK);
    }

    /**
     * Verify the 'long' options, --resource & --route, are working
     */
    @Test
    void shouldAcceptLongOptions() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("create endpoint --resource Pet --route /pets");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(OK);
    }

    /**
     * Verify the 'short' options, -e & -p, are working
     */
    @Test
    void shouldAcceptShortOptions() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("create endpoint -e Pet -p /pets");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(OK);
    }

    @Test
    void shouldAcceptDryRunOption() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("create endpoint --resource Pet --route /pets --dry-run");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(OK);
    }

    @Test
    void shouldRejectInvalidResourceName() {
        // Given: the user attempts to create a resource with an invalid resourceName
        String[] argv = StringHelper.toArgV("create endpoint --resource Pet#1 --route /pets");

        // Expect the command is rejected
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(INVALID_INPUT);
    }

    /**
     * The following classes instrument Guice to use fake objects for testing
     *
     * From: https://picocli.info/#_guice_example
     */
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
        ICodeGenerator provideSpringWebMvcGenerator() {
            return new FakeCodeGenerator();
        }

        @Provides
        @SpringWebFlux
        ICodeGenerator providesSpringWebFluxGenerator() {
            return new FakeCodeGenerator();
        }
    }

    /**
     * A fake code generator suitable for testing
     */
    public static class FakeCodeGenerator implements ICodeGenerator {
        /**
         * Returns the exit code from the generator.
         * 0 = success
         * 1 = general error
         *
         * @return the exit code, with zero indicating success.
         */
        @Override
        public int generateCode() {
            return 0;
        }

        public void setDescriptor(Descriptor ignored) {
            // empty
        }
    }
}
