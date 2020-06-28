package dev.nest.tntrun.listeners;

import dev.nest.tntrun.TNTPlayer;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        TNTPlayer player = new TNTPlayer(event.getPlayer().getName());
        TNTPlayerManager.getInstance().addTntPlayer(player);
        System.out.println("Registered TNTPlayer " + player.getName());
    }

}
