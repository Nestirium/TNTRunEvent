package dev.nest.tntrun.commands;

import dev.nest.tntrun.managers.ParticipantManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventAddCmd implements CommandExecutor {

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
                if (!ParticipantManager.getInstance().isParticipant(target.getName())) {
                    ParticipantManager.getInstance().addParticipant(target.getName());
                    player.sendMessage("Added " + target.getName());
                } else {
                    player.sendMessage("Target is already participant");
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
