package dev.nest.tntrun;

import dev.nest.tntrun.commands.*;
import dev.nest.tntrun.listeners.JoinListener;
import dev.nest.tntrun.listeners.QuitListener;
import dev.nest.tntrun.managers.TNTPlayerManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getCommand("eventplay").setExecutor(new EventPlayCmd());
        getCommand("eventrmv").setExecutor(new EventRmvCmd());
        getCommand("eventchk").setExecutor(new EventChkCmd());
        getCommand("eventlst").setExecutor(new EventLstCmd());
        getCommand("eventrmvall").setExecutor(new EventRmvAllCmd());
        getCommand("eventstrt").setExecutor(new EventStrtCmd());
    }

    @Override
    public void onDisable() {
        for (TNTPlayer p : TNTPlayerManager.getInstance().getTntPlayers()) {
            if (p.isRunning()) {
                p.stop();
            }
        }
    }

}
