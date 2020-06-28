package dev.nest.tntrun.commands;

import dev.nest.tntrun.TNTPlayer;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;


public class EventRmvAllCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be executed by console!");
            return false;
        }
        final Player player = (Player) sender;
        if (player.hasPermission("tntrun.event")) {
            if (args.length > 0) {
                player.sendMessage("Incorrect usage!");
                return false;
            }
            for (TNTPlayer p : TNTPlayerManager.getInstance().getTntPlayers()) {
                if (p != null) {
                    if (p.isPlaying()) {
                        p.setPlaying(false);
                        if (p.getSpigotPlayer().hasPotionEffect(PotionEffectType.GLOWING)) {
                            p.getSpigotPlayer().removePotionEffect(PotionEffectType.GLOWING);
                        }
                    }
                }
            }
            player.sendMessage("Stopped all players!");
        } else {
            player.sendMessage("No perms");
        }
        return false;
    }

}
