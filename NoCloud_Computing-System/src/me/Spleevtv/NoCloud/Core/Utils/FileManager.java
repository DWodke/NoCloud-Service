package me.Spleevtv.NoCloud.Core.Utils;

import me.Spleevtv.NoCloud.Core.Init;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;

public class FileManager {

    public static Config baseconfig;
    public static Config bungeeconfig;
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
        File cache = new File("./Core/Cache.yml");
        if(!cache.exists()) {
            try {
                cache.createNewFile();
                Config cfg = new Config(cache);
                cfg.load();
                cfg.set("BungeeCords", "0");
                cfg.set("Spigot_Ports", "45000");
                cfg.set("BungeeCord_Ports", "25565");
                cfg.set("ProxyConnectPort", "13888");
                cfg.save();
                cfg.unload();
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
        File bungee_config = new File("./Core/BungeeCord_Config.yml");
        if(!bungee_config.exists()) {
            try {
                bungee_config.createNewFile();
                bungeeconfig = new Config(bungee_config);
                bungeeconfig.load();
                bungeeconfig.set("motd_1", "&b&lNoCloud &7× &fMinecraft cloud-system.");
                bungeeconfig.set("motd_2", "&fProgrammed by:&b&l Dominik W.");
                bungeeconfig.set("maintenance_motd_1", "&b&lNoCloud &7× &fMinecraft cloud-system.");
                bungeeconfig.set("maintenance_motd_2", "&fMaintenance | by Dominik W.");
                bungeeconfig.set("maintenance", "false");
                bungeeconfig.set("maintenance_message_amount", "1");
                bungeeconfig.set("maintenance_message_1", "&cThe network is in the maintenance-mode!");
                bungeeconfig.set("server_is_full_message_amount", "2");
                bungeeconfig.set("server_is_full_message_1", "&cThe network is currently full!");
                bungeeconfig.set("server_is_full_message_2", "&cPls join the network again.");
                bungeeconfig.set("maxplayers", "50");
                bungeeconfig.set("fallbackServerGroup", "Lobby");
                bungeeconfig.set("fallbackServerUntilANewServerStartPercent", "50");
                bungeeconfig.save();
                bungeeconfig.unload();
                ArrayList<String> m_messages = new ArrayList<String>();
                m_messages.add("&cThe network is in the maintenance-mode!");
                ArrayList<String> full_messages = new ArrayList<String>();
                full_messages.add("&cThe network is currently full!");
                full_messages.add("&cPls join the network again.");
                Init.bungee_template = new BungeeTemplate("&b&lNoCloud &7× &fMinecraft cloud-system.", "&fCode by:&b&l SPLEEVTV | Dominik W.", "&b&lNoCloud &7× &fMinecraft cloud-system.", "&fMaintenance | by Dominik W.", 50, false, "Lobby", 1, m_messages, 2, full_messages, 50);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            bungeeconfig = new Config(bungee_config);
            bungeeconfig.load();
            int i = bungeeconfig.getInt("maintenance_message_amount");
            ArrayList<String> m_messages = new ArrayList<String>();
            for(int b = 0; b < i; b++) {
                m_messages.add(bungeeconfig.get("maintenance_message_" + (b +1)));
            }
            int full_i = bungeeconfig.getInt("server_is_full_message_amount");
            ArrayList<String> full_messages = new ArrayList<String>();
            for(int b = 0; b < full_i; b++) {
                String message = bungeeconfig.get("server_is_full_message_" + (b +1));
                if(message.equalsIgnoreCase(null)) {
                    full_messages.add(" ");
                } else {
                    full_messages.add(message);
                }

            }
            Init.bungee_template = new BungeeTemplate(bungeeconfig.get("motd_1"), bungeeconfig.get("motd_2"), bungeeconfig.get("maintenance_motd_1"), bungeeconfig.get("maintenance_motd_2"), bungeeconfig.getInt("maxplayers"), Boolean.parseBoolean(bungeeconfig.get("maintenance")), bungeeconfig.get("fallbackServerGroup"), i, m_messages, full_i, full_messages, bungeeconfig.getInt("fallbackServerUntilANewServerStartPercent"));
            bungeeconfig.unload();
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