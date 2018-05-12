package com.fimiyaad.mobile.Helper;

import android.util.Log;
import com.fimiyaad.mobile.Model.GalleryModel;
import com.google.gson.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class GalleryDeserializer
{
    private String json;
    private JsonObject jsonObject;

    public GalleryDeserializer(String s) {
        json = s;
    }

    public HashMap<String, List<GalleryModel>> deserializer() throws JsonParseException {
        HashMap<String, List<GalleryModel>> map = new HashMap<>();
        JsonParser parser = new JsonParser();
        JsonElement elem = parser.parse(json);
        jsonObject = elem.getAsJsonObject();

        map.put("jamaica", parseByCountry("jamaica"));
        map.put("usa",     parseByCountry("usa"));
        map.put("uk",      parseByCountry("uk"));

        return map;
    }

    private List<GalleryModel> parseByCountry(String country) {
        List<GalleryModel> list = new ArrayList<>();
        if(jsonObject.has(country)){
            JsonArray obj = jsonObject.get(country).getAsJsonArray();
            JsonObject asJsonObject;

            for (int i = 0; i < obj.size(); i++) {
                asJsonObject = obj.get(i).getAsJsonObject();
                list.add(new GalleryModel(asJsonObject.get("id").getAsInt(),
                        asJsonObject.get("url").getAsString(),
                        asJsonObject.get("img").getAsString(),
                        asJsonObject.get("country").getAsString(),
                        asJsonObject.get("title").getAsString(),
                        asJsonObject.get("date").getAsString(),
                        asJsonObject.get("year").getAsInt()));
            }
        }

        return list;
    }

}
