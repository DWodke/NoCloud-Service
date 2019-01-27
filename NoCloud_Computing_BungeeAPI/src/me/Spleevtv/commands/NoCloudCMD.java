package me.Spleevtv.commands;

import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.GameServer;
import me.Spleevtv.objekts.ServerGroup;
import net.md_5.bungee.api.CommandSender;
import net.md_5.bungee.api.ProxyServer;
import net.md_5.bungee.api.plugin.Command;

import java.util.concurrent.TimeUnit;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class NoCloudCMD extends Command {

    public NoCloudCMD(String name) {
        super(name);
    }
    @Override
    public void execute(CommandSender sender, String[] args) {
        if(args.length == 0) {
            sender.sendMessage(getHelpMap());
            return;
        } else if(args.length == 1) {
            String value = args[0];
            if(value.equalsIgnoreCase("listServers")) {
                sender.sendMessage(Main.prefix + "§cAll available services§8:");
                for(ServerGroup group : Main.groups.values()) {
                    sender.sendMessage(Main.prefix + "§bGroup§7: §d" + group.getName());
                    for(GameServer server : group.getServers().values()) {
                        sender.sendMessage(Main.prefix + "§a" + server.getName()
                        + " §f(§eonlinePlayers§7: §b" + server.getOnlinePlayers() + "§7, "
                        + " §emaxPlayers§7: §b" + server.getMaxPlayers() + "§7, "
                        + " §estate§7: §b" + server.getState() + "§7, "
                        + " §ebase§7: §b" + server.getBase() + "§f)");
                    }
                }
            } else if(value.equalsIgnoreCase("stats")) {
                Main.core.sendToTheCoreAMessage("GETSTATS");
                sender.sendMessage(Main.prefix + "§7Loading stats from NoCloud database...");
                ProxyServer.getInstance().getScheduler().schedule(Main.instance, new Runnable() {
                    @Override
                    public void run() {
                        sender.sendMessage(Main.prefix + "§cAll available cloud stats§8:");
                        sender.sendMessage(Main.prefix + "§aRegistered players§7: §e" + Main.registeredPlayers);
                        sender.sendMessage(Main.prefix + "§aPings (all time)§7: §e" + Main.maxPings);
                        sender.sendMessage(Main.prefix + "§aPings (last 5 minutes)§7: §e" + Main.pingsLast5Minutes);
                        sender.sendMessage(Main.prefix + "§aAvailable bases§7: §e" + Main.basesize);
                        Double time = round((double) (System.currentTimeMillis() - Main.cloudstarttime) /1000);
                        sender.sendMessage(Main.prefix + "§aUptime (in seconds)§7: §e" + time);
                    }
                }, 1, TimeUnit.SECONDS);
            } else {
                sender.sendMessage(getHelpMap());
                return;
            }
        } else if(args.length == 2) {
            String value = args[0];
            if(value.equalsIgnoreCase("restart")) {
                String subvalue = args[1];
                Main.core.sendToTheCoreAMessage("RESTARTSERVER " + subvalue);
                sender.sendMessage(Main.message);
            } else if(value.equalsIgnoreCase("start")) {
                String subvalue = args[1];
                Main.core.sendToTheCoreAMessage("STARTSERVER " + subvalue);
                sender.sendMessage(Main.message);
            } else if(value.equalsIgnoreCase("stop")) {
                String subvalue = args[1];
                Main.core.sendToTheCoreAMessage("STOPSERVER " + subvalue);
                sender.sendMessage(Main.message);
            } else if(value.equalsIgnoreCase("removeGroup")) {
                String subvalue = args[1];
                Main.core.sendToTheCoreAMessage("REMOVEGROUP " + subvalue);
                sender.sendMessage(Main.message);
            } else if(value.equalsIgnoreCase("copy")) {
                String subvalue = args[1];
                if(subvalue.contains("-")) {
                    Main.core.sendToTheCoreAMessage("COPY " + subvalue);
                    sender.sendMessage(Main.message);
                } else {
                    sender.sendMessage(Main.prefix + "§cYou must write a server!");
                }
            } else if(value.equalsIgnoreCase("maintenance")) {
                String subvalue = args[1];
                if(subvalue.equalsIgnoreCase("on")) {
                    if(Main.maintenance) {
                        sender.sendMessage(Main.prefix + "§cThe maintenance mode is already in use!");
                        return;
                    }
                    Main.core.sendToTheCoreAMessage("MAINTENANCE true");
                    sender.sendMessage(Main.message);
                } else if(subvalue.equalsIgnoreCase("off")) {
                    if(!Main.maintenance) {
                        sender.sendMessage(Main.prefix + "§cThe maintenance mode is already not in use!");
                        return;
                    }
                    Main.core.sendToTheCoreAMessage("MAINTENANCE false");
                    sender.sendMessage(Main.message);
                } else if(subvalue.equalsIgnoreCase("list")) {
                    sender.sendMessage(Main.prefix + "§cWhitelist users§8:");
                    for(String name : Main.whitelist) {
                        sender.sendMessage(Main.prefix + "§8- §a" + name);
                    }
                } else {
                    sender.sendMessage(getHelpMap());
                    return;
                }
            } else {
                sender.sendMessage(getHelpMap());
                return;
            }
        }
    }
    public static String getHelpMap() {
        String ret = "hjkhjbmqwve";
        for(String s : Main.helpmap) {
            ret = ret + "\n" + s;
        }
        ret = ret.replaceAll("hjkhjbmqwve\n", "");
        return ret;
    }
    public static double round(double betrag) {
        double round = Math.round(betrag*10000);
        round = round / 10000;
        round = Math.round(round*1000);
        round = round / 1000;
        round = Math.round(round*100);
        return round / 100;
    }
}
