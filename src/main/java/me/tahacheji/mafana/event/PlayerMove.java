package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaAdminManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.scheduler.BukkitRunnable;

public class PlayerMove implements Listener {

    @EventHandler
    public void playerMoveEvent(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        Location location = player.getLocation().clone();
        new BukkitRunnable() {
            @Override
            public void run() {
                if (player.getLocation() == location) {
                    if (MafanaAdminManager.getInstance().getBuilderData(player) != null) {
                        if (!MafanaAdminManager.getInstance().getAfkBuilderDataList().contains(MafanaAdminManager.getInstance().getBuilderData(player))) {
                            MafanaAdminManager.getInstance().getBuilderData(player).setAfkLocation(player.getLocation());
                            player.sendMessage(ChatColor.YELLOW + "[MafanaAdminManager]: You are now AFK");
                            MafanaAdminManager.getInstance().getAfkBuilderDataList().add(MafanaAdminManager.getInstance().getBuilderData(player));
                        }
                    }
                }
            }
        }.runTaskLater(MafanaAdminManager.getInstance(), 20L * 25);
    }
}
