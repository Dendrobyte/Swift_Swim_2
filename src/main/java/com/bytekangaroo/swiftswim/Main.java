package com.bytekangaroo.swiftswim;

import com.bytekangaroo.swiftswim.commands.SwiftCommand;
import com.bytekangaroo.swiftswim.listeners.PlayerConnectListener;
import com.bytekangaroo.swiftswim.listeners.PlayerDisconnectListener;
import com.bytekangaroo.swiftswim.listeners.PlayerTeleportListener;
import com.bytekangaroo.swiftswim.listeners.WeatherChangeListener;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.logging.Level;

/**
 * Created by Mark on 8/20/2018
 * Written for project SwiftSwimManager
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class Main extends JavaPlugin {

    private static Main instance;
    private String prefix = "§8[§bSwiftSwim§8]§b ";

    @Override
    public void onEnable(){

        // Create configuration file(s)
        createConfig();

        // Enable instance
        instance = this;

        // Register Events
        Bukkit.getPluginManager().registerEvents(new WeatherChangeListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerTeleportListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerDisconnectListener(), this);
        Bukkit.getPluginManager().registerEvents(new PlayerConnectListener(), this);

        // Register Commands
        getCommand("swiftswim").setExecutor(new SwiftCommand());

        getLogger().log(Level.INFO, "SwiftSwimManager v" + getDescription().getVersion() + " has successfully been enabled!");
    }

    @Override
    public void onDisable(){

        // Make sure to clear the swiftswim list, disabling everyone with swiftswim
        getLogger().log(Level.INFO, "SwiftSwimManager v" + getDescription().getVersion() + " has successfully been disabled!");

    }

    private void createConfig(){
        if(!getDataFolder().exists()){
            getDataFolder().mkdirs();
        }
        File file = new File(getDataFolder(), "config.yml");
        if(!file.exists()){
            saveDefaultConfig();
            getLogger().log(Level.INFO, "SwiftSwimManager v" + getDescription().getVersion() + " configuration has been created!");
        } else {
            getLogger().log(Level.INFO, "SwiftSwimManager v" + getDescription().getVersion() + " has been loaded!");
        }
    }

    public static Main getInstance(){
        return instance;
    }

    public String getPrefix(){
        return prefix;
    }

}
