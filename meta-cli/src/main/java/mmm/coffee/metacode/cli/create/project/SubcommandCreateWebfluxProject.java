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
import mmm.coffee.metacode.annotations.guice.SpringWebFlux;
import mmm.coffee.metacode.cli.validation.PackageNameValidator;
import mmm.coffee.metacode.cli.validation.SpringIntegrationValidator;
import mmm.coffee.metacode.common.descriptor.Framework;
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
@SuppressWarnings({ "java:S1854", "java:S1841", "java:S125" } )
// S1854, S1841: allow  unused vars for now
// S125: allow comments that look like code
public class SubcommandCreateWebfluxProject extends AbstractCreateSpringProject {

    /**
     * Handle to the code generator
     */
    @SuppressWarnings("all") // false positive about setting this to final; doing so creates havoc with IoC injection
    private ICodeGenerator<RestProjectDescriptor> codeGenerator;

    /**
     * Construct instance with a given generator
     * @param codeGenerator the code generator used by this command
     */
    @Inject
    public SubcommandCreateWebfluxProject(@SpringWebFlux ICodeGenerator<RestProjectDescriptor> codeGenerator) {
        this.codeGenerator = codeGenerator;
    }

    /**
     * Lifecycle for PicoCLI commands
     * @return the exit code of the code generator
     */
    @Override public Integer call() {
        PackageNameValidator pnv = PackageNameValidator.of(packageName);
        SpringIntegrationValidator siv = SpringIntegrationValidator.of(features);
        super.runValidations(pnv, siv);
        var descriptor = buildProjectDescriptor(Framework.SPRING_WEBFLUX);
        return codeGenerator.doPreprocessing(descriptor).generateCode(descriptor);
    }
}