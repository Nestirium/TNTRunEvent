package dev.nest.tntrun.managers;

import java.util.ArrayList;

public class ParticipantManager {

    private static final ArrayList<String> parts = new ArrayList<>(0);
    private static ParticipantManager instance = new ParticipantManager();

    private ParticipantManager() {}

    public void addParticipant(String name) {
        if (!parts.contains(name)) {
            parts.add(name);
        }
    }

    public void clear() {
        parts.clear();
    }

    public void removeParticipant(String name) {
        parts.remove(name);
    }

    public boolean isParticipant(String name) {
        return parts.contains(name);
    }

    public ArrayList<String> getParticipants() {
        return parts;
    }

    public static ParticipantManager getInstance() {
        return instance;
    }

}
