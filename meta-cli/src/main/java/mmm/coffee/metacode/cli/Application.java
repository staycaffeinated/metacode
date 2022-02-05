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

import mmm.coffee.metacode.cli.create.CreateCommand;
import mmm.coffee.metacode.cli.traits.CallTrait;
import mmm.coffee.metacode.common.annotations.Generated;
import picocli.AutoComplete.GenerateCompletion;
import picocli.CommandLine;

/**
 * Main application entry point
 */
/**
 * The main application for the code generator
 */
@CommandLine.Command(
        name="metacode",
        description="A code generator",
        version = "0.1.0",
        mixinStandardHelpOptions = true,
        subcommands = { GenerateCompletion.class, CreateCommand.class }

)
@Generated // exclude this class from code coverage
public class Application implements CallTrait {
    
    private static int exitCode;

    /**
     * Main
     * @param args command line args
     */
    public static void main(String[] args) {
        exitCode = new CommandLine(new Application())
                .setUsageHelpAutoWidth(true) // take advantage of wide terminals when available
                .execute(args);
    }

    public static int getExitCode() { return exitCode; }
}
