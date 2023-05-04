package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "start")
public class StartCommand implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Option(names = { "-p", "--port" }, description = "Solr Listen Port")
	int port = CLIConstants.DEFAULT_SOLR_PORT;

	// Some debugging stuff
	/*
	@Option(names = { "-D" }, description = "System Properties")
	List<String> propList;

	@Parameters(index = "0..*")
	List<String> argList;
	*/

	@Override
	public void run() {
		log.info("Beginning start command.");
		System.out.println(port);
	}
}
