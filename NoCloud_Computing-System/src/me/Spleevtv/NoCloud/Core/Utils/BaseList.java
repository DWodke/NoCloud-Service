package me.Spleevtv.NoCloud.Core.Utils;

import me.Spleevtv.NoCloud.Main;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class BaseList {

    private ArrayList<String> base_list = new ArrayList<String>();
    private HashMap<String, Base> bases = new HashMap<String, Base>();

    public BaseList() {
        File file = new File("./Core/Base_List.yml");
        try {
            FileInputStream in = new FileInputStream(file);
            BufferedReader reader = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String line;
            while((line = reader.readLine()) != null) {
                if(!this.base_list.contains(line)) {
                    this.base_list.add(line);
                }
            }
            if(this.base_list.isEmpty()) {
                System.out.println(Main.getPrefix() + "No base has yet been registered in the core.");
            } else {
                System.out.println(Main.getPrefix() + "____________________");
                System.out.println(Main.getPrefix() + " ");
                System.out.println(Main.getPrefix() + Main.ANSI_RED + "Available bases" + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
                for(String base : this.base_list) {
                    System.out.println(Main.getPrefix() + Main.ANSI_GREEN + base + Main.ANSI_RESET);
                }
                System.out.println(Main.getPrefix() + "____________________");
                System.out.println(Main.getPrefix() + " ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        connectToAllBases();
    }
    public void connectToAllBases() {
        System.out.println(Main.getPrefix() + "____________________");
        System.out.println(Main.getPrefix() + " ");
        System.out.println(Main.getPrefix() + Main.ANSI_RED + "Loading bases" + Main.ANSI_RESET + Main.ANSI_WHITE + ":" + Main.ANSI_RESET);
        FileManager.baseconfig.load();
        for(String base : this.base_list) {
            this.bases.put(base, new Base(base, FileManager.baseconfig.getInt(base + ".Port")));
            System.out.println(Main.getPrefix() + Main.ANSI_GREEN + "Open the socket-channel for " + Main.ANSI_RESET + "'" + Main.ANSI_YELLOW + base + Main.ANSI_RESET + "'");
        }
        FileManager.baseconfig.unload();
        System.out.println(Main.getPrefix() + "____________________");
        System.out.println(Main.getPrefix() + " ");
    }
    public HashMap<String, Base> getBaselist() {
        return this.bases;
    }
    public void createBase(String name, String port) {
        this.base_list.add(name);
        this.bases.put(name, new Base(name, Integer.parseInt(port)));
        FileManager.baseconfig.load();
        FileManager.baseconfig.set(name + ".Port", port);
        FileManager.baseconfig.save();
        FileManager.baseconfig.unload();
        File file = new File("./Core/Base_List.yml");
        if(file.exists()) {
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                for(String base : this.base_list) {
                    writer.write(base);
                    writer.newLine();
                }
                writer.flush();
                writer.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            try {
                file.createNewFile();
                try {
                    BufferedWriter writer = new BufferedWriter(new FileWriter(file));
                    for(String base : this.base_list) {
                        writer.write(base);
                        writer.newLine();
                    }
                    writer.flush();
                    writer.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
