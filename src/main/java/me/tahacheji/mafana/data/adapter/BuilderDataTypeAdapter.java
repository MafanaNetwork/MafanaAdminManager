package me.tahacheji.mafana.data.adapter;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonWriter;
import me.tahacheji.mafana.data.BuilderData;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BuilderDataTypeAdapter extends TypeAdapter<BuilderData> {

    @Override
    public void write(JsonWriter out, BuilderData builderData) throws IOException {
        out.beginObject();
        if (builderData.getPlayer() != null) {
            out.name("player").value(builderData.getPlayer().getUniqueId().toString());
        }
        if (!builderData.getBlocksPlaced().isEmpty()) {
            out.name("blocksPlaced");
            out.beginArray();
            for (Material material : builderData.getBlocksPlaced()) {
                out.value(material.toString());
            }
            out.endArray();
        }
        if (!builderData.getBlocksBroke().isEmpty()) {
            out.name("blocksBroke");
            out.beginArray();
            for (Material material : builderData.getBlocksBroke()) {
                out.value(material.toString());
            }
            out.endArray();
        }
        out.name("date").value(builderData.getDate());
        out.name("timeSpent").value(builderData.getTimeSpent());
        out.name("timeAFK").value(builderData.getTimeAFK());
        out.endObject();
    }

    @Override
    public BuilderData read(JsonReader in) throws IOException {
        in.beginObject();
        Player player = null;
        List<Material> blocksPlaced = new ArrayList<>();
        List<Material> blocksBroke = new ArrayList<>();
        String date = "";
        String timeSpent = "";
        String timeAFK = "";

        while (in.hasNext()) {
            switch (in.nextName()) {
                case "player":
                    player = Bukkit.getPlayer(UUID.fromString(in.nextString()));
                    break;
                case "blocksPlaced":
                    in.beginArray();
                    while (in.hasNext()) {
                        blocksPlaced.add(Material.getMaterial(in.nextString()));
                    }
                    in.endArray();
                    break;
                case "blocksBroke":
                    in.beginArray();
                    while (in.hasNext()) {
                        blocksBroke.add(Material.getMaterial(in.nextString()));
                    }
                    in.endArray();
                    break;
                case "date":
                    date = in.nextString();
                    break;
                case "timeSpent":
                    timeSpent = in.nextString();
                    break;
                case "timeAFK":
                    timeAFK = in.nextString();
                    break;
            }
        }
        in.endObject();

        BuilderData builderData = new BuilderData(player);
        builderData.setBlocksPlaced(blocksPlaced);
        builderData.setBlocksBroke(blocksBroke);
        builderData.setDate(date);
        builderData.setTimeSpent(timeSpent);
        builderData.setTimeAFK(timeAFK);

        return builderData;
    }
}

