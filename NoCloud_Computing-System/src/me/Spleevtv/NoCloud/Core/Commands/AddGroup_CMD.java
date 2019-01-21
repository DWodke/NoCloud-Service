package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.*;
import me.Spleevtv.NoCloud.Main;

import java.io.File;

public class AddGroup_CMD implements Command {

    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("addgroup")) {
            if(args.length == 1) {
                System.out.println(Main.getPrefix() + getUsage());
                return;
            }
            if(args[1].equalsIgnoreCase("proxy")) {
                if(args.length == 9) {
                    String baseName = args[8];
                    String groupName = args[2];
                    int servers = Integer.parseInt(args[3]);
                    int onlineAmount = Integer.parseInt(args[4]);
                    int ram = Integer.parseInt(args[5]);
                    Boolean dinamic = Boolean.parseBoolean(args[6]);
                    int maxPlayers = Integer.parseInt(args[7]);
                    if(!groupName.contains("-")) {
                        if(Init.baselist.getBaselist().containsKey(baseName)) {
                            Base base = Init.baselist.getBaselist().get(baseName);
                            if(base.getAccesToStartServer()) {
                                String ports = null;
                                int o = Init.cache.getBungeeAnfangsPort();
                                for(int i = 0; i < servers; i++) {
                                    if(ports == null) {
                                        ports = o + ",";
                                    } else {
                                        ports = ports + o + ",";
                                    }
                                    o++;
                                }
                                int c = Init.cache.getProxy_connection_port();
                                String c_ports = null;
                                for(int i = 0; i < servers; i++) {
                                    if(c_ports == null) {
                                        c_ports = c + ",";
                                    } else {
                                        c_ports = c_ports + c + ",";
                                    }
                                    Init.proxys.add(new BungeeCord(c));
                                    c++;
                                }
                                Init.cache.editBungeeAnfangsPort(Init.cache.getBungeeAnfangsPort() + servers);
                                Init.cache.editProxyConnectionPorts(Init.cache.getProxy_connection_port() + servers);
                                Init.cache.editBungeeCords(Init.cache.getBungeeCords() + servers);
                                base.sendTheBaseAMessage("CREATEPROXYGROUP " + groupName + " " + onlineAmount + " " + servers + " " + ram + " " + dinamic + " " + maxPlayers + " " + ports + " " + c_ports);
                                Base_Servers.addGroupToCache(groupName, base.getName());
                                System.out.println(Main.getPrefix() + "The base '" + baseName + "' has create the group '" + groupName + "'.");
                                System.out.println(Main.getPrefix() + "Told base " + baseName + " to start proxygroup '" + groupName + "'.");
                            } else {
                                System.out.println(Main.getPrefix() + "The base '" + baseName + "' has no templates. (spigot.jar and BungeeCord.jar)");
                            }
                        } else {
                            System.out.println(Main.getPrefix() + "The base '" + baseName + "' is not registered in the core.");
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "The character '-' is not allowed in a group.");
                    }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: addgroup proxy,server <name (string)> <maxAmount (int)> <onlineAmount (int)> <ram (int)> <dinamic (boolean)> <maxPlayers (int)> <base (string)>");
                }
            } else if(args[1].equalsIgnoreCase("server")) {
                if(args.length == 9) {
                    String baseName = args[8];
                    String groupName = args[2];
                    int servers = Integer.parseInt(args[3]);
                    int onlineAmount = Integer.parseInt(args[4]);
                    int ram = Integer.parseInt(args[5]);
                    Boolean dinamic = Boolean.parseBoolean(args[6]);
                    int maxPlayers = Integer.parseInt(args[7]);
                    if(!groupName.contains("-")) {
                        if(Init.baselist.getBaselist().containsKey(baseName)) {
                            Base base = Init.baselist.getBaselist().get(baseName);
                            if(base.getAccesToStartServer()) {
                                String ports = null;
                                int o = Init.cache.getSpigotAnfangPorts();
                                for(int i = 0; i < servers; i++) {
                                    if(ports == null) {
                                        ports = o + ",";
                                    } else {
                                        ports = ports + o + ",";
                                    }
                                    o++;
                                }
                                Init.cache.editSpigotAnfangsPort(Init.cache.getSpigotAnfangPorts() + servers);
                                base.sendTheBaseAMessage("CREATESERVERGROUP " + groupName + " " + onlineAmount + " " + servers + " " + ram + " " + dinamic + " " + maxPlayers + " " + ports);
                                Base_Servers.addGroupToCache(groupName, base.getName());
                                System.out.println(Main.getPrefix() + "The base '" + baseName + "' has create the group '" + groupName + "'.");
                                System.out.println(Main.getPrefix() + "Told base " + baseName + " to start server_group '" + groupName + "'.");
                            } else {
                                System.out.println(Main.getPrefix() + "The base '" + baseName + "' has no templates. (spigot.jar and BungeeCord.jar)");
                            }
                        } else {
                            System.out.println(Main.getPrefix() + "The base '" + baseName + "' is not registered in the core.");
                        }
                    } else {
                        System.out.println(Main.getPrefix() + "The character '-' is not allowed in a group.");
                    }
                } else {
                    System.out.println(Main.getPrefix() + "Usage: addgroup proxy,server <name (string)> <maxAmount (int)> <onlineAmount (int)> <ram (int)> <dinamic (boolean)> <maxPlayers (int)> <base (string)>");
                }
            } else {
                System.out.println(Main.getPrefix() + "Usage: addgroup proxy,server <name (string)> <maxAmount (int)> <onlineAmount (int)> <ram (int)> <dinamic (boolean)> <maxPlayers (int)> <base (string)>");
            }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "addgroup proxy" + Main.ANSI_RESET + "," + Main.ANSI_GREEN + "server" + Main.ANSI_RESET + " <" + Main.ANSI_YELLOW + "name " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "maxAmount " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "int" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "onlineAmount " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "int" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "ram " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "int" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "dinamic " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "boolean" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "maxPlayers " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "int" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + "> <" + Main.ANSI_YELLOW + "base " + Main.ANSI_GREEN + "(" + Main.ANSI_RESET + "string" + Main.ANSI_GREEN + ")" + Main.ANSI_RESET + ">";
    }
}
