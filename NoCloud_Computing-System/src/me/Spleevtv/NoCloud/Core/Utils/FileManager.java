package me.Spleevtv.NoCloud.Core.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonElement;
import me.Spleevtv.NoCloud.Core.Init;

import java.io.*;
import java.util.ArrayList;

public class FileManager {

    public static Config baseconfig;
    public static Whitelist whitelist;

    public static void loadAllFiles() {
        File ordner = new File("./Core/");
        if(!ordner.exists()) {
            ordner.mkdirs();
        }
        File base_config = new File("./Core/Base_Config.yml");
        if(!base_config.exists()) {
            try {
                base_config.createNewFile();
                baseconfig = new Config(base_config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            baseconfig = new Config(base_config);
        }
        File base_list = new File("./Core/Base_List.yml");
        if(!base_list.exists()) {
            try {
                base_list.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File whitelist_file = new File("./Core/whitelist.yml");
        if(!whitelist_file.exists()) {
            try {
                whitelist_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        whitelist = new Whitelist(whitelist_file);
        loadWhitelist(whitelist_file);
        updateConfig();
        File cache = new File("./Core/Cache.json");
        if(!cache.exists()) {
            try {
                cache.createNewFile();
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(cache), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject json = new JsonObject();
                json.addProperty("proxys", 0);
                json.addProperty("proxySocketStartPort", 13888);
                writer.write(gson.toJson(json));
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File folder = new File("./Core/PlayerCache/");
        File uuidcache = new File("./Core/PlayerCache/uuidcache.yml");
        if(!folder.exists()) {
            folder.mkdirs();
        }
        if(!uuidcache.exists()) {
            try {
                uuidcache.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Init.cache = new Cache();
        File base_servers = new File("./Core/base_server.yml");
        if(!base_servers.exists()) {
            try {
                base_servers.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File cfg = new File("./Core/PlayerCache/pings.yml");
        if(!cfg.exists()) {
            try {
                cfg.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void updateConfig() {
        File bungee_config = new File("./Core/BungeeCord_Config.json");
        if(!bungee_config.exists()) {
            try {
                bungee_config.createNewFile();
                OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(bungee_config), "UTF-8");
                Gson gson = new GsonBuilder().setPrettyPrinting().create();
                JsonObject json = new JsonObject();
                json.addProperty("motd-1", "&b&lNoCloud &7× &fMinecraft cloud-system.");
                json.addProperty("motd-2", "&fProgrammed by:&b&l Dominik W.");
                json.addProperty("maintenanceMotd-1", "&b&lNoCloud &7× &fMinecraft cloud-system.");
                json.addProperty("maintenanceMotd-2", "&fMaintenance | by Dominik W.");
                json.addProperty("maintenance", true);
                JsonArray maintenanceMessage = new JsonArray();
                maintenanceMessage.add("&cThe network is in the maintenance-mode!");
                json.add("maintenanceMessage", maintenanceMessage);
                JsonArray serverIsFullMessage = new JsonArray();
                serverIsFullMessage.add("&cThe network is currently full!");
                serverIsFullMessage.add("&cPls join the network again.");
                json.add("serverIsFullMessage", serverIsFullMessage);
                json.addProperty("maxPlayers", 50);
                json.addProperty("fallbackGroup", "Lobby");
                json.addProperty("fallbackPercentToStartServer", 50);
                writer.write(gson.toJson(json));
                writer.close();
                ArrayList<String> m_messages = new ArrayList<String>();
                m_messages.add("&cThe network is in the maintenance-mode!");
                ArrayList<String> full_messages = new ArrayList<String>();
                full_messages.add("&cThe network is currently full!");
                full_messages.add("&cPls join the network again.");
                Init.bungee_template = new BungeeTemplate("&b&lNoCloud &7× &fMinecraft cloud-system.", "&fCode by:&b&l SPLEEVTV | Dominik W.", "&b&lNoCloud &7× &fMinecraft cloud-system.", "&fMaintenance | by Dominik W.", 50, false, "Lobby", 1, m_messages, 2, full_messages, 50);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            try {
                Gson gson = new Gson();
                JsonObject json = gson.fromJson(new FileReader(bungee_config), JsonObject.class);
                ArrayList<String> m_messages = new ArrayList<String>();
                JsonArray messages = json.get("maintenanceMessage").getAsJsonArray();
                for(int i = 1; i < messages.size(); i++) {
                    m_messages.add(messages.get(i).getAsString());
                }
                ArrayList<String> full_messages = new ArrayList<String>();
                JsonArray fullmessages = json.get("serverIsFullMessage").getAsJsonArray();
                for(int b = 1; b < fullmessages.size(); b++) {
                    full_messages.add(fullmessages.get(b).getAsString());
                }
                Init.bungee_template = new BungeeTemplate(json.get("motd-1").getAsString(), json.get("motd-2").getAsString(), json.get("maintenanceMotd-1").getAsString(), json.get("maintenanceMotd-2").getAsString(), json.get("maxPlayers").getAsNumber().intValue(), json.get("maintenance").getAsBoolean(), json.get("fallbackGroup").getAsString(), messages.size(), m_messages, fullmessages.size(), full_messages, json.get("fallbackPercentToStartServer").getAsNumber().intValue());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    public static void resetConfig() {
        File bungee_config = new File("./Core/BungeeCord_Config.yml");
        if(bungee_config.exists()) {
            bungee_config.delete();
        }
        updateConfig();
    }
    private static void loadWhitelist(File file) {
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String var = "";
            while((var = reader.readLine()) != null) {
                whitelist.getWhitelist().add(var);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}