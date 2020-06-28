package dev.nest.tntrun.managers;

import dev.nest.tntrun.TNTPlayer;

import java.util.ArrayList;

public class TNTPlayerManager {

    private static TNTPlayerManager instance = new TNTPlayerManager();

    private TNTPlayerManager() {}

    private static final ArrayList<TNTPlayer> tntPlayers = new ArrayList<>();


    public void addTntPlayer(TNTPlayer tntPlayer) {
        if (!tntPlayers.contains(tntPlayer)) {
            tntPlayers.add(tntPlayer);
        }
    }

    public void removeTntPlayer(TNTPlayer tntPlayer) {
        tntPlayers.remove(tntPlayer);
    }

    public TNTPlayer getTntPlayer(String name) {
        for (TNTPlayer p : tntPlayers) {
            if (p.getName().equalsIgnoreCase(name)) {
                return p;
            }
        }
        return null;
    }

    public ArrayList<TNTPlayer> getTntPlayers() {
        return tntPlayers;
    }

    public static TNTPlayerManager getInstance() {
        return instance;
    }

}
