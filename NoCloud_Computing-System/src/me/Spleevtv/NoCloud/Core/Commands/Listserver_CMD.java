package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.GameServer;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class Listserver_CMD implements Command {


    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("listservers")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + Main.ANSI_RED + "All available server" + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
                for(Base base : Init.baselist.getBaselist().values()) {
                    System.out.println(Main.getPrefix() + Main.ANSI_PURPLE + base.getName() + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
                    for(GameServer server : Init.gameserver.values()) {
                        if(server.getCurrentbase().getName().equalsIgnoreCase(base.getName())) {
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
                                    + " " + round(elipsedTime) + " seconds"
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
                }
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "listservers" + Main.ANSI_RESET + " | Show all available server of the cloud";
    }
    public static double round(double betrag) {
        double round = Math.round(betrag*10000);
        round = round / 10000;
        round = Math.round(round*1000);
        round = round / 1000;
        round = Math.round(round*100);
        return round / 100;
    }
}
