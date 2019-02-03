package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.*;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.messaging.PluginMessageListener;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class GetDataListener implements PluginMessageListener {

    @Override
    public void onPluginMessageReceived(String s, Player p, byte[] message) {
        try {
            if(!s.equalsIgnoreCase("NoCloud")) {
                return;
            }
            DataInputStream stream = new DataInputStream(new ByteArrayInputStream(message));
            String subchannel = stream.readUTF();
            if(subchannel.equalsIgnoreCase("ServerData")) {
                String input;
                while((input = stream.readUTF()) != null) {
                    if(input.startsWith("GROUP: ")) {
                        input = input.replaceAll("GROUP: ", "");
                        ServerGroup group = Main.groups.get(input);
                        if(group == null) {
                            Main.groups.put(input, new ServerGroup(input));
                        }
                    } else if(input.startsWith("SERVER: ")) {
                        input = input.replaceAll("SERVER: ", "");
                        String[] args = input.split(":");
                        String name = args[0];
                        String group = args[0].split("-")[0];
                        if(Main.groups.get(group).getServerOutGroup(name) == null) {
                            Integer op = Integer.parseInt(args[1]);
                            Integer mp = Integer.parseInt(args[2]);
                            String base = args[4];
                            String ip = args[5];
                            Integer port = Integer.parseInt(args[6]);
                            ServerState state = ServerState.ONLINE;
                            if(args[3].equalsIgnoreCase("LOBBY")) {
                                state = ServerState.LOBBY;
                            } else if(args[3].equalsIgnoreCase("ONLINE")) {
                                state = ServerState.ONLINE;
                            } else if(args[3].equalsIgnoreCase("ENDING")) {
                                state = ServerState.ENDING;
                            } else if(args[3].equalsIgnoreCase("INGAME")) {
                                state = ServerState.INGAME;
                            }
                            Main.groups.get(group).addServer(new GameServer(name, state, op, mp, base, ip, port));
                        }
                    }
                }
            } else if(subchannel.equalsIgnoreCase("ServerUpdate")) {
                String input = stream.readUTF();
                String[] args = input.split(":");
                String name = args[0];
                String group = name.split("-")[0];
                Integer op = Integer.parseInt(args[1]);
                Integer mp = Integer.parseInt(args[2]);
                ServerState state = ServerState.ONLINE;
                if(args[3].equalsIgnoreCase("LOBBY")) {
                    state = ServerState.LOBBY;
                } else if(args[3].equalsIgnoreCase("ONLINE")) {
                    state = ServerState.ONLINE;
                } else if(args[3].equalsIgnoreCase("ENDING")) {
                    state = ServerState.ENDING;
                } else if(args[3].equalsIgnoreCase("INGAME")) {
                    state = ServerState.INGAME;
                }
                ServerGroup servergroup = Main.groups.get(group);
                servergroup.getServers().get(name).updateState(state);
                servergroup.getServers().get(name).updateOnlinePlayer(op);
                servergroup.getServers().get(name).updateMaxPlayers(mp);
                if(state == ServerState.LOBBY) {
                    if(Main.templates.get(SignTemplateKind.LOBBY).isHide()) {
                        for(CloudSign sign : Main.signs.values()) {
                            if(sign.getCurrentServer() == servergroup.getServers().get(name)) {
                                sign.search();
                            }
                        }
                    }
                } else if(state == ServerState.INGAME) {
                    if(Main.templates.get(SignTemplateKind.INGAME).isHide()) {
                        for(CloudSign sign : Main.signs.values()) {
                            if(sign.getCurrentServer() == servergroup.getServers().get(name)) {
                                sign.search();
                            }
                        }
                    }
                } else if(state == ServerState.ENDING) {
                    if(Main.templates.get(SignTemplateKind.ENDING).isHide()) {
                        for(CloudSign sign : Main.signs.values()) {
                            if(sign.getCurrentServer() == servergroup.getServers().get(name)) {
                                sign.search();
                            }
                        }
                    }
                } else if(state == ServerState.ONLINE) {
                    if(Main.templates.get(SignTemplateKind.ONLINE).isHide()) {
                        for(CloudSign sign : Main.signs.values()) {
                            if(sign.getCurrentServer() == servergroup.getServers().get(name)) {
                                sign.search();
                            }
                        }
                    }
                }
            } else if(subchannel.equalsIgnoreCase("AddServer")) {
                String input = stream.readUTF();
                String[] args = input.split(":");
                String name = args[0];
                String base = args[4];
                String group = name.split("-")[0];
                    Integer op = Integer.parseInt(args[1]);
                    Integer mp = Integer.parseInt(args[2]);
                    String ip = args[5];
                    Integer port = Integer.parseInt(args[6]);
                    ServerState state = ServerState.ONLINE;
                    if(args[3].equalsIgnoreCase("LOBBY")) {
                        state = ServerState.LOBBY;
                    } else if(args[3].equalsIgnoreCase("ONLINE")) {
                        state = ServerState.ONLINE;
                    } else if(args[3].equalsIgnoreCase("ENDING")) {
                        state = ServerState.ENDING;
                    } else if(args[3].equalsIgnoreCase("INGAME")) {
                        state = ServerState.INGAME;
                    }
                    ServerGroup servergroup = Main.groups.get(group);
                    if(servergroup == null) {
                        Main.groups.put(group, new ServerGroup(group));
                        servergroup = Main.groups.get(group);
                        servergroup.addServer(new GameServer(name, state, op, mp, base, ip, port));
                    } else {
                        servergroup.addServer(new GameServer(name, state, op, mp, base, ip, port));
                    }
            } else if(subchannel.equalsIgnoreCase("RemoveServer")) {
                String server = stream.readUTF();
                String group = server.split("-")[0];
                ServerGroup servergroup = Main.groups.get(group);
                if(Main.usedserver.contains(servergroup.getServerOutGroup(server))) {
                    Main.usedserver.remove(servergroup.getServerOutGroup(server));
                    for(CloudSign sign : Main.signs.values()) {
                        if(sign.getCurrentServer() == servergroup.getServerOutGroup(server)) {
                            servergroup.removeServer(server);
                            sign.search();
                            return;
                        }
                    }
                    servergroup.removeServer(server);
                    return;
                }
                servergroup.removeServer(server);
                for(CloudSign sign : Main.signs.values()) {
                    if(sign.getCurrentServer() == servergroup.getServerOutGroup(server)) {
                        servergroup.removeServer(server);
                        sign.search();
                        return;
                    }
                }
            }
        } catch (Exception e1) {
        }
    }
}
