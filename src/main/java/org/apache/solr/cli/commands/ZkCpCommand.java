package org.apache.solr.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

@Command(name = "cp")
public class ZkCpCommand implements Runnable {
    @Option(names = {"-z", "--zkhost"}, description = "ZK Host string")
    String zkHost;
    @Parameters(index = "0", description = "source file")
    String sourceFile;
    @Parameters(index = "1", description = "destinationfile")
    String destFile;

    @Override
    public void run() {
        System.out.println("This is zk subcommand cp");
    }
}
