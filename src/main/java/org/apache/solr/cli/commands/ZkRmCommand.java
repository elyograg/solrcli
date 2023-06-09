package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.StaticStuff;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "rm", description = "Delete file from zookeeper,", footer = "\nThe deleteFile argument will be treated as if it has the 'zk:' prefix.  You can explicitly add that prefix if you wish."
    + StaticStuff.USAGE_OPTION_SEPARATOR_TEXT)
public class ZkRmCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Parameters(index = "0", description = "File to delete.  Example: zk:/security.json")
  String deleteFile;
  @Option(names = { "-r", "--recursive" }, description = "Recursively delete a directory.  Default '${DEFAULT-VALUE}'")
  boolean recursive;
 @Option(names = { "-z", "--zkhost" }, required = true, description = "ZK connection string")
  String zkHost;

  @Override
  public void run() {
    log.info("Starting zk rm command");
    // TODO: Validate zk: prefix usage.
  }
}
