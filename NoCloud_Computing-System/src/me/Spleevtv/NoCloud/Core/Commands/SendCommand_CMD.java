package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base_Servers;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class SendCommand_CMD implements Command {
    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("sendcommand")) {
            if(args.length >= 3) {
                String arg0 = args[1];
                String command = "#ahsgdjqlwdon";
                for(int i = 2; i < args.length; i++) {
                    command = command + " " + args[i];
                }
                command = command.replaceAll("#ahsgdjqlwdon ", "");
                if(arg0.contains("-")) {
                    String group = arg0.split("-")[0];
                    String base = Base_Servers.getBase(group);
                    if(base != null) {
                        Init.baselist.getBaselist().get(base).sendTheBaseAMessage("SENDCOMMANDTOSERVER " + arg0 + " " + command);
                        System.out.println(Main.getPrefix() + "Send command '" + command + "' to the server '" + arg0 + "'.");
                    } else {
                        System.out.println(Main.getPrefix() + "The server is not registered in the core.");
                    }
                } else {
                    String group = arg0;
                    String base = Base_Servers.getBase(group);
                    if(base != null) {
                        Init.baselist.getBaselist().get(base).sendTheBaseAMessage("SENDCOMMANDTOGROUP " + arg0 + " " + command);
                        System.out.println(Main.getPrefix() + "Send command '" + command + "' to the group '" + group + "'.");
                    } else {
                        System.out.println(Main.getPrefix() + "The group is not registered in the core.");
                    }
                }
            } else {
                System.out.println(getUsage());
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "sendcommand " + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "server/group " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN+ ")" + Main.ANSI_RESET + "> " + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "command " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN+ ")" + Main.ANSI_RESET + ">";
    }
}