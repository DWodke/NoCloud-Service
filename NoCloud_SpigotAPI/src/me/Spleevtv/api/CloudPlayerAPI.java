package me.Spleevtv.api;

import me.Spleevtv.main.Main;
import org.bukkit.entity.Player;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class CloudPlayerAPI {

    public void sendToFallback(Player p) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("sendtofallback");
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(Main.instance, "NoCloud", b.toByteArray());
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
        }
    }
    public void sendToGameServer(Player p, String server) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("sendtoserver");
                out.writeUTF(server);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(Main.instance, "NoCloud", b.toByteArray());
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
            p.sendMessage(Main.prefix.getPrefix() + "§cThis service is not exist's.");
        }
    }
    public void sendToGroupRandom(Player p, String group) {
        try {
            ByteArrayOutputStream b = new ByteArrayOutputStream();
            DataOutputStream out = new DataOutputStream(b);
            try {
                out.writeUTF("sendtogroup");
                out.writeUTF(group);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
            p.sendPluginMessage(Main.instance, "NoCloud", b.toByteArray());
            try {
                out.close();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        } catch (Exception ex) {
        }
    }
}