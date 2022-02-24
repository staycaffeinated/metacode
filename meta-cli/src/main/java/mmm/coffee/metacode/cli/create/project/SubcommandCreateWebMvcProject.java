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
package mmm.coffee.metacode.cli.create.project;

import com.google.inject.Inject;
import mmm.coffee.metacode.annotations.guice.SpringWebMvc;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import mmm.coffee.metacode.spring.constant.WebMvcIntegration;
import picocli.CommandLine;

/**
 * CLI to create a Spring WebMVC project
 */
@CommandLine.Command(
        name="spring-webmvc",
        mixinStandardHelpOptions = true,
        headerHeading = "%nSynopsis:%n",
        header = "  Generate a Spring WebMVC project",
        descriptionHeading = "%nDescription:%n",
        description="  Creates a Spring project that leverages Spring WebMVC",
        synopsisHeading = "%nUsage:%n",
        optionListHeading = "%nOptions:%n"
)
@SuppressWarnings({ "java:S1854", "java:S1481" } ) // S1854, S1481: allow  unused vars for now
public class SubcommandCreateWebMvcProject extends AbstractCreateRestProject {

    //
    // The Support option. These are the added capabilities supported by the generated application.
    // I've been back and forth with what to name this flag.  Spring Initialzr has the notion of
    // 'dependencies', but all Spring Initializr does is add those dependencies to the gradle/maven build file.
    // Micronaut uses the term 'feature', but added libraries aren't exactly 'features' of the
    // generated application. The term 'trait' seems in line: for example, a 'postgres trait' indicates
    // the traits of a Postgres database are enabled in the application. Along that line, 'support'
    // also seems like a good term: the generated application will 'support' postgres or liquibase or CORS
    // or heath checks or whatever
    // First, we have to provide an Iterable<String> that provides the list of valid candidates
    //
    // The declaration the additional library options
    // For example, ```--add postgres liquibase```
    @CommandLine.Option(names={"-s", "--support"},
            arity="0..*",
            paramLabel = "LIBRARY",
            description = { "Add the support of additional libraries to the project. Currently supported libraries are: ${COMPLETION-CANDIDATES}." }
    )
    private WebMvcIntegration[] features;

    /**
     * Handle to the code generator
     */
    private ICodeGenerator<RestProjectDescriptor> codeGenerator;

    /**
     * Construct with the given code generator
     * @param codeGenerator the code generator to use
     */
    @Inject
    public SubcommandCreateWebMvcProject(@SpringWebMvc ICodeGenerator<RestProjectDescriptor> codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    /**
     * Entry point by PicoCLI
     * @return the exit code
     */
    @Override public Integer call() {
        super.validateInputs();
        var descriptor = buildProjectDescriptor();
        return codeGenerator.generateCode(descriptor);
    }

    /**
     * Creates a project descriptor from the command-line arguments
     * @return a POJO that encapsulates the command-line arguments
     */
    RestProjectDescriptor buildProjectDescriptor() {
        // Get the basic information
        var descriptor = SpringWebMvcProjectDescriptor
                .builder()
                .applicationName(applicationName)
                .basePackage(packageName)
                .basePath(basePath)
                .groupId(groupId)
                .build();

        // take note of any features/integrations
        if (features != null) {
            for (WebMvcIntegration f : features) {
                descriptor.getIntegrations().add(f.name());
            }
        }
        return descriptor;
    }
}