package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base_Servers;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.GameServer;
import me.Spleevtv.NoCloud.Main;

import java.awt.*;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class Stop_CMD implements Command {


    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("stop")) {
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
                                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPPROXYSERVER " + name);
                                System.out.println(Main.getPrefix() + "Told base " + base + " to stop proxy_server '" + name + "'.");
                            } else {
                                System.out.println(Main.getPrefix() + "The server '" + name + "' doesn't exists.");
                            }
                        } else {
                            String base = Base_Servers.getBase(name);
                            if(base != null) {
                                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPPROXYGROUP " + name);
                                System.out.println(Main.getPrefix() + "Told base " + base + " to stop proxy_group '" + name + "'.");
                            } else {
                                System.out.println(Main.getPrefix() + "The group '" + name + "' doesn't exists.");
                            }
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "Usage: stop proxy,server <name (string)>");
                    }
                } else if(args[1].equalsIgnoreCase("server")) {
                    if(args.length == 3) {
                        String name = args[2];
                        if(name.contains("-")) {
                            String[] a = name.split("-");
                            String base = Base_Servers.getBase(a[0]);
                            if(base != null) {
                                Init.gameserver.get(name).setRestartState(false);
                                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPSERVER " + name);
                                System.out.println(Main.getPrefix() + "Told base " + base + " to stop server '" + name + "'.");
                            } else {
                                System.out.println(Main.getPrefix() + "The server '" + name + "' doesn't exists.");
                            }
                        } else {
                            String base = Base_Servers.getBase(name);
                            if(base != null) {
                                for(GameServer gs : Init.gameserver.values()) {
                                    if(gs.getGroup().equalsIgnoreCase(name)) {
                                        Init.gameserver.get(gs.getServername()).setRestartState(false);
                                    }
                                }
                                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPGROUP " + name);
                                System.out.println(Main.getPrefix() + "Told base " + base + " to stop server_group '" + name + "'.");
                            } else {
                                System.out.println(Main.getPrefix() + "The group '" + name + "' doesn't exists.");
                            }
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "Usage: stop proxy,server <name (string)>");
                    }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: stop proxy,server <name (string)>");
                }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "stop proxy" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "server " + Main.ANSI_RESET + "<" + Main.ANSI_YELLOW + "name " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN+ ")" + Main.ANSI_RESET + ">";
    }
}