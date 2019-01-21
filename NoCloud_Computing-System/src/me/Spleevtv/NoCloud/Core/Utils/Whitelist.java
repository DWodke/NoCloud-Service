package me.Spleevtv.NoCloud.Core.Utils;

import me.Spleevtv.NoCloud.Core.Init;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Dez, 2018
 */
public class Whitelist {

    private ArrayList<String> whitelist;
    private File whitelist_file;

    public Whitelist(File file) {
        this.whitelist = new ArrayList<String>();
        this.whitelist_file = file;
    }
    public void addUser(String username) {
        if(!this.whitelist.contains(username)) {
            this.whitelist.add(username);
            this.whitelist_file.delete();
            try {
                this.whitelist_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(this.whitelist_file));
                for(String user : this.whitelist) {
                    writer.write(user);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String end = "";
            for(String user : FileManager.whitelist.getWhitelist()) {
                end = end + user + ",";
            }
            for(BungeeCord proxy : Init.proxys) {
                proxy.sendTheProxyAMessage("UPDATEWHITELIST " + end);
            }
        }
    }
    public void removeUser(String username) {
        if(this.whitelist.contains(username)) {
            this.whitelist.remove(username);
            this.whitelist_file.delete();
            try {
                this.whitelist_file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(this.whitelist_file));
                for(String user : this.whitelist) {
                    writer.write(user);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String end = "";
            for(String user : FileManager.whitelist.getWhitelist()) {
                end = end + user + ",";
            }
            for(BungeeCord proxy : Init.proxys) {
                proxy.sendTheProxyAMessage("UPDATEWHITELIST " + end);
            }
        }
    }
    public ArrayList<String> getWhitelist() {
        return whitelist;
    }
}