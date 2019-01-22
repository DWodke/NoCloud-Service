package me.Spleevtv.objekts;

import me.Spleevtv.main.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class GameServer {

    private String name;
    private ServerState state;
    private Integer onlineplayers;
    private Integer maxplayers;
    private String base;
    private String hostname;
    private Integer port;

    public GameServer(String servername, ServerState serverstate, Integer op, Integer mp, String serverbase, String ip, Integer p) {
        this.name = servername;
        this.state = serverstate;
        this.onlineplayers = op;
        this.maxplayers = mp;
        this.base = serverbase;
        this.port = p;
        this.hostname = ip;
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
    public void updateMaxPlayers(Integer arg0) {
        this.maxplayers = arg0;
    }
    public void updateState(ServerState arg0) {
        this.state = arg0;
        Main.core.sendMessage("UPDATESTATE " + this.name + " " + state);
    }
    public Integer getPort() {
        return port;
    }
    public String getHostName() {
        return hostname;
    }
}
