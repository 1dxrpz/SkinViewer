package com.chillcraft.skinviewer.skinviewer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.property.IProperty;

import java.util.Base64;

public class SkinViewerDataStorage {
    SkinsRestorerAPI skinsRestorerAPI;

    public SkinViewerDataStorage(SkinsRestorerAPI api) {
        skinsRestorerAPI = api;
    }
    public String PropertyToURL(IProperty property) {
        String skinURL = property.getValue();
        byte[] decoded = Base64.getDecoder().decode(skinURL);
        String decodedString = new String(decoded);
        JsonObject jsonObject = JsonParser.parseString(decodedString).getAsJsonObject();
        return jsonObject.getAsJsonObject()
                .get("textures").getAsJsonObject()
                .get("SKIN").getAsJsonObject()
                .get("url").getAsString();
    }
    public String GetSkinURL(String name) {
        return PropertyToURL(skinsRestorerAPI.getSkinData(name));
    }
}
