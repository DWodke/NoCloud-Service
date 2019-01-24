package me.Spleevtv.NoCloud.Core.Utils;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class ServerGroup {

    private String name;
    private Integer onlineAmount;
    private Integer maxAmount;
    private Integer maxRam;
    private Integer maxPlayers;
    private Boolean dynamic;

    public ServerGroup(String arg1, Integer arg2, Integer arg3, Integer arg4, Integer arg5, Boolean arg6) {
        this.name = arg1;
        this.onlineAmount = arg2;
        this.maxAmount = arg3;
        this.maxRam = arg4;
        this.maxPlayers = arg5;
        this.dynamic = arg6;
    }
    public void setDynamic(Boolean dynamic) {
        this.dynamic = dynamic;
    }
    public void setMaxAmount(Integer maxAmount) {
        this.maxAmount = maxAmount;
    }
    public void setMaxPlayers(Integer maxPlayers) {
        this.maxPlayers = maxPlayers;
    }
    public void setMaxRam(Integer maxRam) {
        this.maxRam = maxRam;
    }
    public void setOnlineAmount(Integer onlineAmount) {
        this.onlineAmount = onlineAmount;
    }
    public String getName() {
        return name;
    }
    public Boolean getDynamic() {
        return dynamic;
    }
    public Integer getMaxAmount() {
        return maxAmount;
    }
    public Integer getMaxPlayers() {
        return maxPlayers;
    }
    public Integer getMaxRam() {
        return maxRam;
    }
    public Integer getOnlineAmount() {
        return onlineAmount;
    }
}
