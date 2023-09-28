package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaAdminManager;
import me.tahacheji.mafana.data.BuilderData;
import me.tahacheji.mafana.data.display.BuilderDataDisplay;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if(label.equalsIgnoreCase("MAM")) {
            Player senderPlayer = (Player) sender;
            if(senderPlayer.isOp()) {
                if (args.length == 2 && args[0].equalsIgnoreCase("add")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: Player not found dumbass");
                        return true;
                    }
                    MafanaAdminManager.getInstance().getBuilderMYSQL().addBuilder(player);
                    player.setGameMode(GameMode.CREATIVE);
                    senderPlayer.sendMessage(ChatColor.GREEN + "[MAM]: added builder");
                    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

                    if (plugin != null) {
                        PermissionAttachment attachment = player.addAttachment(plugin);
                        attachment.setPermission("worldedit.*", true);
                        player.recalculatePermissions();
                    } else {

                    }
                }
                if (args.length == 2 && args[0].equalsIgnoreCase("remove")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (!offlinePlayer.isOnline()) {
                        MafanaAdminManager.getInstance().getBuilderMYSQL().removeBuilder(offlinePlayer);
                        return true;
                    }
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: Player not found dumbass");
                        return true;
                    }
                    MafanaAdminManager.getInstance().getBuilderMYSQL().removeBuilder(player);
                    player.setGameMode(GameMode.CREATIVE);
                    senderPlayer.sendMessage(ChatColor.RED + "[MAM]: player removed");
                    Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

                    if (plugin != null) {
                        PermissionAttachment attachment = player.addAttachment(plugin);
                        attachment.unsetPermission("worldedit.*");
                        player.recalculatePermissions();
                    } else {
                        return true;
                    }
                }
                if(args.length == 2 && args[0].equalsIgnoreCase("logs")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (offlinePlayer == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: Player not found dumbass");
                        return true;
                    }
                    new BuilderDataDisplay().getBuilderDataDisplay(offlinePlayer, senderPlayer).open(senderPlayer);
                }
            } else {
                if(args[0].equalsIgnoreCase("logs")) {
                    new BuilderDataDisplay().getBuilderDataDisplay(senderPlayer, senderPlayer).open(senderPlayer);
                }
            }
        }
        return false;
    }
}
