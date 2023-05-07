package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;

@Command(name = "zk", description = "A tool for manipulating ZK for Solr", synopsisSubcommandLabel = "COMMAND", subcommands = {
    ZkCpCommand.class, ZkRmCommand.class, ZkMvCommand.class, ZkUpConfigCommand.class,
    ZkDownConfigCommand.class })
public class ZkCommand {
  @SuppressWarnings("unused")
  private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
}
