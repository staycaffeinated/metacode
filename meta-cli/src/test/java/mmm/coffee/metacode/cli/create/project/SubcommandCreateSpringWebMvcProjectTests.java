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
package mmm.coffee.metacode.cli.create.project;

import mmm.coffee.metacode.common.ExitCodes;
import mmm.coffee.metacode.cli.StringHelper;
import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
@SuppressWarnings("java:S5976") // S5976: don't refactor tests simply to satisfy sonarqube
class SubcommandCreateSpringWebMvcProjectTests {

    protected CommandLine commandLine;
    
    /**
     * Set up
     */
    @BeforeEach
    public void setUp() {
        /*
         * NB: Since we're creating an instance of SubcommandCreateWebMvcProject,
         * we're also imply that the first part of the command line,
         * "create project spring-webmvc" has been entered. That's why the
         * command line args in the tests start with the options of the spring-webmvc subcommand.
         */
        SubcommandCreateWebMvcProject createProjectCommand = new SubcommandCreateWebMvcProject(new FakeCodeGenerator());
        commandLine = new CommandLine(createProjectCommand);
    }

    /**
     * When all options on the command line are valid, execution should succeed
     */
    @Test
    void shouldAcceptWellFormedCommandLine() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore --base-path /petstore");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldErrorOnBadPackageName() throws Exception {
        // Given: a invalid packageName
        String[] argv = StringHelper.toArgV("--name petstore --package 123.#4$abc --base-path /petstore");
        String errText = tapSystemErrAndOut(() -> {
            int rc = commandLine.execute(argv);
            assertThat(rc).isEqualTo(ExitCodes.INVALID_INPUT);
        });
    }

    @Test
    void shouldErrorIfNameIsMissing() throws Exception {
        // Given: the required argument, --name, was not given
        String[] argv = StringHelper.toArgV("--package acme.petstore --base-path /petstore");
        String errText = tapSystemErrAndOut(() -> {
            int rc = commandLine.execute(argv);
            assertThat(rc).isEqualTo(ExitCodes.INVALID_INPUT);
        });
    }

    @Test
    void shouldErrorIfPackageIsMissing() throws Exception {
        // Given: the required argument, --package, was not given
        String[] argv = StringHelper.toArgV("--name petstore --base-path /petstore");
        String errText = tapSystemErrAndOut(() -> {
            int rc = commandLine.execute(argv);
            assertThat(rc).isEqualTo(ExitCodes.INVALID_INPUT);
        });
    }

    /**
     * --base-path is not a required parameter, so leaving it off the command line should be accepted
     */
    @Test
    void shouldAcceptMissingBasePathOption() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    /**
     * Verify the --group option is recognized
     */
    @Test
    void shouldAcceptGroupOption() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore --group org.acme.petstore");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptPostgresSupportOption() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore --group org.acme.petstore --support postgres");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptLiquibaseSupportOption() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore --group org.acme.petstore --support liquibase");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptTestContainersSupportOption() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore --group org.acme.petstore --support testcontainers");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    @Test
    void shouldAcceptMultipleSupportOption() {
        String[] argv = StringHelper.toArgV("--name petstore --package acme.petstore --group org.acme.petstore --support postgres testcontainers");
        int rc = commandLine.execute(argv);
        assertThat(rc).isEqualTo(ExitCodes.OK);
    }

    
    /**
     * A fake code generator suitable for testing
     */

    public static class FakeCodeGenerator implements ICodeGenerator<RestProjectDescriptor> {
        public void setDescriptor(Descriptor ignored) {
            // empty
        }

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
    }
}
