package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;
import java.util.concurrent.atomic.AtomicBoolean;

import org.apache.solr.cli.MainCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "info")
public class InfoCommand implements Runnable {
	@SuppressWarnings("unused")
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
	private static final AtomicBoolean verbose = new AtomicBoolean(false);

//	@Option(names = { "-h", "--help" }, description = "Print usage")
//	private static boolean help;

	@Option(names = { "-d", "--debug", "-v", "--verbose" }, description = "Debug/Verbose mode")
	private static boolean verboseFlag;

	@Option(names = { "--exit" }, description = "Immediately exit.  Mostly used to ensure the application can run.")
	private static boolean exitFlag;

	@Option(names = { "--logdir" }, description = "get logging directory")
	private static boolean getLogDir;

	@Override
	public void run() {
		verbose.set(verboseFlag);

		if (exitFlag) {
			MainCommand.halt();
		}

		if (getLogDir) {
			// TODO: Once the init() function is done, actually return
			// configured log dir.
			System.out.println("/tmp/cli_log");
			MainCommand.halt();
		}

		log.info("Starting info command");
	}
}
