package com.chillcraft.skinviewer.skinviewer;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import net.skinsrestorer.api.SkinsRestorerAPI;
import net.skinsrestorer.api.bukkit.events.SkinApplyBukkitEvent;
import net.skinsrestorer.api.property.IProperty;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.Base64;

public class Logger implements Listener {
    SkinViewer plugin;
    SkinViewerDataStorage DataStorage;
    public Logger(SkinViewer p, SkinViewerDataStorage storage) {
        plugin = p;
        DataStorage = storage;

    }

    @EventHandler
    private void Join(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        File PlayerData = new File(plugin.getDataFolder() + "/data/" + player.getName());
        if (!PlayerData.exists()) {
            FileConfiguration configuration = new YamlConfiguration();
            configuration.set("url", DataStorage.GetSkinURL(player.getName()));
            try {
                configuration.save(PlayerData);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
    @EventHandler
    private void Skin(SkinApplyBukkitEvent event) {
        Player player = event.getWho();
        String URL = DataStorage.PropertyToURL(event.getProperty());
        File PlayerData = new File(plugin.getDataFolder() + "/data/" + player.getName());
        FileConfiguration configuration = new YamlConfiguration();
        configuration.set("url", URL);
        try {
            configuration.save(PlayerData);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
