package me.Spleevtv.NoCloud.Core;

import me.Spleevtv.NoCloud.Core.Commands.*;
import me.Spleevtv.NoCloud.Core.Utils.*;
import me.Spleevtv.NoCloud.Main;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LongSummaryStatistics;

public class Init {

    public static ArrayList<Command> commands = new ArrayList<Command>();
    public static BaseList baselist;
    public static BungeeTemplate bungee_template;
    public static Cache cache;
    public static ArrayList<BungeeCord> proxys = new ArrayList<>();
    public static HashMap<String, String[]> online_servers = new HashMap<String, String[]>();
    public static CloudPlayers players;
    public static HashMap<String, GameServer> gameserver = new HashMap<String, GameServer>();
    public static HashMap<String, ServerGroup> groups = new HashMap<String, ServerGroup>();
    public static Integer addedfallbackserver;
    public static HashMap<String, Integer> latestplayer = new HashMap<String, Integer>();
    public static ServerChannel serverchannel;
    public static PlayerStats playerstats;
    public static Long starttime;
    public static HashMap<String, Long> time = new HashMap<String, Long>();

    public static void startCore() {
        starttime = System.currentTimeMillis();
        startCommandReading();
        FileManager.loadAllFiles();
        serverchannel = new ServerChannel();
        baselist = new BaseList();
        players = new CloudPlayers();
        addedfallbackserver = 0;
        playerstats = new PlayerStats();
        int port = cache.getProxy_connection_port() -1;
        for(int i = 1; i <= cache.getBungeeCords(); i++) {
            proxys.add(new BungeeCord(port));
            port++;
        }
        System.out.println(Main.getPrefix() + "Core is successfully loaded and available.");
    }
    private static void startCommandReading() {
        addCommand(new AddGroup_CMD());
        addCommand(new Start_CMD());
        addCommand(new Help_CMD());
        addCommand(new RemoveGroup_CMD());
        addCommand(new Restart_CMD());
        addCommand(new Stop_CMD());
        addCommand(new SendCommand_CMD());
        addCommand(new Base_CMD());
        addCommand(new Config_CMD());
        addCommand(new Maintenance_CMD());
        addCommand(new Stats_CMD());
        addCommand(new Listserver_CMD());
        addCommand(new EditGroup_CMD());
        addCommand(new Copy_CMD());
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    String line;
                    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
                        while((line = reader.readLine()) != null) {
                            if(line.length() != 0) {
                                for(Command cmd : commands) {
                                    cmd.execute(line.split(" "));
                                }
                            }
                    }
                } catch (Exception e) {
                }
            }
        }).start();
    }
    private static void addCommand(Command c) {
        commands.add(c);
    }
}
