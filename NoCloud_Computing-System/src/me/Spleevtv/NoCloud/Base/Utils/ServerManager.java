package me.Spleevtv.NoCloud.Base.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Main;

import java.io.*;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class ServerManager {

    public static void createServerGroup(String name, int maxram, Boolean h, int s, int seronstart, int maxp, int startPort) {
        if(!Init.proxy_groups.containsKey(name)) {
            File settings = new File("./Base/GroupSettings/" + name + ".json");
            if(!settings.exists()) {
                try {
                    settings.createNewFile();
                } catch (Exception e) {
                }
            }
            try {
                OutputStreamWriter w = new OutputStreamWriter(new FileOutputStream(settings), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject json = new JsonObject();
                json.addProperty("name", name);
                json.addProperty("startPort", startPort);
                json.addProperty("onlineAmount", seronstart);
                json.addProperty("maxAmount", s);
                json.addProperty("maxPlayers", maxp);
                json.addProperty("maxRam", maxram);
                json.addProperty("dynamic", h);
                w.write(gson.toJson(json));
                w.close();
                File server_list = new File("./Base/server_list.yml");
                if(server_list.exists()) {
                    server_list.delete();
                }
                ServerGroup g = new ServerGroup(name, maxram, h, s, seronstart, maxp, startPort);
                Init.game_groups.put(name, g);
                BufferedWriter writer = new BufferedWriter(new FileWriter(server_list));
                for(String group : Init.game_groups.keySet()) {
                    writer.write(group);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
                Init.core.sendTheCoreAMessage("INITGROUP " + name + " " + seronstart + " " + s + " " + maxram + " " + maxp + " " + h);
                g.startServerOutGroup(seronstart);
                System.out.println(Main.getPrefix() + "Server '" + name + "' was successfully created/loaded.");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeServerGroup(String name) {
        ServerGroup currentgroup = Init.game_groups.get(name);
        if(currentgroup != null) {
            File settings = new File("./Base/GroupSettings/" + name + ".json");
            if(settings.exists()) {
                settings.delete();
            }
            Init.core.sendTheCoreAMessage("DISINITGROUP " + name);
            currentgroup.stopAllServersOutGroup();
            Init.game_groups.remove(name);
            File ordner = new File("./Base/temporary/" + name + "/");
            if(ordner.exists()) {
                delete(ordner);
            }
            File template = new File("./Base/templates/" + name + "/");
            if(template.exists()) {
                delete(template);
            }
            File server_list = new File("./Base/server_list.yml");
            try {
                if(server_list.exists()) {
                    server_list.delete();
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(server_list));
                for(String group : Init.game_groups.keySet()) {
                    writer.write(group);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void delete(File dir){
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