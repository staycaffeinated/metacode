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
import mmm.coffee.metacode.common.descriptor.Framework;
import mmm.coffee.metacode.common.descriptor.RestProjectDescriptor;
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
@SuppressWarnings({ "java:S1854", "java:S1481" } ) // S1854, S1481: allow  unused vars for now
public class SubcommandCreateWebMvcProject extends AbstractCreateSpringProject {
    
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
        var descriptor = buildProjectDescriptor(Framework.SPRING_WEBMVC);
        return codeGenerator.doPreprocessing(descriptor).generateCode(descriptor);
    }
}