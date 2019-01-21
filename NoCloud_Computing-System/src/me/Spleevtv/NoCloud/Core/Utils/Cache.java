package me.Spleevtv.NoCloud.Core.Utils;

import java.io.File;

public class Cache {

    private int spigot_ports;
    private int bungee_ports;
    private int proxy_connection_port;
    private int bungeeCords;

    public Cache() {
        File cache = new File("./Core/Cache.yml");
        Config cfg = new Config(cache);
        cfg.load();
        this.spigot_ports = cfg.getInt("Spigot_Ports");
        this.bungee_ports = cfg.getInt("BungeeCord_Ports");
        this.proxy_connection_port = cfg.getInt("ProxyConnectPort");
        this.bungeeCords = cfg.getInt("BungeeCords");
        cfg.unload();
    }
    public int getBungeeAnfangsPort() {
        return bungee_ports;
    }
    public int getBungeeCords() {
        return bungeeCords;
    }
    public int getProxy_connection_port() {
        return proxy_connection_port;
    }
    public int getSpigotAnfangPorts() {
        return spigot_ports;
    }
    public void editBungeeAnfangsPort(int port) {
        File cache = new File("./Core/Cache.yml");
        Config cfg = new Config(cache);
        cfg.load();
        this.bungee_ports = port;
        cfg.set("BungeeCord_Ports", port + "");
        cfg.save();
        cfg.unload();
    }
    public void editSpigotAnfangsPort(int port) {
        File cache = new File("./Core/Cache.yml");
        Config cfg = new Config(cache);
        cfg.load();
        this.spigot_ports = port;
        cfg.set("Spigot_Ports", port + "");
        cfg.save();
        cfg.unload();
    }
    public void editBungeeCords(int cords) {
        File cache = new File("./Core/Cache.yml");
        Config cfg = new Config(cache);
        cfg.load();
        this.bungeeCords = cords;
        cfg.set("BungeeCords", cords + "");
        cfg.save();
        cfg.unload();
    }
    public void editProxyConnectionPorts(int p) {
        File cache = new File("./Core/Cache.yml");
        Config cfg = new Config(cache);
        cfg.load();
        this.proxy_connection_port = p;
        cfg.set("ProxyConnectPort", p + "");
        cfg.save();
        cfg.unload();
    }
}
