package me.Spleevtv.NoCloud.Base.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.Spleevtv.NoCloud.Base.Init;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
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
    private int startPort;

    public ServerGroup(String n, int m, Boolean h, int s, int sonstart, int max, int startp) {
        this.name = n;
        this.maxram = m;
        this.haveTemplate = h;
        this.maxamount = s;
        this.onlineamount = sonstart;
        this.maxplayers = max;
        this.startPort = startp;
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
    public void editMaxRam(int max) {
        this.maxram = max;
        File settings = new File("./Base/GroupSettings/" + this.name + ".json");
        try {
            if(settings.exists()) {
                settings.delete();
            }
            settings.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(settings), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("startPort", startPort);
            json.addProperty("onlineAmount", onlineamount);
            json.addProperty("maxAmount", maxamount);
            json.addProperty("maxPlayers", maxplayers);
            json.addProperty("maxRam", maxram);
            json.addProperty("dynamic", haveTemplate);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.restartAllServersOutGroup();
    }
    public void editMaxServerValue(int server_value) {
        this.maxamount = server_value;
        File settings = new File("./Base/GroupSettings/" + this.name + ".json");
        try {
            if(settings.exists()) {
                settings.delete();
            }
            settings.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(settings), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("startPort", startPort);
            json.addProperty("onlineAmount", onlineamount);
            json.addProperty("maxAmount", maxamount);
            json.addProperty("maxPlayers", maxplayers);
            json.addProperty("maxRam", maxram);
            json.addProperty("dynamic", haveTemplate);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.getOnlineServer() > this.maxamount) {
            Integer stop = this.getOnlineServer() - this.maxamount;
            this.stopServersOutGroup(stop);
        }
    }
    public void editDynamic(Boolean template) {
        File settings = new File("./Base/GroupSettings/" + this.name + ".json");
        this.haveTemplate = template;
        try {
            if(settings.exists()) {
                settings.delete();
            }
            settings.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(settings), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("startPort", startPort);
            json.addProperty("onlineAmount", onlineamount);
            json.addProperty("maxAmount", maxamount);
            json.addProperty("maxPlayers", maxplayers);
            json.addProperty("maxRam", maxram);
            json.addProperty("dynamic", haveTemplate);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        this.restartAllServersOutGroup();
    }
    public void editOnlineServerValue(int i) {
        this.onlineamount = i;
        File settings = new File("./Base/GroupSettings/" + this.name + ".json");
        try {
            if(settings.exists()) {
                settings.delete();
            }
            settings.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(settings), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("startPort", startPort);
            json.addProperty("onlineAmount", onlineamount);
            json.addProperty("maxAmount", maxamount);
            json.addProperty("maxPlayers", maxplayers);
            json.addProperty("maxRam", maxram);
            json.addProperty("dynamic", haveTemplate);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.getOnlineServer() < this.onlineamount) {
            this.startAllOnlineAmountServer();
        } else if(this.getOnlineServer() == this.onlineamount) {
            return;
        } else {
            this.stopAllServerOverOnlineAmount();
        }
    }
    public void editMaxPlayers(int max) {
        this.maxplayers = max;
        File settings = new File("./Base/GroupSettings/" + this.name + ".json");
        try {
            if(settings.exists()) {
                settings.delete();
            }
            settings.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(settings), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("name", name);
            json.addProperty("startPort", startPort);
            json.addProperty("onlineAmount", onlineamount);
            json.addProperty("maxAmount", maxamount);
            json.addProperty("maxPlayers", maxplayers);
            json.addProperty("maxRam", maxram);
            json.addProperty("dynamic", haveTemplate);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void startAllServersOutGroup() {
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            GameServer server = new GameServer(name, ServerProcessManager.getNextFreePort(this.startPort), this, this.maxram, this.haveTemplate, this.maxplayers);
            server.startServer();
        }
    }
    public Boolean startNextServer() {
        for(int i = 1; i <= this.onlineamount; i++) {
            String name = this.name + "-" + i;
            if(!Init.game_servers.containsKey(name)) {
                startServerOutGroup(i);
                return true;
            }
        }
        return false;
    }
    public void restartAllServersOutGroup() {
        ArrayList<String> ids = new ArrayList<String>();
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            if(Init.game_servers.containsKey(name)) {
                ids.add(name);
            }
        }
        for(String name : ids) {
            Init.game_servers.get(name).stopServer();
        }
        for(String name : ids) {
            this.startServerOutGroup(Integer.parseInt(name.split("-")[1]));
        }
    }
    public void stopAllServersOutGroup() {
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            if(Init.game_servers.containsKey(name)) {
                ServerProcessManager.unregisterPort(Init.game_servers.get(name).getPort());
                Init.game_servers.get(name).stopServer();
            }
        }
    }
    public void stopAllServerOverOnlineAmount() {
        Integer online = this.getOnlineServer();
        if(online <= this.onlineamount) {
            return;
        }
        for(int i = this.maxamount; i >= 1; i--) {
            String name = this.name + "-" + i;
            if(online == this.onlineamount) {
                return;
            }
            if(Init.game_servers.containsKey(name)) {
                ServerProcessManager.unregisterPort(Init.game_servers.get(name).getPort());
                Init.game_servers.get(name).stopServer();
                online--;
            }
        }
    }
    public void stopServersOutGroup(Integer servers) {
        Integer online = this.getOnlineServer();
        if((online + servers) <= this.onlineamount) {
            return;
        }
        for(int i = this.maxamount; i >= 1; i--) {
            String name = this.name + "-" + i;
            if(servers == this.onlineamount) {
                return;
            }
            if(servers == 0) {
                return;
            }
            if(Init.game_servers.containsKey(name)) {
                ServerProcessManager.unregisterPort(Init.game_servers.get(name).getPort());
                Init.game_servers.get(name).stopServer();
                servers--;
            }
        }
    }
    public void startServerOutGroup(int id) {
        String name = this.name + "-" + id;
        if(id > this.getMaxServerValue()) {
            return;
        }
        if(!Init.game_servers.containsKey(name)) {
            new GameServer(name, ServerProcessManager.getNextFreePort(this.startPort), this, this.maxram, this.haveTemplate, this.maxplayers).startServer();
        }
    }
    public void stopServerOutGroup(int id) {
        String name = this.name + "-" + id;
        if(id > this.getMaxServerValue()) {
            return;
        }
        if(Init.game_servers.containsKey(name)) {
            ServerProcessManager.unregisterPort(Init.game_servers.get(name).getPort());
            Init.game_servers.get(name).stopServer();
        }
    }
    public Integer getOnlineServer() {
        Integer online = 0;
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            if(Init.game_servers.containsKey(name)) {
                online++;
            }
        }
        return online;
    }
    public void startServersOutGroup(int ser) {
        ArrayList<String> serverNotStarted = new ArrayList<String>();
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            if(!Init.game_servers.containsKey(name)) {
                serverNotStarted.add(i + "");
            }
        }
        for(int i = 0; i < ser; i++) {
            if(i > this.maxamount) {
                return;
            }
            startServerOutGroup(Integer.parseInt(serverNotStarted.get(i)));
        }
    }
    public void startAllOnlineAmountServer() {
        Integer online = 0;
        ArrayList<Integer> serverdoesonline = new ArrayList<Integer>();
        for(int i = 1; i <= this.maxamount; i++) {
            String name = this.name + "-" + i;
            if(Init.game_servers.containsKey(name)) {
                online++;
            } else {
                serverdoesonline.add(i);
            }
        }
        if(!(online >= this.onlineamount)) {
            Integer a;
            a = this.onlineamount - online;
            for(int i = 0; i < a; i++) {
                if(i > this.maxamount) {
                    return;
                }
                startServerOutGroup(serverdoesonline.get(i));
            }
        } else {
            return;
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