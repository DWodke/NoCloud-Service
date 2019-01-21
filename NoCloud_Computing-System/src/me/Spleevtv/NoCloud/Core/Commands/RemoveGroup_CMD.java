package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base_Servers;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.FileManager;
import me.Spleevtv.NoCloud.Core.Utils.GameServer;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class RemoveGroup_CMD implements Command {


    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("removegroup")) {
         if(args.length == 2) {
             String group = args[1];
             String base = Base_Servers.getBase(group);
             if(base != null) {
                 Init.baselist.getBaselist().get(base).sendTheBaseAMessage("REMOVEGROUP " + group);
                 for(GameServer server : Init.gameserver.values()) {
                     if(server.getGroup().equals(group)) {
                         server.setRestartState(false);
                     }
                 }
                 Base_Servers.removeGroupOutCache(group);
                 System.out.println(Main.getPrefix() + "The group '" + group + "' was deleted.");
             } else {
                 System.out.println(Main.getPrefix() + "The group '" + group + "' isn't exists.");
             }
         } else {
             System.out.println(Main.getPrefix() + "Usage: removeGroup <group (string)>");
            }
        }
    }

    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "removeGroup " + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "group " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + ">";
    }
}
