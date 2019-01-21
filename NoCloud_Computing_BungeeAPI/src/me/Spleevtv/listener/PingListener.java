package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class PingListener implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        Main.core.sendToTheCoreAMessage("PINGPROXY " + e.getConnection().getAddress().getAddress().getHostAddress());
    }
}
