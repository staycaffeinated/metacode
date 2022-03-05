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
package mmm.coffee.metacode.cli.create.endpoint;

import com.google.inject.*;
import mmm.coffee.metacode.annotations.guice.RestEndpointGeneratorProvider;
import mmm.coffee.metacode.annotations.guice.SpringWebFlux;
import mmm.coffee.metacode.annotations.guice.SpringWebMvc;
import mmm.coffee.metacode.cli.Application;
import mmm.coffee.metacode.cli.StringHelper;
import mmm.coffee.metacode.common.descriptor.RestEndpointDescriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import org.junit.Rule;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.google.common.truth.Truth.assertThat;
import static mmm.coffee.metacode.common.ExitCodes.INVALID_INPUT;
import static mmm.coffee.metacode.common.ExitCodes.OK;

/**
 * Unit test
 */
@SuppressWarnings("java:S5976") // S5976: don't refactor tests simply to satisfy sonarqube
class SubcommandCreateEndpointTests {

    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog().muteForSuccessfulTests();

    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog().muteForSuccessfulTests();

    // Define an instance of the SubcommandCreateEndpoint
    final SubcommandCreateEndpoint command = new SubcommandCreateEndpoint(new FakeCodeGenerator());

    // Create a Guice factory
    final CommandLine.IFactory guiceFactory = new GuiceFactory();

    // Create our commandLine instance, using Guice to inject components as needed
    final CommandLine commandLine = new CommandLine(command, guiceFactory);


    @BeforeEach
    public void setUp() {
        commandLine.clearExecutionResults();
    }

    /**
     * SPECIAL NOTE:
     * Because of where this test starts in the CLI hierarchy, the
     * tokens "create endpoint" are assumed. By the time this Command class
     * that's being tested is reached, the "create" and "endpoint" tokens have
     * already been consumed by the PicoCLI. Because of that, you'll notice
     * the test command strings do not start with "create endpoint" but,
     * instead, with whatever tokens occur next. 
     */

    @Test
    void shouldAcceptCreateProjectHelpFlag() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("--help");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(OK);
    }

    /**
     * Verify the 'long' options, --resource & --route, are working.
     * Because of where this Test starts in the CLI hierarchy, the
     * tokens "create endpoint" are assumed. (By the time the Command class
     * being tested is reached, the "create" and "endpoint" tokens have
     * already been consumed by the PicoCLI.)
     */
    @Test
    void shouldAcceptOptionsInLongForm() {
        // Given: the command line is: create project --help
        String[] argv = StringHelper.toArgV("--resource Pet --route /pets");

        // Expect the command is accepted and successful
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(OK);
    }

    /**
     * Verify the 'short' options, -e & -p, are working
     */
    @Test
    void shouldAcceptOptionsInShortForm() {
        // Given: the command line is: create endpoint -e Pet -p /pets
        String[] argv = StringHelper.toArgV("-e Pet -p /pets");

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

    // ------------------------------------------------------------------------------
    //
    // Helper classes
    //
    // ------------------------------------------------------------------------------

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

    /**
     * Defines the components to be injected. The project-level code generators
     * are included because Guice's injector will complain if it doesn't find
     * a component suitable for whatever it's trying to inject. Even though
     * this specific test case doesn't need them, Guice scans all classes for
     * instances of the @Inject annotation and checks this Module class for
     * a class suitable for injection at that @Inject point.
     */
    final static class SpringTestModule extends AbstractModule {
        @Provides
        @RestEndpointGeneratorProvider
        ICodeGenerator<RestEndpointDescriptor> providesRestEndpointCodeGenerator() {
            return new FakeCodeGenerator();
        }

        @Provides
        @SpringWebMvc
        ICodeGenerator<RestProjectDescriptor> provideSpringWebMvcGenerator() {
            return new FakeProjectCodeGenerator();
        }

        @Provides
        @SpringWebFlux
        ICodeGenerator<RestProjectDescriptor> providesSpringWebFluxGenerator() {
            return new FakeProjectCodeGenerator();
        }
    }

    /**
     * A fake code generator suitable for testing
     */
    public static class FakeCodeGenerator implements ICodeGenerator<RestEndpointDescriptor> {

        @Override
        public ICodeGenerator<RestEndpointDescriptor> doPreprocessing(RestEndpointDescriptor spec) { return this; }

        @Override
        public int generateCode(RestEndpointDescriptor spec) { return 0; }
    }

    public static class FakeProjectCodeGenerator implements ICodeGenerator<RestProjectDescriptor> {

        @Override
        public ICodeGenerator<RestProjectDescriptor> doPreprocessing(RestProjectDescriptor spec) { return this; }

        @Override
        public int generateCode(RestProjectDescriptor spec) { return 0; }
    }

}
