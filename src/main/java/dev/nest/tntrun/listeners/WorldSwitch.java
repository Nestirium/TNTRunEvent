package dev.nest.tntrun.listeners;

import dev.nest.tntrun.managers.ParticipantManager;
import dev.nest.tntrun.timers.PlayerBlockTimer;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;
import org.bukkit.potion.PotionEffectType;

public class WorldSwitch implements Listener {

    @EventHandler
    public void onSwitch(PlayerChangedWorldEvent event) {
        if (ParticipantManager.getInstance().isParticipant(event.getPlayer().getName())) {
            if (event.getPlayer().hasPotionEffect(PotionEffectType.GLOWING)) {
                event.getPlayer().removePotionEffect(PotionEffectType.GLOWING);
            }
            if (!PlayerBlockTimer.isCritical()) {
                ParticipantManager.getInstance().removeParticipant(event.getPlayer().getName());
            }
        }
    }

}
