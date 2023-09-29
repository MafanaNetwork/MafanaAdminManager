package me.tahacheji.mafana.data;

import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

public class AdminData {

    private Player player;
    private String clearance = "";

    public AdminData(Player player) {
        this.player = player;
    }

    public void setClearance(String clearance) {
        this.clearance = clearance;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Player getPlayer() {
        return player;
    }

    public String getClearance() {
        return clearance;
    }
}
