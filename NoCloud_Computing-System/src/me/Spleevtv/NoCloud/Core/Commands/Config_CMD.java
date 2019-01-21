package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.BungeeCord;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.FileManager;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Nov, 2018
 */
public class Config_CMD implements Command {

    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("config")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
            if(args[1].equalsIgnoreCase("reload")) {
                FileManager.updateConfig();
                for(BungeeCord proxy : Init.proxys) {
                    proxy.pushConfigs();
                    proxy.sendTheProxyAMessage("UPDATECONFIG");
                }
                System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "All configuration-files was successfully reloaded." + Main.ANSI_RESET);
            } else if(args[1].equalsIgnoreCase("reset")) {
                FileManager.resetConfig();
                for(BungeeCord proxy : Init.proxys) {
                    proxy.pushConfigs();
                }
                System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "All configuration-files was successfully reset." + Main.ANSI_RESET);
            } else {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "config reload" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "reset" + Main.ANSI_RESET;
    }
}
