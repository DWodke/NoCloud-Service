package me.Spleevtv.commands;

import me.Spleevtv.main.Main;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.config.ServerInfo;
import net.md_5.bungee.api.connection.ProxiedPlayer;
import net.md_5.bungee.api.plugin.Command;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class HubCMD extends Command {

    public HubCMD(String name) {
        super(name);
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(sender instanceof ProxiedPlayer) {
            ProxiedPlayer p = (ProxiedPlayer) sender;
            if(Main.usehubcmd) {
                if(!p.getServer().getInfo().getName().startsWith(Main.fallbackServerGroup)) {
                    ServerInfo i = Main.getBestPerformenceFallbackServer();
                    p.connect(i);
                    p.sendMessage(Main.hubmessage.replaceAll("%server%", i.getName()).replaceAll("%prefix%", Main.prefix).replaceAll("&", "§"));
                } else {
                    p.sendMessage(Main.alreadyonhub.replaceAll("&", "§").replaceAll("%prefix%", Main.prefix));
                }
            } else {
                p.sendMessage(Main.prefix + "§cThe '§6/hub§c' command is disabled.");
            }
        } else {
            sender.sendMessage(Main.prefix + "§cYou must be a player to use this command!");
        }
    }
}
