package me.Spleevtv.NoCloud.Core.Utils;

import java.io.File;
import java.util.HashMap;

public class Base_Servers {

    public static HashMap<String, String> cache = new HashMap<String, String>();

    public static void addGroupToCache(String group, String base) {
        File base_servers = new File("./Core/base_server.yml");
        Config cfg = new Config(base_servers);
        cfg.load();
        if(cfg.get(group) == null) {
            cfg.set(group, base);
            cfg.save();
            cache.put(group, base);
        }
        cfg.unload();
    }
    public static void removeGroupOutCache(String group) {
        if(cache.containsKey(group)) {
            cache.remove(group);
        }
        File base_servers = new File("./Core/base_server.yml");
        Config cfg = new Config(base_servers);
        cfg.load();
        if(cfg.get(group) != null) {
            cfg.remove(group);
            cfg.save();
        }
        cfg.unload();
    }
    public static String getBase(String group) {
        if(cache.containsKey(group)) {
            return cache.get(group);
        } else {
            File base_servers = new File("./Core/base_server.yml");
            Config cfg = new Config(base_servers);
            cfg.load();
            if(cfg.get(group) != null) {
                cache.put(group, cfg.get(group));
                cfg.unload();
                return cache.get(group);
            }
        }
        return null;
    }

}
