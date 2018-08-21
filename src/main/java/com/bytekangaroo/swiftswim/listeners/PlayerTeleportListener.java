package com.bytekangaroo.swiftswim.listeners;

import com.bytekangaroo.swiftswim.SwiftSwimManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

/**
 * Created by Mark on 8/20/2018
 * Written for project SwiftSwimManager
 * Please do not use or edit this code unless permission has been given (or if it's on GitHub...)
 * Contact me on Twitter, @Mobkinz78, with any questions
 * §
 */
public class PlayerTeleportListener implements Listener {

    SwiftSwimManager manager = SwiftSwimManager.getInstance();

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event){
        Player player = event.getPlayer();
        if(!event.getFrom().getWorld().equals(event.getTo().getWorld())) manager.disableSwimForPlayer(player);
    }

}
