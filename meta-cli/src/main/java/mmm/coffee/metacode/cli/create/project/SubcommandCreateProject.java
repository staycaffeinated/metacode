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

import mmm.coffee.metacode.annotations.jacoco.Generated;
import mmm.coffee.metacode.cli.traits.CallTrait;
import picocli.CommandLine;

/**
 * Generates the boilerplate code for a software project
 */
@CommandLine.Command(
        name="project",
        mixinStandardHelpOptions = true,
        headerHeading = "%nSynopsis:%n",
        // Add 2 leading spaces for consistency with picocolo default indentation
        header = "  Generates the basic project artifacts for an application. See the Commands listed below for the kinds of projects supported",
        descriptionHeading = "%nDescription:%n",
        description="Generates the basic project artifacts for an application",
        synopsisHeading = "%nUsage:%n",
        optionListHeading = "%nOptions:%n",
        subcommands = { SubcommandCreateWebfluxProject.class, SubcommandCreateWebMvcProject.class },
        commandListHeading = "%nTemplates:%n",
        synopsisSubcommandLabel = "[TEMPLATE]"
)
@Generated // exclude this from code coverage 
public class SubcommandCreateProject implements CallTrait {}
