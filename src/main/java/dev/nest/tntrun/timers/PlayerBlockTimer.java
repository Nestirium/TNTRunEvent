package dev.nest.tntrun.timers;

import dev.nest.tntrun.managers.ParticipantManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class PlayerBlockTimer {

    private ScheduledExecutorService executor = Executors.newScheduledThreadPool(2);
    private static HashMap<Player, List<Location>> map = new HashMap<>();
    private static boolean isCritical = false;

    public static boolean isCritical() {
        return isCritical;
    }

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

    public static void removePlayer(Player player) {
        List<Location> blocks;
        if (map != null) {
            if (map.containsKey(player)) {
                blocks = map.get(player);
                blocks.clear();
                map.remove(player);
            }
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
            Optional<Player> player;
            Location loc;
            for (String name : ParticipantManager.getInstance().getParticipants()) {
                player = Optional.ofNullable(Bukkit.getPlayer(name));
                if (player.isPresent()) {
                    if (player.get().getAllowFlight()) {
                        player.get().setAllowFlight(false);
                    }
                    if (player.get().isFlying()) {
                        player.get().setFlying(false);
                    }
                    if (player.get().getLocation().getY() <= 0) {
                        player.get().teleport(new Location(Bukkit.getWorld("world"), 0, 75, 0));
                        if (player.get().hasPotionEffect(PotionEffectType.GLOWING)) {
                            player.get().removePotionEffect(PotionEffectType.GLOWING);
                        }
                        continue;
                    }
                    block = getBlockAt(player.get());
                    if (block.isPresent()) {
                        addPlayer(player.get(), block.get());
                    } else {
                        removePlayer(player.get());
                    }
                    blocks = Optional.ofNullable(map.get(player.get()));
                    if (blocks.isPresent()) {
                        isCritical = true;
                        Location l = player.get().getLocation();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (player.get().getLocation().getBlockX() == l.getBlockX()
                        && player.get().getLocation().getBlockZ() == l.getBlockZ()) {
                            for (Location lc : blocks.get()) {
                                lc.getBlock().setType(Material.AIR);
                            }
                        }
                        if (blocks.get().size() >= 2) {
                            loc = blocks.get().get(0);
                            loc.getBlock().setType(Material.AIR);
                            blocks.get().remove(0);
                        }
                    }
                }
            }
            isCritical = false;
        }, 0, 50, TimeUnit.MILLISECONDS);
    }

    public boolean isRunning() {
        return !executor.isTerminated();
    }

    public void stop() {
        executor.shutdown();
    }

}
