package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base;
import me.Spleevtv.NoCloud.Core.Utils.BaseList;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.FileManager;
import me.Spleevtv.NoCloud.Main;

public class Base_CMD implements Command {


    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("base")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
            if(args[1].equalsIgnoreCase("create")) {
                if(args.length == 4) {
                    String basename = args[2];
                    String baseport = args[3];
                    if(!Init.baselist.getBaselist().containsKey(basename)) {
                        Init.baselist.createBase(basename, baseport);
                        System.out.println(Main.getPrefix() + "The base '" + basename + "' is now created.");
                    } else {
                        System.out.println(Main.getPrefix() + "This base is already registered!");
                    }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: base create <name> <port> | Create a new base");
                }
            } else if(args[1].equalsIgnoreCase("list")) {
                if(args.length == 2) {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "Available bases" + Main.ANSI_RESET + Main.ANSI_WHITE + ":");
                    for(Base base : Init.baselist.getBaselist().values()) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Base" + Main.ANSI_RESET + Main.ANSI_WHITE + ": " + Main.ANSI_RESET + Main.ANSI_GREEN + base.getName() + Main.ANSI_RESET + Main.ANSI_WHITE +  " (" + Main.ANSI_RESET + Main.ANSI_RED + "Port" + Main.ANSI_RESET + Main.ANSI_WHITE + ": " + Main.ANSI_RESET + Main.ANSI_GREEN + base.getPort() + Main.ANSI_RESET + Main.ANSI_WHITE + ")");
                     }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: base create <name> <port> | List all bases");
                }
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "base create" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "list" + Main.ANSI_RESET;
    }
}
