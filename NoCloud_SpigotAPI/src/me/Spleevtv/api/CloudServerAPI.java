package me.Spleevtv.api;

import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.GameServer;
import me.Spleevtv.objekts.ServerGroup;
import me.Spleevtv.objekts.ServerState;
import org.bukkit.Bukkit;

import java.util.HashMap;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class CloudServerAPI {

    public GameServer getServerById(String group, Integer id) {
        return Main.groups.get(group).getServers().get(group + "-" + id);
    }
    public ServerGroup getGroup(String group) {
        return Main.groups.get(group);
    }
    public HashMap<String, GameServer> getServersFromGroup(String group) {
        return Main.groups.get(group).getServers();
    }
    public HashMap<String, ServerGroup> getGroups() {
        return Main.groups;
    }
    public Integer getSizeFromGroup(String group) {
        return Main.groups.get(group).getServers().size();
    }
    public Integer getSizeFromAllGroups() {
        return Main.groups.size();
    }
    public void setState(ServerState state) {
        Main.core.sendMessage("UPDATESTATE " + getServerName() + " " + state);
    }
    public String getServerName() {
        return Bukkit.getServer().getServerName();
    }
}