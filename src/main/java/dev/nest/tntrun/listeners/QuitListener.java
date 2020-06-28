package dev.nest.tntrun.listeners;

import dev.nest.tntrun.TNTPlayer;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffectType;

public class QuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        TNTPlayer player = TNTPlayerManager.getInstance().getTntPlayer(event.getPlayer().getName());
        if (player != null) {
            if (event.getPlayer().hasPotionEffect(PotionEffectType.GLOWING)) {
                event.getPlayer().removePotionEffect(PotionEffectType.GLOWING);
            }
            if (player.isRunning()) {
                player.stop();
            }
            TNTPlayerManager.getInstance().removeTntPlayer(player);
            System.out.println("Unregistered TNTPlayer " + player.getName());
        }
    }

}
