package org.apache.solr.cli.commands;

import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

@Command(name = "upconfig")
public class ZkUpConfigCommand implements Runnable {
    @Option(names = {"-o", "--output"}, description = "Output file")
    String outputFile;

    @Override
    public void run() {
        System.out.println("This is the zk upconfig command");
    }
}

