package me.Spleevtv.NoCloud.Core.Utils;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Main;

import java.awt.*;
import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.SocketAddress;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class Base {

    private String name;
    private Integer port;
    private ServerSocket socket;
    private Socket chatsocket;
    private Boolean accesToStartServer;
    private Base instance;

    public Base(String n, int p) {
        this.port = p;
        this.name = n;
        this.instance = this;
        startConnectingToBase();
    }
    public void sendTheBaseAMessage(String message) {
        try {
            OutputStream out = this.chatsocket.getOutputStream();
            PrintWriter writer = new PrintWriter(new OutputStreamWriter(out, StandardCharsets.UTF_8), true);
            writer.write(message + "\n");
            writer.flush();
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void startConnectingToBase() {
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
                            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "The base " + Main.ANSI_RESET + "'" + Main.ANSI_YELLOW + name + Main.ANSI_RESET + "'" + Main.ANSI_GREEN + " is successfully connected to the socket-channel" + Main.ANSI_RESET + ".");
                            chatsocket = s;
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
                            if(line.startsWith("UPDATESERVERSTARTBOOLEAN")) {
                                String[] args = line.split(" ");
                                Boolean b = Boolean.parseBoolean(args[1]);
                                accesToStartServer = b;
                            } else if(line.startsWith("STARTSPIGOTSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                String port = args[2];
                                if(!Init.online_servers.containsKey(server)) {
                                    Init.online_servers.put(server, new String[]{chatsocket.getLocalAddress().getHostAddress(), port});
                                    System.out.println(Main.getPrefix() + "The server '" + server + "' load all configuration-files.");
                                    Init.time.put(server, System.currentTimeMillis());
                                }
                                if(!Init.gameserver.containsKey(server)) {
                                    Init.gameserver.put(server, new GameServer(server, Integer.parseInt(port), Integer.parseInt(args[3]), Integer.parseInt(args[4]), instance, Integer.parseInt(args[5])));
                                }
                            } else if(line.startsWith("STOPSPIGOTSERVER")) {
                                String[] args = line.split(" ");
                                String server = args[1];
                                if(Init.online_servers.containsKey(server)) {
                                    Init.online_servers.remove(server);
                                    for(BungeeCord proxy : Init.proxys) {
                                        proxy.sendTheProxyAMessage("STOPSPIGOTSERVER " + server);
                                    }
                                }
                            }
                        } else {
                            if(r.read() == -1) {
                                System.out.println(Main.getPrefix() + Main.ANSI_RED + "The base " + Main.ANSI_RESET + "'" + Main.ANSI_YELLOW + name + Main.ANSI_RESET + "'" + Main.ANSI_RED + " is disconnected" + Main.ANSI_RESET + ".");
                                for(GameServer server : Init.gameserver.values()) {
                                    if(server.getCurrentbase() == instance) {
                                        Init.gameserver.remove(server.getServername());
                                        System.out.println(Main.getPrefix() + "The server " + server.getServername() + " has stop now.");
                                    }
                                }
                                startConnectingToBase();
                                return;
                            }
                        }
                    }
                } catch (IOException e) {
                    System.out.println(Main.getPrefix() + Main.ANSI_RED + "The base " + Main.ANSI_RESET + "'" + Main.ANSI_YELLOW + name + Main.ANSI_RESET + "'" + Main.ANSI_RED + " is disconnected" + Main.ANSI_RESET + ".");
                    startConnectingToBase();
                    return;
                }
            }
        }).start();
    }
    public Integer getPort() {
        return port;
    }
    public String getName() {
        return name;
    }

    public Boolean getAccesToStartServer() {
        return accesToStartServer;
    }
}
