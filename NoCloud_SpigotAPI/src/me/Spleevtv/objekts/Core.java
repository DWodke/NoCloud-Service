package me.Spleevtv.objekts;

import me.Spleevtv.main.Main;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.BufferedWriter;
import java.io.File;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class Core {

    private Integer port;
    private String hostname;
    private Socket socket;

    public Core() {
        File file = new File(Main.instance.getDataFolder().getPath() + "/core.yml");
        YamlConfiguration cfg = YamlConfiguration.loadConfiguration(file);
        this.hostname = cfg.getString("IP");
        this.port = 13000;
    }
    public void sendMessage(String message) {
        try {
            this.socket = new Socket(this.hostname, this.port);
            BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(this.socket.getOutputStream()));
            writer.write(message);
            writer.flush();
            writer.close();
            this.socket.close();
            this.socket = null;
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
