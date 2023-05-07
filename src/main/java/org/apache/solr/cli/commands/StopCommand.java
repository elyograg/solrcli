package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.StaticStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.ArgGroup;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "stop", header = "Stop Solr", description = "Stops one or more Solr instances.")
public final class StopCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @ArgGroup(multiplicity = "1")
  private static StopPorts stopPorts;

  private static final class StopPorts {
    @Option(names = {
        "-all", }, arity = "0", description = "Find and stop all running Solr servers on this host.")
    boolean stopAll;

    @Option(names = { "-p",
        "--port" }, arity = "1", description = "Specify the port the Solr HTTP listener is bound to.")
    String port;
  }

  @Option(names = { "-k",
      "--stop-key" }, arity = "1", description = "Stop key; default is 'SolrRocks' or what's defined in the config.")
  String stopKey;

  @Override
  public void run() {
    stopKey = StaticStuff.getProperty(StaticStuff.SYSPROP_STOPKEY);
    log.info("Beginning start command.");

    // TODO: implement!
  }
}
