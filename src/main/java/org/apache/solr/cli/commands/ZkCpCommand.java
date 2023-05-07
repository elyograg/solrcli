package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.StaticStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "cp", header = "Copy file to/from ZooKeeper", description = "Copy file", footer = "\nOne of the source or destination files must be prefixed by 'zk:' for this command. You can explicitly include that prefix on either or both files."
    + StaticStuff.USAGE_OPTION_SEPARATOR_TEXT)
public class ZkCpCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Parameters(index = "0", description = "source file")
  String sourceFile;
  @Parameters(index = "1", description = "destination file")
  String destFile;
  @Option(names = { "-r", "--recursive" }, description = "Recursively copy a directory")
  boolean recursive;
  @Option(names = { "-z", "--zkhost" }, description = "ZK connection string", required = true)
  String zkHost;

  @Override
  public void run() {
    log.info("Starting zk cp command");
    // TODO: Validate zk: prefix usage.
  }
}
