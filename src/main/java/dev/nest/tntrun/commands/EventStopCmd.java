package dev.nest.tntrun.commands;

import dev.nest.tntrun.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class EventStopCmd implements CommandExecutor {

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
            if (Main.timer.isRunning()) {
                Main.timer.stop();
                player.sendMessage("Timer stopped!");
            } else {
                player.sendMessage("Timer not running!");
            }
        } else {
            player.sendMessage("No perms");
        }
        return false;
    }

}
