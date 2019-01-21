package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.FileManager;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class Maintenance_CMD implements Command {


    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("maintenance")) {
            if (args.length == 1) {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
            if (args[1].equalsIgnoreCase("add")) {
                if (args.length == 3) {
                    String username = args[2];
                    if(!FileManager.whitelist.getWhitelist().contains(username)) {
                        FileManager.whitelist.addUser(username);
                        System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "You have entered a new user in the whitelist!" + Main.ANSI_RESET);
                        System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "His name is" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + username + Main.ANSI_RESET);
                    } else {
                        System.out.println(Main.getPrefix() + "The username " + username + " is already exists in the whitelist.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    String username = args[2];
                    if(FileManager.whitelist.getWhitelist().contains(username)) {
                        FileManager.whitelist.removeUser(username);
                        System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "You have removed a user from the whitelist!" + Main.ANSI_RESET);
                        System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "His name was" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + username + Main.ANSI_RESET);
                    } else {
                        System.out.println(Main.getPrefix() + "The user " + username + " is not exists in the whitelist.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("list")) {
                if (args.length == 2) {
                    for(String name : FileManager.whitelist.getWhitelist()) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Whitelist" + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
                        System.out.println(Main.getPrefix() + "- " + Main.ANSI_GREEN + name + Main.ANSI_RESET);
                    }
                }
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "maintenance on" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "off" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "add(user)" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "remove(user)" + Main.ANSI_RESET;
    }
}
