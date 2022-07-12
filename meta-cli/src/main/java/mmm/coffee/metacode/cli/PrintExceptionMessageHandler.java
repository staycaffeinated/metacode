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
package mmm.coffee.metacode.cli;

import mmm.coffee.metacode.common.exception.CreateEndpointUnsupportedException;
import picocli.CommandLine;

/**
 * This ExceptionHandler prints a custom error for application exceptions.
 * Exceptions that fall outside the scope of parameter checks and syntax checks
 * fall into the bucket of `execution exceptions` (picocli's term for this phenomena).
 * To print a meaningful message to the end-user, this exception handler is plugged into
 * the picocli CommandLine instance that's created when the application runs.
 * <p>
 * References:
 * <a href="https://picocli.info/apidocs/picocli/CommandLine.IExecutionExceptionHandler.html">IExecutionExceptionHandler</a>
 * <a href="https://picocli.info/#_business_logic_exceptions">Business Logic Exceptions</a>
 * </p>
 */
public class PrintExceptionMessageHandler implements CommandLine.IExecutionExceptionHandler {
    @Override
    public int handleExecutionException(Exception ex, CommandLine commandLine, CommandLine.ParseResult parseResult) throws Exception {
        if (ex instanceof CreateEndpointUnsupportedException) {
            commandLine.getErr().println("\nERROR:");
            commandLine.getErr().println("\tCreating endpoints is not supported by this project template.");
            commandLine.getErr().println("\tUse either the `spring-webflux` or `spring-webmvc` project template if you want to create endpoints.");
        }
        else {
            commandLine.getErr().println(commandLine.getColorScheme().errorText(ex.getMessage()));
        }
        return commandLine.getExitCodeExceptionMapper() != null ?
                commandLine.getExitCodeExceptionMapper().getExitCode(ex) :
                commandLine.getCommandSpec().exitCodeOnExecutionException();
    }
}
