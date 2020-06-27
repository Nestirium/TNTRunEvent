package dev.nest.tntrun.listeners;

import dev.nest.tntrun.managers.FileManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        FileManager.getInstance().serializeParticipant(event.getPlayer().getName());
    }

}