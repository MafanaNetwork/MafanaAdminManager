package me.tahacheji.mafana.data.gson;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import me.tahacheji.mafana.data.BuilderData;
import me.tahacheji.mafana.data.adapter.BuilderDataTypeAdapter;

import java.util.List;

public class BuilderDataGson {

    public String builderDataToGson(List<BuilderData> builderData) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BuilderData.class, new BuilderDataTypeAdapter())
                .create();
        return gson.toJson(builderData);
    }

    public List<BuilderData> builderDataFromGson(String s) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapter(BuilderData.class, new BuilderDataTypeAdapter())
                .create();
        return gson.fromJson(s, new TypeToken<List<BuilderData>>() {}.getType());
    }
}
