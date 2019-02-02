package me.Spleevtv.listener;

import me.Spleevtv.api.NoCloudAPI;
import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.GameServer;
import me.Spleevtv.objekts.ServerGroup;
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
            for(ServerGroup group : Main.groups.values()) {
                for(GameServer server : Main.groups.get(group).getServers().values()) {
                    p.sendMessage(server.getName());
                }
            }
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
