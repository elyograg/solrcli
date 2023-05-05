package org.apache.solr.cli.commands;

import java.lang.invoke.MethodHandles;
import java.util.List;
import java.util.Properties;

import org.apache.solr.cli.MainCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "start")
public class StartCommand implements Runnable {
	private static final Logger log = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

	private static enum GcType {
		g1, zgc
	};

	@Option(names = { "-c",
			"--cloud" }, arity = "0", description = "Run Solr in SolrCloud mode.  If this option is present but -z is not present, Solr will start an embedded ZK server.")
	boolean cloud;

	@Option(names = { "-f",
			"--force" }, arity = "0", description = "Force Starting Solr as root and accepting port numbers below 1024.  Not recommended.")
	boolean forceRoot;

	@Option(names = { "-m", "--mem", "--memory",
			"--heap" }, arity = "1", defaultValue = "512m", description = "Solr Heap Size (values like 4g).")
	String heap;

	@Option(names = { "-gc", "--gc" }, arity = "1", defaultValue = "g1", description = "Garbage collector: g1 or zfc")
	GcType gc;

	@Option(names = { "-p",
			"--listen-port" }, arity = "1", defaultValue = "8983", description = "Solr Listen Port.  Default is 8983.")
	int listenPort;

	@Option(names = { "-sp", "--sp",
			"--stop-port" }, arity = "1", description = "Solr Stop Port.  Default is listen port minus 1000")
	int stopPort = Integer.MIN_VALUE;

	@Option(names = { "-zp", "--zp", "--zk-port",
			"--zookeeper-port" }, arity = "1", description = "Embedded ZooKeeper Port.  Default is listen port plus 1000.  Not used if not in cloud mode or -z option specified.")
	int zkPort = Integer.MIN_VALUE;

	@Option(names = { "-z", "--zkhost",
			"--zkHost" }, arity = "1", description = "ZK connection string.  See ZK docs for how to specify multiple hosts and a chroot.  Using this option forces cloud mode.")
	String zkHost;

	@Option(names = {
			"-D" }, arity = "1", description = "System Property.  Can be specified multiple times.  Correct syntax is -Dprop=\"properties value\" with quotes required for spaces or other special characters.")
	List<String> properties;

	@Override
	public void run() {
		log.info("Beginning start command.");

		if (zkHost != null && !zkHost.equals("")) {
			cloud = true;
		}

		log.info("Heap {}", heap);
		log.info("GC {}", gc);
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
			} else {
				zkPort = 0;
				log.info("ZKHost '{}'", zkHost);
			}
		} else {
			zkPort = 0;
		}

		log.info("listenPort {}, stopPort {}, zkPort {}", listenPort, stopPort, zkPort);

		int startPort = 1024;
		if (forceRoot) {
			log.warn("Forcing acceptance of low port numbers!");
			startPort = 1;
		}

		if (stopPort < startPort || stopPort > 65535 || listenPort < startPort || listenPort > 65535
				|| (zkPort != 0 && (zkPort < startPort || zkPort > 65535))) {
			log.error("One of the ports is outside the valid range of 1024 to 65535");
			MainCommand.exit(1);
		}

		Properties props = new Properties();
		if (properties != null) {
			for (String p : properties) {
				String[] split = p.split("=");
				String prop = split[0];
				String value = split[1];
				props.put(prop, value);
				log.info("sysProp {}", p);
			}
		}
	}
}
