package me.Spleevtv.objekts;

import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;

import java.net.Proxy;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class GameServer {

    private String name;
    private ServerState state;
    private Integer onlineplayers;
    private Integer maxplayers;
    private String base;
    private ServerInfo info;

    public GameServer(String servername, ServerState serverstate, Integer op, Integer mp, String serverbase) {
        this.name = servername;
        this.state = serverstate;
        this.onlineplayers = op;
        this.maxplayers = mp;
        this.base = serverbase;
        this.info = ProxyServer.getInstance().getServerInfo(this.name);
    }
    public void updateOnlinePlayer(Integer arg0) {
        this.onlineplayers = arg0;
    }
    public String getName() {
        return name;
    }
    public Integer getMaxPlayers() {
        return maxplayers;
    }
    public Integer getOnlinePlayers() {
        return onlineplayers;
    }
    public ServerState getState() {
        return state;
    }
    public String getBase() {
        return base;
    }
    public ServerInfo getInfo() {
        return info;
    }
    public void setState(ServerState state) {
        this.state = state;
    }
}
