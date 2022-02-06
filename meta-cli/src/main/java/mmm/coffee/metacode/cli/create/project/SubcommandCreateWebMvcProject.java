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

import com.google.inject.Inject;
import mmm.coffee.metacode.annotations.SpringWebMvc;
import mmm.coffee.metacode.cli.ExitCodes;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
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
    private final ICodeGenerator codeGenerator;

    /**
     * Construct with the given code generator
     * @param codeGenerator the code generator to use
     */
    @Inject
    public SubcommandCreateWebMvcProject(@SpringWebMvc ICodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    /**
     * Entry point by PicoCLI
     * @return the exit code
     */
    @Override public Integer call() {
        super.validateInputs();
        IRestProjectDescriptor projectDescriptor = buildProjectDescriptor();

        codeGenerator.generateCode();

        // Build a CodeGenerator - execute ( projectDescriptor )
        return ExitCodes.OK;
    }

    /**
     * Creates a project descriptor from the command-line arguments
     * @return a POJO that encapsulates the command-line arguments
     */
    IRestProjectDescriptor buildProjectDescriptor() {
        SpringWebMvcProjectDescriptor descriptor = new SpringWebMvcProjectDescriptor();
        descriptor.setApplicationName(applicationName);
        descriptor.setBasePackage(packageName);
        descriptor.setBasePath(basePath);
        descriptor.setGroupId(groupId);
        if (features != null) {
            for (WebMvcIntegration f : features) {
                descriptor.getIntegrations().add(f);
            }
        }
        return descriptor;
    }
}