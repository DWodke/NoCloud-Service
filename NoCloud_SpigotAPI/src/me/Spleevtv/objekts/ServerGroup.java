package me.Spleevtv.objekts;

import java.util.HashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class ServerGroup {

    private String name;
    private HashMap<String, GameServer> server = new HashMap<String, GameServer>();

    public ServerGroup(String groupname) {
        this.name = groupname;
    }
    public GameServer addServer(GameServer gameserver) {
        server.put(gameserver.getName(), gameserver);
        return gameserver;
    }
    public void removeServer(String name) {
        server.remove(name);
    }
    public GameServer getServerOutGroup(String name) {
        return server.get(name);
    }
    public String getName() {
        return name;
    }
    public HashMap<String, GameServer> getServers() {
        return server;
    }

}
