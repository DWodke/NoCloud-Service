package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

public class ProxyManager {

    public static void createProxyGroup(String name, int maxram, Boolean h, int s, int seronstart, int maxp, int startPort, String proxyPorts) {
        if(!Init.proxy_groups.containsKey(name)) {
            int o = 0;
            String[] ports2 = proxyPorts.split(",");
            String updatedPorts2 = null;
            for(int i = 1; i <= s; i++) {
                String n = name + "-" + i;
                if(updatedPorts2 == null) {
                    updatedPorts2 =  n + ":" + ports2[o] + ",";
                } else {
                    updatedPorts2 = updatedPorts2 + n + ":" + ports2[o] + ",";
                }
                o++;
            }
            File settings = new File("./Base/groups_settings.yml");
            Config cfg = new Config(settings);
            cfg.load();
            cfg.set(name + ".MaxRam", maxram + "");
            cfg.set(name + ".ServerValue", s + "");
            cfg.set(name + ".HaveTemplate", h + "");
            cfg.set(name + ".ServerOnStart", seronstart + "");
            cfg.set(name + ".MaxPlayers", maxp + "");
            cfg.set(name + ".StartPort", startPort + "");
            cfg.set(name + ".ConnectionPorts", updatedPorts2);
            cfg.save();
            cfg.unload();
            File proxy_list = new File("./Base/proxy_list.yml");
            try {
                if(proxy_list.exists()) {
                    proxy_list.delete();
                }
                ProxyGroup g = new ProxyGroup(name, maxram, h, s, seronstart, maxp, startPort, updatedPorts2);
                Init.proxy_groups.put(name, g);
                BufferedWriter writer = new BufferedWriter(new FileWriter(proxy_list));
                for(String group : Init.proxy_groups.keySet()) {
                    writer.write(group);
                    writer.newLine();
                }
                writer.flush();
                writer.close();

                g.startServerOutGroup(seronstart);
                System.out.println(Main.getPrefix() + "Proxy '" + name + "' was successfully created/loaded.");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeProxyGroup(String name) {
        ProxyGroup currentgroup = Init.proxy_groups.get(name);
        if(currentgroup != null) {
            File settings = new File("./Base/groups_settings.yml");
            Config cfg = new Config(settings);
            cfg.load();
            cfg.remove(name + ".MaxRam");
            cfg.remove(name + ".ServerValue");
            cfg.remove(name + ".HaveTemplate");
            cfg.remove(name + ".ServerOnStart");
            cfg.remove(name + ".MaxPlayers");
            cfg.remove(name + ".Ports");
            cfg.remove(name + ".ConnectionPorts");
            cfg.save();
            cfg.unload();
            currentgroup.stopAllServersOutGroup();
            Init.proxy_groups.remove(name);
            File proxy_list = new File("./Base/proxy_list.yml");
            try {
                if(proxy_list.exists()) {
                    proxy_list.delete();
                }
                BufferedWriter writer = new BufferedWriter(new FileWriter(proxy_list));
                for(String group : Init.proxy_groups.keySet()) {
                    writer.write(group);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            File ordner = new File("./Base/temporary/" + name + "/");
            if(ordner.exists()) {
                delete(ordner);
            }
            File template = new File("./Base/templates/" + name + "/");
            if(template.exists()) {
                delete(template);
            }
        }
    }
    private static void delete(File dir){
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