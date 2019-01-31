package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Main;

import java.io.*;
import java.nio.channels.FileChannel;
import java.nio.file.CopyOption;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

public class FileManager {

    public static Config baseconfig;

    public static void loadAllFiles() {
        File ordner = new File("./Base/");
        if(!ordner.exists()) {
            ordner.mkdirs();
        }
        File base_config = new File("./Base/Base_Config.yml");
        if(!base_config.exists()) {
            try {
                base_config.createNewFile();
                baseconfig = new Config(base_config);
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            baseconfig = new Config(base_config);
        }
        File settings = new File("./Base/GroupSettings/");
        if(!settings.exists()) {
            settings.mkdirs();
        }
        File proxy_list = new File("./Base/proxy_list.yml");
        if(!proxy_list.exists()) {
            try {
                proxy_list.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File server_list = new File("./Base/server_list.yml");
        if(!server_list.exists()) {
            try {
                server_list.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File s = new File("./Base/groups_settings.yml");
        if(!s.exists()) {
            try {
                s.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        File temorary_directory = new File("./Base/temporary/");
        if(!temorary_directory.exists()) {
            temorary_directory.mkdirs();
        }
        File templates = new File("./Base/templates/");
        if(!templates.exists()) {
            templates.mkdirs();
        }
        File globaltemplate = new File("./Base/templates/" + "Global" + "/");
        if(!globaltemplate.exists()) {
            globaltemplate.mkdirs();
        }
        File standards = new File("./Base/standards/");
        if(!standards.exists()) {
            standards.mkdirs();
        }
        setStandardsInDirectory();
        if(!isStandardsWasCopied()) {
            System.out.println(Main.getPrefix() + "The standard files have not been set yet!");
            System.out.println(Main.getPrefix() + "Pls copy a 'BungeeCord.jar' and 'spigot.jar' file in the standard directory. (./Base/standards)");
            System.out.println(Main.getPrefix() + "And restart the cloud afterwards.");
            Init.accesToStartServers = false;
        } else {
            Init.accesToStartServers = true;
        }
    }
    public static Boolean isStandardsWasCopied() {
        File bungeecord = new File("./Base/standards/BungeeCord.jar");
        File spigot = new File("./Base/standards/spigot.jar");
        if(bungeecord.exists() && spigot.exists()) {
            return true;
        } else {
            return false;
        }
    }
    public static void insertData(String paramString1, String paramString2) {
        InputStream localInputStream = FileManager.class.getClassLoader().getResourceAsStream(paramString1);
        try {
            Files.copy(localInputStream, Paths.get(paramString2, new String[0]),
                    new CopyOption[] { StandardCopyOption.REPLACE_EXISTING });
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private static void setStandardsInDirectory() {
        File config = new File("./Base/standards/config.yml");
        File spigot = new File("./Base/standards/spigot.yml");
        File picture = new File("./Base/standards/Cloud_Picture.png");
        File eula = new File("./Base/standards/eula.txt");
        if(!config.exists()) {
            insertData("config.yml", "./Base/standards/config.yml");
        }
        if(!spigot.exists()) {
            insertData("spigot.yml", "./Base/standards/spigot.yml");
        }
        if(!picture.exists()) {
            insertData("Cloud_Bild.png", "./Base/standards/Cloud_Picture.png");
        }
        if(!eula.exists()) {
            insertData("eula.txt", "./Base/standards/eula.txt");
        }
    }
    public static void copyFile(File in, File out) throws IOException {
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = new FileInputStream(in).getChannel();
            outChannel = new FileOutputStream(out).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (IOException e) {
            throw e;
        } finally {
            try {
                if (inChannel != null)
                    inChannel.close();
                if (outChannel != null)
                    outChannel.close();
            } catch (IOException e) {
            }
        }
    }
    public static void copy(File from, File to) throws IOException {
        if(from.exists()) {
            if (!to.exists()) {
                to.mkdirs();
            }
            for (File file : from.listFiles()) {
                if (file.isDirectory()) {
                    copy(file, new File(to.getAbsolutePath() + "/" + file.getName()));
                } else {
                    File n = new File(to.getAbsolutePath() + "/" + file.getName());
                    Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
                }
            }
        }
    }
}