package fr.elowyr.lobby.profiles.data;

import org.bukkit.entity.Player;

import java.util.UUID;

public class ProfileData {

    //DATA PRINCIPAL
    private UUID playerUUID;
    private String playerName;

    private boolean onCombat;

    private int kills;
    private int deaths;
    private double killStreak;



    public ProfileData() {
        this.init();
    }

    public ProfileData(Player player) {
        this.playerUUID = player.getUniqueId();
        this.playerName = player.getName();
        this.init();
    }

    public void init() {
        this.onCombat = false;
        this.kills = 0;
        this.deaths = 0;
        this.killStreak = 0;
    }

    public UUID getPlayerUUID() {
        return playerUUID;
    }

    public void setPlayerUUID(UUID playerUUID) {
        this.playerUUID = playerUUID;
    }

    public String getPlayerName() {
        return playerName;
    }

    public void setPlayerName(String playerName) {
        this.playerName = playerName;
    }

    public boolean isOnCombat() {
        return onCombat;
    }

    public void setOnCombat(boolean onCombat) {
        this.onCombat = onCombat;
    }

    public int getKills() {
        return kills;
    }

    public void setKills(int kills) {
        this.kills = kills;
    }

    public int getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public double getKillStreak() {
        return killStreak;
    }

    public void setKillStreak(double killStreak) {
        this.killStreak = killStreak;
    }
}
