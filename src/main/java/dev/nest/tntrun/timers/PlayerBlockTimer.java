package dev.nest.tntrun.timers;

import dev.nest.tntrun.managers.ParticipantManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerBlockTimer {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private HashMap<Player, List<Location>> map = new HashMap<>();

    public void addPlayer(Player player, Block block) {
        List<Location> blocks;
        if (map != null) {
            if (map.containsKey(player)) {
                blocks = map.get(player);
                if (blocks.contains(block.getLocation())) {
                    return;
                }
            } else {
                blocks = new ArrayList<>();
            }
            blocks.add(block.getLocation());
            map.put(player, blocks);
        }
    }

    public Optional<Block> getBlockAt(Player player) {
        Location backRight = player.getLocation().clone().add(0.3, -0.9, -0.3);
        Location backLeft = player.getLocation().clone().add(-0.3, -0.9, -0.3);
        Location upperRight = player.getLocation().clone().add(0.3, -0.9, 0.3);
        Location upperLeft = player.getLocation().clone().add(-0.3, -0.9, 0.3);
        if (backRight.getBlock().getType() == Material.TNT) {
            return Optional.of(backRight.getBlock());
        }
        if (backLeft.getBlock().getType() == Material.TNT) {
            return Optional.of(backLeft.getBlock());
        }
        if (upperLeft.getBlock().getType() == Material.TNT) {
            return Optional.of(upperLeft.getBlock());
        }
        if (upperRight.getBlock().getType() == Material.TNT) {
            return Optional.of(upperRight.getBlock());
        }
        return Optional.empty();
    }

    public void run() {

        executor.scheduleAtFixedRate(() -> {
            Optional<Block> block;
            Optional<List<Location>> blocks;
            Location loc;
            for (String name : ParticipantManager.getInstance().getParticipants()) {
                Player player = Bukkit.getPlayer(name);
                if (player != null) {
                    block = getBlockAt(player);
                    block.ifPresent(b -> addPlayer(player, b));
                    blocks = Optional.ofNullable(map.get(player));
                    if (blocks.isPresent()) {
                        Location l = player.getLocation();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (player.getLocation().getBlockX() == l.getBlockX()
                        && player.getLocation().getBlockZ() == l.getBlockZ()) {
                            for (Location lc : blocks.get()) {
                                if (lc.getBlock().getType() == Material.TNT) {
                                    lc.getBlock().setType(Material.AIR);
                                }
                            }
                        }

                        if (blocks.get().size() >= 2) {
                            loc = blocks.get().get(0);
                            if (loc.getBlock().getType() == Material.TNT) {
                                loc.getBlock().setType(Material.AIR);
                            }
                            blocks.get().remove(0);
                        }
                    }
                }
            }
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public boolean isRunning() {
        return !executor.isTerminated();
    }

    public void stop() {
        executor.shutdown();
    }

}
