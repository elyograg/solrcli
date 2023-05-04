package org.apache.solr.cli;

import java.lang.invoke.MethodHandles;

import org.apache.solr.cli.commands.InfoCommand;
import org.apache.solr.cli.commands.StartCommand;
import org.apache.solr.cli.commands.ZkCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine;
import picocli.CommandLine.Command;

@Command(name = "solr", subcommands = { InfoCommand.class, ZkCommand.class, StartCommand.class })
public class MainCommand {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static CommandLine cmdLine;

	public static CommandLine getCmdLine() {
		return cmdLine;
	}

	// TODO: Make sure this is the right place for this.
	// Add data structure to hold parsed config.
	public static synchronized void init() {
		// TODO Parse the config file to get startup options for Solr
	}

	public static void halt(int... code) {
		int exitCode = 0;
		if (code.length > 0) {
			exitCode = code[0];
		}
		Runtime.getRuntime().halt(exitCode);
	}

	public static void main(String[] args) {
		cmdLine = new CommandLine(new MainCommand());
		cmdLine.execute(args);
	}
}
