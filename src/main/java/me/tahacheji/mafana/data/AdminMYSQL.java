package me.tahacheji.mafana.data;

import me.TahaCheji.mysqlData.MySQL;
import me.TahaCheji.mysqlData.MysqlValue;
import me.TahaCheji.mysqlData.SQLGetter;
import org.bukkit.OfflinePlayer;

public class AdminMYSQL extends MySQL {

    SQLGetter sqlGetter = new SQLGetter(this);

    public AdminMYSQL() {
        super("localhost", "3306", "51190", "51190", "26c58bbe8e");
    }

    public void addAdmin(AdminData adminData) {
        if(sqlGetter.exists(adminData.getPlayer().getUniqueId())) {
            sqlGetter.setString(new MysqlValue("NAME", adminData.getPlayer().getUniqueId(), adminData.getPlayer().getName()));
            sqlGetter.setString(new MysqlValue("CLEARANCE", adminData.getPlayer().getUniqueId(), adminData.getClearance()));
            sqlGetter.setUUID(new MysqlValue("UUID", adminData.getPlayer().getUniqueId(), adminData.getPlayer().getUniqueId()));
        }
    }

    public void setAdminClearance(OfflinePlayer offlinePlayer, String clearance) {
        if(sqlGetter.exists(offlinePlayer.getUniqueId())) {
            sqlGetter.setString(new MysqlValue("CLEARANCE", offlinePlayer.getUniqueId(), clearance));
        }
    }

    public String getAdminClearance(OfflinePlayer offlinePlayer) {
        if(isAdmin(offlinePlayer)) {
            return sqlGetter.getString(offlinePlayer.getUniqueId(), new MysqlValue("CLEARANCE"));
        }
        return null;
    }

    public boolean isAdmin(OfflinePlayer offlinePlayer) {
        return sqlGetter.exists(offlinePlayer.getUniqueId());
    }

    public void removeAdmin(OfflinePlayer offlinePlayer) {
        if(isAdmin(offlinePlayer)) {
            sqlGetter.removeUUID(offlinePlayer.getUniqueId(), new MysqlValue("UUID"));
        }
    }

    @Override
    public SQLGetter getSqlGetter() {
        return sqlGetter;
    }

    @Override
    public void connect() {
        super.connect();
        if (this.isConnected()) sqlGetter.createTable("mafana_admin_manager",
                new MysqlValue("NAME", ""),
                new MysqlValue("CLEARANCE", ""));
    }
}
