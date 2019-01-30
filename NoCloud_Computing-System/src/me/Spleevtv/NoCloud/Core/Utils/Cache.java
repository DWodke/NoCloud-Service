package me.Spleevtv.NoCloud.Core.Utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;

import java.io.*;

public class Cache {

    private int proxy_connection_port;
    private int bungeeCords;

    public Cache() {
        try {
            File cache = new File("./Core/Cache.json");
            Gson gson = new Gson();
            JsonObject json = gson.fromJson(new FileReader(cache), JsonObject.class);
            this.bungeeCords = json.get("proxys").getAsNumber().intValue();
            this.proxy_connection_port = json.get("proxySocketStartPort").getAsNumber().intValue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
    public int getBungeeCords() {
        return bungeeCords;
    }
    public int getProxy_connection_port() {
        return proxy_connection_port;
    }
    public void editBungeeCords(int cords) {
        try {
            File cache = new File("./Core/Cache.json");
            if(cache.exists()) {
                cache.delete();
            }
            cache.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(cache), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("proxys", cords);
            json.addProperty("proxySocketStartPort", this.proxy_connection_port);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public void editProxyConnectionPorts(int p) {
        try {
            File cache = new File("./Core/Cache.json");
            if(cache.exists()) {
                cache.delete();
            }
            cache.createNewFile();
            OutputStreamWriter writer = new OutputStreamWriter(new FileOutputStream(cache), "UTF-8");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            JsonObject json = new JsonObject();
            json.addProperty("proxys", this.bungeeCords);
            json.addProperty("proxySocketStartPort", p);
            writer.write(gson.toJson(json));
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
