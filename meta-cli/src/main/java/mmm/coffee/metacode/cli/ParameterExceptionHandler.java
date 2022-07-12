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

import mmm.coffee.metacode.annotations.jacoco.Generated;
import picocli.CommandLine;

/**
 * Handles parse exceptions. The PicoCLI's default behavior is
 * overridden to suppress printing the usage text. PicoCLI's
 * inclination is to print the help text when a user enters a bad command.
 * IMHO, the error message gets lost with all that other output,
 * so this handler is introduced to enable printing the error w/o
 * printing the usage/help text.
 * <p>
 * References:
 * https://github.com/remkop/picocli/blob/main/picocli-examples/src/main/java/picocli/examples/exceptionhandler/ParameterExceptionHandlerDemo.java
 * </p>
 */
@Generated // suppress code coverage analytics for this class
public class ParameterExceptionHandler implements CommandLine.IParameterExceptionHandler {

    /**
     * By default, Picocli will print the exception's error message, then print the usage/help
     * message.  In the interest of keeping the error text to a minimum, this custom handler is used,
     * which enables us to present as little or as much information as we want. 
     */
    @Override
    public int handleParseException(CommandLine.ParameterException ex, String[] args) throws Exception {
        CommandLine cmd = ex.getCommandLine();
        cmd.getErr().println(ex.getMessage());
        return cmd.getCommandSpec().exitCodeOnInvalidInput();
    }
}
