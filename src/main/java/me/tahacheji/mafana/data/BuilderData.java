package me.tahacheji.mafana.data;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuilderData {

    private final Player player;
    private List<Material> blocksPlaced = new ArrayList<>();
    private List<Material> blocksBroke = new ArrayList<>();
    private String date = "";
    private String timeSpent = "";
    private String timeAFK = "";
    private int activeSeconds = 0;
    private int afkSeconds = 0;
    private Location afkLocation;
    private int afkX = 0;


    public BuilderData(Player builder) {
        this.player = builder;
    }

    public void setBlocksPlaced(List<Material> blocksPlaced) {
        this.blocksPlaced = blocksPlaced;
    }

    public void setBlocksBroke(List<Material> blocksBroke) {
        this.blocksBroke = blocksBroke;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setTimeSpent(String timeSpent) {
        this.timeSpent = timeSpent;
    }

    public void setTimeAFK(String timeAFK) {
        this.timeAFK = timeAFK;
    }

    public Player getPlayer() {
        return player;
    }

    public int getActiveSeconds() {
        return activeSeconds;
    }

    public void setActiveSeconds(int activeSeconds) {
        this.activeSeconds = activeSeconds;
    }

    public int getAfkSeconds() {
        return afkSeconds;
    }

    public Location getAfkLocation() {
        return afkLocation;
    }

    public void setAfkLocation(Location afkLocation) {
        this.afkLocation = afkLocation;
    }

    public void setAfkSeconds(int afkSeconds) {
        this.afkSeconds = afkSeconds;
    }

    public List<Material> getBlocksPlaced() {
        return blocksPlaced;
    }

    public List<Material> getBlocksBroke() {
        return blocksBroke;
    }

    public String formatTime(int seconds) {
        int hours = seconds / 3600;
        int minutes = (seconds % 3600) / 60;
        int secs = seconds % 60;

        return String.format("%02d:%02d:%02d", hours, minutes, secs);
    }

    public int getAfkX() {
        return afkX;
    }

    public void setAfkX(int afkX) {
        this.afkX = afkX;
    }

    public String getDate() {
        return date;
    }

    public String getTimeSpent() {
        return timeSpent;
    }

    public String getTimeAFK() {
        return timeAFK;
    }
}
