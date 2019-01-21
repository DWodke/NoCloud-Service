package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base_Servers;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Main;

public class Start_CMD implements Command {

    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("start")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
            if(args[1].equalsIgnoreCase("proxy")) {
                if(args.length == 3) {
                    String name = args[2];
                    if(name.contains("-")) {
                        String[] a = name.split("-");
                        String base = Base_Servers.getBase(a[0]);
                        if(base != null) {
                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTPROXYSERVER " + name);
                            System.out.println(Main.getPrefix() + "Told base " + base + " to start proxy_server '" + name + "'.");
                        } else {
                            System.out.println(Main.getPrefix() + "The server '" + name + "' doesn't exists.");
                        }
                    } else {
                        String base = Base_Servers.getBase(name);
                        if(base != null) {
                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTPROXYGROUP " + name);
                            System.out.println(Main.getPrefix() + "Told base " + base + " to start proxy_group '" + name + "'.");
                        } else {
                            System.out.println(Main.getPrefix() + "The group '" + name + "' doesn't exists.");
                        }
                    }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: start proxy,server <name (string)>");
                }
            } else if(args[1].equalsIgnoreCase("server")) {
                if(args.length == 3) {
                    String name = args[2];
                    if(name.contains("-")) {
                        String[] a = name.split("-");
                        String base = Base_Servers.getBase(a[0]);
                        if(base != null) {
                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTSERVER " + name);
                            System.out.println(Main.getPrefix() + "Told base " + base + " to start server '" + name + "'.");
                        } else {
                            System.out.println(Main.getPrefix() + "The server '" + name + "' doesn't exists.");
                        }
                    } else {
                        String base = Base_Servers.getBase(name);
                        if(base != null) {
                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTGROUP " + name);
                            System.out.println(Main.getPrefix() + "Told base " + base + " to start server_group '" + name + "'.");
                        } else {
                            System.out.println(Main.getPrefix() + "The group '" + name + "' doesn't exists.");
                        }
                    }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: start proxy,server <name (string)>");
                }
            } else {
                System.out.println(Main.getPrefix() + "Usage: start proxy,server <name (string)>");
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "start proxy" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "server " + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "name " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN+ ")" + Main.ANSI_RESET + ">";
    }
}
