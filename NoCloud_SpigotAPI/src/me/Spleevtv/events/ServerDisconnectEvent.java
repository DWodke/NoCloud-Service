package me.Spleevtv.events;

import me.Spleevtv.objekts.ServerGroup;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Feb, 2019
 */
public class ServerDisconnectEvent extends Event {

    String servername;
    ServerGroup group;

    public ServerDisconnectEvent(String arg0, ServerGroup arg1) {
        this.servername = arg0;
        this.group = arg1;
    }
    public String getServerName() {
        return servername;
    }
    public ServerGroup getGroup() {
        return group;
    }
    private static final HandlerList handlerlist = new HandlerList();
    @Override
    public HandlerList getHandlers() {
        return handlerlist;
    }
}
