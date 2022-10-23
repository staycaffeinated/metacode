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

import picocli.CommandLine;
import picocli.CommandLine.Help.Column;
import picocli.CommandLine.Help.Column.Overflow;
import picocli.CommandLine.Help.TextTable;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Model.UsageMessageSpec;

/**
 * This customizes the help message. The default
 * help message is replaced with a list that displays
 * the immediate command and the full hierarchy of subcommands.
 */
public class CommandHelpRenderer implements CommandLine.IHelpSectionRenderer {
    @Override
    public String render(CommandLine.Help help) {
        CommandSpec spec = help.commandSpec();
        if (spec.subcommands().isEmpty()) { return ""; }

        // Prepare the layout in 2 columns
        // The left column overflows, the right column wraps if the
        // text is too long
        int leftWidth = 25;
        TextTable textTable = TextTable.forColumns(help.colorScheme(),
                new Column(leftWidth, 2, Overflow.SPAN),
                new Column(spec.usageMessage().width() - leftWidth, 0, Overflow.WRAP));
        textTable.setAdjustLineBreaksForWideCJKCharacters(spec.usageMessage().adjustLineBreaksForWideCJKCharacters());

        for (CommandLine subcommand : spec.subcommands().values()) {
            addHierarchy(subcommand, textTable, "");
        }
        return textTable.toString();
    }

    private void addHierarchy(CommandLine cmd, TextTable textTable, String indent) {
        // Create a comma-separated list of command names and aliases
        String names = cmd.getCommandSpec().names().toString();
        names = names.substring(1, names.length() - 1); // remove leading '[' and trailing ']'

        // Command description is taken from header or description
        String description = pullDescription(cmd.getCommandSpec().usageMessage());

        // Add a line for this command to the layout
        textTable.addRowValues(indent + names, description);

        // Add its subcommands, if any
        for (CommandLine sub : cmd.getSubcommands().values()) {
            addHierarchy(sub, textTable, indent + "  "); // add a 2-space indent to left column
        }
    }

    private String pullDescription(UsageMessageSpec usageMessage) {
        if (usageMessage.header().length > 0) {
            return usageMessage.header()[0];
        }
        if (usageMessage.description().length > 0) {
            return usageMessage.description()[0];
        }
        return "";
    }
}
