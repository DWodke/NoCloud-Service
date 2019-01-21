package me.Spleevtv.objekts;

import java.util.UUID;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Okt, 2018
 */
public class CloudPlayer {

    private String name;
    private UUID uuid;
    private String currentserver;

    public CloudPlayer(String playername, String puuid, String server) {
        this.name = playername;
        this.uuid = UUID.fromString(puuid);
        this.currentserver = server;
    }
    public String getName() {
        return name;
    }
    public UUID getUUID() {
        return uuid;
    }
    public String getCurrentServer() {
        return currentserver;
    }
    public void setCurrentserver(String arg0) {
        this.currentserver = arg0;
    }
}
