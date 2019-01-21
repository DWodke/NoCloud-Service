package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.CloudSign;
import org.bukkit.Location;
import org.bukkit.block.Sign;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.SignChangeEvent;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.ServiceConfigurationError;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class SignPlaceListener implements Listener {

    @EventHandler
    public void onSignPlace(SignChangeEvent e) {
        Sign sign = (Sign) e.getBlock().getState();
        if(e.getLine(0).equals("[NoCloud]")) {
            File signfile = new File(Main.instance.getDataFolder().getPath() + "/signs.yml");
            YamlConfiguration signcfg = YamlConfiguration.loadConfiguration(signfile);
            String group = e.getLine(1);
            Location loc = sign.getLocation();
            String name = loc.getBlockX() + ";" + loc.getBlockY() + ";" + loc.getBlockZ();
            List<String> list = signcfg.getStringList("SignList");
            list.add(name);
            signcfg.set("SignList", list);
            signcfg.set(name + ".group", group);
            signcfg.set(name + ".world", loc.getWorld().getName());
            signcfg.set(name + ".x", loc.getBlockX());
            signcfg.set(name + ".y", loc.getBlockY());
            signcfg.set(name + ".z", loc.getBlockZ());
            try {
                signcfg.save(signfile);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            Main.signs.put(name, new CloudSign(group, loc));
            e.getPlayer().sendMessage(Main.prefix.getPrefix() + "§7You have placed a server-sign.");
        }
    }
}
