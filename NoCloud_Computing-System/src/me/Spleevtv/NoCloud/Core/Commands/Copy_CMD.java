package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Base.Utils.FileManager;
import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base_Servers;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.ServerGroup;
import me.Spleevtv.NoCloud.Main;

import java.io.File;
import java.io.IOException;

public class Copy_CMD implements Command {

    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("copy")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + this.getUsage());
                return;
            } else if(args.length == 2) {
                String server = args[1];
                if(Init.gameserver.containsKey(server)) {
                    ServerGroup group = Init.groups.get(server.split("-")[0]);
                    if(group != null) {
                        if(group.getDynamic()) {
                            Init.baselist.getBaselist().get(Base_Servers.getBase(group.getName())).sendTheBaseAMessage("COPYDIR " + server);
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "The template was successfully updated." + Main.ANSI_RESET);
                        } else {
                            System.out.println(Main.getPrefix() + "This server is not in the dynamic mode.");
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "This server is doesn't exists.");
                    }
                } else {
                    System.out.println(Main.getPrefix() + "This server is not online or doesn't exists.");
                }
            } else {
                System.out.println(Main.getPrefix() + this.getUsage());
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "copy " + Main.ANSI_RESET  + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "server " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN+ ")" + Main.ANSI_RESET + ">" + " | Copy one server to the template directory";
    }
}