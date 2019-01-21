package me.Spleevtv.api;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class NoCloudAPI {

    private static CloudPlayerAPI playerapi = new CloudPlayerAPI();
    private static CloudServerAPI serverapi = new CloudServerAPI();

    public static CloudPlayerAPI getPlayerAPI() {
        return playerapi;
    }
    public static CloudServerAPI getServerAPI() {
        return serverapi;
    }
}
