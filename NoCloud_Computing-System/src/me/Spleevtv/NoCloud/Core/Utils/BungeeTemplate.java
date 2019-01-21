package me.Spleevtv.NoCloud.Core.Utils;

import java.util.ArrayList;

public class BungeeTemplate {

    private String motd_1;
    private String motd_2;
    private String maintenance_motd_1;
    private String maintenance_motd_2;
    private int maxplayers;
    private Boolean wartung;
    private String fallbackServerGroup;
    private int mm_ammount;
    private ArrayList<String> m_message;
    private int full_amount;
    private ArrayList<String> full_message;
    private String maintenance_message;
    private String full_server_messsage;
    private Integer serverPercent;

    public BungeeTemplate(String motd1, String motd2, String mmotd1, String mmotd2, int max_players, Boolean wartungen, String fallbackGroup, int mm_amount, ArrayList<String> m_messages, int full_amount, ArrayList<String> full_messsages, Integer percent) {
        this.motd_1 = motd1;
        this.motd_2 = motd2;
        this.maintenance_motd_1 = mmotd1;
        this.maintenance_motd_2 = mmotd2;
        this.maxplayers = max_players;
        this.wartung = wartungen;
        this.fallbackServerGroup = fallbackGroup;
        this.m_message = m_messages;
        this.mm_ammount = mm_amount;
        this.full_message = full_messsages;
        this.full_amount = full_amount;
        this.maintenance_message = "#65768234";
        this.serverPercent = percent;
        for(String s : this.m_message) {
            this.maintenance_message = this.maintenance_message + "#67868u349374897453" + s;
        }
        this.maintenance_message = this.maintenance_message.replaceAll("#65768234#67868u349374897453", "");
        this.full_server_messsage = "#65768234";
        for(String s : this.full_message) {
            this.full_server_messsage = this.full_server_messsage + "#67868u349374897453" + s;
        }
        this.full_server_messsage = this.full_server_messsage.replaceAll("#65768234#67868u349374897453", "");
    }
    public Boolean getWartung() {
        return this.wartung;
    }
    public Integer getServerPercent() {
        return serverPercent;
    }
    public int getMaxPlayers() {
        return this.maxplayers;
    }
    public String getMotd_1() {
        return this.motd_1;
    }
    public String getMotd_2() {
        return this.motd_2;
    }
    public String getFallbackServerGroup() {
        return this.fallbackServerGroup;
    }
    public ArrayList<String> getMaintenanceMessage() {
        return m_message;
    }
    public int getMaintenanceMessageAmount() {
        return mm_ammount;
    }
    public ArrayList<String> getFullMessages() {
        return full_message;
    }
    public int getFullMessagesAmount() {
        return full_amount;
    }
    public String getFullMessageAsString() {
        return full_server_messsage;
    }
    public String getMaintenanceMessageAsString() {
        return maintenance_message;
    }
    public String getMaintenanceMotd1() {
        return maintenance_motd_1;
    }
    public String getMaintenanceMotd2() {
        return maintenance_motd_2;
    }
}