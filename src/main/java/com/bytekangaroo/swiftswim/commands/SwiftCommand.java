package com.bytekangaroo.swiftswim.commands;

import com.bytekangaroo.swiftswim.Main;
import com.bytekangaroo.swiftswim.SwiftSwimManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Mark on 8/20/2018
 * Written for project SwiftSwimManager
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class SwiftCommand implements CommandExecutor {

    String prefix = Main.getInstance().getPrefix();
    SwiftSwimManager manager = SwiftSwimManager.getInstance();

    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        // Do the necessary player checks and cast
        if(!(sender instanceof Player)){
            sender.sendMessage(prefix + ChatColor.RED + "You must be a player to use this command!");
            return true;
        }
        Player player = (Player) sender;

        // Being checking for actual command
        if(command.getName().equalsIgnoreCase("swiftswim")){
            if(!player.hasPermission("swiftswim.use")){
                player.sendMessage(prefix + ", Sorry, you don't have permission to use this command." + ChatColor.RED + " swiftswim.use");
                return true;
            }
            if(args.length == 0){
                player.sendMessage(prefix + "Not enough arguments! See " + ChatColor.DARK_AQUA + "/swiftswim help" + ChatColor.getLastColors(prefix) + " for command list.");
                return true;
            }
            if(args.length > 1){
                player.sendMessage(prefix + "Too many arguments! See " + ChatColor.DARK_AQUA + "/swiftswim help" + ChatColor.getLastColors(prefix) + " for command list.");
                return true;
            }
            if(args[0].equals("help")){
                player.sendMessage(prefix + "----" + ChatColor.GRAY + "[" + ChatColor.AQUA + "SwiftSwim " + ChatColor.GRAY + "]" + ChatColor.AQUA + " v" + Main.getInstance().getDescription().getVersion() + " by " + Main.getInstance().getDescription().getAuthors().toString() + "----");
                player.sendMessage("" + ChatColor.AQUA + "/swiftswim enable " + ChatColor.GRAY + "- Enables SwiftSwim when raining in enabled worlds.");
                player.sendMessage("" + ChatColor.AQUA + "/swiftswim disable " + ChatColor.GRAY + "- Disables SwiftSwim when enabled.");
                player.sendMessage("" + ChatColor.AQUA + "/swiftswim reload " + ChatColor.GRAY + "- Reload the swiftswim config. " + ChatColor.RED + "swiftswim.reload");
                player.sendMessage("" + ChatColor.AQUA + "Enabled Worlds: " + manager.getEnabledWorlds().toString());
                return true;
            }
            if(args[0].equals("enable")){
                manager.enableSwim(player);
                return true;
            }
            if(args[0].equals("disable")){
                if(manager.swimEnabled(player)){
                    manager.disableSwimForPlayer(player);
                } else {
                    player.sendMessage(prefix + "You do not have Swift Swim enabled.");
                }
                return true;
            }
            if(args[0].equals("reload") && (player.hasPermission("swiftswim.reload") || player.isOp())){
                Main.getInstance().reloadConfig();
                manager.resetEnabledWorlds();
                player.sendMessage(prefix + "Swift Swim v" + Main.getInstance().getDescription().getVersion() + " has been reloaded!");
                player.sendMessage(prefix + "Enabled Worlds: " + ChatColor.GRAY + manager.getEnabledWorlds().toString());
                player.sendMessage(prefix + "Speed Level: " + ChatColor.GRAY + Main.getInstance().getConfig().getInt("speed"));
                player.sendMessage(prefix + "Particles Enabled: " + ChatColor.GRAY + Main.getInstance().getConfig().getBoolean("particles-enabled") + ChatColor.RED + " [NOT IMPLEMENTED]");
            } else {
                player.sendMessage(prefix + "Argument not recognized! See " + ChatColor.DARK_AQUA + "/swiftswim help" + ChatColor.getLastColors(prefix) + " for command list.");
            }
            // TODO: Add the ability for players to enable particle effects (also make that a separate permission node)
        }
        return true;
    }

}
