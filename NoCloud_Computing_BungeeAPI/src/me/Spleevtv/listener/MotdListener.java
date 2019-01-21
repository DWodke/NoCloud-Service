package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.ServerPing;
import net.md_5.bungee.api.event.ProxyPingEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.util.List;
import java.util.UUID;

public class MotdListener implements Listener {

    @EventHandler
    public void onPing(ProxyPingEvent e) {
        ServerPing ping = e.getResponse();
        ServerPing.Players p = ping.getPlayers();

        p.setMax(Main.maxplayers);
        p.setOnline(Main.onlineplayers);
        if(Main.maintenance) {
            ping.setVersion(new ServerPing.Protocol(Main.maintenanceaction.replaceAll("%OP%", Main.onlineplayers + "").replaceAll("%MP%", Main.maxplayers + ""), -1));
            p.setSample(getMotdPlayerInfo(Main.maintenanceactiondescription));
            ping.setDescription(Main.maintenance_motd[0] + "\n" + Main.maintenance_motd[1]);
        } else {
            ping.setVersion(new ServerPing.Protocol(Main.normalaction.replaceAll("%OP%", Main.onlineplayers + "").replaceAll("%MP%", Main.maxplayers + ""), -1));
            p.setSample(getMotdPlayerInfo(Main.normalactiondescription));
            ping.setDescription(Main.motd[0] + "\n" + Main.motd[1]);
        }
        ping.setPlayers(p);
        e.setResponse(ping);
    }
    public ServerPing.PlayerInfo[] getMotdPlayerInfo(List<String> vars) {
        ServerPing.PlayerInfo[] playerInfos = new ServerPing.PlayerInfo[vars.size()];
        for (int i = 0; i < vars.size(); i++) {
            playerInfos[i] = new ServerPing.PlayerInfo(vars.get(i), UUID.randomUUID());
        }
        return playerInfos;
    }
}