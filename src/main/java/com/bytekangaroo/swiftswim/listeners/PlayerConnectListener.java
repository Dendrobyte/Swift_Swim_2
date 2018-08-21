package com.bytekangaroo.swiftswim.listeners;

import com.bytekangaroo.swiftswim.SwiftSwimManager;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Mark on 8/20/2018
 * Written for project SwiftSwimManager
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class PlayerConnectListener implements Listener {

    SwiftSwimManager manager = SwiftSwimManager.getInstance();

    @EventHandler
    public void onPlayerConnect(PlayerJoinEvent event){
        Player player = event.getPlayer();
        World world = player.getWorld();
        if(world.hasStorm()){
            manager.promptForSwiftSwim(player);
        }
    }
}
