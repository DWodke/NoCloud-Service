package me.Spleevtv.NoCloud.Core.Utils;

import com.sun.security.ntlm.Server;
import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Main;

import javax.swing.text.Segment;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class ServerChannel {

    private Integer port;
    private ServerSocket serversocket;

    public ServerChannel() {
        this.port = 13000;
        try {
            this.serversocket = new ServerSocket(this.port);
        } catch (IOException e) {
            e.printStackTrace();
        }
        read();
    }
    private void read() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                Socket socket;
                try {
                    while((socket = serversocket.accept()) != null) {
                        String message = "";
                        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                        message = reader.readLine();
                        if(message.startsWith("REGISTERSERVER")) {
                            String[] args = message.split(" ");
                            Double elipsedTime = (double) System.currentTimeMillis() - Init.time.get(args[1]);
                            elipsedTime = elipsedTime / 1000;
                            System.out.println(Main.getPrefix() + "The server '" + args[1] + "' is registered now. [" + round(elipsedTime) + " seconds]");
                            Init.time.replace(args[1], System.currentTimeMillis());
                            GameServer server = Init.gameserver.get(args[1]);
                            for(BungeeCord proxy : Init.proxys) {
                                proxy.sendTheProxyAMessage("STARTSPIGOTSERVER " + args[1]
                                        + " " + Init.online_servers.get(args[1])[0]
                                        + " " + Init.online_servers.get(args[1])[1]
                                        + " " + server.getGroup()
                                        + " " + server.getCurrentbase().getName()
                                        + " " + server.getMaxPlayers()
                                        + " " + server.getSize()
                                        + " " + server.getState());
                            }
                        } else if(message.startsWith("UNREGISTERSERVER")) {
                            String[] args = message.split(" ");
                            System.out.println(Main.getPrefix() + "The server '" + args[1] + "' is unregistered now.");
                            if(Init.gameserver.get(args[1]).getRestartState()) {
                                Init.baselist.getBaselist().get(Base_Servers.getBase(args[1].split("-")[0])).sendTheBaseAMessage("RESTARTAUTOSERVER " + args[1]);
                            }
                            if(Init.gameserver.containsKey(args[1])) {
                                Init.gameserver.remove(args[1]);
                            }
                        } else if(message.startsWith("UPDATESTATE")) {
                            String[] args = message.split(" ");
                            String server = args[1];
                            GameServer gameserver = Init.gameserver.get(server);
                            if(gameserver != null) {
                                if(args[2].equals("LOBBY")) {
                                    gameserver.setState(ServerState.LOBBY);
                                } else if(args[2].equals("INGAME")) {
                                    gameserver.setState(ServerState.INGAME);
                                } else if(args[2].equals("ENDING")) {
                                    gameserver.setState(ServerState.ENDING);
                                } else if(args[2].equals("ONLINE")) {
                                    gameserver.setState(ServerState.ONLINE);
                                } else {
                                    gameserver.setState(ServerState.ONLINE);
                                }
                                for(BungeeCord proxy : Init.proxys) {
                                    proxy.sendTheProxyAMessage("UPDATESERVERSTATE " + server + " " + args[2]);
                                }
                            }
                        } else if(message.startsWith("JOINPLAYER")) {
                            String[] args = message.split(" ");
                            String uuid = args[1];
                            String server = args[2];
                            GameServer gameserver = Init.gameserver.get(server);
                            gameserver.addPlayer(Init.players.getPlayerList().get(uuid));
                            Init.players.getPlayerList().get(uuid).setCurrentserver(server);
                            for(BungeeCord proxy : Init.proxys) {
                                proxy.sendTheProxyAMessage("UPDATESERVERPLAYERSIZE " + server + " " + gameserver.getSize());
                            }
                        } else if(message.startsWith("QUITPLAYER")) {
                            String[] args = message.split(" ");
                            String uuid = args[1];
                            String server = args[2];
                            GameServer gameserver = Init.gameserver.get(server);
                            gameserver.removePlayer(uuid);
                            for(BungeeCord proxy : Init.proxys) {
                                proxy.sendTheProxyAMessage("UPDATESERVERPLAYERSIZE " + server + " " + gameserver.getSize());
                            }
                        }
                        socket.close();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }
    public double round(double betrag) {
        double round = Math.round(betrag*10000);
        round = round / 10000;
        round = Math.round(round*1000);
        round = round / 1000;
        round = Math.round(round*100);
        return round / 100;
    }
}
