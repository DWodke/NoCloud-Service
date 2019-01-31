package me.Spleevtv.NoCloud;

import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Base.Utils.Config;

import java.io.BufferedReader;
import java.io.File;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ThreadFactory;

public class Main {

    public static final String ANSI_RESET = "\u001B[0m";
    public static final String ANSI_BLACK = "\u001B[30m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_WHITE = "\u001B[37m";

    public static String prefix = ANSI_WHITE + "[" + ANSI_CYAN + "NoCloud" + ANSI_WHITE + "] " + ANSI_RESET;
    public static final SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");

    public static void main(String[] args) {
        System.out.println("\n" +
                "\n" +
                " _   _        ____ _                 _ \n" +
                "| \\ | | ___  / ___| | ___  _   _  __| |\n" +
                "|  \\| |/ _ \\| |   | |/ _ \\| | | |/ _` |\n" +
                "| |\\  | (_) | |___| | (_) | |_| | (_| |\n" +
                "|_| \\_|\\___/ \\____|_|\\___/ \\__,_|\\__,_|   by: SPLEEVTV | Dominik W.\n\n");
        System.out.println(getPrefix() + "CloudSystem is loading...");
        System.out.println(" ");
        System.out.println(" ");
        System.out.println(getPrefix() + ANSI_RED + "Choose one of the two" + ANSI_RESET + ":");
        System.out.println(getPrefix() + ANSI_GREEN + "Core" + ANSI_RESET + ": " + ANSI_YELLOW + "1" + ANSI_RESET);
        System.out.println(getPrefix() + ANSI_GREEN + "Base" + ANSI_RESET + ": " + ANSI_YELLOW + "2" + ANSI_RESET);
                try {
                    File cache = new File("cachekey.yml");
                    if(cache.exists()) {
                        Config cfg = new Config(cache);
                        cfg.load();
                        if(cfg.get("key").equalsIgnoreCase("Core")) {
                            cache.delete();
                            System.out.println(getPrefix() + "Core is loading...");
                            me.Spleevtv.NoCloud.Core.Init.startCore();
                        } else {
                            cache.delete();
                            System.out.println(getPrefix() + "Base is loading...");
                            Init.startBase();
                        }
                        return;
                    }
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                    while((line = reader.readLine()) != null) {
                        if(line.equalsIgnoreCase("1")) {
                            System.out.println(getPrefix() + "Core is loading...");
                            me.Spleevtv.NoCloud.Core.Init.startCore();
                            break;
                        } else if(line.equalsIgnoreCase("2")) {
                            System.out.println(getPrefix() + "Base is loading...");
                            Init.startBase();
                            break;
                        } else {
                            System.out.println(getPrefix() + "Pls choose one of the two!");
                        }
                    }
                } catch (Exception e) {
                }
    }
    public static String getPrefix() {
        return (getTime() + prefix);
    }
    public static String getTime() {
        return "[" + format.format(new Date()) + "] ";
    }
}
