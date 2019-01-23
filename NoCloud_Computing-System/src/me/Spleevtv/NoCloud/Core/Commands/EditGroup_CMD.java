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
            if(args[1].equalsIgnoreCase("onlineAmount")) {
                String group = args[2];
                if(Init.groups.containsKey(group)) {
                    Integer onlineAmount = Integer.parseInt(args[3]);
                    try {
                        if(GroupManager.editOnlineAmount(group, onlineAmount)) {
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "The following group has changed his data:" + Main.ANSI_RESET);
                            for(GameServer server : Init.gameserver.values()) {
                                if(server.getGroup().equalsIgnoreCase(group)) {
                                    Double elipsedTime = (double) System.currentTimeMillis() - Init.time.get(server.getServername());
                                    elipsedTime = elipsedTime / 1000;
                                    System.out.println(Main.getPrefix() + Main.ANSI_GREEN + server.getServername()
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
                                            + " " + server.getMaxram() + "MB"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "maxplayers"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + server.getMaxPlayers()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "uptime"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + Listserver_CMD.round(elipsedTime) + " seconds"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "onlineplayer"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + server.getSize()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "base"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + server.getCurrentbase().getName()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ", "
                                            + Main.ANSI_RESET
                                            + Main.ANSI_YELLOW
                                            + "state"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ":"
                                            + Main.ANSI_RESET
                                            + Main.ANSI_CYAN
                                            + " " + server.getState()
                                            + Main.ANSI_RESET
                                            + Main.ANSI_WHITE
                                            + ")"
                                            + Main.ANSI_RESET);
                                }
                            }
                        } else {
                            System.out.println(Main.getPrefix() + Main.ANSI_RED + "An error is available!" + Main.ANSI_RESET);
                        }
                    } catch (NumberFormatException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Pls type in the console a number!" + Main.ANSI_RESET);
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
