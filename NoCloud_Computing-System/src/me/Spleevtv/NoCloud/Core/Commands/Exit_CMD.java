package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Base;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Core.Utils.Config;
import me.Spleevtv.NoCloud.Main;

import java.io.File;
import java.io.IOException;

public class Exit_CMD implements Command {
    @Override
    public void execute(String[] args) {
        if(args[0].equalsIgnoreCase("exit")) {
            if(args.length == 1) {
                for(int i = 0; i < 200; i++) {
                    System.out.println(" ");
                }
                System.out.println("\n" +
                        "\n" +
                        " _   _        ____ _                 _ \n" +
                        "| \\ | | ___  / ___| | ___  _   _  __| |\n" +
                        "|  \\| |/ _ \\| |   | |/ _ \\| | | |/ _` |\n" +
                        "| |\\  | (_) | |___| | (_) | |_| | (_| |\n" +
                        "|_| \\_|\\___/ \\____|_|\\___/ \\__,_|\\__,_|   by: SPLEEVTV | Dominik W.\n\n");
                System.out.println(Main.getPrefix() + "CloudSystem is unloading...");
                File cache = new File("cachekey.yml");
                try {
                    cache.createNewFile();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                Config cfg = new Config(cache);
                cfg.load();
                cfg.set("key", "Core");
                cfg.save();
                cfg.unload();
                for(Base base : Init.baselist.getBaselist().values()) {
                    base.sendTheBaseAMessage("RESTARTCLOUD");
                }
                System.exit(0);
            } else {
                System.out.println(Main.getPrefix() + "Usage: exit");
            }
        }
    }

    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "exit" + Main.ANSI_RESET + " | exit the cloud";
    }
}
