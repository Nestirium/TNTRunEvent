package dev.nest.tntrun;

import dev.nest.tntrun.commands.*;
import dev.nest.tntrun.listeners.JoinListener;
import dev.nest.tntrun.listeners.QuitListener;
import dev.nest.tntrun.listeners.WorldSwitch;
import dev.nest.tntrun.managers.FileManager;
import dev.nest.tntrun.timers.PlayerBlockTimer;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

    public static PlayerBlockTimer timer = new PlayerBlockTimer();

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new JoinListener(), this);
        getServer().getPluginManager().registerEvents(new QuitListener(), this);
        getServer().getPluginManager().registerEvents(new WorldSwitch(), this);
        getCommand("eventadd").setExecutor(new EventAddCmd());
        getCommand("eventrmv").setExecutor(new EventRmvCmd());
        getCommand("eventchk").setExecutor(new EventChkCmd());
        getCommand("eventstop").setExecutor(new EventStopCmd());
        getCommand("eventlst").setExecutor(new EventLstCmd());
        getCommand("eventrmvall").setExecutor(new EventRmvAllCmd());
        FileManager.getInstance().setup(this);
        timer.run();
    }

    @Override
    public void onDisable() {
        FileManager.getInstance().serializeParticipants();
        if (timer.isRunning()) {
            timer.stop();
        }
    }

}
