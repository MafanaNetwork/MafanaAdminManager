package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaAdminManager;
import me.tahacheji.mafana.data.BuilderData;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerLeave implements Listener {

    @EventHandler
    public void playerLeaveGame(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        if(MafanaAdminManager.getInstance().getBuilderMYSQL().existBuilder(player)) {
            if(MafanaAdminManager.getInstance().getBuilderData(player) != null) {
                BuilderData builderData = MafanaAdminManager.getInstance().getBuilderData(player);
                builderData.setTimeSpent(builderData.formatTime(builderData.getActiveSeconds()));
                builderData.setTimeAFK(builderData.formatTime(builderData.getAfkSeconds()));
                MafanaAdminManager.getInstance().getBuilderMYSQL().addBuilderData(builderData, player);
                MafanaAdminManager.getInstance().getBuilderDataList().remove(builderData);
            }
        }
    }
}
