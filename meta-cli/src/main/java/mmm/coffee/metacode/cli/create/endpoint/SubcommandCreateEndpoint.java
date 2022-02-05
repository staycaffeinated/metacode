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

import mmm.coffee.metacode.cli.mixin.DryRunOption;
import mmm.coffee.metacode.cli.validation.ResourceNameValidator;
import picocli.CommandLine;

import java.util.concurrent.Callable;

/**
 * Generates the boilerplate code for a REST endpoint
 */
@CommandLine.Command(
        name="endpoint",
        mixinStandardHelpOptions = true,
        headerHeading = "%nSynopsis:%n%n",
        header = "Generate the boilerplate code for a REST endpoint",
        descriptionHeading = "%nDescription:%n%n",
        description="Creates the basic code for a REST endpoint",
        synopsisHeading = "%nUsage:%n%n",
        optionListHeading = "%nOptions:%n%n"
)
public class SubcommandCreateEndpoint implements Callable<Integer> {

    @CommandLine.Spec
    CommandLine.Model.CommandSpec commandSpec;  // injected by picocli

     @CommandLine.Mixin
     DryRunOption dryRunOption;

    @CommandLine.Option(
            names={"-e", "--resource"},
            description="The resource (entity) available at this endpoint (e.g: --resource Coffee)",
            paramLabel = "ENTITY")
    String resourceName; // visible for testing

    @CommandLine.Option(
            names={"-p", "--route"},
            description="The route (path) to the resource (e.g: --route /coffee)",
            paramLabel = "ROUTE")
    String baseRoute; // visible for testing


    /* This is never called directly */
    @Override public Integer call() {
        validateInputs();

        // create an EndpointDescriptor from commandline & config file
        // create an endpointGenerator with the EndpointDescriptor
        return 0;
    }

    private void validateInputs() {
        if ( !ResourceNameValidator.isValid(resourceName)) {
            throw new CommandLine.ParameterException( commandSpec.commandLine(),
                    String.format("%nERROR: %n\tThe resource name '%s' cannot be used; it will not produce a legal Java class name", resourceName));
        }
    }
    /**
     * Side-bar task to look up the project's framework, since the framework
     * affects whether the code generator produces WebMVC or WebFlux controllers
     */
//    private SupportedFramework determineFramework(org.apache.commons.configuration2.Configuration configuration) {
//        var s = configuration.getString(ProjectKeys.FRAMEWORK);
//        return SupportedFramework.convert(s);
//    }
}
