package me.Spleevtv.NoCloud.Base.Utils;

import java.util.ArrayList;

/**
 * Created by 'SPLEEVTV | Dominik W.' on Jan, 2019
 */
public class ServerProcessManager {

    private static ArrayList<Integer> ports = new ArrayList<>();

    public static void registerPort(Integer port) {
        if(!ports.contains(port)) {
            ports.add(port);
        }
    }
    public static void unregisterPort(Integer port) {
        if(ports.contains(port)) {
            ports.remove(port);
        }
    }
    public static Integer getNextFreePort(Integer startPort) {
        while(ports.contains(startPort)) {
            startPort++;
        }
        return startPort;
    }
}
