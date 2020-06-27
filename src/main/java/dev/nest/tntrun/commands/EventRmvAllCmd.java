package dev.nest.tntrun.commands;

import dev.nest.tntrun.managers.FileManager;
import dev.nest.tntrun.managers.ParticipantManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

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
            ParticipantManager.getInstance().clear();
            File[] files = FileManager.getInstance().getTemp().listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.delete()) {
                        System.out.println("Deleted a serialized file!");
                    }
                }
            }
            player.sendMessage("Removed all participants!");
        } else {
            player.sendMessage("No perms");
        }
        return false;
    }

}
