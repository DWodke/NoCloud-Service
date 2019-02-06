package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;

import javax.print.attribute.IntegerSyntax;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Aug, 2018
 */
public class GameServer {

    private int port;
    private String name;
    private int maxram;
    private ServerGroup group;
    private Process process;
    private Boolean haveTemplate;
    private Boolean isStarted;
    private Integer maxplayers;

    public GameServer(String n, int p, ServerGroup g, int m, Boolean h, Integer max) {
        this.name = n;
        this.port = p;
        ServerProcessManager.registerPort(this.port);
        this.maxram = m;
        this.group = g;
        this.haveTemplate = h;
        Init.game_servers.put(name, this);
        this.isStarted = false;
        this.maxplayers = max;
    }
    public void startServer() {
        if(Init.currentstartetserver == null || Init.currentstartetserver == this) {
            Init.currentstartetserver = this;
            this.isStarted = true;
            File folder = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/");
            if(this.haveTemplate) {
                if(folder.exists()) {
                    this.delete(folder);
                    folder.mkdirs();
                    this.resetServerProperties();
                    this.setSpigotJar();
                    this.loadTemplates();
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.directory(folder);
                    pb.command("java", "-jar", "-Xms" + this.maxram + "M", "-Xmx" + this.maxram + "M", "-jar", "spigot.jar");
                    try {
                        this.process = pb.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Init.core.sendTheCoreAMessage("STARTSPIGOTSERVER " + this.name + " " + this.port + " " + this.maxplayers + " " + this.maxram + " " + this.group.getMaxServerValue());
                } else {
                    folder.mkdirs();
                    this.resetServerProperties();
                    this.setSpigotJar();
                    this.loadTemplates();
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.directory(folder);
                    pb.command("java", "-jar", "-Xms" + this.maxram + "M", "-Xmx" + this.maxram + "M", "-jar", "spigot.jar");
                    try {
                        this.process = pb.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Init.core.sendTheCoreAMessage("STARTSPIGOTSERVER " + this.name + " " + this.port + " " + this.maxplayers + " " + this.maxram + " " + this.group.getMaxServerValue());
                }
            } else {
                if(folder.exists()) {
                    this.setSpigotJar();
                    try {
                        File template = new File("./Base/templates/" + "Global" + "/");
                        if(!template.exists()) {
                            template.mkdirs();
                        }
                        copyFilesInDirectory(template, new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.directory(folder);
                    pb.command("java", "-jar", "-Xms" + this.maxram + "M", "-Xmx" + this.maxram + "M", "-jar", "spigot.jar");
                    try {
                        this.process = pb.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Init.core.sendTheCoreAMessage("STARTSPIGOTSERVER " + this.name + " " + this.port + " " + this.maxplayers + " " + this.maxram + " " + this.group.getMaxServerValue());
                } else {
                    folder.mkdirs();
                    this.resetServerProperties();
                    try {
                        File template = new File("./Base/templates/" + "Global" + "/");
                        if(!template.exists()) {
                            template.mkdirs();
                        }
                        copyFilesInDirectory(template, new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/"));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    this.setSpigotJar();
                    ProcessBuilder pb = new ProcessBuilder();
                    pb.directory(folder);
                    pb.command("java", "-jar", "-Xms" + this.maxram + "M", "-Xmx" + this.maxram + "M", "-jar", "spigot.jar");
                    try {
                        this.process = pb.start();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Init.core.sendTheCoreAMessage("STARTSPIGOTSERVER " + this.name + " " + this.port + " " + this.maxplayers + " " + this.maxram + " " + this.group.getMaxServerValue());
                }
            }
        } else {
            if(!Init.server_warteschlange.contains(this)) {
                Init.server_warteschlange.add(this);
            }
        }
    }
    public void stopServer() {
        if(this.isStarted) {
            try {
                PrintStream writer = new PrintStream(this.process.getOutputStream());
                writer.println("stop");
                writer.flush();
            } catch (Exception e) {
            }
            if(Init.currentstartetserver != null) {
                if(Init.currentstartetserver.getName().equalsIgnoreCase(this.name)) {
                    if(!Init.server_warteschlange.isEmpty()) {
                        Init.currentstartetserver = Init.server_warteschlange.get(0);
                        Init.server_warteschlange.remove(0);
                        Init.currentstartetserver.startServer();
                    } else {
                        Init.currentstartetserver = null;
                    }
                }
            }
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Init.core.sendTheCoreAMessage("STOPSPIGOTSERVER " + name);
                    process.destroyForcibly();
                    process = null;
                    Init.game_servers.remove(name);
                    scheduler.shutdown();
                    if(haveTemplate) {
                        final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
                        scheduler.scheduleAtFixedRate(new Runnable() {
                            @Override
                            public void run() {
                                File temp = new File("./Base/temporary/" + group.getName() + "/" + name + "/");
                                if(temp.exists()) {
                                    delete(temp);
                                }
                                isStarted = false;
                                scheduler.shutdown();
                            }
                        }, 1, 1, TimeUnit.SECONDS);
                    } else {
                        isStarted = false;
                    }
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }
    private void delete(File dir){
        if (dir.isDirectory()){
            String[] entries = dir.list();
            for (int x=0;x<entries.length;x++){
                File aktFile = new File(dir.getPath(),entries[x]);
                delete(aktFile);
            }
            dir.delete();
        } else {
            dir.delete();
        }
    }
    public void restartServer() {
        if(this.isStarted) {
            try {
                PrintStream writer = new PrintStream(this.process.getOutputStream());
                writer.println("stop");
                writer.flush();
            } catch (Exception e) {
            }
            Init.core.sendTheCoreAMessage("STOPSPIGOTSERVER " + this.name);
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    process.destroyForcibly();
                    process = null;
                    startServer();
                    scheduler.shutdown();
                }
            }, 1, 1, TimeUnit.SECONDS);
        } else {
            if(Init.server_warteschlange.contains(this)) {
                return;
            }
            startServer();
        }
    }
    private void loadTemplates() {
        try {
            File template = new File("./Base/templates/" + "Global" + "/");
            if(!template.exists()) {
                template.mkdirs();
            }
            copyFilesInDirectory(template, new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        if(this.haveTemplate) {
            try {
                File template = new File("./Base/templates/" + this.getGroup() + "/");
                if(!template.exists()) {
                    template.mkdirs();
                }
                copyFilesInDirectory(template, new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
     private void resetServerProperties() {
       File server = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/server.properties");
       File plugins = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/plugins/");
       File plugin = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/plugins/CloudAPI/");
       File config = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/plugins/CloudAPI/core.yml");
       if(!plugins.exists()) {
           plugins.mkdirs();
       }
       if(!plugin.exists()) {
           plugin.mkdirs();
       }
       if(config.exists()) {
           config.delete();
       }
       if(server.exists()) {
           server.delete();
       }
         try {
             FileManager.copyFile(new File("./Base/standards/eula.txt"), new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/eula.txt"));
         } catch (IOException e) {
             e.printStackTrace();
         }
      try {
          server.createNewFile();
          FileWriter r = new FileWriter(server);
          BufferedWriter write = new BufferedWriter(r);
          write.write("#Minecraft server properties\n#Sat Mar 17 18:09:54 CET 2018\nspawn-protection=0\ngenerator-settings=\nop-permission-level=4\nallow-nether=false\nresource-pack-hash=\nlevel-name=world\nenable-query=false\nallow-flight=false\nannounce-player-achievements=true\nserver-port=" + this.port + "\nmax-world-size=29999984\nlevel-type=DEFAULT\nenable-rcon=false\nlevel-seed=\nforce-gamemode=false\nserver-ip=\nnetwork-compression-threshold=256\nmax-build-height=256\nspawn-npcs=true\nwhite-list=false\nspawn-animals=true\nhardcore=false\nsnooper-enabled=true\nonline-mode=false\nresource-pack=\npvp=true\ndifficulty=1\nenable-command-block=false\ngamemode=0\nplayer-idle-timeout=-1\nmax-players=" + this.maxplayers + "\nspawn-monsters=true\ngenerate-structures=true\nview-distance=10\nmotd=&7Service by &bNo&fCloud\nserver-name=" + this.name + "\n");
          write.close();
          r.close();
     } catch (IOException e) {
         e.printStackTrace();
     }
         try {
             config.createNewFile();
             FileWriter r = new FileWriter(config);
             BufferedWriter write = new BufferedWriter(r);
             write.write("# Here is the port and the host of the core.");
             write.newLine();
             write.write("# Don't change this data!");
             write.newLine();
             write.write("IP: '" + Init.core.getIP() + "'");
             write.newLine();
             write.write("Port: 13000");
             write.newLine();
             write.close();
             r.close();
         } catch (IOException e) {
             e.printStackTrace();
         }
    }
    private void setSpigotJar() {
        try {
            FileManager.copyFile(new File("./Base/standards/spigot.jar"), new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/spigot.jar"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public String getGroup() {
        return this.group.getName();
    }
    void copyFilesInDirectory(File from, File to) throws IOException {
        if(!to.exists()) {
            to.mkdirs();
        }
        for (File file : from.listFiles()) {
            if (file.isDirectory()) {
                File w = new File(to.getAbsolutePath() + "/" + file.getName());
                if(w.exists()) {
                    w.delete();
                    w.mkdirs();
                }
                copyFilesInDirectory(file, new File(to.getAbsolutePath() + "/" + file.getName()));
            } else {
                File n = new File(to.getAbsolutePath() + "/" + file.getName());
                Files.copy(file.toPath(), n.toPath(), StandardCopyOption.REPLACE_EXISTING);
            }
        }
    }
    public void sendTheServerACommand(String cmd) {
        PrintStream writer = new PrintStream(this.process.getOutputStream());
        writer.println(cmd + "\n");
        writer.flush();
    }
    public Process getProcess() {
        return process;
    }
    public Boolean isStarted() {
        return isStarted;
    }
    public String getName() {
        return name;
    }
    public Integer getId() {
        return Integer.parseInt(this.getName().split("-")[0]);
    }
    public int getPort() {
        return port;
    }
}