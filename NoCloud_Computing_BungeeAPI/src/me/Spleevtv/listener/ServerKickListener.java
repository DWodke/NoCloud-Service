package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.event.ServerKickEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class ServerKickListener implements Listener {

    @EventHandler
    public void onServerKickEvent(ServerKickEvent e) {
        e.setCancelServer(null);
        e.setCancelled(true);
        e.getPlayer().sendMessage(e.getKickReason());
        if(e.getPlayer().getServer().getInfo().getName().startsWith(Main.fallbackServerGroup)) {
            return;
        }
        ServerInfo info = Main.getBestPerformenceFallbackServer();
        if(info != null) {
            e.setCancelServer(info);
        }
    }
}
