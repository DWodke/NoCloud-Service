package me.Spleevtv.objekts;

import me.Spleevtv.main.Main;
import me.Spleevtv.utils.ServerInfos;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.net.Socket;
import java.nio.charset.StandardCharsets;

public class Core {

    private String ipaddress;
    private int port;
    private Socket socket;

    public Core(String ip, int p) {
            this.ipaddress = ip;
            this.port = p;
            connectToCore();
    }
    public void sendToTheCoreAMessage(String message) {
        try {
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.socket.getOutputStream(), StandardCharsets.UTF_8), true);
            writer.write(message + "\n");
            writer.flush();
            this.socket.getOutputStream().flush();
        } catch (Exception e) {
        }
    }
    public void connectToCore() {
        try {
            this.socket = new Socket();
            this.socket.connect(new InetSocketAddress(this.ipaddress, this.port));
        } catch (IOException e) {
            ProxyServer.getInstance().getConsole().sendMessage(Main.prefix + "Connection to the core failed.");
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e1) {
                e1.printStackTrace();
            }
            connectToCore();
            return;
        }
        ProxyServer.getInstance().getConsole().sendMessage(Main.prefix + "Connect successfully to the core.");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String line;
                try {
                    BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
                    while((line = r.readLine()) != null) {
                        if(line.startsWith("UPDATEINFOS")) {
                            String[] args = line.split("#225646");
                            Main.motd = new String[]{args[1].replaceAll("&", "§"), args[2].replaceAll("&", "§")};
                            Main.maintenance_motd = new String[]{args[10].replaceAll("&", "§"), args[11].replaceAll("&", "§")};
                            Main.maxplayers = Integer.parseInt(args[3]);
                            Main.maintenance = Boolean.parseBoolean(args[4]);
                            Main.maintenance = Boolean.parseBoolean(args[4]);
                            Main.fallbackServerGroup = args[5];
                            Main.maintenance_messsage_amount = Integer.parseInt(args[6]);
                            Main.maintenance_message = args[7].replaceAll("&", "§").replaceAll("#67868u349374897453", "\n");
                            Main.full_messsage_amount = Integer.parseInt(args[8]);
                            Main.full_message = args[9].replaceAll("&", "§").replaceAll("#67868u349374897453", "\n");
                        } else if(line.startsWith("STARTSPIGOTSERVER")) {
                            String[] args = line.split(" ");
                            String server = args[1];
                            String ip = args[2];
                            Integer port = Integer.parseInt(args[3]);
                            String group = args[4];
                            ServerGroup servergroup = Main.groups.get(group);
                            if(servergroup == null) {
                                Main.groups.put(group, new ServerGroup(group));
                            }
                            servergroup =  Main.groups.get(group);
                            String base = args[5];
                            Integer maxplayers = Integer.parseInt(args[6]);
                            Integer onlineplayers = Integer.parseInt(args[7]);
                            ServerState state = ServerState.ONLINE;
                            if(args[8].equalsIgnoreCase("ONLINE")) {
                                state = ServerState.ONLINE;
                            } else if(args[8].equalsIgnoreCase("INGAME")) {
                                state = ServerState.INGAME;
                            } else if(args[8].equalsIgnoreCase("ENDING")) {
                                state = ServerState.ENDING;
                            } else if(args[8].equalsIgnoreCase("LOBBY")) {
                                state = ServerState.LOBBY;
                            }
                            InetSocketAddress s = new InetSocketAddress(ip, port);
                            ServerInfo i = ProxyServer.getInstance().getServers().put(server, ProxyServer.getInstance()
                                    .constructServerInfo(server, s, "NoCloud - Gameserver", false));
                            GameServer gameserver = servergroup.addServer(new GameServer(server, state, onlineplayers, maxplayers, base));
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                if(all.hasPermission("cloud.use")) {
                                    all.sendMessage(Main.start_message.replaceAll("%server%", server));
                                }
                            }
                            if(server.toLowerCase().startsWith(Main.fallbackServerGroup.toLowerCase())) {
                                if(!Main.fallbackServers.contains(server)) {
                                    Main.fallbackServers.add(server);
                                }
                            }
                            ServerInfos.sendServerData(ProxyServer.getInstance().getServerInfo(server));
                            for(ServerInfo infos : ProxyServer.getInstance().getServers().values()) {
                                if(infos != ProxyServer.getInstance().getServerInfo(server)) {
                                    ServerInfos.addServer(gameserver, infos);
                                }
                            }
                        } else if(line.startsWith("STOPSPIGOTSERVER")) {
                            String[] args = line.split(" ");
                            String server = args[1];
                            String group = server.split("-")[0];
                            ServerGroup servergroup = Main.groups.get(group);
                            for(ServerInfo infos : ProxyServer.getInstance().getServers().values()) {
                                ServerInfos.removeServer(servergroup.getServers().get(server), infos);
                            }
                            servergroup.removeServer(server);
                            ServerInfo i = ProxyServer.getInstance().getServers().remove(server);
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                if(all.hasPermission("cloud.use")) {
                                    all.sendMessage(Main.stopp_message.replaceAll("%server%", server));
                                }
                            }
                            if(server.toLowerCase().startsWith(Main.fallbackServerGroup.toLowerCase())) {
                                if(Main.fallbackServers.contains(server)) {
                                    Main.fallbackServers.remove(server);
                                }
                            }
                        } else if(line.startsWith("CLOUDPLAYERJOIN")) {
                            String[] args = line.split(" ");
                            String p_name = args[1];
                            String p_uuid = args[2];
                            String p_server = args[3];
                            Integer player_size = Integer.parseInt(args[4]);
                            if(!Main.players.containsKey(p_uuid)) {
                                Main.players.put(p_uuid, new CloudPlayer(p_name, p_uuid, p_server));
                            }
                            Main.onlineplayers = player_size;
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                String server;
                                try {
                                     server = all.getServer().getInfo().getName();
                                } catch (Exception e) {
                                    server = "fallback";
                                }
                                all.setTabHeader(new TextComponent(Main.tabheader.replaceAll("%op%", Main.onlineplayers + "").replaceAll("%mp%", Main.maxplayers + "").replaceAll("%server%", server)),
                                        new TextComponent(Main.tabfooter.replaceAll("%op%", Main.onlineplayers + "").replaceAll("%mp%", Main.maxplayers + "").replaceAll("%server%", server)));
                            }
                        } else if(line.startsWith("CLOUDPLAYERQUIT")) {
                            String[] args = line.split(" ");
                            String p_uuid = args[1];
                            Integer player_size = Integer.parseInt(args[2]);
                            if(Main.players.containsKey(p_uuid)) {
                                Main.players.remove(p_uuid);
                            }
                            Main.onlineplayers = player_size;
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                String server;
                                try {
                                    server = all.getServer().getInfo().getName();
                                } catch (Exception e) {
                                    server = "fallback";
                                }
                                all.setTabHeader(new TextComponent(Main.tabheader.replaceAll("%op%", Main.onlineplayers + "").replaceAll("%mp%", Main.maxplayers + "").replaceAll("%server%", server)),
                                        new TextComponent(Main.tabfooter.replaceAll("%op%", Main.onlineplayers + "").replaceAll("%mp%", Main.maxplayers + "").replaceAll("%server%", server)));
                            }
                        } else if(line.startsWith("UPDATECONFIG")) {
                            Main.updateConfig();
                        } else if(line.startsWith("UPDATEWHITELIST")) {
                            String[] args = line.split(" ");
                            if(args.length == 1) {
                                continue;
                            }
                            String[] users = args[1].split(",");
                            Main.whitelist.clear();
                            for(String u : users) {
                                Main.whitelist.add(u.toLowerCase());
                            }
                            for(ProxiedPlayer all : ProxyServer.getInstance().getPlayers()) {
                                if(!Main.whitelist.contains(all.getName().toLowerCase())) {
                                    all.disconnect(Main.maintenance_message);
                                }
                            }
                        } else if(line.startsWith("NEWUSERREGISTERED")) {
                            String[] args = line.split(" ");
                            if(Main.newplayer) {
                                ProxyServer.getInstance().broadcast( Main.newplayermessage.replaceAll("%player%", args[1]).replaceAll("%prefix%", Main.prefix).replaceAll("&", "§"));
                            }
                        } else if(line.startsWith("UPDATESERVERPLAYERSIZE")) {
                            String[] args = line.split(" ");
                            String server = args[1];
                            String size = args[2];
                            GameServer gameserver = Main.groups.get(server.split("-")[0]).getServerOutGroup(server);
                            gameserver.updateOnlinePlayer(Integer.parseInt(size));
                            System.out.println(gameserver.getName());
                            for(ServerInfo info : ProxyServer.getInstance().getServers().values()) {
                                ServerInfos.sendUpdate(gameserver, info);
                            }
                        } else if(line.startsWith("UPDATESERVERSTATE")) {
                            String[] args = line.split(" ");
                            String server = args[1];
                            GameServer gameserver = Main.groups.get(server.split("-")[0]).getServerOutGroup(server);
                            gameserver.setState(ServerState.valueOf(args[2]));
                            for(ServerInfo info : ProxyServer.getInstance().getServers().values()) {
                                ServerInfos.sendUpdate(gameserver, info);
                            }
                        } else if(line.startsWith("STATSUPDATE")) {
                            String[] args = line.split(" ");
                            Main.maxPings = Integer.parseInt(args[1]);
                            Main.pingsLast5Minutes = Integer.parseInt(args[2]);
                            Main.registeredPlayers = Integer.parseInt(args[3]);
                            Main.cloudstarttime = Long.parseLong(args[4]);
                            Main.basesize = Integer.parseInt(args[5]);
                        }
                    }
                } catch (IOException e) {
                    System.out.println(Main.prefix + "Connection lost.");
                    connectToCore();
                    return;
                }
            }
        }).start();
    }
}