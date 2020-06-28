package dev.nest.tntrun.commands;

import dev.nest.tntrun.TNTPlayer;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

public class EventRmvCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be executed by console!");
            return false;
        }
        final Player player = (Player) sender;
        if (player.hasPermission("tntrun.event")) {
            if (args.length != 1) {
                player.sendMessage("Incorrect usage!");
                return false;
            }
            final TNTPlayer target = TNTPlayerManager.getInstance().getTntPlayer(args[0]);
            if (target != null) {
                if (target.isPlaying()) {
                    target.setPlaying(false);
                    if (target.getSpigotPlayer().hasPotionEffect(PotionEffectType.GLOWING)) {
                        target.getSpigotPlayer().removePotionEffect(PotionEffectType.GLOWING);
                    }
                    player.sendMessage("Stopped " + target.getName());
                } else {
                    player.sendMessage("Target is not playing!");
                }
            } else {
                player.sendMessage("Target not online!");
            }
        } else {
            player.sendMessage("No perms");
        }
        return false;
    }

}
