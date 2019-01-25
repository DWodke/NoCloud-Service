package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Main;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class ServerManager {

    public static void createServerGroup(String name, int maxram, Boolean h, int s, int seronstart, int maxp, int startPort) {
        if(!Init.proxy_groups.containsKey(name)) {
            File settings = new File("./Base/groups_settings.yml");
            Config cfg = new Config(settings);
            cfg.load();
            cfg.set(name + ".MaxRam", maxram + "");
            cfg.set(name + ".ServerValue", s + "");
            cfg.set(name + ".HaveTemplate", h + "");
            cfg.set(name + ".ServerOnStart", seronstart + "");
            cfg.set(name + ".MaxPlayers", maxp + "");
            cfg.set(name + ".StartPort", startPort + "");
            cfg.save();
            cfg.unload();
            File server_list = new File("./Base/server_list.yml");
            try {
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
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    public static void removeServerGroup(String name) {
        ServerGroup currentgroup = Init.game_groups.get(name);
        if(currentgroup != null) {
            File settings = new File("./Base/groups_settings.yml");
            Config cfg = new Config(settings);
            cfg.load();
            cfg.remove(name + ".MaxRam");
            cfg.remove(name + ".ServerValue");
            cfg.remove(name + ".HaveTemplate");
            cfg.remove(name + ".ServerOnStart");
            cfg.remove(name + ".MaxPlayers");
            cfg.remove(name + ".StartPort");
            cfg.save();
            cfg.unload();
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
