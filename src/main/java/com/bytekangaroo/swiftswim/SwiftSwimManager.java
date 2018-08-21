package com.bytekangaroo.swiftswim;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import sun.awt.image.IntegerComponentRaster;

import java.util.HashMap;
import java.util.List;
import java.util.UUID;

/**
 * Created by Mark on 8/20/2018
 * Written for project SwiftSwimManager
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class SwiftSwimManager {

    private static SwiftSwimManager instance = null;
    private static List<String> ENABLED_WORLDS = Main.getInstance().getConfig().getStringList("enabled-worlds");

    private static HashMap<UUID, String> enabledPlayers = new HashMap<UUID, String>();

    String prefix = Main.getInstance().getPrefix();

    private SwiftSwimManager(){

    }

    public static SwiftSwimManager getInstance(){
        if(instance == null){
            instance = new SwiftSwimManager();
        }

        return instance;
    }

    // Get the hashmap that stores each player.
    // The key is the world, so it is easier to loop through when disabling SwiftSwim for any given players
    public HashMap<UUID, String> getEnabledPlayers(){
        return enabledPlayers;
    }

    // Get the enabled worlds from config.yml so you don't have to access the config every time
    public List<String> getEnabledWorlds(){
        return ENABLED_WORLDS;
    }

    public void resetEnabledWorlds() {
        ENABLED_WORLDS = Main.getInstance().getConfig().getStringList("enabled-worlds");
    }

    // Check if it is raining in the player's world so that swiftswim can be safely enabled
    public boolean isRainingInWorld(Player player){
        World world = player.getWorld();
        return world.hasStorm();
    }

    public void promptForSwiftSwim(Player player){
        if(player.hasPermission("swiftswim.use")) {
            player.sendMessage(prefix + "*gasp* It is now raining in your world!");
            player.sendMessage(prefix + "Would you like to enable " + ChatColor.BOLD + "SwiftSwim" + ChatColor.getLastColors(prefix) + " for a speed boost?");
            player.sendMessage(prefix + "Type " + ChatColor.GREEN + "/swiftswim enable" + ChatColor.getLastColors(prefix) + " to do so!");
        }
    }

    // Check if a player has SwiftSwim enabled without having to read the list every time.
    public boolean swimEnabled(Player player){
        return getEnabledPlayers().keySet().contains(player.getUniqueId());
    }

    // Enable swiftswim for a given player
    public void enableSwim(Player player){
        World world = player.getWorld();

        if(!ENABLED_WORLDS.contains(world.getName())){
            player.sendMessage(prefix + ChatColor.RED + "Sorry, " + ChatColor.AQUA + "SwiftSwim" + ChatColor.RED + " can not be enabled in this world!");
            return;
        }

        if(!world.hasStorm()){
            player.sendMessage(prefix + "Sorry, it is not raining in this world =(");
            return;
        }

        if (enabledPlayers.containsKey(player.getUniqueId())) {
            player.sendMessage(prefix +"You already have SwiftSwim enabled!");
            return;
        }

        enabledPlayers.put(player.getUniqueId(), world.getName());
        giveSpeed(player);
        player.sendMessage(prefix + "SwiftSwim has been " + ChatColor.GREEN + "enabled!" + ChatColor.getLastColors(prefix) + " Enjoy being one speedy boi!");
    }

    // Remove a player from the list, thus effectively disabling swiftswim for that player
    public void disableSwimForPlayer(Player player){
        if(!swimEnabled(player)) return; // Do nothing
        getEnabledPlayers().remove(player.getUniqueId());
        removeSpeed(player);
        player.sendMessage(prefix + ChatColor.RED + "SwiftSwim has been " + ChatColor.RED + "disabled.");
    }

    // Disable swiftswim for all players in a world (for when the weather changes, see WeatherChangeListener)
    public void disableSwimForWorld(String worldName){
        World world = Bukkit.getWorld(worldName);

        // Disable swim for every player in the world that has it enabled.
        for(Player playerInWorld : world.getPlayers()){
            disableSwimForPlayer(playerInWorld);
        }

    }

    // Method to give speed to a player
    private void giveSpeed(Player player){
        int amplifier = Main.getInstance().getConfig().getInt("speed");
        PotionEffect speed = new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, amplifier, true, false);
        player.addPotionEffect(speed);
    }

    // Method to remove speed from a player
    private void removeSpeed(Player player){
        player.removePotionEffect(PotionEffectType.SPEED);
    }
}