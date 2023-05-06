package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "cp", separator = " ", header = "Copy file to/from ZooKeeper", description = "Copy file", footer = "\nOne of the source or destination files must be prefixed by 'zk:' for this command. You can explicitly include that prefix on either or both files.")
public class ZkCpCommand implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Option(names = { "-z", "--zkhost" }, description = "ZK connection string", required = true)
	String zkHost;
	@Option(names = { "-r", "--recursive" }, description = "Recursively copy a directory")
	boolean recursive;
	@Parameters(index = "0", description = "source file")
	String sourceFile;
	@Parameters(index = "1", description = "destination file")
	String destFile;

	@Override
	public void run() {
		log.info("Starting zk cp command");
		// TODO: Validate zk: prefix usage.
	}
}
