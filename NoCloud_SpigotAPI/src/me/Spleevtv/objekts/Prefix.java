package me.Spleevtv.objekts;

import me.Spleevtv.main.Main;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class Prefix {

    private String prefix;
    private String noperm;

    public Prefix() {
        this.prefix = Main.instance.getConfig().getString("Prefix").replaceAll("&", "§");
        this.noperm = Main.instance.getConfig().getString("NoPerm").replaceAll("&", "§").replaceAll("%prefix%", this.prefix);
    }
    public String getPrefix() {
        return prefix;
    }
    public String getNoPerm() {
        return noperm;
    }
}
