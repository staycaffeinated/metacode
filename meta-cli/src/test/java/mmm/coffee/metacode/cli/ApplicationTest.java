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
package mmm.coffee.metacode.cli;

import com.google.inject.*;
import mmm.coffee.metacode.annotations.guice.*;
import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import mmm.coffee.metacode.common.trait.WriteOutputTrait;
import mmm.coffee.metacode.common.writer.ContentToNullWriter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.google.common.truth.Truth.assertThat;


/**
 * Unit test
 */
@SuppressWarnings("java:S5976") // S5976: don't refactor tests simply to satisfy sonarqube
class ApplicationTest {

    final CommandLine.IFactory myFactory = new GuiceFactory();
    final Application app = new Application();
    final CommandLine commandLine = new CommandLine(app, myFactory);

    @BeforeEach
    public void setUp() {
        commandLine.clearExecutionResults();
    }
    @Test
    void shouldAcceptHelpFlag() {
        // Given the --help command is entered on the command line
        String[] argv = StringHelper.toArgV("--help");

        // Expect --help is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptVersionFlag() {
        // Given the --version command
        String[] argv = StringHelper.toArgV("--version");

        // Expect --version is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptCreateProjectHelpFlag() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("create project --help");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptCreateProjectSpringWebFluxHelp() {
        // Give the commandline is: create project spring-webflux --help
        String[] argv = StringHelper.toArgV("create project spring-webflux --help");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    /**
     * This is a simple exercise of the main() method and getExitCode() method.
     */
    @Test
    void whenPrintingHelp_shouldReturnOk() {
        String[] argv = StringHelper.toArgV("--help");
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
            return new FakeSpringCodeGenerator();
        }

        @Provides
        @SpringWebFlux
        ICodeGenerator<RestProjectDescriptor> providesSpringWebFluxGenerator() {
            return new FakeSpringCodeGenerator();
        }

        @Provides
        @SpringBootProvider
        ICodeGenerator<RestProjectDescriptor> providesSpringBootGenerator() { return new FakeSpringCodeGenerator(); }

        /**
         * The code generator needs a class that will handle writing content to a file.
         * Specifically, once a template is parsed and rendered as a String, that String
         * gets written to a file (such as a .java source file). The WriteOutputProvider
         * provides the class that handles that.
         *
         * The WriteOutputProvider is made "pluggable" so that, during testing, a NullWriter
         * can be used to exercise the code w/o producing files that need to be cleaned up
         * after the tests finish.
         */
        @Provides
        @WriteOutputProvider
        WriteOutputTrait providesWriteOutput() { return new ContentToNullWriter(); }

        @Provides
        @RestEndpointGeneratorProvider
        ICodeGenerator<RestEndpointDescriptor> providesRestEndpointGenerator() {
            return new FakeEndpointGenerator();
        }
    }

    /**
     * A fake code generator suitable for testing
     */
    public static class FakeSpringCodeGenerator implements ICodeGenerator<RestProjectDescriptor> {
        /**
         * Performs the code generation. Returns:
         * 0 = success
         * 1 = general error
         *
         * @return the exit code, with zero indicating success.
         */
        @Override
        public int generateCode(RestProjectDescriptor ignored) {
            return 0;
        }

        public FakeSpringCodeGenerator doPreprocessing(RestProjectDescriptor ignored) {
            return this;
        }
    }

    public static class FakeEndpointGenerator implements ICodeGenerator<RestEndpointDescriptor> {
        public int generateCode(RestEndpointDescriptor d) { return 0; }
        public FakeEndpointGenerator doPreprocessing(RestEndpointDescriptor d) {
            return this;
        }
    }

}
