package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class Stats_CMD implements Command {

    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("stats")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + Main.ANSI_RED + "Cloud stats" + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
                System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "Registered players" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + Init.playerstats.getRegisteredPlayerSize() + Main.ANSI_RESET);
                System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "Pings (all time)" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + Init.playerstats.getMaxPings() + Main.ANSI_RESET);
                System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "Pings (last 5 minutes)" + Main.ANSI_RESET + ": " + Main.ANSI_YELLOW + Init.playerstats.getPingsFromLast5min() + Main.ANSI_RESET);
            } else {
                System.out.println(Main.getPrefix() + "Usage: stats");
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "stats" + Main.ANSI_RESET + " | shows the stats of the cloud-system";
    }

}
