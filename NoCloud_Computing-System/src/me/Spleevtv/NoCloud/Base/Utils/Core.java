package me.Spleevtv.NoCloud.Base.Utils;
import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Main;

import javax.print.attribute.IntegerSyntax;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.function.BooleanSupplier;

public class Core {

    private String ip;
    private Integer port;
    private Socket socket;

    public Core() {
            if(!FileManager.baseconfig.isLoaded()) {
                FileManager.baseconfig.load();
            }
            this.ip = FileManager.baseconfig.get("Core-IP");
            this.port = FileManager.baseconfig.getInt("Core-Port");
            connectToCore();
        }
    public void sendTheCoreAMessage(String message) {
        try {
            OutputStream out = this.socket.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);
            writer.write(message + "\n");
            writer.flush();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
        public void connectToCore() {
            this.socket = new Socket();
            try {
                System.out.println(Main.getPrefix() + "Trying to connect to core...");
                socket.connect(new InetSocketAddress(this.ip, this.port));
            } catch (Exception e) {
                System.out.println(Main.getPrefix() + Main.ANSI_RED + "Connection failed." + Main.ANSI_RESET);
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                connectToCore();
                return;
            }
            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "Base is successfully connected to the core." + Main.ANSI_RESET);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String line;
                        BufferedReader r = new BufferedReader(new InputStreamReader(socket.getInputStream(), "UTF8"));
                        while((line = r.readLine()) != null) {
                            if(line.startsWith("CREATEPROXYGROUP")) {
                                String[] args = line.split(" ");
                                String groupName = args[1];
                                int onlineAmount = Integer.parseInt(args[2]);
                                int servers = Integer.parseInt(args[3]);
                                int maxRam = Integer.parseInt(args[4]);
                                Boolean dinamic = Boolean.parseBoolean(args[5]);
                                int maxPlayers = Integer.parseInt(args[6]);
                                String proxyPorts = args[7];
                                ProxyManager.createProxyGroup(groupName, maxRam, dinamic, servers, onlineAmount, maxPlayers, 25565, proxyPorts);
                            } else if(line.startsWith("CREATESERVERGROUP")) {
                                String[] args = line.split(" ");
                                String groupName = args[1];
                                int onlineAmount = Integer.parseInt(args[2]);
                                int servers = Integer.parseInt(args[3]);
                                int maxRam = Integer.parseInt(args[4]);
                                Boolean dinamic = Boolean.parseBoolean(args[5]);
                                int maxPlayers = Integer.parseInt(args[6]);
                                ServerManager.createServerGroup(groupName, maxRam, dinamic, servers, onlineAmount, maxPlayers, 40000);
                            } else if(line.startsWith("STARTPROXYSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String group = server.split("-")[0];
                                int id = Integer.parseInt(server.split("-")[1]);
                                if(Init.proxy_groups.containsKey(group)) {
                                    Init.proxy_groups.get(group).startServerOutGroup(id);
                                }
                            } else if(line.startsWith("STOPPROXYSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String group = server.split("-")[0];
                                int id = Integer.parseInt(server.split("-")[1]);
                                if(Init.proxy_groups.containsKey(group)) {
                                    Init.proxy_groups.get(group).stopServerOutGroup(id);
                                }
                            } else if(line.startsWith("STARTPROXYGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                if(Init.proxy_groups.containsKey(group)) {
                                    Init.proxy_groups.get(group).startAllServersOutGroup();
                                }
                            } else if(line.startsWith("STOPPROXYGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                if(Init.proxy_groups.containsKey(group)) {
                                    Init.proxy_groups.get(group).stopAllServersOutGroup();
                                }
                            } else if(line.startsWith("STARTSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String group = server.split("-")[0];
                                int id = Integer.parseInt(server.split("-")[1]);
                                if(Init.game_groups.containsKey(group)) {
                                    Init.game_groups.get(group).startServerOutGroup(id);
                                }
                            } else if(line.startsWith("STOPSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String group = server.split("-")[0];
                                int id = Integer.parseInt(server.split("-")[1]);
                                if(Init.game_groups.containsKey(group)) {
                                    Init.game_groups.get(group).stopServerOutGroup(id);
                                }
                            } else if(line.startsWith("STARTGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                if(Init.game_groups.containsKey(group)) {
                                    Init.game_groups.get(group).startAllServersOutGroup();
                                }
                            } else if(line.startsWith("STOPGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                if(Init.game_groups.containsKey(group)) {
                                    Init.game_groups.get(group).stopAllServersOutGroup();
                                }
                            } else if(line.startsWith("REMOVEGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                if(Init.game_groups.containsKey(group)) {
                                    ServerManager.removeServerGroup(group);
                                } else if(Init.proxy_groups.containsKey(group)) {
                                    ProxyManager.removeProxyGroup(group);
                                }
                            } else if(line.startsWith("RESTARTSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String group = server.split("-")[0];
                                if(Init.game_groups.containsKey(group)) {
                                    if(Init.game_servers.get(server) != null) {
                                        Init.game_servers.get(server).restartServer();
                                    }
                                } else if(Init.proxy_groups.containsKey(group)) {
                                    if(Init.proxy_servers.get(server) != null) {
                                        Init.proxy_servers.get(server).restartServer();
                                    }
                                }
                            } else if(line.startsWith("RESTARTGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                if(Init.game_groups.containsKey(group)) {
                                    for(GameServer server : Init.game_servers.values()) {
                                        if(server.getGroup().equalsIgnoreCase(group)) {
                                            server.restartServer();
                                        }
                                    }
                                } else if(Init.proxy_groups.containsKey(group)) {
                                    for(ProxyServer server : Init.proxy_servers.values()) {
                                        if(server.getGroup().equalsIgnoreCase(group)) {
                                            server.restartServer();
                                        }
                                    }
                                }
                            } else if(line.startsWith("SENDCOMMANDTOGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                String command = "#ahsgdjqlwdon";
                                for(int i = 2; i < args.length; i++) {
                                    command = command + " " + args[i];
                                }
                                command = command.replaceAll("#ahsgdjqlwdon ", "");
                                if(Init.proxy_groups.containsKey(group)) {
                                    for(ProxyServer server : Init.proxy_servers.values()) {
                                        if(server.getGroup().equalsIgnoreCase(group)) {
                                            server.sendTheServerACommand(command);
                                        }
                                    }
                                } else if(Init.game_groups.containsKey(group)) {
                                    for(GameServer server : Init.game_servers.values()) {
                                        if(server.getGroup().equalsIgnoreCase(group)) {
                                            server.sendTheServerACommand(command);
                                        }
                                    }
                                }
                            } else if(line.startsWith("SENDCOMMANDTOSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String command = "#ahsgdjqlwdon";
                                for(int i = 2; i < args.length; i++) {
                                    command = command + " " + args[i];
                                }
                                command = command.replaceAll("#ahsgdjqlwdon ", "");
                                if(Init.proxy_servers.containsKey(server)) {
                                    Init.proxy_servers.get(server).sendTheServerACommand(command);
                                } else if(Init.game_servers.containsKey(server)) {
                                    Init.game_servers.get(server).sendTheServerACommand(command);
                                }
                            } else if(line.startsWith("STARTFALLBACK")) {
                                String[] args = line.split(" ");
                                String g = args[1];
                                Integer os = 0;
                                ServerGroup group = Init.game_groups.get(g);
                                for(GameServer server : Init.game_servers.values()) {
                                    if(server.getGroup().equals(group.getName())) {
                                        os++;
                                    }
                                }
                                os++;
                                group.startServerOutGroup(os);
                            } else if(line.startsWith("STOPFALLBACK")) {
                                String[] args = line.split(" ");
                                String g = args[1];
                                Integer os = 0;
                                ServerGroup group = Init.game_groups.get(g);
                                for(GameServer server : Init.game_servers.values()) {
                                    if(server.getGroup().equals(group.getName())) {
                                        os++;
                                    }
                                }
                                group.stopServerOutGroup(os);
                            } else if(line.startsWith("RESTARTAUTOSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                Init.game_servers.get(server).restartServer();
                            } else if(line.startsWith("EDITGROUP")) {
                                String[] args = line.split(" ");
                                String group = args[1];
                                String value = args[2];
                                String arg = args[3];
                                if(value.equalsIgnoreCase("onlineAmount")) {
                                    Init.game_groups.get(group).editOnlineServerValue(Integer.parseInt(arg));
                                } else if(value.equalsIgnoreCase("maxAmount")) {
                                    Init.game_groups.get(group).editMaxServerValue(Integer.parseInt(arg));
                                } else if(value.equalsIgnoreCase("maxPlayers")) {
                                    Init.game_groups.get(group).editMaxPlayers(Integer.parseInt(arg));
                                } else if(value.equalsIgnoreCase("maxRam")) {
                                    Init.game_groups.get(group).editMaxRam(Integer.parseInt(arg));
                                } else if(value.equalsIgnoreCase("dynamic")) {
                                    Init.game_groups.get(group).editDynamic(Boolean.parseBoolean(arg));
                                }
                            } else if(line.startsWith("COPYDIR")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                File dir = new File("./Base/templates/" + server.split("-")[0] + "/");
                                if(dir.exists()) {
                                    ServerManager.delete(dir);
                                }
                                dir.mkdirs();
                                FileManager.copyDir("./Base/temporary/" + server.split("-")[0] + "/" + server + "/", "./Base/templates/" + server.split("-")[0] + "/");
                                System.out.println(Main.getPrefix() + "The template was created successfully!");
                            }
                        }
                    } catch (IOException e) {
                        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Connection lost." + Main.ANSI_RESET);
                        try {
                            Thread.sleep(3000);
                        } catch (InterruptedException e1) {
                        }
                        connectToCore();
                        return;
                    }
                }
            }).start();
            if(Init.accesToStartServers) {
                sendTheCoreAMessage("UPDATESERVERSTARTBOOLEAN true");
            } else {
                sendTheCoreAMessage("UPDATESERVERSTARTBOOLEAN false");
            }
    }
    public String getIP() {
        return ip;
    }
    public Integer getPort() {
        return port;
    }
}