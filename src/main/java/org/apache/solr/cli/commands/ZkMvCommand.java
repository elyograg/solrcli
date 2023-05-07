package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.StaticStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "mv", header = "Move/Rename a file within ZK", description = "Move file in ZK", footer = "\nBoth source and desintation paths will be treated as if they have the 'zk:' prefix.  You can explicitly add that prefix if you wish."
    + StaticStuff.OPTION_SEPARATOR_USAGE_TEXT)
public class ZkMvCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Option(names = { "-z", "--zkhost" }, description = "ZK connection string", required = true)
  String zkHost;
  @Parameters(index = "0", description = "source file")
  String sourceFile;
  @Parameters(index = "1", description = "destination file")
  String destFile;

  @Override
  public void run() {
    log.info("Starting zk mv command");
    // TODO: Validate zk: prefix usage.
  }
}
