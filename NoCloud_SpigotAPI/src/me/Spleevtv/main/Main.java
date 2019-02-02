package me.Spleevtv.main;

import me.Spleevtv.listener.GetDataListener;
import me.Spleevtv.listener.JoinQuitListener;
import me.Spleevtv.listener.SignKlickListener;
import me.Spleevtv.listener.SignPlaceListener;
import me.Spleevtv.objekts.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldCreator;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class Main extends JavaPlugin {

    public static Main instance;
    public static Core core;
    public static HashMap<String, ServerGroup> groups = new HashMap<String, ServerGroup>();
    public static Prefix prefix;
    public static HashMap<SignTemplateKind, SignTemplate> templates = new HashMap<SignTemplateKind, SignTemplate>();
    public static Integer animationspeed;
    public static Integer updatespeed;
    public static Boolean pushfromsign;
    public static Double pushfromsigndistance;
    public static Double pushfromsignstrength;
    public static ArrayList<GameServer> usedserver = new ArrayList<GameServer>();
    public static HashMap<String, CloudSign> signs = new HashMap<String, CloudSign>();

    @Override
    public void onEnable() {
        init();
    }
    @Override
    public void onDisable() {
        core.sendMessage("UNREGISTERSERVER " + Bukkit.getServer().getServerName());
    }
    void init() {
        instance = this;
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdirs();
        }
        File file = new File(Main.instance.getDataFolder().getPath() + "/signTemplate.yml");
        if(!file.exists()) {
            insertData("signTemplate.yml", Main.instance.getDataFolder().getPath() + "/signTemplate.yml");
        }
        this.saveDefaultConfig();
        prefix = new Prefix();
        core = new Core();
        core.sendMessage("REGISTERSERVER " + Bukkit.getServer().getServerName());
        register();
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        animationspeed = cfg.getInt("Animation-Speed");
        updatespeed = cfg.getInt("Update-Speed");
        pushfromsign = cfg.getBoolean("PushPlayerFromSign");
        pushfromsigndistance = cfg.getDouble("PushPlayerFromSignDistance");
        pushfromsignstrength = cfg.getDouble("PushPlayerFromSignStrength");
        for(SignTemplateKind kind : SignTemplateKind.values()) {
            templates.put(kind, new SignTemplate(kind));
        }
        File signfile = new File(Main.instance.getDataFolder().getPath() + "/signs.yml");
        YamlConfiguration signcfg = YamlConfiguration.loadConfiguration(signfile);
        for(String sign : signcfg.getStringList("SignList")) {
            if(Bukkit.getWorld(signcfg.getString(sign + ".world")) == null) {
                Bukkit.createWorld(new WorldCreator(signcfg.getString(sign + ".world")));
            }
            signs.put(sign, new CloudSign(signcfg.getString(sign + ".group"), new Location(Bukkit.getWorld(signcfg.getString(sign + ".world"))
                    , signcfg.getInt(sign + ".x"), signcfg.getInt(sign + ".y"), signcfg.getInt(sign + ".z"))));
        }
        this.startSignUpdate();
    }
    void register() {
        PluginManager manager = Bukkit.getPluginManager();
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "NoCloud");
        this.getServer().getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
        this.getServer().getMessenger().registerIncomingPluginChannel(this, "NoCloud", new GetDataListener());
        manager.registerEvents(new SignPlaceListener(), instance);
        manager.registerEvents(new SignKlickListener(), instance);
        manager.registerEvents(new JoinQuitListener(), instance);
    }
    void startSignUpdate() {
        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
                try {
                    for(CloudSign sign : signs.values()) {
                        if(pushfromsign) {
                            for(Player all : Bukkit.getOnlinePlayers()) {
                                if(sign.getLocation().distance(all.getLocation()) <= pushfromsigndistance || sign.getLocation().distance(all.getEyeLocation()) <= pushfromsigndistance) {
                                    all.setVelocity(sign.getLocation().getDirection().multiply(pushfromsignstrength));
                                }
                            }
                        }
                        if(!sign.getLocation().getChunk().isLoaded()) {
                            sign.getLocation().getChunk().load();
                        }
                        if(sign.getCurrentGroup() == null || sign.getCurrentGroup().getServers().isEmpty()) {
                            if(sign.getState() != SignTemplateKind.SEARCHING) {
                                sign.setState(SignTemplateKind.SEARCHING);
                                sign.updateSign();
                                continue;
                            }
                        }
                        if(sign.getState() == SignTemplateKind.SEARCHING) {
                            sign.search();
                            continue;
                        }
                        if(sign.getState() == SignTemplateKind.LOBBY) {
                            if(templates.get(SignTemplateKind.LOBBY).isHide()) {
                                sign.search();
                                continue;
                            } else {
                                sign.updateSign();
                            }
                        }
                        if(sign.getState() == SignTemplateKind.INGAME) {
                            if(templates.get(SignTemplateKind.INGAME).isHide()) {
                                sign.search();
                                continue;
                            } else {
                                sign.updateSign();
                            }
                        }
                        if(sign.getState() == SignTemplateKind.ONLINE) {
                            if(templates.get(SignTemplateKind.ONLINE).isHide()) {
                                sign.search();
                                continue;
                            } else {
                                sign.updateSign();
                            }
                        }
                        if(sign.getState() == SignTemplateKind.ENDING) {
                            if(templates.get(SignTemplateKind.ENDING).isHide()) {
                                sign.search();
                                continue;
                            } else {
                                sign.updateSign();
                            }
                        }
                    }
                } catch (Exception e) {
                }
            }
        }, 20, updatespeed);
        Bukkit.getScheduler().runTaskTimerAsynchronously(instance, new Runnable() {
            @Override
            public void run() {
            try {
                for(SignTemplateKind kind : SignTemplateKind.values()) {
                    if(templates.get(kind).isAnimation()) {
                        templates.get(kind).addCurrentUpdateInt(0);
                        templates.get(kind).addCurrentUpdateInt(1);
                        templates.get(kind).addCurrentUpdateInt(2);
                        templates.get(kind).addCurrentUpdateInt(3);
                    }
                }
                for(CloudSign sign : signs.values()) {
                    if(sign.getState() == SignTemplateKind.SEARCHING) {
                        if(templates.get(SignTemplateKind.SEARCHING).isAnimation()) {
                            sign.updateSign();
                            continue;
                        }
                    }
                    if(sign.getState() == SignTemplateKind.LOBBY) {
                        if(templates.get(SignTemplateKind.LOBBY).isHide()) {
                            continue;
                        } else {
                            if(templates.get(SignTemplateKind.LOBBY).isAnimation()) {
                                sign.updateSign();
                            }
                        }
                    }
                    if(sign.getState() == SignTemplateKind.INGAME) {
                        if(templates.get(SignTemplateKind.INGAME).isHide()) {
                            continue;
                        } else {
                            if(templates.get(SignTemplateKind.INGAME).isAnimation()) {
                                sign.updateSign();
                            }
                        }
                    }
                    if(sign.getState() == SignTemplateKind.ONLINE) {
                        if(templates.get(SignTemplateKind.ONLINE).isHide()) {
                            continue;
                        } else {
                            if(templates.get(SignTemplateKind.ONLINE).isAnimation()) {
                                sign.updateSign();
                            }
                        }
                    }
                    if(sign.getState() == SignTemplateKind.ENDING) {
                        if(templates.get(SignTemplateKind.ENDING).isHide()) {
                            continue;
                        } else {
                            if(templates.get(SignTemplateKind.ENDING).isAnimation()) {
                                sign.updateSign();
                            }
                        }
                    }
                }
            } catch (Exception e) {
            }
            }
        }, 40, animationspeed);
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
}
