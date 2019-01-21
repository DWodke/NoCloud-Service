package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class ProxyGroup {

    private String name;
    private int maxram;
    private Boolean haveTemplate;
    private int servers;
    private int serveronstart;
    private int maxplayers;
    private HashMap<String, Integer> ports = new HashMap<String, Integer>();
    private HashMap<String, Integer> connection_ports = new HashMap<String, Integer>();

    public ProxyGroup(String n, int m, Boolean h, int s, int sonstart, int max, String portString, String connectionPorts) {
        this.name = n;
        this.maxram = m;
        this.haveTemplate = h;
        this.servers = s;
        this.serveronstart = sonstart;
        this.maxplayers = max;
        String[] strings = portString.split(",");
        for(String cache : strings) {
            String key = cache.split(":")[0];
            String value = cache.split(":")[1];
            ports.put(key, Integer.parseInt(value));
        }
        String[] strings2 = connectionPorts.split(",");
        for(String cache : strings2) {
            String key = cache.split(":")[0];
            String value = cache.split(":")[1];
            connection_ports.put(key, Integer.parseInt(value));
        }
        startServersOutGroup(this.serveronstart);
    }
    public Boolean getHaveTemplate() {
        return haveTemplate;
    }
    public String getName() {
        return name;
    }
    public int getMaxRam() {
        return maxram;
    }
    public int getMaxServerValue() {
        return servers;
    }
    public int getServeronstart() {
        return serveronstart;
    }
    public int getMaxPlayers() {
        return maxplayers;
    }
    public void editMaxRam(int max) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.maxram = max;
        cfg.set(this.name + ".MaxRam", max + "");
        cfg.save();
        cfg.unload();
    }
    public void editServers(int server_value) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.servers = server_value;
        cfg.set(this.name + ".ServerValue", server_value + "");
        cfg.save();
        cfg.unload();
    }
    public void editHaveTemplate(Boolean template) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.haveTemplate = template;
        cfg.set(this.name + ".HaveTemplate", template + "");
        cfg.save();
        cfg.unload();
    }
    public void editServerOnStart(int servers) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.serveronstart = servers;
        cfg.set(this.name + ".ServerOnStart", servers + "");
        cfg.save();
        cfg.unload();
    }
    public void editMaxPlayers(int max) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.maxplayers = max;
        cfg.set(this.name + ".MaxPlayers", max + "");
        cfg.save();
        cfg.unload();
    }
    public void addPorts(String p) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        String updatedPorts = cfg.get(this.name + ".Ports") + p;
        cfg.set(this.name + ".Ports", updatedPorts);
        cfg.save();
        cfg.unload();
        String[] strings = updatedPorts.split(",");
        ports.clear();
        for(String cache : strings) {
            String key = cache.split(":")[0];
            String value = cache.split(":")[1];
            ports.put(key, Integer.parseInt(value));
        }
    }
    public void startAllServersOutGroup() {
        for(int i = 1; i <= this.servers; i++) {
            String name = this.name + "-" + i;
            ProxyServer proxy = new ProxyServer(name, this.ports.get(name), this, this.maxram, this.haveTemplate, this.maxplayers, this.connection_ports.get(name));
            proxy.startServer();
        }
    }
    public void stopAllServersOutGroup() {
        for(int i = 1; i <= this.servers; i++) {
            String name = this.name + "-" + i;
            if(Init.proxy_servers.containsKey(name)) {
                Init.proxy_servers.get(name).stopServer();
            }
        }
    }
    public void startServerOutGroup(int id) {
            String name = this.name + "-" + id;
        if(id > this.getMaxServerValue()) {
            return;
        }
            if(!Init.proxy_servers.containsKey(name)) {
                new ProxyServer(name, this.ports.get(name), this, this.maxram, this.haveTemplate, this.maxplayers, this.connection_ports.get(name)).startServer();
            }
    }
    public void stopServerOutGroup(int id) {
        String name = this.name + "-" + id;
        if(id > this.getMaxServerValue()) {
            return;
        }
        if(Init.proxy_servers.containsKey(name)) {
            Init.proxy_servers.get(name).stopServer();
        }
    }
    public void startServersOutGroup(int ser) {
        ArrayList<String> serverNotStarted = new ArrayList<String>();
        for(int i = 1; i <= this.servers; i++) {
            String name = this.name + "-" + i;
            if(!Init.proxy_servers.containsKey(name)) {
                serverNotStarted.add(i + "");
            }
        }
        for(int i = 0; i < ser; i++) {
            startServerOutGroup(Integer.parseInt(serverNotStarted.get(i)));
        }
    }
}