package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PlayerDisconnectEvent;
import net.md_5.bungee.api.event.PostLoginEvent;
import net.md_5.bungee.api.event.ServerConnectEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.ArrayList;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Sep, 2018
 */
public class JoinQuitListener implements Listener {

    private ArrayList<ProxiedPlayer> pending = new ArrayList<ProxiedPlayer>();

    @EventHandler
    public void onPostJoin(PostLoginEvent e) {
        pending.add(e.getPlayer());
    }
    @EventHandler
    public void onJoin(ServerConnectEvent e) {
            ProxiedPlayer p = e.getPlayer();
            ServerInfo server = e.getTarget();
            if(pending.contains(p)) {
                ServerInfo fallback = Main.getBestPerformenceFallbackServer();
                e.setTarget(fallback);
                Boolean disconnect = false;
                if(Main.maintenance) {
                    if(!Main.whitelist.containsKey(p.getUniqueId().toString())) {
                        e.setCancelled(true);
                        p.disconnect(new TextComponent(Main.maintenance_message));
                        disconnect = true;
                        Main.core.sendToTheCoreAMessage("JOINMAINTENANCE " + p.getName());
                    }
                }
                if(!disconnect) {
                    if(Main.onlineplayers >= Main.maxplayers) {
                        e.setCancelled(true);
                        p.disconnect(new TextComponent(Main.full_message));
                        disconnect = true;
                    }
                }
                if(!disconnect) {
                    Main.core.sendToTheCoreAMessage("CLOUDPLAYERJOIN " + p.getName() + " " + p.getUniqueId().toString() + " " + server.getName());
                }
                pending.remove(p);
            }
    }
    @EventHandler
    public void onQuit(PlayerDisconnectEvent e) {
        try {
            ProxiedPlayer p = e.getPlayer();
            Main.core.sendToTheCoreAMessage("CLOUDPLAYERQUIT " + p.getUniqueId().toString());
        } catch (Exception e1) {
        }
    }
}
