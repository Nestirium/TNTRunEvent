package dev.nest.tntrun.listeners;

import dev.nest.tntrun.TNTPlayer;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffectType;

public class WorldSwitch implements Listener {

    @EventHandler
    public void onSwitch(PlayerChangedWorldEvent event) {
        TNTPlayer player = TNTPlayerManager.getInstance().getTntPlayer(event.getPlayer().getName());
        if (player != null) {
            if (player.isPlaying()) {
                if (event.getPlayer().hasPotionEffect(PotionEffectType.GLOWING)) {
                    event.getPlayer().removePotionEffect(PotionEffectType.GLOWING);
                }
                player.setPlaying(false);
            }
        }
    }

}
