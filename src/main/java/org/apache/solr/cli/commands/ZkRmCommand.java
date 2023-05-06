package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "rm", separator = " ", header = "Delete a file ZooKeeper", description = "Delete file")
public class ZkRmCommand implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Option(names = { "-z", "--zkhost" }, description = "ZK connection string", required = true)
	String zkHost;
	@Option(names = { "-r", "--recursive" }, description = "Recursively delete a directory")
	boolean recursive;
	@Parameters(index = "0", description = "File to delete.  Example: zk:/security.json")
    String deleteFile;

	@Override
	public void run() {
		log.info("Starting zk rm command");
		// TODO: Validate zk: prefix usage.
	}
}
