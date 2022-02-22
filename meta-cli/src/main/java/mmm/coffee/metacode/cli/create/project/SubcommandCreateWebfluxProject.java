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
import mmm.coffee.metacode.annotations.guice.SpringWebFlux;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
import mmm.coffee.metacode.common.generator.ICodeGenerator;
import picocli.CommandLine;

/**
 * CLI to create a Spring Webflux project
 */
@CommandLine.Command(
        name="spring-webflux",
        mixinStandardHelpOptions = true,
        headerHeading = "%nSynopsis:%n",
        header = "  Generate a Spring Webflux project",
        descriptionHeading = "%nDescription:%n",
        description="  Creates a Spring project that leverages Spring Webflux",
        synopsisHeading = "%nUsage:%n",
        optionListHeading = "%nOptions:%n"
)
public class SubcommandCreateWebfluxProject extends AbstractCreateRestProject {

    /**
     * Handle to the code generator
     */
    private final ICodeGenerator codeGenerator;
    
    /**
     * Construct instance with a given generator
     * @param codeGenerator the code generator used by this command
     */
    @Inject
    public SubcommandCreateWebfluxProject(@SpringWebFlux ICodeGenerator codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    /**
     * Lifecycle for PicoCLI commands
     * @return the exit code of the code generator
     */
    @Override public Integer call() {
        super.validateInputs();
        // generator.setDescriptor()

        int rc = codeGenerator.generateCode();

        // Next step: build a ProjectDescriptor
        // Build CodeGenerator ( SpringWebFluxProjectGenerator (descriptor ) ).generate();
        // generator.getZipFile() 
        return rc;
    }
}