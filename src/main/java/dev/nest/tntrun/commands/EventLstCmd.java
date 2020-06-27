package dev.nest.tntrun.commands;

import dev.nest.tntrun.managers.ParticipantManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventLstCmd implements CommandExecutor {

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
            for (String s : ParticipantManager.getInstance().getParticipants()) {
                player.sendMessage(s);
            }
        } else {
            player.sendMessage("No perms");
        }
        return false;
    }

}
