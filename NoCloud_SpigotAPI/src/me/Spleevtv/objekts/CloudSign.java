package me.Spleevtv.objekts;

import me.Spleevtv.main.Main;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.event.server.ServerListPingEvent;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ConnectException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class CloudSign {

    private ServerGroup currentgroup;
    private String groupname;
    private GameServer curentserver;
    private SignTemplateKind state;
    private Location location;

    public CloudSign(String group, Location loc) {
        this.location = loc;
        this.groupname = group;
        ServerGroup g = Main.groups.get(group);
        if(g != null) {
        this.currentgroup = g;
        Integer i = 1;
            for(GameServer server : this.currentgroup.getServers().values()) {
                if(!Main.usedserver.contains(server)) {
                    if(!Main.templates.get(SignTemplateKind.INGAME).isHide()) {
                        if(server.getState() == ServerState.INGAME) {
                            this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                            this.state = SignTemplateKind.INGAME;
                            Main.usedserver.add(this.curentserver);
                            updateSign();
                            break;
                        }
                    }
                    if(!Main.templates.get(SignTemplateKind.ENDING).isHide()) {
                        if(server.getState() == ServerState.ENDING) {
                            this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                            this.state = SignTemplateKind.ENDING;
                            Main.usedserver.add(this.curentserver);
                            updateSign();
                            break;
                        }
                    }
                    if(!Main.templates.get(SignTemplateKind.LOBBY).isHide()) {
                        if(server.getState() == ServerState.LOBBY) {
                            this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                            this.state = SignTemplateKind.LOBBY;
                            Main.usedserver.add(this.curentserver);
                            updateSign();
                            break;
                        }
                    }
                    if(!Main.templates.get(SignTemplateKind.ONLINE).isHide()) {
                        if(server.getState() == ServerState.ONLINE) {
                            this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                            this.state = SignTemplateKind.ONLINE;
                            Main.usedserver.add(this.curentserver);
                            updateSign();
                            break;
                        }
                    }
                    i++;
                    if(i == this.currentgroup.getServers().size()) {
                        this.curentserver = null;
                        this.state = SignTemplateKind.SEARCHING;
                        updateSign();
                    }
                }
            }
            if(this.curentserver == null) {
                this.state = SignTemplateKind.SEARCHING;
                this.curentserver = null;
                updateSign();
            }
        } else {
            this.state = SignTemplateKind.SEARCHING;
            this.curentserver = null;
            updateSign();
        }
    }
    public GameServer getCurrentServer() {
        return curentserver;
    }
    public Location getLocation() {
        return location;
    }
    public ServerGroup getCurrentGroup() {
        return currentgroup;
    }
    public SignTemplateKind getState() {
        return state;
    }
    public void search() {
        if(this.currentgroup == null) {
            ServerGroup group = Main.groups.get(this.groupname);
            if(group != null) {
                this.currentgroup = group;
                this.state = SignTemplateKind.SEARCHING;
            } else {
                this.curentserver = null;
                this.state = SignTemplateKind.SEARCHING;
                updateSign();
                return;
            }
        }
        if(this.currentgroup.getServers().isEmpty()) {
            this.curentserver = null;
            this.state = SignTemplateKind.SEARCHING;
            updateSign();
            return;
        }
        if(this.curentserver != null) {
           if(Main.usedserver.contains(this.curentserver)) {
               Main.usedserver.remove(this.curentserver);
           }
            Integer i = 1;
            for(GameServer server : this.currentgroup.getServers().values()) {
                if(server != this.curentserver || server != null) {
                    if(!Main.usedserver.contains(server)) {
                        if(!Main.templates.get(SignTemplateKind.INGAME).isHide()) {
                            if(server.getState() == ServerState.INGAME) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.INGAME;
                                Main.usedserver.add(this.curentserver);
                                this.updateSign();
                                break;
                            }
                        }
                        if(!Main.templates.get(SignTemplateKind.ENDING).isHide()) {
                            if(server.getState() == ServerState.ENDING) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.ENDING;
                                Main.usedserver.add(this.curentserver);
                                this.updateSign();
                                break;
                            }
                        }
                        if(!Main.templates.get(SignTemplateKind.LOBBY).isHide()) {
                            if(server.getState() == ServerState.LOBBY) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.LOBBY;
                                Main.usedserver.add(this.curentserver);
                                this.updateSign();
                                break;
                            }
                        }
                        if(!Main.templates.get(SignTemplateKind.ONLINE).isHide()) {
                            if(server.getState() == ServerState.ONLINE) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.ONLINE;
                                Main.usedserver.add(this.curentserver);
                                this.updateSign();
                                break;
                            }
                        }
                        if(i == this.currentgroup.getServers().size()) {
                            this.curentserver = null;
                            this.state = SignTemplateKind.SEARCHING;
                            updateSign();
                            return;
                        }
                        i++;
                    }
                }
            }
            if(this.curentserver == null) {
                this.curentserver = null;
                this.state = SignTemplateKind.SEARCHING;
                updateSign();
            }
        } else {
            Integer i = 1;
            for(GameServer server : this.currentgroup.getServers().values()) {
                if(server != this.curentserver) {
                    if(!Main.usedserver.contains(server)) {
                        if(!Main.templates.get(SignTemplateKind.INGAME).isHide()) {
                            if(server.getState() == ServerState.INGAME) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.INGAME;
                                Main.usedserver.add(this.curentserver);
                                break;
                            }
                        }
                        if(!Main.templates.get(SignTemplateKind.ENDING).isHide()) {
                            if(server.getState() == ServerState.ENDING) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.ENDING;
                                Main.usedserver.add(this.curentserver);
                                break;
                            }
                        }
                        if(!Main.templates.get(SignTemplateKind.LOBBY).isHide()) {
                            if(server.getState() == ServerState.LOBBY) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.LOBBY;
                                Main.usedserver.add(this.curentserver);
                                break;
                            }
                        }
                        if(!Main.templates.get(SignTemplateKind.ONLINE).isHide()) {
                            if(server.getState() == ServerState.ONLINE) {
                                this.curentserver = this.currentgroup.getServerOutGroup(server.getName());
                                this.state = SignTemplateKind.ONLINE;
                                Main.usedserver.add(this.curentserver);
                                break;
                            }
                        }
                        if(i == this.currentgroup.getServers().size()) {
                            this.curentserver = null;
                            this.state = SignTemplateKind.SEARCHING;
                            updateSign();
                            return;
                        }
                        i++;
                    }
                }
            }
            if(this.curentserver == null) {
                this.state = SignTemplateKind.SEARCHING;
                updateSign();
            }
        }
    }
    public void updateSign() {
        if(this.currentgroup == null || this.currentgroup.getServers().isEmpty()) {
            SignTemplate template = Main.templates.get(SignTemplateKind.SEARCHING);
            if(template.isAnimation()) {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getSplittedLine(0)[template.getCurrentupdateint().get("0")].replaceAll("&", "§"));
                sign.setLine(1, template.getSplittedLine(1)[template.getCurrentupdateint().get("1")].replaceAll("&", "§"));
                sign.setLine(2, template.getSplittedLine(2)[template.getCurrentupdateint().get("2")].replaceAll("&", "§"));
                sign.setLine(3, template.getSplittedLine(3)[template.getCurrentupdateint().get("3")].replaceAll("&", "§"));
                sign.update();
            } else {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getLines()[0].replaceAll("&", "§"));
                sign.setLine(1, template.getLines()[1].replaceAll("&", "§"));
                sign.setLine(2, template.getLines()[2].replaceAll("&", "§"));
                sign.setLine(3, template.getLines()[3].replaceAll("&", "§"));
                sign.update();
            }
            return;
        }
        if(this.state == SignTemplateKind.LOBBY) {
            Infos infos = new Infos(this.getCurrentServer().getHostName(), this.getCurrentServer().getPort());
            SignTemplate template = Main.templates.get(SignTemplateKind.LOBBY);
            if(template.isAnimation()) {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getSplittedLine(0)[template.getCurrentupdateint().get("0")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getSplittedLine(1)[template.getCurrentupdateint().get("1")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getSplittedLine(2)[template.getCurrentupdateint().get("2")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getSplittedLine(3)[template.getCurrentupdateint().get("3")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            } else {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getLines()[0].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getLines()[1].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getLines()[2].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getLines()[3].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            }
        } else if(this.state == SignTemplateKind.INGAME) {
            Infos infos = new Infos(this.getCurrentServer().getHostName(), this.getCurrentServer().getPort());
            SignTemplate template = Main.templates.get(SignTemplateKind.INGAME);
            if(template.isAnimation()) {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getSplittedLine(0)[template.getCurrentupdateint().get("0")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getSplittedLine(1)[template.getCurrentupdateint().get("1")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getSplittedLine(2)[template.getCurrentupdateint().get("2")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getSplittedLine(3)[template.getCurrentupdateint().get("3")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            } else {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getLines()[0].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getLines()[1].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getLines()[2].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getLines()[3].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            }
        } else if(this.state == SignTemplateKind.ENDING) {
            Infos infos = new Infos(this.getCurrentServer().getHostName(), this.getCurrentServer().getPort());
            SignTemplate template = Main.templates.get(SignTemplateKind.ENDING);
            if(template.isAnimation()) {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getSplittedLine(0)[template.getCurrentupdateint().get("0")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getSplittedLine(1)[template.getCurrentupdateint().get("1")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getSplittedLine(2)[template.getCurrentupdateint().get("2")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getSplittedLine(3)[template.getCurrentupdateint().get("3")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            } else {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getLines()[0].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getLines()[1].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getLines()[2].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getLines()[3].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            }
        } else if(this.state == SignTemplateKind.ONLINE) {
            Infos infos = new Infos(this.getCurrentServer().getHostName(), this.getCurrentServer().getPort());
            SignTemplate template = Main.templates.get(SignTemplateKind.ONLINE);
            if(template.isAnimation()) {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getSplittedLine(0)[template.getCurrentupdateint().get("0")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getSplittedLine(1)[template.getCurrentupdateint().get("1")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getSplittedLine(2)[template.getCurrentupdateint().get("2")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getSplittedLine(3)[template.getCurrentupdateint().get("3")].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            } else {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getLines()[0].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(1, template.getLines()[1].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(2, template.getLines()[2].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.setLine(3, template.getLines()[3].replaceAll("&", "§")
                        .replaceAll("%op%", infos.getOnline() + "")
                        .replaceAll("%mp%", infos.getMax() + "")
                        .replaceAll("%server%", this.curentserver.getName())
                        .replaceAll("%motd%", infos.getMotd()));
                sign.update();
            }
        } else if(this.state == SignTemplateKind.SEARCHING) {
            SignTemplate template = Main.templates.get(SignTemplateKind.SEARCHING);
            if(template.isAnimation()) {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getSplittedLine(0)[template.getCurrentupdateint().get("0")].replaceAll("&", "§"));
                sign.setLine(1, template.getSplittedLine(1)[template.getCurrentupdateint().get("1")].replaceAll("&", "§"));
                sign.setLine(2, template.getSplittedLine(2)[template.getCurrentupdateint().get("2")].replaceAll("&", "§"));
                sign.setLine(3, template.getSplittedLine(3)[template.getCurrentupdateint().get("3")].replaceAll("&", "§"));
                sign.update();
            } else {
                Sign sign = (Sign) this.location.getBlock().getState();
                Block block = null;
                switch(sign.getData().getData()) {
                    case 5 : block = sign.getLocation().add(-1, 0, 0).getBlock(); break;
                    case 2 : block = sign.getLocation().add(0, 0, 1).getBlock(); break;
                    case 4 : block = sign.getLocation().add(1, 0, 0).getBlock(); break;
                    case 3 : block = sign.getLocation().add(0, 0, -1).getBlock(); break;
                }
                if(block.getType().getId() != template.getBlockID()) {
                    block.setType(Material.getMaterial(template.getBlockID()));
                }
                block.setData(Byte.parseByte(template.getBlockSubID() + ""));
                sign.setLine(0, template.getLines()[0].replaceAll("&", "§"));
                sign.setLine(1, template.getLines()[1].replaceAll("&", "§"));
                sign.setLine(2, template.getLines()[2].replaceAll("&", "§"));
                sign.setLine(3, template.getLines()[3].replaceAll("&", "§"));
                sign.update();
            }
        }
    }
    public void setState(SignTemplateKind kind) {
        this.state = kind;
    }
}
