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
package mmm.coffee.metacode.cli.mixin;

import mmm.coffee.metacode.common.annotations.Generated;
import picocli.CommandLine;

/**
 * This class offers a '--dry-run' option for picocli commands.
 *
 * This class is based on this example: https://picocli.info/#_mixins
 */
@CommandLine.Command(synopsisHeading = "%nUsage:%n%n",
        descriptionHeading   = "%nDescription:%n%n",
        parameterListHeading = "%nParameters:%n%n",
        optionListHeading    = "%nOptions:%n%n",
        commandListHeading   = "%nCommands:%n%n")
@Generated // jacoco should ignore this in code coverage reports
public class DryRunOption {
    // The map key for dry runs.
    // The _absence_ of the dryRun key presumes a live run is desired, not a dry run
    public static final String DRY_RUN_KEY = "dryRun";

    @CommandLine.Option(
            names = { "--dry-run" },
            description = "Walk through the code generation life-cycle without generating any code")
    protected boolean dryRun;

    public boolean isDryRun() { return dryRun; }
}
