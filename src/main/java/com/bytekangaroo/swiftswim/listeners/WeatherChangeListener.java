package com.bytekangaroo.swiftswim.listeners;

import com.bytekangaroo.swiftswim.Main;
import com.bytekangaroo.swiftswim.SwiftSwimManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.weather.WeatherChangeEvent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mark on 8/20/2018
 * Written for project SwiftSwimManager
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class WeatherChangeListener implements Listener {

    String prefix = Main.getInstance().getPrefix();
    SwiftSwimManager manager = SwiftSwimManager.getInstance();

    @EventHandler
    public void onWeatherChange(WeatherChangeEvent event){
        World world = event.getWorld();
        String worldName = world.getName();
        List<String> enabledWorlds = manager.getEnabledWorlds();

        // Get the type of weather change. True = isRaining, False = isNotRaining
        boolean isRaining = event.toWeatherState();

        // Check to make sure that the world has swift swim enabled, to prevent it from being used in all worlds
        if(!enabledWorlds.contains(worldName)){
            return;
        }

        /* Weather changes to rain, thus prompt players to enable swift swim */
        if(isRaining) {
            // Ask each player in the world that it is now raining in if they would like to enable swift swim.
            List<Player> playersInWorld = Bukkit.getWorld(worldName).getPlayers();
            for (Player player : playersInWorld) {
                manager.promptForSwiftSwim(player);
            }
        }

        /* Weather changes to sun, thus disable swift swim */
        if(!isRaining){
            manager.disableSwimForWorld(worldName);
        }

    }
}