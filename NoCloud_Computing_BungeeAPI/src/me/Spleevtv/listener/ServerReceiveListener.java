package me.Spleevtv.listener;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.event.PluginMessageEvent;
import net.md_5.bungee.api.plugin.Listener;
import net.md_5.bungee.event.EventHandler;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.ArrayList;
import java.util.Random;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class ServerReceiveListener implements Listener {

    @EventHandler
    public void onFallbackMessage(PluginMessageEvent e) {
        if(!e.getTag().equalsIgnoreCase("NoCloud")) {
            return;
        }
        DataInputStream input = new DataInputStream(new ByteArrayInputStream(e.getData()));
        try {
            String channel = input.readUTF();
            if(channel.equalsIgnoreCase("sendtofallback")) {
                ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
                if(p != null) {
                    p.connect(Main.getBestPerformenceFallbackServer());
                }
            } else if(channel.equalsIgnoreCase("sendtoserver")) {
                ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
                String server = input.readUTF();
                if(p != null) {
                    ServerInfo info = ProxyServer.getInstance().getServerInfo(server);
                    if(info != null) {
                        p.connect(info);
                    }
                }
            } else if(channel.equalsIgnoreCase("sendtogroup")) {
                ProxiedPlayer p = (ProxiedPlayer) e.getReceiver();
                String group = input.readUTF();
                if(p != null) {
                    ArrayList<ServerInfo> server = new ArrayList<ServerInfo>();
                    for(ServerInfo info : ProxyServer.getInstance().getServers().values()) {
                        if(info.getName().startsWith(group)) {
                            server.add(info);
                        }
                    }
                    Random random = new Random();
                    Integer rnum = random.nextInt(server.size());
                    p.connect(server.get(rnum));
                }
            }
        } catch(Exception e1) {
        }
    }

}
