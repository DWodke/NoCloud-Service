package me.Spleevtv.listener;

import me.Spleevtv.api.NoCloudAPI;
import me.Spleevtv.main.Main;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class JoinQuitListener implements Listener {

    @EventHandler
    public void handleJoin(PlayerJoinEvent e) {
        try {
            Player p = e.getPlayer();
            Main.core.sendMessage("JOINPLAYER " + p.getUniqueId().toString() + " " + NoCloudAPI.getServerAPI().getServerName());
        } catch (Exception e1) {
        }
    }
    @EventHandler
    public void handleQuit(PlayerQuitEvent e) {
        try {
            Player p = e.getPlayer();
            Main.core.sendMessage("QUITPLAYER " + p.getUniqueId().toString() + " " + NoCloudAPI.getServerAPI().getServerName());
        } catch (Exception e1) {
        }
    }
}
