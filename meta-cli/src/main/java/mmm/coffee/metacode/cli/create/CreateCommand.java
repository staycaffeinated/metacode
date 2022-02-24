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
package mmm.coffee.metacode.cli.create;

import mmm.coffee.metacode.cli.create.endpoint.SubcommandCreateEndpoint;
import mmm.coffee.metacode.cli.create.project.SubcommandCreateProject;
import mmm.coffee.metacode.cli.traits.CallTrait;
import picocli.CommandLine;

/**
 * Implements the 'create' command
 */
@CommandLine.Command(
        name="create",
        descriptionHeading = "%nDescription:%n",
        description="Create a project or other artifact",
        mixinStandardHelpOptions = true,
        commandListHeading = "%nCommands:%n",
        optionListHeading = "%nOptions:%n",
        subcommands = { SubcommandCreateProject.class, SubcommandCreateEndpoint.class }
)
public class CreateCommand implements CallTrait {}