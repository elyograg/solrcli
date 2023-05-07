package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.StaticStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "info", header = "Information tool", description = "Obtain information about the Solr install and exit quickly.")
public class InfoCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @ArgGroup(multiplicity = "1")
  private static InfoArgs infoArgs;

  private static final class InfoArgs {
    @Option(names = { "-x",
        "--exit" }, description = "Immediately exit.  Mostly used to ensure the application can run.")
    private static boolean exitFlag;

    @Option(names = { "-ld",
        "--logdir" }, description = "Print logging directory to stdout and exit.")
    private static boolean getLogDir;
  }

  @Override
  public void run() {

    if (InfoArgs.exitFlag) {
      log.warn("Exiting program as requested");
      StaticStuff.exitProgram();
    }

    if (InfoArgs.getLogDir) {
      // TODO: Actually print configured log directory, not this placeholder.
      System.out.println("/tmp/cli_log");
      StaticStuff.exitProgram();
    }

    log.info("Starting info command");
  }
}
