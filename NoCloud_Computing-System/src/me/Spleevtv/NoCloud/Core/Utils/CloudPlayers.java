package me.Spleevtv.NoCloud.Core.Utils;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Sep, 2018
 */
public class CloudPlayers {

    private HashMap<String, CloudPlayer> players;

    public CloudPlayers() {
        this.players = new HashMap<>();
    }
    public Integer getPlayerSize() {
        return this.players.size();
    }
    public void joinPlayer(String arg0, String arg1, String arg2, BungeeCord args3) {
        if(!players.containsKey(arg1)) {
            players.put(arg1, new CloudPlayer(arg0, arg1, arg2, args3));
            if(!Init.playerstats.checkPlayer(arg1)) {
                for(BungeeCord proxy : Init.proxys) {
                    proxy.sendTheProxyAMessage("NEWUSERREGISTERED " + arg0);
                }
            }
            System.out.println(Main.getPrefix() + "Player '" + arg0 + "' connected successfully.");
        }
    }
    public void quitPlayer(String arg0) {
        CloudPlayer oldplayer = players.get(arg0);
        if(players.containsValue(oldplayer)) {
            String name = oldplayer.getName();
            players.remove(arg0);
            System.out.println(Main.getPrefix() + "Player '" + name + "' disconnected successfully.");
        }
    }
    public HashMap<String, CloudPlayer> getPlayerList() {
        return this.players;
    }
}
