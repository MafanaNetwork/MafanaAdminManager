package me.tahacheji.mafana.data;

import me.TahaCheji.mysqlData.MySQL;
import me.TahaCheji.mysqlData.MysqlValue;
import me.TahaCheji.mysqlData.SQLGetter;
import me.tahacheji.mafana.data.gson.BuilderDataGson;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuilderMYSQL extends MySQL {

    SQLGetter sqlGetter = new SQLGetter(this);

    public BuilderMYSQL() {
        super("localhost", "3306", "51190", "51190", "26c58bbe8e");
    }

    public void addBuilder(OfflinePlayer player) {
        if(!sqlGetter.exists(player.getUniqueId())) {
            sqlGetter.setString(new MysqlValue("NAME", player.getUniqueId(), player.getName()));
            sqlGetter.setString(new MysqlValue("BUILDER_DATA", player.getUniqueId(), ""));
            sqlGetter.setUUID(new MysqlValue("UUID", player.getUniqueId(), player.getUniqueId()));
        }
    }

    public List<BuilderData> getAllBuilderData(OfflinePlayer player) {
        List<BuilderData> getBuilderData = new ArrayList<>();
        try {
            for (BuilderData builderData : getAllBuilderData()) {
                if (builderData.getPlayer().getUniqueId().toString().equalsIgnoreCase(player.getUniqueId().toString())) {
                    getBuilderData.add(builderData);
                }
            }
        } catch (Exception ignore) {

        }
        return getBuilderData;
    }

    public List<BuilderData> getAllBuilderData() throws SQLException {
        List<BuilderData> list = new ArrayList<>();
        List<UUID> uuids = sqlGetter.getAllUUID(new MysqlValue("UUID"));
        for(UUID uuid : uuids) {
            OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(uuid);
            if(offlinePlayer != null) {
                if(getBuilderData(offlinePlayer) != null) {
                    list.addAll(getBuilderData(offlinePlayer));
                }
            }
        }
        return list;
    }

    public boolean existBuilder(OfflinePlayer offlinePlayer) {
        return sqlGetter.exists(offlinePlayer.getUniqueId());
    }

    public void addBuilderData(BuilderData builderData, OfflinePlayer player) {
        if(sqlGetter.exists(player.getUniqueId())) {
            if(getBuilderData(player) != null) {
                List<BuilderData> builderDataList = getBuilderData(player);
                builderDataList.add(builderData);
                setBuilderData(builderDataList, player);
            } else {
                List<BuilderData> builderDataList = new ArrayList<>();
                builderDataList.add(builderData);
                setBuilderData(builderDataList, player);
            }
        }
    }

    public List<BuilderData> getBuilderData(OfflinePlayer offlinePlayer) {
        if(sqlGetter.exists(offlinePlayer.getUniqueId())) {
            return new BuilderDataGson().builderDataFromGson(sqlGetter.getString(offlinePlayer.getUniqueId(), new MysqlValue("BUILDER_DATA")));
        }
        return new ArrayList<>();
    }

    public void setBuilderData(List<BuilderData> builderData, OfflinePlayer offlinePlayer) {
        sqlGetter.setString(new MysqlValue("BUILDER_DATA", offlinePlayer.getUniqueId(), new BuilderDataGson().builderDataToGson(builderData)));
    }

    public void removeBuilder(OfflinePlayer offlinePlayer) {
        sqlGetter.removeUUID(offlinePlayer.getUniqueId(), new MysqlValue("UUID"));
    }

    @Override
    public void setSqlGetter(SQLGetter sqlGetter) {
        this.sqlGetter = sqlGetter;
    }

    @Override
    public void connect() {
        super.connect();
        if (this.isConnected()) sqlGetter.createTable("mafana_builder_manager",
                new MysqlValue("NAME", ""),
                new MysqlValue("BUILDER_DATA", ""));
    }
}
