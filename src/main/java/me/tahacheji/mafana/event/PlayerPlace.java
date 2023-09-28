package me.tahacheji.mafana.event;

import me.tahacheji.mafana.MafanaAdminManager;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlace implements Listener {


    @EventHandler
    public void playerPlaceBlock(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Material material = event.getBlockPlaced().getType();
        if(MafanaAdminManager.getInstance().getBuilderData(player) != null) {
            MafanaAdminManager.getInstance().getBuilderData(player).getBlocksPlaced().add(material);
        }
    }
}
