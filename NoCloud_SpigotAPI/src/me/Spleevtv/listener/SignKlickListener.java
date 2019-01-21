package me.Spleevtv.listener;

import me.Spleevtv.api.CloudPlayerAPI;
import me.Spleevtv.api.NoCloudAPI;
import me.Spleevtv.main.Main;
import me.Spleevtv.objekts.SignTemplateKind;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class SignKlickListener implements Listener {

    @EventHandler
    public void onKlick(PlayerInteractEvent e) {
        try {
            Player p = e.getPlayer();
            if(e.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                if(e.getClickedBlock().getState() instanceof Sign) {
                    String loc = e.getClickedBlock().getLocation().getBlockX() + ";" + e.getClickedBlock().getLocation().getBlockY() + ";" + e.getClickedBlock().getLocation().getBlockZ();
                    if(Main.signs.get(loc) != null) {
                        if(Main.signs.get(loc).getState() != SignTemplateKind.SEARCHING) {
                            NoCloudAPI.getPlayerAPI().sendToGameServer(p, Main.signs.get(loc).getCurrentServer().getName());
                        } else {
                            p.sendMessage(Main.prefix.getPrefix() + "§cThis sign is empty.");
                        }
                    }
                }
            }
        } catch (Exception e1) {
        }
    }
}
