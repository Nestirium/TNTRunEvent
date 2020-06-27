package dev.nest.tntrun.managers;

import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;

public class FileManager {

    private FileManager() {}
    private static FileManager instance = new FileManager();
    private File file, temp;
    private Plugin plugin;

    public void setup(Plugin plugin) {
        this.plugin = plugin;
        if (!plugin.getDataFolder().exists()) {
            if (plugin.getDataFolder().mkdir()) {
                System.out.println("Created data dir");
            }
        }
        temp = new File(plugin.getDataFolder(), "temp");
        if (!temp.exists()) {
            if (temp.mkdir()) {
                System.out.println("Created temp dir");
            }
        }
    }

    public void serializeParticipant(String name) {
        if (temp.exists() && temp.isDirectory()) {
            if (ParticipantManager.getInstance().isParticipant(name)) {
                ParticipantManager.getInstance().removeParticipant(name);
                file = new File(temp, name);
                try {
                    if (file.createNewFile()) {
                        System.out.println("Serialized " + file.getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void serializeParticipants() {
        if (temp.exists() && temp.isDirectory()) {
            for (String s : ParticipantManager.getInstance().getParticipants()) {
                file = new File(temp, s);
                try {
                    if (file.createNewFile()) {
                        System.out.println("Serialized " + file.getName());
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            ParticipantManager.getInstance().clear();
        }
    }

    public void deserializeParticipant(String name) {
        if (temp.exists() && temp.isDirectory()) {
            File[] files = temp.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.getName().equalsIgnoreCase(name)) {
                        ParticipantManager.getInstance().addParticipant(file.getName());
                        if (file.delete()) {
                            System.out.println("Deserialized " + name);
                        }
                    }
                }
            }
        }
    }

    public void deserializeParticipants() {
        if (temp.exists() && temp.isDirectory()) {
            File[] files = temp.listFiles();
            if (files != null) {
                for (File file : files) {
                    ParticipantManager.getInstance().addParticipant(file.getName());
                    if (file.delete()) {
                        System.out.println("Deserialized a file");
                    }
                }
            }
        }
    }

    public File getTemp() {
        return temp;
    }

    public static FileManager getInstance() {
        return instance;
    }

}
