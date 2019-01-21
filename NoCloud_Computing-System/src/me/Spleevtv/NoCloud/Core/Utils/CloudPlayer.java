package me.Spleevtv.NoCloud.Core.Utils;

import java.util.UUID;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Sep, 2018
 */
public class CloudPlayer {

    private String name;
    private UUID uuid;
    private String currentserver;
    private BungeeCord proxy;

    public CloudPlayer(String playername, String puuid, String server, BungeeCord pproxy) {
        this.name = playername;
        this.uuid = UUID.fromString(puuid);
        this.currentserver = server;
        this.proxy = pproxy;
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
    public BungeeCord getProxy() {
        return this.proxy;
    }
}