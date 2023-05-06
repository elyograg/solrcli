package org.apache.solr.cli;

import java.lang.invoke.MethodHandles;
import java.security.InvalidParameterException;

import org.apache.solr.cli.commands.InfoCommand;
import org.apache.solr.cli.commands.StartCommand;
import org.apache.solr.cli.commands.StopCommand;
import org.apache.solr.cli.commands.ZkCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Help;
import picocli.CommandLine.Help.ColorScheme;
import picocli.CommandLine.IHelpFactory;
import picocli.CommandLine.Model.ArgSpec;
import picocli.CommandLine.Model.CommandSpec;
import picocli.CommandLine.Option;
import picocli.CommandLine.ScopeType;

@Command(name = "solr", separator = " ", scope = ScopeType.INHERIT, header = "Solr Control Program", description = "A program that controls Solr and related functionality.", version = "9.3.0", synopsisSubcommandLabel = "COMMAND", subcommands = {
    InfoCommand.class, ZkCommand.class, StartCommand.class, StopCommand.class })
public final class MainCommand {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static CommandLine cmdLine;

  @Option(names = { "-h",
      "--help" }, arity = "0", usageHelp = true, scope = ScopeType.INHERIT, description = "Display command usage.")
  boolean help;

  @Option(names = { "-config",
      "--config" }, arity = "1", scope = ScopeType.INHERIT, description = "The location of the cross-platform config file.")
  private static String configFile;

  @Option(names = { "-v", "--verbose", "--d",
      "--debug" }, arity = "0", scope = ScopeType.INHERIT, description = "Log all debug messages.")
  boolean verbose;

  public static final void main(final String[] args) {
    if (!MainConfig.validateConfig()) {
      final String msg = "Something is amiss in the sysProps or config.";
      log.error("", new InvalidParameterException(msg));
    }
    cmdLine = new CommandLine(new MainCommand());
    cmdLine.setHelpFactory(createCustomizedUsageHelp());
    cmdLine.execute(args);
  }

  public static final void exitProgram(final int... code) {
    int exitCode = 0;
    if (code == null || code.length > 0) {
      exitCode = code[0];
    }
    System.exit(exitCode);
  }

  /**
   * This method was obtained from the picocli project issue tracker. It causes
   * options in the automatically generated help/usage text to all be
   * left-aligned. Without this, it indents two-character options further than the
   * single-character options, which looks weird.
   *
   * @return a class that can be used by {@link CommandLine}.
   */
  private static final IHelpFactory createCustomizedUsageHelp() {
    return new IHelpFactory() {
      private static final int COLUMN_REQUIRED_OPTION_MARKER_WIDTH = 2;
      private static final int COLUMN_SHORT_OPTION_NAME_WIDTH = 2;
      private static final int COLUMN_OPTION_NAME_SEPARATOR_WIDTH = 2;
      private static final int COLUMN_LONG_OPTION_NAME_WIDTH = 22;

      private static final int INDEX_REQUIRED_OPTION_MARKER = 0;
      private static final int INDEX_SHORT_OPTION_NAME = 1;
      private static final int INDEX_OPTION_NAME_SEPARATOR = 2;
      private static final int INDEX_LONG_OPTION_NAME = 3;
      private static final int INDEX_OPTION_DESCRIPTION = 4;

      @Override
      public Help create(final CommandSpec commandSpec, final ColorScheme colorScheme) {
        return new Help(commandSpec, colorScheme) {
          @Override
          public Layout createDefaultLayout() {

            // The default layout creates a TextTable with 5 columns, as follows:
            // 0: empty text or (if configured) the requiredOptionMarker character
            // 1: short option name
            // 2: comma separator (if option has both short and long option)
            // 3: long option name(s)
            // 4: option description
            //
            // The code below creates a TextTable with 3 columns, as follows:
            // 0: empty text or (if configured) the requiredOptionMarker character
            // 1: all option names, comma-separated if necessary
            // 2: option description

            final int optionNamesColumnWidth = COLUMN_SHORT_OPTION_NAME_WIDTH
                + COLUMN_OPTION_NAME_SEPARATOR_WIDTH + COLUMN_LONG_OPTION_NAME_WIDTH;

            final TextTable table = TextTable.forColumnWidths(colorScheme,
                COLUMN_REQUIRED_OPTION_MARKER_WIDTH, optionNamesColumnWidth,
                commandSpec.usageMessage().width()
                    - (optionNamesColumnWidth + COLUMN_REQUIRED_OPTION_MARKER_WIDTH));
            final Layout result = new Layout(colorScheme, table, createDefaultOptionRenderer(),
                createDefaultParameterRenderer()) {
              @Override
              public void layout(final ArgSpec argSpec, final Ansi.Text[][] cellValues) {

                // The default option renderer produces 5 Text values for each option.
                // Below we combine the short option name, comma separator and long option name
                // into a single Text object, and we pass 3 Text values to the TextTable.
                for (final Ansi.Text[] original : cellValues) {
                  if (original[INDEX_OPTION_NAME_SEPARATOR].getCJKAdjustedLength() > 0) {
                    original[INDEX_OPTION_NAME_SEPARATOR] = original[INDEX_OPTION_NAME_SEPARATOR]
                        .concat(" ");
                  }
                  final Ansi.Text[] threeColumns = new Ansi.Text[] {
                      original[INDEX_REQUIRED_OPTION_MARKER],
                      original[INDEX_SHORT_OPTION_NAME]
                          .concat(original[INDEX_OPTION_NAME_SEPARATOR])
                          .concat(original[INDEX_LONG_OPTION_NAME]),
                      original[INDEX_OPTION_DESCRIPTION], };
                  table.addRowValues(threeColumns);
                }
              }
            };
            return result;
          }
        };
      }
    };
  }
}
