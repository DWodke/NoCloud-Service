package me.Spleevtv.NoCloud.Base;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import me.Spleevtv.NoCloud.Base.Utils.*;
import me.Spleevtv.NoCloud.Main;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Init {

    private static int install;
    public static Core core;
    public static Boolean accesToStartServers;
    public static HashMap<String, ProxyServer> proxy_servers = new HashMap<String, ProxyServer>();
    public static HashMap<String, GameServer> game_servers = new HashMap<String, GameServer>();
    public static HashMap<String, ProxyGroup> proxy_groups = new HashMap<String, ProxyGroup>();
    public static HashMap<String, ServerGroup> game_groups = new HashMap<String, ServerGroup>();
    public static Boolean canServerStart;
    public static ArrayList<ProxyServer> proxy_warteschlange = new ArrayList<ProxyServer>();
    public static ArrayList<GameServer> server_warteschlange = new ArrayList<GameServer>();
    public static GameServer currentstartetserver;

    public static void startBase() {
        FileManager.loadAllFiles();
        FileManager.baseconfig.load();
        if(FileManager.baseconfig.get("BaseName") != null) {
            core = new Core();
            System.out.println(Main.getPrefix() + "This base has successfully loaded.");
            File proxy_list = new File("./Base/proxy_list.yml");
            canServerStart = true;
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(proxy_list), "UTF-8"));
                String line;
                File settings = new File("./Base/groups_settings.yml");
                Config cfg = new Config(settings);
                cfg.load();
                while((line = reader.readLine()) != null) {
                    proxy_groups.put(line, new ProxyGroup(line, cfg.getInt(line + ".MaxRam"), Boolean.parseBoolean(cfg.get(line + ".HaveTemplate")), cfg.getInt(line + ".ServerValue"), cfg.getInt(line + ".ServerOnStart"), cfg.getInt(line + ".MaxPlayers"), cfg.getInt(line + ".StartPort"), cfg.get(line + ".ConnectionPorts")));
                }
                cfg.unload();
            } catch (Exception e) {
                e.printStackTrace();
            }
            File gameserver_list = new File("./Base/server_list.yml");
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(gameserver_list), "UTF-8"));
                String line;
                while((line = reader.readLine()) != null) {
                    File settings = new File("./Base/GroupSettings/" + line + ".json");
                    if(settings.exists()) {
                        try {
                            Gson gson = new Gson();
                            JsonObject json = gson.fromJson(new FileReader(settings), JsonObject.class);
                            Integer onlineamount = json.get("onlineAmount").getAsNumber().intValue();
                            Integer maxamount = json.get("maxAmount").getAsNumber().intValue();
                            Integer maxram = json.get("maxRam").getAsNumber().intValue();
                            Integer maxplayers = json.get("maxPlayers").getAsNumber().intValue();
                            Integer startport = json.get("startPort").getAsNumber().intValue();
                            Boolean dynamic = json.get("dynamic").getAsBoolean();
                            core.sendTheCoreAMessage("INITGROUP " + line + " " + onlineamount + " " + maxamount + " " + maxram + " " + maxplayers + " " + dynamic);
                            game_groups.put(line, new ServerGroup(line, maxram, dynamic, maxamount, onlineamount, maxplayers, startport));
                        } catch (FileNotFoundException e) {
                            e.printStackTrace();
                    }
                    } else {
                        System.out.println(Main.getPrefix() + "The group '" + line + "' don't loaded.");
                        System.out.println(Main.getPrefix() + "Reason: the group file doesn't exists!");
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            install = 0;
            System.out.println(Main.getPrefix() + "This Base is not installed yet!");
            System.out.println(" ");
            System.out.println(Main.getPrefix() + "Pls configure this Base.");
            System.out.println(Main.getPrefix() + "Pls type the name of the base in the console.");
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        String line;
                        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        while((line = reader.readLine()) != null) {
                            if(install == 0) {
                                FileManager.baseconfig.set("BaseName", line);
                                FileManager.baseconfig.save();
                                install = 1;
                                System.out.println(Main.getPrefix() + "Pls type the total ram of the base in the console.");
                            } else if(install == 1) {
                                int i;
                                try {
                                    i = Integer.parseInt(line);
                                } catch (NumberFormatException e) {
                                    System.out.println(Main.getPrefix() + "Pls type the total ram of the base in the console.");
                                    continue;
                                }
                                FileManager.baseconfig.set("TotalRam", line);
                                FileManager.baseconfig.save();
                                install = 2;
                                System.out.println(Main.getPrefix() + "Pls type the ip-address of the core in the base console. (Standard: 127.0.0.1)");
                            } else if(install == 2) {
                                FileManager.baseconfig.set("Core-IP", line);
                                FileManager.baseconfig.save();
                                install = 3;
                                System.out.println(Main.getPrefix() + "Please type in the console the port that you have set in the core of this base.");
                            } else if(install == 3) {
                                int i;
                                try {
                                    i = Integer.parseInt(line);
                                } catch (NumberFormatException e) {
                                    System.out.println(Main.getPrefix() + "Please type in the console the port that you have set in the core of this base.");
                                    continue;
                                }
                                FileManager.baseconfig.set("Core-Port", line);
                                FileManager.baseconfig.save();
                                FileManager.baseconfig.unload();
                                System.out.println(Main.getPrefix() + "You have successfully installed this base.");
                                break;
                            }
                        }
                    } catch (Exception e) {
                    }
                }
            }).start();
        }
    }
    /*/public static void registerCheckServers() {
            final ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
            scheduler1.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    for(String servername : game_servers.keySet()) {
                        GameServer api = game_servers.get(servername);
                        if(api.isStarted()) {
                            if(!isAlive(api.getProcess(), game_groups.get(api.getGroup()).getPortFromServer(api.getName()))) {
                                api.restartServer();
                                continue;
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }, 15, 2, TimeUnit.SECONDS);
    }
    static Socket socket = new Socket();
    public static boolean isAlive(Process p, Integer port) {
        if(p != null) {
            if(p.getInputStream() == null) {
                return false;
            }
            if(p.getErrorStream() == null) {
                return false;
            }
            if(p.getOutputStream() == null) {
                return false;
            }
            try {
                return (p != null) && (p.isAlive()) && (p.getInputStream().available() != -1) && (p.getErrorStream().available() != -1);
            } catch (IOException e) {
            }
            try {
                socket.bind(new InetSocketAddress(port));
                socket.close();
                return true;
            } catch (IOException e) {
                return false;
            }
        } else {
            return true;
        }
    }/*/
}
