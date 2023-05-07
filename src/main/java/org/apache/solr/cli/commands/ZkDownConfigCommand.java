package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "downconfig", header = "Download collection config from ZK", description = "Download collection config")
public class ZkDownConfigCommand implements Runnable {
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Option(names = { "-z", "--zkhost" }, description = "ZK connection string", required = true)
  String zkHost;
  @Option(names = { "-d", "--directory" }, description = "Local directory to copy config to")
  String localDir;
  @Option(names = { "-n", "--name" }, description = "The name of the config in ZK")
  String configName;

  @Override
  public void run() {
    log.info("Starting zk downconfig command");
  }
}
