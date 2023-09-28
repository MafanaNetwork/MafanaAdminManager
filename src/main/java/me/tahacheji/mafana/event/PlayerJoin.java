package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaAdminManager;
import me.tahacheji.mafana.data.BuilderData;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class PlayerJoin implements Listener {

    @EventHandler
    public void playerJoinEvent(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if(MafanaAdminManager.getInstance().getBuilderMYSQL().existBuilder(player)) {
            if(MafanaAdminManager.getInstance().getBuilderData(player) == null) {
                player.sendMessage(ChatColor.GREEN + "[MafanaAdminManager]: You are now a builder.");
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("M/d/yyyy h:mm a");
                LocalDateTime now = LocalDateTime.now();
                String date = "[" + dtf.format(now) + "]";
                BuilderData builderData = new BuilderData(player);
                builderData.setDate(date);
                MafanaAdminManager.getInstance().getBuilderDataList().add(builderData);
            }
        }
    }
}
