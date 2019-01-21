package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.chat.TextComponent;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.ServerSwitchEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class ServerSwitchListener implements Listener {

    @EventHandler
    public void onServerSwitch(ServerSwitchEvent e) {
        ProxiedPlayer p = e.getPlayer();
        Main.core.sendToTheCoreAMessage("PLAYERCHANGESERVER " + p.getUniqueId().toString() + " " + p.getServer().getInfo().getName());
        p.setTabHeader(new TextComponent(Main.tabheader.replaceAll("%op%", Main.onlineplayers + "").replaceAll("%mp%", Main.maxplayers + "").replaceAll("%server%", p.getServer().getInfo().getName())),
        new TextComponent(Main.tabfooter.replaceAll("%op%", Main.onlineplayers + "").replaceAll("%mp%", Main.maxplayers + "").replaceAll("%server%", p.getServer().getInfo().getName())));
    }
}