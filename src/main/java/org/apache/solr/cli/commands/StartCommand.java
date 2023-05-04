package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "start")
public class StartCommand implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	@Option(names = { "-c", "--cloud" }, description = "Run Solr in SolrCloud mode")
	boolean cloud;

	@Option(names = { "-h", "--heap" }, description = "Solr Heap Size (values like 4g)")
	String heap = CLIConstants.DEFAULT_SOLR_HEAP;

	@Option(names = { "-p", "--listen-port" }, description = "Solr Listen Port")
	int listenPort = CLIConstants.DEFAULT_SOLR_PORT;

	@Option(names = { "-sp", "--stop-port" }, description = "Solr Stop Port")
	int stopPort = Integer.MIN_VALUE;

	@Option(names = { "-zp", "--zk-port", "--zookeeper-port" }, description = "Embedded ZooKeeper Port")
	int zkPort = Integer.MIN_VALUE;

	@Option(names = { "-z", "--zkhost", "--zkHost" }, description = "ZK connection string, can specify multiple hosts")
	String zkHost;

	@Option(names = { "-D" }, description = "System Properties")
	List<String> propList;

	@Parameters(index = "0..*")
	List<String> argList;

	@Override
	public void run() {
		log.warn("zkhost {}", zkHost);
		if (zkHost != null && ! zkHost.equals("")) {
			cloud = true;
		}

		log.info("Beginning start command.");
		log.info("Listen Port {}", listenPort);
		if (stopPort == Integer.MIN_VALUE) {
			stopPort = listenPort - 1000;
		}
		log.info("Stop Port {}", stopPort);
		if (cloud) {
			log.info("Start in SolrCloud mode");
			if (zkHost == null || zkHost.equals("")) {
				if (zkPort == Integer.MIN_VALUE) {
					zkPort = listenPort + 1000;
				}
				log.info("Embedded ZK port {}", zkPort);
			} else {
				log.info("ZKHost '{}'", zkHost);
			}
		}
		Properties props = new Properties();
		if (propList != null) {
			for (String p : propList) {
				String[] splitArray = p.split("=");
				String prop = splitArray[0];
				String value = splitArray[1];
				props.put(prop, value);
				log.info("sysProp {}", p);
			}
		}
	}
}
