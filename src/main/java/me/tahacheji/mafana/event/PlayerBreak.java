package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaAdminManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerBreak implements Listener {

    @EventHandler
    public void playerBreakBlock(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBlock().getType();
        if(MafanaAdminManager.getInstance().getBuilderData(player) != null) {
            MafanaAdminManager.getInstance().getBuilderData(player).getBlocksBroke().add(material);
        }
    }
}
