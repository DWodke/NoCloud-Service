package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.*;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class EditGroup_CMD  implements Command {

    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("editgroup")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
            String group = args[1];
            if(args[2].equalsIgnoreCase("onlineAmount")) {
                if(Init.groups.containsKey(group)) {
                    Integer onlineAmount = Integer.parseInt(args[3]);
                    try {
                        if(GroupManager.editOnlineAmount(group, onlineAmount)) {
                            ServerGroup servergroup = Init.groups.get(group);
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The following group has changed his data:" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + group
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + " ("
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "memory"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + servergroup.getMaxRam() + "MB"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "onlineAmount"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + servergroup.getOnlineAmount()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "maxAmount"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + servergroup.getMaxAmount()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "maxPlayers"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + servergroup.getMaxPlayers()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "dynamic"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + servergroup.getDynamic()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ")"
                                            + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The value is higher then the maxAmount!" + Main.ANSI_RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Pls type in the console a number!" + Main.ANSI_RESET);
                    }
                } else {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "This group isn't exists!" + Main.ANSI_RESET);
                }
            } else if(args[2].equalsIgnoreCase("maxAmount")) {
                if(Init.groups.containsKey(group)) {
                    Integer maxAmount = Integer.parseInt(args[3]);
                    try {
                        if(GroupManager.editMaxAmount(group, maxAmount)) {
                            ServerGroup servergroup = Init.groups.get(group);
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The following group has changed his data:" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + group
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + " ("
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "memory"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxRam() + "MB"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "onlineAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getOnlineAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxPlayers"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxPlayers()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "dynamic"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getDynamic()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ")"
                                    + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The value is lower then the onlineAmount!" + Main.ANSI_RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Pls type in the console a number!" + Main.ANSI_RESET);
                    }
                } else {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "This group isn't exists!" + Main.ANSI_RESET);
                }
            } else if(args[2].equalsIgnoreCase("maxRam")) {
                if(Init.groups.containsKey(group)) {
                    Integer maxRam = Integer.parseInt(args[3]);
                    try {
                        if(GroupManager.editMaxRam(group, maxRam)) {
                            ServerGroup servergroup = Init.groups.get(group);
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The following group has changed his data:" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + group
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + " ("
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "memory"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxRam() + "MB"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "onlineAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getOnlineAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxPlayers"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxPlayers()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "dynamic"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getDynamic()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ")"
                                    + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The value is equals then the last!" + Main.ANSI_RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Pls type in the console a number!" + Main.ANSI_RESET);
                    }
                } else {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "This group isn't exists!" + Main.ANSI_RESET);
                }
            } else if(args[2].equalsIgnoreCase("maxPlayers")) {
                if(Init.groups.containsKey(group)) {
                    Integer maxPlayers = Integer.parseInt(args[3]);
                    try {
                        if(GroupManager.editMaxPlayers(group, maxPlayers)) {
                            ServerGroup servergroup = Init.groups.get(group);
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The following group has changed his data:" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + group
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + " ("
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "memory"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxRam() + "MB"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "onlineAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getOnlineAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxPlayers"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxPlayers()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "dynamic"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getDynamic()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ")"
                                    + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The value is equals then the last!" + Main.ANSI_RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Pls type in the console a number!" + Main.ANSI_RESET);
                    }
                } else {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "This group isn't exists!" + Main.ANSI_RESET);
                }
            } else if(args[2].equalsIgnoreCase("dynamic")) {
                if(Init.groups.containsKey(group)) {
                    Boolean dynamic = Boolean.parseBoolean(args[3]);
                    try {
                        if(GroupManager.editDynamic(group, dynamic)) {
                            ServerGroup servergroup = Init.groups.get(group);
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The following group has changed his data:" + Main.ANSI_RESET);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + group
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + " ("
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "memory"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxRam() + "MB"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "onlineAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getOnlineAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxAmount"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxAmount()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "maxPlayers"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getMaxPlayers()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ", "
                                    + Main.ANSI_RESET
                                    + Main.ANSI_YELLOW
                                    + "dynamic"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ":"
                                    + Main.ANSI_RESET
                                    + Main.ANSI_CYAN
                                    + " " + servergroup.getDynamic()
                                    + Main.ANSI_RESET
                                    + Main.ANSI_WHITE
                                    + ")"
                                    + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "The value is equals then the last!" + Main.ANSI_RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Pls type in the console a boolean!" + Main.ANSI_RESET);
                    }
                } else {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "This group isn't exists!" + Main.ANSI_RESET);
                }
            } else {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "editgroup" + Main.ANSI_RESET + " <" + Main.ANSI_YELLOW + "group " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "variable " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "onlineAmount, maxAmount, maxRam, maxPlayer, dynamic" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + ">";
    }
}
