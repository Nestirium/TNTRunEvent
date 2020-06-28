package dev.nest.tntrun.commands;

import dev.nest.tntrun.TNTPlayer;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class EventLstCmd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) {
            sender.sendMessage("This command cannot be executed by console!");
            return false;
        }
        final Player player = (Player) sender;
        if (player.hasPermission("tntrun.event")) {
            if (args.length == 0) {
                for (TNTPlayer p : TNTPlayerManager.getInstance().getTntPlayers()) {
                    player.sendMessage(p.getName());
                }
            } else if (args.length == 1) {
                if (args[0].equalsIgnoreCase("playing")) {
                    List<String> pl = new ArrayList<>();
                    for (TNTPlayer p : TNTPlayerManager.getInstance().getTntPlayers()) {
                        if (p.isPlaying()) {
                            pl.add(p.getName());
                        }
                    }
                    for (String s : pl) {
                        player.sendMessage(s);
                    }
                }
            }

        } else {
            player.sendMessage("No perms");
        }
        return false;
    }

}
