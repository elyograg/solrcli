package org.apache.solr.cli;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.commands.InfoCommand;
import org.apache.solr.cli.commands.StartCommand;
import org.apache.solr.cli.commands.StopCommand;
import org.apache.solr.cli.commands.ZkCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParameterException;
import picocli.CommandLine.ParseResult;
import picocli.CommandLine.ScopeType;

/** The main command class. */
@Command(name = "solr", sortOptions = false, version = StaticStuff.SOLR_VERSION, scope = ScopeType.INHERIT, description = "A script that controls Solr and related functionality.", synopsisSubcommandLabel = "COMMAND", subcommands = {
    StartCommand.class, StopCommand.class, ZkCommand.class,
    InfoCommand.class }, footer = StaticStuff.USAGE_OPTION_SEPARATOR_TEXT)
public final class MainCommand {

  /** Logger object. */
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  /** Help option. */
  @Option(names = { "-h",
      "--help" }, arity = "0", usageHelp = true, scope = ScopeType.INHERIT, description = "Display this command usage.")
  private static boolean help;

  /** Configuration file option. */
  @Option(names = { "-cfg", "-config",
      "--config" }, arity = "1", scope = ScopeType.INHERIT, description = "The location of the cross-platform config file.  If not present, program will search predefined paths for the file.")
  private static String configFile = null;

  /** Debug option. */
  @Option(names = { "-v", "--verbose",
      "--debug" }, arity = "0", scope = ScopeType.INHERIT, description = "Log any available debug messages.  Some commands may not have any debug logging defined.  Default '${DEFAULT-VALUE}'")
  private static boolean verbose;

  /** Service name option. */
  @Option(names = { "-sn",
      "--service-name" }, arity = "1", scope = ScopeType.INHERIT, defaultValue = "solr", description = "The name of the service.  Default '${DEFAULT-VALUE}'")
  private static String serviceName = null;

  /** Debug option. */
  @Option(names = { "-f",
      "--force" }, arity = "0", scope = ScopeType.INHERIT, description = "Force running as root, and as a side effect, allow binding to ports below 1024.  Default '${DEFAULT-VALUE}'")
  private static boolean force;

  /**
   * The main method.
   *
   * @param args command-line arguments
   */
  public static final void main(final String[] args) {
    final MainCommand app = new MainCommand();
    new CommandLine(app).setHelpFactory(StaticStuff.createLeftAlignedUsageHelp())
        .setExecutionStrategy(app::executionStrategy).execute(args);
  }

  /**
   * Do custom validation and enable logging here.
   * 
   * @param parseResult
   * @return
   */
  private final int executionStrategy(final ParseResult parseResult) {
    StaticStuff.setVerboseFlag(verbose);
    StaticStuff.setForceFlag(force);
    StaticStuff.setServiceName(serviceName);
    if (!StaticStuff.parseAndValidateConfig(configFile)) {
      throw new ParameterException(parseResult.commandSpec().commandLine(),
          String.format("Something is amiss with the config."));
    }

    return new CommandLine.RunLast().execute(parseResult); // default execution strategy
  }
}
