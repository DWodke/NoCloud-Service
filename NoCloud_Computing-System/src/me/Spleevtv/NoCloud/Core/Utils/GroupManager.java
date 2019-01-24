package me.Spleevtv.NoCloud.Core.Utils;

import me.Spleevtv.NoCloud.Core.Init;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class GroupManager {

    public static Boolean editOnlineAmount(String group, Integer i) {
        String base = Base_Servers.getBase(group);
        if(base != null) {
            ServerGroup servergroup = Init.groups.get(group);
            if(servergroup != null) {
                if(servergroup.getMaxAmount() >= i) {
                    if(i == servergroup.getOnlineAmount()) {
                        return false;
                    }
                    servergroup.setOnlineAmount(i);
                    Init.baselist.getBaselist().get(base).sendTheBaseAMessage("EDITGROUP " + group + " onlineAmount " + i + " ");
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    public static Boolean editMaxAmount(String group, Integer i) {
        String base = Base_Servers.getBase(group);
        if(base != null) {
            ServerGroup servergroup = Init.groups.get(group);
            if(servergroup != null) {
                if(i >= servergroup.getOnlineAmount()) {
                    if(i == servergroup.getMaxAmount()) {
                        return false;
                    }
                    for(GameServer server : Init.gameserver.values()) {
                        if(server.getGroup().equalsIgnoreCase(group)) {
                            server.setMaxServerValue(i);
                        }
                    }
                    servergroup.setMaxAmount(i);
                    Init.baselist.getBaselist().get(base).sendTheBaseAMessage("EDITGROUP " + group + " maxAmount " + i);
                    return true;
                } else {
                    return false;
                }
            }
        }
        return false;
    }
    public static Boolean editMaxRam(String group, Integer i) {
        String base = Base_Servers.getBase(group);
        if(base != null) {
            ServerGroup servergroup = Init.groups.get(group);
            if(servergroup != null) {
                if(i == servergroup.getMaxRam()) {
                    return false;
                }
                for(GameServer server : Init.gameserver.values()) {
                    if(server.getGroup().equalsIgnoreCase(group)) {
                        server.setMaxRam(i);
                    }
                }
                servergroup.setMaxRam(i);
                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("EDITGROUP " + group + " maxRam " + i);
                return true;
            }
        }
        return false;
    }
    public static Boolean editMaxPlayers(String group, Integer i) {
        String base = Base_Servers.getBase(group);
        if(base != null) {
            ServerGroup servergroup = Init.groups.get(group);
            if(servergroup != null) {
                if(i == servergroup.getMaxPlayers()) {
                    return false;
                }
                for(GameServer server : Init.gameserver.values()) {
                    if(server.getGroup().equalsIgnoreCase(group)) {
                        server.setMaxPlayers(i);
                    }
                }
                servergroup.setMaxPlayers(i);
                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("EDITGROUP " + group + " maxPlayers " + i);
                return true;
            }
        }
        return false;
    }
    public static Boolean editDynamic(String group, Boolean b) {
        String base = Base_Servers.getBase(group);
        if(base != null) {
            ServerGroup servergroup = Init.groups.get(group);
            if(servergroup != null) {
                if(b == servergroup.getDynamic()) {
                    return false;
                }
                servergroup.setDynamic(b);
                Init.baselist.getBaselist().get(base).sendTheBaseAMessage("EDITGROUP " + group + " dynamic " + b);
                return true;
            }
        }
        return false;
    }
}
