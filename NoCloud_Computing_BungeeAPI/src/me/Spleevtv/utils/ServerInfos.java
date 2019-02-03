package me.Spleevtv.utils;
import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.GameServer;
import me.Spleevtv.objekts.ServerGroup;
import net.md_5.bungee.api.config.ServerInfo;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class ServerInfos {

    public static void sendUpdate(GameServer server, ServerInfo info) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("ServerUpdate");
            out.writeUTF(server.getName() + ":" + server.getOnlinePlayers() + ":" + server.getMaxPlayers() + ":" + server.getState() + ":" + server.getBase());
        } catch(Exception e) {
        }
        info.sendData("NoCloud", stream.toByteArray());
        try {
            stream.close();
            out.close();
        } catch(Exception e) {
        }
    }
    public static void sendServerData(ServerInfo info) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("ServerData");
            for(ServerGroup group : Main.groups.values()) {
                out.writeUTF("GROUP: " + group.getName());
                for(GameServer server : group.getServers().values()) {
                    out.writeUTF("SERVER: " + server.getName() + ":" + server.getOnlinePlayers() + ":" + server.getMaxPlayers() + ":" + server.getState() + ":" + server.getBase() + ":" + server.getInfo().getAddress().getHostName() + ":" + server.getInfo().getAddress().getPort());
                }
            }
        } catch(Exception e) {
        }
        info.sendData("NoCloud", stream.toByteArray());
        try {
            stream.close();
            out.close();
        } catch(Exception e) {
        }
    }
    public static void addServer(GameServer server, ServerInfo info) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("AddServer");
            out.writeUTF(server.getName() + ":" + server.getOnlinePlayers() + ":" + server.getMaxPlayers() + ":" + server.getState() + ":" + server.getBase() + ":" + server.getInfo().getAddress().getHostName() + ":" + server.getInfo().getAddress().getPort());
        } catch(Exception e) {
        }
        info.sendData("NoCloud", stream.toByteArray());
        try {
            stream.close();
            out.close();
        } catch(Exception e) {
        }
    }
    public static void removeServer(String server, ServerInfo info) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        DataOutputStream out = new DataOutputStream(stream);
        try {
            out.writeUTF("RemoveServer");
            out.writeUTF(server);
        } catch(Exception e) {
        }
        info.sendData("NoCloud", stream.toByteArray());
        try {
            stream.close();
            out.close();
        } catch(Exception e) {
        }
    }
}
