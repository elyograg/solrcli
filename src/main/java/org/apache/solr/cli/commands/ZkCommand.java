package org.apache.solr.cli.commands;

import picocli.CommandLine.Command;

@Command(name = "zk", subcommands = {
        ZkCpCommand.class,
        ZkUpConfigCommand.class,
        ZkDownConfigCommand.class
})
public class ZkCommand implements Runnable {
    @Override
    public void run() {
        System.out.println("This is command 1");
    }
}
