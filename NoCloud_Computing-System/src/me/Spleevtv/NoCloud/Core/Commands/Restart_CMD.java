package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base_Servers;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Main;

import java.awt.*;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class Restart_CMD implements Command {


    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("restart")) {
            if(args.length == 2) {
                if(args[1].contains("-")) {
                    String group = args[1].split("-")[0];
                    String base = Base_Servers.getBase(group);
                    if(base != null) {
                        Init.baselist.getBaselist().get(base).sendTheBaseAMessage("RESTARTSERVER " + args[1]);
                        System.out.println(Main.getPrefix() + "Told " + base + " to restart server '" + args[1] + "'.");
                    } else {
                        System.out.println(Main.getPrefix() + "The group '" + group + "' isn't exists.");
                    }
                } else {
                    String base = Base_Servers.getBase(args[1]);
                    if(base != null) {
                        Init.baselist.getBaselist().get(base).sendTheBaseAMessage("RESTARTGROUP " + args[1]);
                        System.out.println(Main.getPrefix() + "Told " + base + " to restart group '" + args[1] + "'.");
                    } else {
                        System.out.println(Main.getPrefix() + "The group '" + args[1] + "' isn't exists.");
                    }
                }
            } else {
                System.out.println(Main.getPrefix() + "Usage: restart <server,proxy,group>");
            }
        }
    }

    @Override
    public String getUsage() {
        return  Main.ANSI_GREEN + "restart " + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "server" + Main.ANSI_RESET + "," + Main.ANSI_YELLOW + "proxy" + Main.ANSI_RESET + "," + Main.ANSI_YELLOW + "group" + Main.ANSI_RESET + ">";
    }
}
