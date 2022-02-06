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

import mmm.coffee.metacode.cli.ExitCodes;
import mmm.coffee.metacode.cli.StringHelper;
import mmm.coffee.metacode.common.descriptor.Descriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import picocli.CommandLine;

import static com.github.stefanbirkner.systemlambda.SystemLambda.tapSystemErrAndOut;
import static com.google.common.truth.Truth.assertThat;

/**
 * Unit tests
 */
class SubcommandCreateSpringWebFluxProjectTests {
    
    CommandLine commandLine;

    /**
     * Set up
     */
    @BeforeEach
    public void setUp() {
        SubcommandCreateWebfluxProject createProjectCommand = new SubcommandCreateWebfluxProject(new FakeCodeGenerator());
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
        // todo: wire up capturing stderr & stdout to reduce noise to console
        String[] argv = StringHelper.toArgV("--name petstore --package 123.#4$abc --base-path /petstore");
        String errText = tapSystemErrAndOut(() -> {
            int rc = commandLine.execute(argv);
            assertThat(rc).isEqualTo(ExitCodes.INVALID_INPUT);
        });
    }

    /**
     * A fake code generator suitable for testing
     */
    public static class FakeCodeGenerator implements ICodeGenerator {

        @Override
        public void setDescriptor(Descriptor descriptor) {

        }

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
    }
}
