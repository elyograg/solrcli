package org.apache.solr.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "downconfig")
public class ZkDownConfigCommand implements Runnable {
    @Option(names = {"-o", "--output"}, description = "Output file")
    String outputFile;

    @Override
    public void run() {
        System.out.println("This is the zk downconfig command");
    }
}

