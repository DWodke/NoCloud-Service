package me.Spleevtv.NoCloud.Core.Utils;

import java.io.*;
import java.util.ArrayList;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class PlayerStats {

    private ArrayList<String> players;
    private ArrayList<String> hostnames;
    private ArrayList<String> hostnamesall;
    private Integer maxpings;
    private Integer pingsfromlast5min;
    private File cfg = new File("./Core/PlayerCache/pings.yml");

    public PlayerStats() {
        this.players = new ArrayList<String>();
        this.hostnames = new ArrayList<String>();
        this.hostnamesall = new ArrayList<String>();
        Config config = new Config(cfg);
        config.load();
        if(config.get("maxpings") == null) {
            this.maxpings = 0;
            config.set("maxpings", "0");
            config.save();
        } else {
            this.maxpings = config.getInt("maxpings");
        }
        config.unload();
        this.pingsfromlast5min = 0;
        final ScheduledExecutorService scheduler1 = Executors.newScheduledThreadPool(1);
        scheduler1.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                pingsfromlast5min = 0;
                hostnames.clear();
            }
        }, 5, 5, TimeUnit.MINUTES);
        File file = new File("./Core/PlayerCache/uuidcache.yml");
        try {
            BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            while((line = reader.readLine()) != null) {
                players.add(line);
            }
            reader.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public Boolean checkPlayer(String uuid) {
        if(this.players.contains(uuid)) {
            return true;
        } else {
            this.players.add(uuid);
            File file = new File("./Core/PlayerCache/uuidcache.yml");
            if(file.exists()) {
                file.delete();
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for(String uuids : this.players) {
                    writer.write(uuids);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return false;
        }
    }
    public void addPing(String hostname) {
        if(!this.hostnames.contains(hostname)) {
            this.pingsfromlast5min++;
            this.hostnames.add(hostname);
            if(!hostnamesall.contains(hostname)) {
                this.maxpings++;
                hostnamesall.add(hostname);
                Config config = new Config(cfg);
                config.load();
                config.set("maxpings", this.maxpings + "");
                config.save();
                config.unload();
            }
        }
    }
    public Integer getMaxPings() {
        return maxpings;
    }
    public Integer getPingsFromLast5min() {
        return pingsfromlast5min;
    }
    public Integer getRegisteredPlayerSize() {
        return players.size();
    }
}
