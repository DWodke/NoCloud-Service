package me.Spleevtv.NoCloud.Core.Utils;


import me.Spleevtv.NoCloud.Core.Init;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class GameServer {

    private HashMap<String, CloudPlayer> player;
    private String servername;
    private String group;
    private Integer serverport;
    private Integer maxram;
    private Integer maxplayers;
    private Base currentbase;
    private Integer maxservervalue;
    private Boolean restartstate;
    private ServerState state;

    public GameServer(String name, Integer port, Integer maxp, Integer maxr, Base base, Integer max) {
        this.servername = name;
        this.serverport = port;
        this.group = this.servername.split("-")[0];
        this.player = new HashMap<String, CloudPlayer>();
        this.maxplayers = maxp;
        this.maxram = maxr;
        this.currentbase = base;
        this.maxservervalue = max;
        this.restartstate = true;
        this.state = ServerState.ONLINE;
    }
    public void addPlayer(CloudPlayer p) {
        if(!this.player.containsValue(p)) {
            this.player.put(p.getUUID().toString(), p);
            if(this.group.equalsIgnoreCase(Init.bungee_template.getFallbackServerGroup())) {
                double end = 0;
                Integer i = 0;
                Integer op = 0;
                for(GameServer server : Init.gameserver.values()) {
                    if(server.getGroup().equalsIgnoreCase(this.group)) {
                        i++;
                        op = op + server.getPlayers().size();
                    }
                }
                end = this.maxplayers * i;
                end = end / 100;
                end = end * Init.bungee_template.getServerPercent();
                if(op >= end) {
                    Integer os = 0;
                    for(GameServer server : Init.gameserver.values()) {
                        if(server.getGroup().equals(this.group)) {
                            os++;
                        }
                    }
                    os++;
                    if(os > this.maxservervalue) {
                        return;
                    }
                    Init.addedfallbackserver++;
                    Init.latestplayer.put(Init.addedfallbackserver + "", op);
                    this.currentbase.sendTheBaseAMessage("STARTFALLBACK " + this.group);
                    return;
                }
            }
        }
    }
    public void removePlayer(String uuid) {
        if(this.player.containsKey(uuid)) {
            this.player.remove(uuid);
            Integer op = 0;
            for(GameServer server : Init.gameserver.values()) {
                if(server.getGroup().equalsIgnoreCase(this.group)) {
                    op = op + server.getPlayers().size();
                }
            }
            if(Init.addedfallbackserver > 0) {
                if(op < Init.latestplayer.get(Init.addedfallbackserver + "")) {
                    this.currentbase.sendTheBaseAMessage("STOPFALLBACK " + this.group);
                    Init.latestplayer.remove(Init.addedfallbackserver + "");
                    Init.addedfallbackserver--;
                }
            }
        }
    }
    public Integer getSize() {
        return this.player.size();
    }
    public HashMap<String, CloudPlayer> getPlayers() {
        return player;
    }
    public Integer getMaxPlayers() {
        return maxplayers;
    }
    public String getGroup() {
        return group;
    }
    public Base getCurrentbase() {
        return currentbase;
    }
    public String getServername() {
        return servername;
    }
    public void setRestartState(Boolean b) {
        this.restartstate = b;
    }
    public Boolean getRestartState() {
        return restartstate;
    }
    public void setState(ServerState state) {
        this.state = state;
    }
    public ServerState getState() {
        return state;
    }
    public Integer getMaxram() {
        return maxram;
    }
}
