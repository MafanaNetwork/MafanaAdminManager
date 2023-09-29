package me.tahacheji.mafana.command;

import me.tahacheji.mafana.MafanaAdminManager;
import me.tahacheji.mafana.data.AdminData;
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
import org.checkerframework.checker.units.qual.A;
import org.jetbrains.annotations.NotNull;

public class AdminCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (label.equalsIgnoreCase("MAM")) {
            Player senderPlayer = (Player) sender;
            if(senderPlayer.isOp()) {
                if(args.length == 3 && args[0].equalsIgnoreCase("addAdmin")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    String value = args[2];
                    if (player == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: PLAYER_NOT_FOUND");
                        return true;
                    }
                    AdminData adminData = new AdminData(player);
                    adminData.setClearance(value);
                    MafanaAdminManager.getInstance().getAdminMYSQL().addAdmin(adminData);
                    senderPlayer.sendMessage(ChatColor.GREEN + "[MAM]: Added Admin");
                }
                if(args.length == 3 && args[0].equalsIgnoreCase("setAdmin")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    String value = args[2];
                    if (player == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: PLAYER_NOT_FOUND");
                        return true;
                    }
                    MafanaAdminManager.getInstance().getAdminMYSQL().setAdminClearance(player, value);
                    senderPlayer.sendMessage(ChatColor.GREEN + "[MAM]: Set Admin");
                }
                if(args.length == 2 && args[0].equalsIgnoreCase("removeAdmin")) {
                    Player player = Bukkit.getPlayer(args[1]);
                    if (player == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: PLAYER_NOT_FOUND");
                        return true;
                    }
                    MafanaAdminManager.getInstance().getAdminMYSQL().removeAdmin(player);
                    senderPlayer.sendMessage(ChatColor.GREEN + "[MAM]: Removed Admin");
                }
            }
            if(MafanaAdminManager.getInstance().getAdminMYSQL().isAdmin(senderPlayer)) {
                if(MafanaAdminManager.getInstance().getAdminMYSQL().getAdminClearance(senderPlayer).equalsIgnoreCase("3")) {
                    if (args.length == 2 && args[0].equalsIgnoreCase("addBuilder")) {
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player == null) {
                            senderPlayer.sendMessage(ChatColor.RED + "[MAM]: PLAYER_NOT_FOUND");
                            return true;
                        }
                        MafanaAdminManager.getInstance().getBuilderMYSQL().addBuilder(player);
                        player.setGameMode(GameMode.CREATIVE);
                        senderPlayer.sendMessage(ChatColor.GREEN + "[MAM]: Added Builder");
                        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

                        if (plugin != null) {
                            PermissionAttachment attachment = player.addAttachment(plugin);
                            attachment.setPermission("worldedit.*", true);
                            player.recalculatePermissions();
                        } else {

                        }
                    }
                    if (args.length == 2 && args[0].equalsIgnoreCase("removeBuilder")) {
                        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                        if (!offlinePlayer.isOnline()) {
                            MafanaAdminManager.getInstance().getBuilderMYSQL().removeBuilder(offlinePlayer);
                            return true;
                        }
                        Player player = Bukkit.getPlayer(args[1]);
                        if (player == null) {
                            senderPlayer.sendMessage(ChatColor.RED + "[MAM]: PLAYER_NOT_FOUND");
                            return true;
                        }
                        MafanaAdminManager.getInstance().getBuilderMYSQL().removeBuilder(player);
                        player.setGameMode(GameMode.CREATIVE);
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: Player Removed");
                        Plugin plugin = Bukkit.getServer().getPluginManager().getPlugin("WorldEdit");

                        if (plugin != null) {
                            PermissionAttachment attachment = player.addAttachment(plugin);
                            attachment.unsetPermission("worldedit.*");
                            player.recalculatePermissions();
                        } else {
                            return true;
                        }
                    }
                }
                if (args.length == 2 && args[0].equalsIgnoreCase("privlogs")) {
                    OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(args[1]);
                    if (offlinePlayer == null) {
                        senderPlayer.sendMessage(ChatColor.RED + "[MAM]: PLAYER_NOT_FOUND");
                        return true;
                    }
                    new BuilderDataDisplay().getBuilderDataDisplay(offlinePlayer, senderPlayer).open(senderPlayer);
                }
            }
            if (args[0].equalsIgnoreCase("logs")) {
                new BuilderDataDisplay().getBuilderDataDisplay(senderPlayer, senderPlayer).open(senderPlayer);
            }
        }
        return false;
    }
}
