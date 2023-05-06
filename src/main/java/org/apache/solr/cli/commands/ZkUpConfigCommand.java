package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "upconfig", separator = " ", header = "Upload collection config to ZK", description = "Upload collection config")
public class ZkUpConfigCommand implements Runnable {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Option(names = { "-z", "--zkhost" }, description = "ZK connection string", required = true)
  String zkHost;
  @Option(names = { "-d", "--directory" }, description = "Local directory to copy config from")
  String localDir;
  @Option(names = { "-n", "--name" }, description = "The name of the config in ZK")
  String configName;

  @Override
  public void run() {
    System.out.println("This is the zk upconfig command");
  }
}
