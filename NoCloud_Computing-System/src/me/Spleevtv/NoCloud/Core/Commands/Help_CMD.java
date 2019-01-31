package me.Spleevtv.NoCloud.Core.Commands;

import me.Spleevtv.NoCloud.Core.Init;
import me.Spleevtv.NoCloud.Core.Utils.Command;
import me.Spleevtv.NoCloud.Main;

public class Help_CMD implements Command {


    @Override
    public void execute(String[] args) {
     if(args[0].equalsIgnoreCase("help")) {
         if(args.length == 1) {
             System.out.println(Main.getPrefix() + "Exit cloud system.");
             for(Command cmd : Init.commands) {
                 if(!cmd.getUsage().equalsIgnoreCase(this.getUsage())) {
                     System.out.println(Main.getPrefix() + cmd.getUsage());
                 }
             }
         } else {
             System.out.println(Main.getPrefix() + "Usage: help");
         }
        }
    }
    @Override
    public String getUsage() {
        return Main.ANSI_GREEN + "help" + Main.ANSI_RESET + " | open the help menu";
    }
}
