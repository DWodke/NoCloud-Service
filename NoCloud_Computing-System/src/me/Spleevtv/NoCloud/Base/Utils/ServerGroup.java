package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class ServerGroup {

    private String name;
    private int maxram;
    private Boolean haveTemplate;
    private int maxamount;
    private int onlineamount;
    private int maxplayers;
    private HashMap<String, Integer> ports = new HashMap<String, Integer>();

    public ServerGroup(String n, int m, Boolean h, int s, int sonstart, int max, String portString) {
        this.name = n;
        this.maxram = m;
        this.haveTemplate = h;
        this.maxamount = s;
        this.onlineamount = sonstart;
        this.maxplayers = max;
        String[] strings = portString.split(",");
        for(String cache : strings) {
            String key = cache.split(":")[0];
            String value = cache.split(":")[1];
            ports.put(key, Integer.parseInt(value));
        }
        startServersOutGroup(this.onlineamount);
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
        return maxamount;
    }
    public int getOnlineAmount() {
        return onlineamount;
    }
    public int getMaxPlayers() {
        return maxplayers;
    }
    public int getPortFromServer(String arg0) {
        return this.ports.get(arg0);
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
    public void editMaxServerValue(int server_value) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.maxamount = server_value;
        cfg.set(this.name + ".ServerValue", server_value + "");
        cfg.save();
        cfg.unload();
    }
    public void editDynamic(Boolean template) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.haveTemplate = template;
        cfg.set(this.name + ".HaveTemplate", template + "");
        cfg.save();
        cfg.unload();
    }
    public void editOnlineServerValue(int i) {
        File settings = new File("./Base/groups_settings.yml");
        Config cfg = new Config(settings);
        cfg.load();
        this.onlineamount = i;
        cfg.set(this.name + ".ServerOnStart", i + "");
        cfg.save();
        cfg.unload();
        if(Init.game_servers.size() < i) {
            Integer def = i - Init.game_servers.size();
            this.startServersOutGroup(def);
        }
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
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            GameServer server = new GameServer(name, this.ports.get(name), this, this.maxram, this.haveTemplate, this.maxplayers);
            server.startServer();
        }
    }
    public void stopAllServersOutGroup() {
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            if(Init.game_servers.containsKey(name)) {
                Init.game_servers.get(name).stopServer();
            }
        }
    }
    public void startServerOutGroup(int id) {
        String name = this.name + "-" + id;
        if(id > this.getMaxServerValue()) {
            return;
        }
        if(!Init.game_servers.containsKey(name)) {
            new GameServer(name, this.ports.get(name), this, this.maxram, this.haveTemplate, this.maxplayers).startServer();
        }
    }
    public void stopServerOutGroup(int id) {
        String name = this.name + "-" + id;
        if(id > this.getMaxServerValue()) {
            return;
        }
        if(Init.game_servers.containsKey(name)) {
            Init.game_servers.get(name).stopServer();
        }
    }
    public void startServersOutGroup(int ser) {
        ArrayList<String> serverNotStarted = new ArrayList<String>();
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            File temp = new File("./Base/temporary/" + this.name + "/" + name + "/");
            if(temp.exists()) {
                this.delete(temp);
            }
            if(!Init.game_servers.containsKey(name)) {
                serverNotStarted.add(i + "");
            }
        }
        if(serverNotStarted.size() < ser) {
            Integer def = ser - serverNotStarted.size();
            ServerManager.addServersToGroup(def, this.getName(), );
        }
        for(int i = 0; i < ser; i++) {
            startServerOutGroup(Integer.parseInt(serverNotStarted.get(i)));
        }
    }
    private void delete(File dir){
        if (dir.isDirectory()){
            String[] entries = dir.list();
            for (int x=0;x<entries.length;x++){
                File aktFile = new File(dir.getPath(),entries[x]);
                delete(aktFile);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
}