package me.Spleevtv.NoCloud.Base.Utils;

import me.Spleevtv.NoCloud.Base.Init;
import me.Spleevtv.NoCloud.Main;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class ProxyServer {

    private int port;
    private String name;
    private int maxram;
    private ProxyGroup group;
    private Process process;
    private Boolean haveTemplate;
    private Boolean isStarted;
    private Integer maxplayers;
    private Integer connectionPort;

    public ProxyServer(String n, int p, ProxyGroup g, int m, Boolean h, Integer max, int conport) {
        this.name = n;
        this.port = p;
        ServerProcessManager.registerPort(this.port);
        this.maxram = m;
        this.group = g;
        this.haveTemplate = h;
        Init.proxy_servers.put(name, this);
        this.isStarted = false;
        this.maxplayers = max;
        this.connectionPort = conport;
    }

    public void startServer() {
        if(Init.canServerStart) {
            Init.canServerStart = false;
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    Init.canServerStart = true;
                    if(!Init.proxy_warteschlange.isEmpty()) {
                       ProxyServer server = Init.proxy_warteschlange.get(0);
                       server.startServer();
                       Init.proxy_warteschlange.remove(server);
                    } else if(!Init.server_warteschlange.isEmpty()) {
                        GameServer server = Init.server_warteschlange.get(0);
                        server.startServer();
                        Init.server_warteschlange.remove(server);
                    }
                    scheduler.shutdown();
                }
            }, 2, 1, TimeUnit.SECONDS);
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
            this.isStarted = true;
            File folder = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/");
            if(folder.exists()) {
                resetServerProperties();
                setConfigStandards();
                ProcessBuilder pb = new ProcessBuilder();
                pb.directory(folder);
                pb.command("java", "-jar", "-Xms" + this.maxram + "M", "-Xmx" + this.maxram + "M", "-jar", "BungeeCord.jar");
                try {
                    this.process = pb.start();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            } else {
                folder.mkdirs();
                try {
                    FileManager.copyFile(new File("./Base/standards/BungeeCord.jar"), new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/BungeeCord.jar"));
                    FileManager.copyFile(new File("./Base/standards/config.yml"), new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/config.yml"));
                    FileManager.copyFile(new File("./Base/standards/Cloud_Picture.png"), new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/server-icon.png"));
                    setConfigStandards();
                    resetServerProperties();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                ProcessBuilder pb = new ProcessBuilder();
                pb.directory(folder);
                pb.command("java", "-jar", "-Xms" + this.maxram + "M", "-Xmx" + this.maxram + "M", "-jar", "BungeeCord.jar");
                Process p = null;
                try {
                    p = pb.start();
                    this.process = p;
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } else {
            if(!Init.proxy_warteschlange.contains(this)) {
                Init.proxy_warteschlange.add(this);
            }
        }
    }
    public void stopServer() {
        if(this.isStarted) {
            try {
                PrintStream writer = new PrintStream(this.process.getOutputStream());
                writer.println("end");
                writer.flush();
            } catch (Exception e) {
            }
            final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            scheduler.scheduleAtFixedRate(new Runnable() {
                @Override
                public void run() {
                    process.destroyForcibly();
                    process = null;
                    Init.proxy_servers.remove(name);
                    isStarted = false;
                    scheduler.shutdown();
                }
            }, 1, 1, TimeUnit.SECONDS);
        }
    }
    public void restartServer() {
        if(this.isStarted) {
            try {
                PrintStream writer = new PrintStream(this.process.getOutputStream());
                writer.println("end");
                writer.flush();
            } catch (Exception e) {
            }
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
            if(Init.proxy_warteschlange.contains(this)) {
                return;
            }
            startServer();
        }
    }
    void setConfigStandards() {
        File plugins = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/plugins");
        if(!plugins.exists()) {
            plugins.mkdirs();
        }
        File config_directory = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/plugins/NoCloud-BungeeAPI");
        if(!config_directory.exists()) {
            config_directory.mkdirs();
        }
        File config = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/plugins/NoCloud-BungeeAPI/config.yml");
        if(!config.exists()) {
            try {
                config.createNewFile();
                BufferedWriter writer = new BufferedWriter(new PrintWriter(config, "UTF-8"));
                Config cfg = FileManager.baseconfig;
                cfg.load();
                writer.write("# This is the data to use connect to the core.");
                writer.newLine();
                writer.write("IP: " + Init.core.getIP());
                writer.newLine();
                writer.write("Port: " + this.connectionPort);
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This is the system-prefix.");
                writer.newLine();
                writer.write("Prefix: '&b&lNo&f&lCloud &8| &7'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This is the message was the cloud send to you.");
                writer.newLine();
                writer.write("Message: '&7The information was send to the cloud.'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This is the message where a user have not a permission.");
                writer.newLine();
                writer.write("NoRights: '&cYou have no authority!'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# %OP% = OnlinePlayers and %MP% = MaxPlayers");
                writer.newLine();
                writer.write("MaintenanceAction: '&8&l> &b&lMaintenance'");
                writer.newLine();
                writer.write("NormalAction: '&7[&a"+ "%OP%" +"&8/&c" + "%MP%" + "&7]'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# The splitter of the MaintenanceActionDescription is: '/n'");
                writer.newLine();
                writer.write("MaintenanceActionDescription: ' /n&f&lMade by: &bDominik W./n&f&lTwitter: &b@SPLEEVTV/n '");
                writer.newLine();
                writer.write("NormalActionDescription: ' /n&f&lMade by: &bDominik W./n&f&lTwitter: &b@SPLEEVTV/n '");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This message was printed if start a server.");
                writer.newLine();
                writer.write("# You can put it on or off.");
                writer.newLine();
                writer.write("message: true");
                writer.newLine();
                writer.write("start-message: '%prefix%&7The server &e%server% &7has &a&lstarted&8.'");
                writer.newLine();
                writer.write("stop-message: '%prefix%&7The server &e%server% &7has &c&lstopped&8.'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This message was printed if a new player is registered in the cloud.");
                writer.newLine();
                writer.write("# You can put it on or off (true or false).");
                writer.newLine();
                writer.write("# %prefix% = prefix | %player% = player");
                writer.newLine();
                writer.write("NewPlayerBoolean: true");
                writer.newLine();
                writer.write("NewPlayerMessage: '%prefix%The player &a%player% &7is new on this network.'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# The message was printed if a user use the command '/hub'.");
                writer.newLine();
                writer.write("# You can put it on or off (true or false).");
                writer.newLine();
                writer.write("CanUseHubCommand: true");
                writer.newLine();
                writer.write("HubMessage: '%prefix%&7You was send to the server &6%server%&8.'");
                writer.newLine();
                writer.write("AllReadyOnFallback: '%prefix%&cYou are already on a fallback server.'");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This is the tablist header and footer.");
                writer.newLine();
                writer.write("# You can put it on or off (true or false).");
                writer.newLine();
                writer.write("# %split% = split the lines | %op% and %mp% = players | %server% = currentserver");
                writer.newLine();
                writer.write("UseCloudTablist: true");
                writer.newLine();
                writer.write("Header: ''");
                writer.newLine();
                writer.write("Footer: ''");
                writer.newLine();
                writer.newLine();
                writer.newLine();
                writer.write("# This is the maintenance player join message.");
                writer.newLine();
                writer.write("# You can put it on or off (true or false).");
                writer.newLine();
                writer.write("# %player% = currentPlayer | %prefix% = prefix of the system");
                writer.newLine();
                writer.write("MaintenanceMessageAllow: true");
                writer.newLine();
                writer.write("MaintenanceMessage: '%prefix%&7The player &6%player% &7has tried to connect to the network.'");
                writer.flush();
                writer.close();
                writer.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
            }
        }
    void resetServerProperties() {
        File c = new File("./Base/temporary/" + this.group.getName() + "/" + this.name + "/config.yml");
        if(c.exists()) {
            c.delete();
        }
        try {
            c.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
            try {
                BufferedWriter writer = new BufferedWriter(new PrintWriter(c, "UTF-8"));
                writer.write("ip_forward: true\n" +
                        "network_compression_threshold: 256\n" +
                        "stats: 331b78db-6111-43bb-99a7-4917ed942249\n" +
                        "groups:\n" +
                        "  SPLEEVTV:\n" +
                        "  - admin\n" +
                        "servers:\n" +
                        "  Lobby-1:\n" +
                        "    motd: '&1Just another BungeeCord - Forced Host'\n" +
                        "    address: localhost:45000\n" +
                        "    restricted: false\n" +
                        "timeout: 30000\n" +
                        "player_limit: " + this.maxplayers + "\n" +
                        "listeners:\n" +
                        "- query_port: " + this.port + "\n" +
                        "  motd: ''\n" +
                        "  tab_list: GLOBAL_PING\n" +
                        "  query_enabled: false\n" +
                        "  proxy_protocol: false\n" +
                        "  forced_hosts:\n" +
                        "    pvp.md-5.net: pvp\n" +
                        "  ping_passthrough: false\n" +
                        "  bind_local_address: true\n" +
                        "  host: 0.0.0.0:" + this.port + "\n" +
                        "  max_players: " + this.maxplayers + "\n" +
                        "  tab_size: 60\n" +
                        "  force_default_server: false\n" +
                        "  priorities:\n" +
                        "  - Lobby-1\n" +
                        "prevent_proxy_connections: false\n" +
                        "permissions:\n" +
                        "  default:\n" +
                        "  admin:\n" +
                        "  - bungeecord.command.alert\n" +
                        "  - bungeecord.command.end\n" +
                        "  - bungeecord.command.ip\n" +
                        "  - bungeecord.command.reload\n" +
                        "  - bungeecord.command.server\n" +
                        "  - cloud.maintenance\n" +
                        "  - cloud.use\n" +
                        "online_mode: true\n" +
                        "log_commands: false\n" +
                        "disabled_commands:\n" +
                        "- disabledcommandhere\n" +
                        "connection_throttle: -1");
                writer.flush();
                writer.close();
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
    public int getPort() {
        return port;
    }
}