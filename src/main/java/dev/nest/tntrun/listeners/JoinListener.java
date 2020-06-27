package dev.nest.tntrun.listeners;

import dev.nest.tntrun.managers.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class JoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        FileManager.getInstance().deserializeParticipant(event.getPlayer().getName());
    }

}
