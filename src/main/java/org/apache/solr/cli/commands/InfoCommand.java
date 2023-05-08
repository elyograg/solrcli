package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;
import java.util.TreeSet;

import org.apache.solr.cli.StaticStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "info", description = "Obtain information about the Solr install and exit.")
public class InfoCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @ArgGroup(multiplicity = "1")
  private static InfoArgs infoArgs;

  private static final class InfoArgs {
    @Option(names = { "-ld",
        "--logdir" }, description = "Print logging directory and exit.    Default '${DEFAULT-VALUE}'")
    private static boolean getLogDir;

    @Option(names = { "-props",
        "--props" }, description = "Print all Java System Properties and exit.  Default '${DEFAULT-VALUE}'")
    private static boolean printSysProps;

    @Option(names = { "-env",
        "--env" }, description = "Print all environment variables and exit.  Default '${DEFAULT-VALUE}'")
    private static boolean printEnv;
  }

  @Override
  public void run() {
    log.info("Starting info command");

    if (InfoArgs.printSysProps) {
      final TreeSet<Object> sortedProps = new TreeSet<>();
      sortedProps.addAll(System.getProperties().keySet());
      log.info("All system properties (values wrapped in braces):\n");
      for (final Object o : sortedProps) {
        final String prop = (String) o;
        log.info("{}: {{}}", prop, System.getProperty(prop));
      }
      StaticStuff.exitProgram();
    }

    if (InfoArgs.printEnv) {
      final TreeSet<String> sortedEnv = new TreeSet<>();
      sortedEnv.addAll(System.getenv().keySet());
      log.info("All environment variables (values wrapped in braces):\n");
      for (final String env : sortedEnv) {
        log.info("{}: {{}}", env, System.getenv(env));
      }
      StaticStuff.exitProgram();
    }

    if (InfoArgs.getLogDir) {
      // TODO: Actually print configured log directory, not this placeholder.
      System.out.println("/tmp/cli_log");
      StaticStuff.exitProgram();
    }
  }
}
