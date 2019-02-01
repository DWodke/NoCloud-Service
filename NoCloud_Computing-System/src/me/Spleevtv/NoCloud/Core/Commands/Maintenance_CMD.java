package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.FileManager;
import me.Spleevtv.NoCloud.Core.Utils.UUIDFetcher;
import me.Spleevtv.NoCloud.Main;

import java.util.UUID;

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
                    UUID uuid = UUIDFetcher.getUUID(username);
                    if(uuid != null) {
                        if(!FileManager.whitelist.getWhitelist().contains(username + ";" + uuid.toString())) {
                            FileManager.whitelist.addUser(username + ";" + uuid.toString());
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "You have entered a new user in the whitelist!" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "His name is" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + username + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "His uuid is" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + uuid.toString() + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + "The user " + username + " is already exists in the whitelist.");
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "The username " + username + " is not registered on Minecraft.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("remove")) {
                if (args.length == 3) {
                    String username = args[2];
                    UUID uuid = UUIDFetcher.getUUID(username);
                    if(uuid != null) {
                        if(FileManager.whitelist.getWhitelist().contains(username + ";" + uuid.toString())) {
                            FileManager.whitelist.removeUser(username + ";" + uuid.toString());
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "You have removed a user from the whitelist!" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "His name was" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + username + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + "The user " + username + " is not exists in the whitelist.");
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "The username " + username + " is not registered on Minecraft.");
                    }
                }
            } else if (args[1].equalsIgnoreCase("list")) {
                if (args.length == 2) {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "Whitelist" + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
                    int i = 0;
                    for(String arg : FileManager.whitelist.getWhitelist()) {
                        String name = arg.split(";")[0];
                        String uuid = arg.split(";")[1];
                        System.out.println(Main.getPrefix() + "- " + Main.ANSI_GREEN + name + Main.ANSI_RESET + " (" + Main.ANSI_YELLOW + uuid + Main.ANSI_RESET + ")");
                        i++;
                    }
                    if(i == 0) {
                        System.out.println(Main.getPrefix() + "Can't find maintenance list members.");
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
