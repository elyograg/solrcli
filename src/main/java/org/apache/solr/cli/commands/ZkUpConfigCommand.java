package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "upconfig", description = "Upload collection config to ZK")
public class ZkUpConfigCommand implements Runnable {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  @Option(names = { "-d", "--dir",
      "--directory" }, required = true, description = "Local directory to copy config from")
  String localDir;
  @Option(names = { "-n", "--name" }, required = true, description = "The name of the config in ZK")
  String configName;
  @Option(names = { "-z", "--zkhost" }, required = true, description = "ZK connection string")
  String zkHost;

  @Override
  public void run() {
    System.out.println("This is the zk upconfig command");
  }
}
