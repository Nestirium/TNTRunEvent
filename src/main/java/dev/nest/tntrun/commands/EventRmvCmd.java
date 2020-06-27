package dev.nest.tntrun.commands;

import dev.nest.tntrun.managers.FileManager;
import dev.nest.tntrun.managers.ParticipantManager;
import dev.nest.tntrun.timers.PlayerBlockTimer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.io.File;

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
            final Player target = Bukkit.getServer().getPlayer(args[0]);
            if (target != null) {
                if (ParticipantManager.getInstance().isParticipant(target.getName())) {
                    if (!PlayerBlockTimer.isCritical()) {
                        PlayerBlockTimer.removePlayer(player);
                        ParticipantManager.getInstance().removeParticipant(target.getName());
                    } else {
                        player.sendMessage("Thread de-sync imminent, async thread is in a critical part of a for-loop, try again...");
                        return false;
                    }
                    if (target.hasPotionEffect(PotionEffectType.GLOWING)) {
                        target.removePotionEffect(PotionEffectType.GLOWING);
                    }
                    File[] files = FileManager.getInstance().getTemp().listFiles();
                    if (files != null) {
                        for (File file : files) {
                            if (file.getName().equalsIgnoreCase(target.getName())) {
                                if (file.delete()) {
                                    System.out.println("Deleted a serialized file " + target.getName());
                                }
                            }
                        }
                    }
                    player.sendMessage("Removed " + target.getName());
                } else {
                    player.sendMessage("Target is not participant");
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
