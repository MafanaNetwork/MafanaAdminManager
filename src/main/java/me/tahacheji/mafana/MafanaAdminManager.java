package me.tahacheji.mafana;

import me.tahacheji.mafana.command.AdminCommand;
import me.tahacheji.mafana.data.AdminMYSQL;
import me.tahacheji.mafana.data.BuilderData;
import me.tahacheji.mafana.data.BuilderMYSQL;
import me.tahacheji.mafana.event.*;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public final class MafanaAdminManager extends JavaPlugin {

    private static MafanaAdminManager instance;
    private List<BuilderData> builderDataList = new ArrayList<>();
    private BuilderMYSQL builderMYSQL;
    private AdminMYSQL adminMYSQL;
    private List<BuilderData> afkBuilderDataList = new ArrayList<>();
    @Override
    public void onEnable() {
        instance = this;
        builderMYSQL = new BuilderMYSQL();
        builderMYSQL.connect();
        adminMYSQL = new AdminMYSQL();
        adminMYSQL.connect();
        getServer().getPluginManager().registerEvents(new PlayerBreak(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoin(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeave(), this);
        getServer().getPluginManager().registerEvents(new PlayerMove(), this);
        getServer().getPluginManager().registerEvents(new PlayerPlace(), this);
        getCommand("MAM").setExecutor(new AdminCommand());
        runBuilderDataRunCurrentTime();
        runAFKBuilderDataRunCurrentTime();
    }

    @Override
    public void onDisable() {
        builderMYSQL.disconnect();
    }

    public BuilderMYSQL getBuilderMYSQL() {
        return builderMYSQL;
    }

    public AdminMYSQL getAdminMYSQL() {
        return adminMYSQL;
    }

    public void runBuilderDataRunCurrentTime() {
        new BukkitRunnable() {
            @Override
            public void run() {
                for(BuilderData builderData : getBuilderDataList()) {
                    if(builderData == null) {
                        continue;
                    }
                    for(ItemStack itemStack : builderData.getPlayer().getInventory()) {
                        if(itemStack == null) {
                            continue;
                        }
                        if(itemStack.getType() == Material.TNT || itemStack.getType() == Material.LAVA || itemStack.getType() == Material.FLINT_AND_STEEL || itemStack.getType() == Material.CREEPER_SPAWN_EGG) {
                            itemStack.setType(Material.AIR);
                        }
                    }
                    Player player = builderData.getPlayer();
                    if(builderData.getAfkLocation() != null) {
                        if (builderData.getAfkLocation().getX() == player.getLocation().getX() && builderData.getAfkLocation().getY() == player.getLocation().getY() && builderData.getAfkLocation().getZ() == player.getLocation().getZ()) {
                            builderData.setAfkX(builderData.getAfkX() + 1);
                            if (builderData.getAfkX() >= 5) {
                                builderData.setAfkSeconds(builderData.getAfkSeconds() + 5);
                            }
                            continue;
                        }
                    }
                    builderData.setAfkX(0);
                    builderData.setAfkLocation(builderData.getPlayer().getLocation());
                    builderData.setActiveSeconds(builderData.getActiveSeconds() + 5);
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 100L);
    }

    public void runAFKBuilderDataRunCurrentTime() {
        new BukkitRunnable() {
            @Override
            public void run() {
                Iterator<BuilderData> iterator = getAfkBuilderDataList().iterator();

                while (iterator.hasNext()) {
                    BuilderData builderData = iterator.next();

                    if (builderData == null || builderData.getAfkLocation() == null) {
                        continue;
                    }

                    if (builderData.getAfkLocation().equals(builderData.getPlayer().getLocation())) {
                        builderData.setActiveSeconds(builderData.getAfkSeconds() + 5);
                    } else {
                        builderData.getPlayer().sendMessage(ChatColor.YELLOW + "[MafanaAdminManager]: You are now not AFK");
                        iterator.remove(); // Remove the element properly
                    }
                }
            }
        }.runTaskTimerAsynchronously(this, 0L, 100L);
    }


    public List<BuilderData> getAfkBuilderDataList() {
        return afkBuilderDataList;
    }

    public BuilderData getBuilderData(OfflinePlayer offlinePlayer) {
        for(BuilderData builderData : getBuilderDataList()) {
            if(builderData.getPlayer().getUniqueId().toString().equalsIgnoreCase(offlinePlayer.getUniqueId().toString())) {
                return builderData;
            }
        }
        return null;
    }

    public static MafanaAdminManager getInstance() {
        return instance;
    }

    public List<BuilderData> getBuilderDataList() {
        return builderDataList;
    }
}
