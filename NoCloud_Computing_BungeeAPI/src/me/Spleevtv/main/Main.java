package me.Spleevtv.main;

import me.Spleevtv.commands.HubCMD;
import me.Spleevtv.commands.NoCloudCMD;
import me.Spleevtv.listener.*;
import me.Spleevtv.objekts.CloudPlayer;
import me.Spleevtv.objekts.Core;
import me.Spleevtv.objekts.ServerGroup;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.plugin.Plugin;
import net.md_5.bungee.api.plugin.PluginManager;
import net.md_5.bungee.config.Configuration;
import net.md_5.bungee.config.ConfigurationProvider;
import net.md_5.bungee.config.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class Main extends Plugin {

    public static Main instance;
    public static String prefix;
    public static String noperms;
    public static String message;
    public static String ip;
    public static Integer port;
    public static Core core;
    public static String[] motd;
    public static String[] maintenance_motd;
    public static Boolean newplayer;
    public static String newplayermessage;
    public static int maxplayers;
    public static Integer onlineplayers;
    public static ConcurrentHashMap<String, CloudPlayer> players = new ConcurrentHashMap<>();
    public static Boolean maintenance;
    public static String maintenanceaction;
    public static String normalaction;
    public static ArrayList<String> maintenanceactiondescription = new ArrayList<String>();
    public static ArrayList<String> normalactiondescription = new ArrayList<String>();
    public static String fallbackServerGroup;
    public static ArrayList<String> fallbackServers = new ArrayList<String>();
    public static Boolean messageOnOrOff;
    public static String start_message;
    public static String stopp_message;
    public static int full_messsage_amount;
    public static int maintenance_messsage_amount;
    public static String maintenance_message;
    public static String full_message;
    public static ArrayList<String> whitelist = new ArrayList<String>();
    public static HashMap<String, ServerGroup> groups = new HashMap<String, ServerGroup>();
    public static Boolean usehubcmd;
    public static String hubmessage;
    public static String alreadyonhub;
    public static String tabheader;
    public static String tabfooter;
    public static List<String> helpmap = new ArrayList<String>();
    public static Integer maxPings = 0;
    public static Integer registeredPlayers = 0;
    public static Integer pingsLast5Minutes = 0;

    @Override
    public void onEnable() {
        init();
    }
    @Override
    public void onDisable() {
    }

    private void init() {
        instance = this;
        register();
        ProxyServer.getInstance().registerChannel("NoCloud");
        if(!getDataFolder().exists()) {
            getDataFolder().mkdirs();
        }
        updateConfig();
        File messagefile = new File(this.getDataFolder().getPath() + "/messages.yml");
        if(!messagefile.exists()) {
            insertData("messages.yml", this.getDataFolder().getPath() + "/messages.yml");
        }
        try {
            Configuration cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(messagefile);
            for(String s : cfg.getStringList("HelpMap")) {
                helpmap.add(s.replaceAll("%prefix%", prefix).replaceAll("&", "§"));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        onlineplayers = 0;
        maxplayers = 0;
        core = new Core(ip, port);
    }
    private void register() {
        PluginManager manager = ProxyServer.getInstance().getPluginManager();
        manager.registerListener(this, new MotdListener());
        manager.registerListener(this, new JoinQuitListener());
        manager.registerListener(this, new ServerSwitchListener());
        manager.registerListener(this, new ServerReceiveListener());
        manager.registerListener(this, new ServerKickListener());
        manager.registerListener(this, new PingListener());
        manager.registerCommand(this, new HubCMD("hub"));
        manager.registerCommand(this, new HubCMD("l"));
        manager.registerCommand(this, new HubCMD("lobby"));
        manager.registerCommand(this, new NoCloudCMD("nocloud"));
    }
    public static ServerInfo getBestPerformenceFallbackServer() {
        ServerInfo little = null;
        for(String s : fallbackServers) {
            ServerInfo info = ProxyServer.getInstance().getServerInfo(s);
            if(little == null || info.getPlayers().size() < little.getPlayers().size()) {
                little = info;
            }
        }
        return little;
    }
    public static final void insertData(String paramString1, String paramString2) {
        InputStream localInputStream = Main.class.getClassLoader().getResourceAsStream(paramString1);
        try {
            Files.copy(localInputStream, Paths.get(paramString2, new String[0]),
                    new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void updateConfig() {
        File file = new File(instance.getDataFolder().getPath() + "/config.yml");
        Configuration cfg;
        try {
            cfg = ConfigurationProvider.getProvider(YamlConfiguration.class).load(file);
            prefix = cfg.getString("Prefix").replaceAll("&", "§");
            noperms = prefix + cfg.getString("NoRights").replaceAll("&", "§");
            message = cfg.getString("Message").replaceAll("&", "§");
            ip = cfg.getString("IP");
            port = cfg.getInt("Port");
            newplayer = cfg.getBoolean("NewPlayerBoolean");
            newplayermessage = cfg.getString("NewPlayerMessage");
            maintenanceaction = cfg.getString("MaintenanceAction").replaceAll("&", "§");
            normalaction = cfg.getString("NormalAction").replaceAll("&", "§");
            messageOnOrOff = cfg.getBoolean("message");
            start_message = cfg.getString("start-message").replaceAll("&", "§").replaceAll("%prefix%", prefix);
            stopp_message = cfg.getString("stop-message").replaceAll("&", "§").replaceAll("%prefix%", prefix);
            String[] args1 = cfg.getString("MaintenanceActionDescription").replaceAll("&", "§").split("/n");
            String[] args2 = cfg.getString("NormalActionDescription").replaceAll("&", "§").split("/n");
            usehubcmd = cfg.getBoolean("CanUseHubCommand");
            hubmessage = cfg.getString("HubMessage");
            alreadyonhub = cfg.getString("AllReadyOnFallback");
            tabfooter = cfg.getString("Footer").replaceAll("%split%", "\n").replaceAll("&", "§");
            tabheader = cfg.getString("Header").replaceAll("%split%", "\n").replaceAll("&", "§");
            maintenanceactiondescription.clear();
            for(int i = 0; i < args1.length; i++) {
                maintenanceactiondescription.add(args1[i]);
            }
            normalactiondescription.clear();
            for(int i = 0; i < args2.length; i++) {
                normalactiondescription.add(args2[i]);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
