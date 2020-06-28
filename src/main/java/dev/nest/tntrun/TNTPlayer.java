package dev.nest.tntrun;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class TNTPlayer {

    private String name;
    private ScheduledExecutorService scheduledExecutorService;
    private List<Location> blocks;
    private boolean isPlaying;

    public TNTPlayer(String name) {
        this.name = name;
        isPlaying = false;
        scheduledExecutorService = Executors.newScheduledThreadPool(1);
        blocks = new ArrayList<>();
    }

    public void addLocation(Block block) {
        blocks.add(block.getLocation());
    }

    public String getName() {
        return name;
    }

    public Optional<Block> getBlockAt(Player player) {

        Location backRight = player.getLocation().clone().add(0.3, -1, -0.3);
        Location backLeft = player.getLocation().clone().add(-0.3, -1, -0.3);
        Location upperRight = player.getLocation().clone().add(0.3, -1, 0.3);
        Location upperLeft = player.getLocation().clone().add(-0.3, -1, 0.3);

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

    public Executor getExecutor() {
        return scheduledExecutorService;
    }

    public void run() {

        scheduledExecutorService.scheduleAtFixedRate(() -> {
            if (isPlaying) {
                final Player p = getSpigotPlayer();
                Optional<Block> block;
                Optional<List<Location>> blocks;
                Location loc;
                if (p.getAllowFlight()) {
                    p.setAllowFlight(false);
                }
                if (p.isFlying()) {
                    p.setFlying(false);
                }
                if (p.getLocation().getY() <= 0) {
                    p.teleport(new Location(Bukkit.getWorld("world"), 0, 75, 0));
                    if (p.hasPotionEffect(PotionEffectType.GLOWING)) {
                        p.removePotionEffect(PotionEffectType.GLOWING);
                    }
                }
                block = getBlockAt(p);
                if (block.isPresent()) {
                    addLocation(block.get());
                    blocks = Optional.ofNullable(this.blocks);
                    if (blocks.isPresent()) {
                        Location l = p.getLocation();
                        try {
                            Thread.sleep(100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        if (p.getLocation().getX() == l.getX()
                        && p.getLocation().getZ() == l.getZ()) {
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
        }, 0, 50, TimeUnit.MILLISECONDS);

    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean b) {
        isPlaying = b;
    }

    public boolean isRunning() {
        return !scheduledExecutorService.isTerminated();
    }

    public void stop() {
        scheduledExecutorService.shutdown();
    }

    public Player getSpigotPlayer() {
        return Bukkit.getPlayer(name);
    }

}
