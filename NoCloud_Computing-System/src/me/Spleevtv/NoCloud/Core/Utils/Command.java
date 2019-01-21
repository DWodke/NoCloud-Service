package me.Spleevtv.NoCloud.Core.Utils;

public interface Command {

    void execute(String[] args);

    String getUsage();

}
