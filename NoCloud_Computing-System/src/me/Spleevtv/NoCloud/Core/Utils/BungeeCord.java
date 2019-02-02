package me.Spleevtv.NoCloud.Core.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Main;

import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.nio.charset.MalformedInputException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class BungeeCord {

    private Integer port;
    private ServerSocket socket;
    private Socket chatsocket;
    private Boolean isConnected;
    private BungeeCord instance;

    public BungeeCord(int p) {
        this.port = p;
        this.isConnected = false;
        this.instance = this;
        startConnectingToProxy();
    }
    public void sendTheProxyAMessage(String message) {
        if(isConnected()) {
            try {
                PrintWriter writer = new PrintWriter(new OutputStreamWriter(this.chatsocket.getOutputStream(), StandardCharsets.UTF_8), true);
                writer.write(message + "\n");
                writer.flush();
                this.chatsocket.getOutputStream().flush();
            } catch (Exception e) {
            }
        }
    }
    private void startConnectingToProxy() {
        if(this.socket != null) {
            try {
                this.socket.close();
                this.socket = null;
                this.chatsocket = null;
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try {
            this.socket = new ServerSocket();
            socket.bind(new InetSocketAddress(this.port));
        } catch (IOException e) {
            e.printStackTrace();
        }
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    Socket s = null;
                    try {
                        s = socket.accept();
                        if(s != null) {
                            System.out.println(Main.getPrefix() + "Proxy connect [Host: " + socket.getInetAddress().getHostAddress() + "/Port: " + port + "]");
                            chatsocket = s;
                            isConnected = true;
                            pushConfigs();
                            String end = "";
                            for(String user : FileManager.whitelist.getWhitelist()) {
                                end = end + user + ",";
                            }
                            sendTheProxyAMessage("UPDATEWHITELIST " + end);
                            for(String server : Init.online_servers.keySet()) {
                                GameServer gameserver = Init.gameserver.get(server);
                                sendTheProxyAMessage("STARTSPIGOTSERVER " + server
                                        + " " + Init.online_servers.get(server)[0]
                                        + " " + Init.online_servers.get(server)[1]
                                        + " " + gameserver.getGroup()
                                        + " " + gameserver.getCurrentbase().getName()
                                        + " " + gameserver.getMaxPlayers()
                                        + " " + gameserver.getSize()
                                        + " " + gameserver.getState());
                            }
                            Integer i = Init.players.getPlayerSize();
                            for(CloudPlayer player : Init.players.getPlayerList().values()) {
                                sendTheProxyAMessage("CLOUDPLAYERJOIN " + player.getName() + " " + player.getUUID() + " " + player.getCurrentServer() + " " + i);
                            }
                            break;
                        }
                    } catch (IOException e) {
                        continue;
                    }
                }
                try {
                    String line;
                    BufferedReader r = new BufferedReader(new InputStreamReader(chatsocket.getInputStream(), "UTF8"));
                    while(true) {
                        if((line = r.readLine()) != null) {
                            if(line.startsWith("CLOUDPLAYERJOIN")) {
                                String[] args = line.split(" ");
                                String pname = args[1];
                                String puuid = args[2];
                                String pserver = args[3];
                                if(pserver == null) {
                                    pserver = "fallback";
                                }
                                Init.players.joinPlayer(pname, puuid, pserver, instance);
                                for(BungeeCord server : Init.proxys) {
                                    server.sendTheProxyAMessage(line + " " + Init.players.getPlayerSize());
                                }
                            } else if(line.startsWith("CLOUDPLAYERQUIT")) {
                                String[] args = line.split(" ");
                                String puuid = args[1];
                                CloudPlayer p = Init.players.getPlayerList().get(puuid);
                                if(p != null) {
                                    Init.players.quitPlayer(puuid);
                                    for(BungeeCord server : Init.proxys) {
                                        server.sendTheProxyAMessage(line + " " + Init.players.getPlayerSize());
                                    }
                                    continue;
                                }
                                for(BungeeCord server : Init.proxys) {
                                    server.sendTheProxyAMessage(line + " " + Init.players.getPlayerSize());
                                }
                            } else if(line.startsWith("PINGPROXY")) {
                                String[] args = line.split(" ");
                                String host = args[1];
                                Init.playerstats.addPing(host);
                            } else if(line.startsWith("GETSTATS")) {
                                sendTheProxyAMessage("STATSUPDATE " + Init.playerstats.getMaxPings() + " " + Init.playerstats.getPingsFromLast5min() + " " + Init.playerstats.getRegisteredPlayerSize() + " " + Init.starttime + " " + Init.baselist.getBaselist().size());
                            } else if(line.startsWith("RESTARTSERVER")) {
                                String[] args = line.split(" ");
                                String value = args[1];
                                if(value.contains("-")) {
                                    String base = Base_Servers.getBase(value.split("-")[0]);
                                    if(base != null) {
                                        Init.baselist.getBaselist().get(base).sendTheBaseAMessage("RESTARTSERVER " + args[1]);
                                    }
                                } else {
                                    String base = Base_Servers.getBase(value);
                                    if(base != null) {
                                        Init.baselist.getBaselist().get(base).sendTheBaseAMessage("RESTARTGROUP " + args[1]);
                                    }
                                }
                            } else if(line.startsWith("STARTSERVER")) {
                                String[] args = line.split(" ");
                                String value = args[1];
                                if(value.contains("-")) {
                                    String base = Base_Servers.getBase(value.split("-")[0]);
                                    if(base != null) {
                                        if(Init.groups.containsKey(value.split("-")[0])) {
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTSERVER " + value);
                                        } else {
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTPROXYSERVER " + value);
                                        }
                                    }
                                } else {
                                    String base = Base_Servers.getBase(value);
                                    if(base != null) {
                                        if(Init.groups.containsKey(value)) {
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTGROUP " + value);
                                        } else {
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STARTPROXYGROUP " + value);
                                        }
                                    }
                                }
                            } else if(line.startsWith("STOPSERVER")) {
                                String[] args = line.split(" ");
                                String value = args[1];
                                if(value.contains("-")) {
                                    String base = Base_Servers.getBase(value.split("-")[0]);
                                    if(base != null) {
                                        if(Init.gameserver.containsKey(value)) {
                                            Init.gameserver.get(value).setRestartState(false);
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPSERVER " + value);
                                        } else {
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPPROXYSERVER " + value);
                                        }
                                    }
                                } else {
                                    String base = Base_Servers.getBase(value);
                                    if(base != null) {
                                        if(Init.groups.containsKey(value)) {
                                            for(GameServer gs : Init.gameserver.values()) {
                                                if(gs.getGroup().equalsIgnoreCase(value)) {
                                                    Init.gameserver.get(gs.getServername()).setRestartState(false);
                                                }
                                            }
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPGROUP " + value);
                                        } else {
                                            Init.baselist.getBaselist().get(base).sendTheBaseAMessage("STOPPROXYGROUP " + value);
                                        }
                                    }
                                }
                            } else if(line.startsWith("REMOVEGROUP")) {
                                String[] args = line.split(" ");
                                String value = args[1];
                                String base = Base_Servers.getBase(value);
                                if(base != null) {
                                    for(GameServer server : Init.gameserver.values()) {
                                        if(server.getGroup().equals(value)) {
                                            server.setRestartState(false);
                                        }
                                    }
                                    Init.baselist.getBaselist().get(base).sendTheBaseAMessage("REMOVEGROUP " + value);
                                }
                            } else if(line.startsWith("COPY")) {
                                String[] args = line.split(" ");
                                String value = args[1];
                                String base = Base_Servers.getBase(value.split("-")[0]);
                                if(base != null) {
                                    Init.baselist.getBaselist().get(base).sendTheBaseAMessage("COPYDIR " + value);
                                }
                            } else if(line.startsWith("MAINTENANCE")) {
                                String[] args = line.split(" ");
                                Boolean value = Boolean.parseBoolean(args[1]);
                                File bungee_config = new File("./Core/config.json");
                                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                                JsonObject json = gson.fromJson(new FileReader(bungee_config), JsonObject.class);
                                json.remove("maintenance");
                                json.addProperty("maintenance", value);
                                if(bungee_config.exists()) {
                                    bungee_config.delete();
                                }
                                bungee_config.createNewFile();
                                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bungee_config), "UTF-8");
                                writer.write(gson.toJson(json));
                                writer.flush();
                                writer.close();
                                FileManager.updateConfig();
                                pushConfigs();
                            } else if(line.startsWith("JOINMAINTENANCE")) {
                                for(BungeeCord proxy : Init.proxys) {
                                    proxy.sendTheProxyAMessage(line);
                                }
                            }
                        } else {
                            if(r.read() == -1) {
                                System.out.println(Main.getPrefix() + "Proxy disconnect [Host: " + socket.getInetAddress().getHostAddress() + "/Port: " + port + "]");
                                isConnected = false;
                                List<String> toremoveplayers = new ArrayList<String>();
                                for(CloudPlayer player : Init.players.getPlayerList().values()) {
                                    if(player.getProxy() == instance) {
                                        toremoveplayers.add(player.getUUID().toString());
                                        for(BungeeCord p : Init.proxys) {
                                            if(p != instance) {
                                                p.sendTheProxyAMessage("CLOUDPLAYERQUIT " + player.getUUID().toString() + " " + Init.players.getPlayerSize());
                                            }
                                        }
                                    }
                                }
                                for(String uuid : toremoveplayers) {
                                    Init.players.quitPlayer(uuid);
                                }
                                startConnectingToProxy();
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println(Main.getPrefix() + "Proxy disconnect [Host: " + socket.getInetAddress().getHostAddress() + "/Port: " + port + "]");
                    isConnected = false;
                    List<String> toremoveplayers = new ArrayList<String>();
                    for(CloudPlayer player : Init.players.getPlayerList().values()) {
                        if(player.getProxy() == instance) {
                            toremoveplayers.add(player.getUUID().toString());
                            for(BungeeCord p : Init.proxys) {
                                if(p != instance) {
                                    p.sendTheProxyAMessage("CLOUDPLAYERQUIT " + player.getUUID().toString() + " " + Init.players.getPlayerSize());
                                }
                            }
                        }
                    }
                    for(String uuid : toremoveplayers) {
                        Init.players.quitPlayer(uuid);
                    }
                    startConnectingToProxy();
                    return;
                }
            }
        }).start();
    }
    public Boolean isConnected() {
        return isConnected;
    }
    public void pushConfigs() {
        sendTheProxyAMessage("UPDATEINFOS#225646"
                + Init.bungee_template.getMotd_1() + "#225646"
                + Init.bungee_template.getMotd_2() + "#225646"
                + Init.bungee_template.getMaxPlayers() + "#225646"
                + Init.bungee_template.getWartung() + "#225646"
                + Init.bungee_template.getFallbackServerGroup() + "#225646"
                + Init.bungee_template.getMaintenanceMessageAmount() + "#225646"
                + Init.bungee_template.getMaintenanceMessageAsString() + "#225646"
                + Init.bungee_template.getFullMessagesAmount() + "#225646"
                + Init.bungee_template.getFullMessageAsString() + "#225646"
                + Init.bungee_template.getMaintenanceMotd1() + "#225646"
                + Init.bungee_template.getMaintenanceMotd2());
    }
}
