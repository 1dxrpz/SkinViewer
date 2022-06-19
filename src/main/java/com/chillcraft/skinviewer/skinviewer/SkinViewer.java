package com.chillcraft.skinviewer.skinviewer;

import net.skinsrestorer.api.SkinsRestorerAPI;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;

public final class SkinViewer extends JavaPlugin {
    private SkinsRestorerAPI skinsRestorerAPI;
    private SkinViewerDataStorage DataStorage;
    File config = new File(getDataFolder() + File.separator + "config.yml");
    @Override
    public void onEnable() {
        if (!config.exists())
        {
            getLogger().info("Creating config folder");
            getConfig().options().copyDefaults(true);
            saveDefaultConfig();
        }
        File PlayersData = new File(getDataFolder() + "/data");
        if(!PlayersData.exists()) {
            PlayersData.mkdir();
            getLogger().info("Creating data folder");
        }
        skinsRestorerAPI = SkinsRestorerAPI.getApi();
        DataStorage = new SkinViewerDataStorage(skinsRestorerAPI);
        Bukkit.getPluginManager().registerEvents(new Logger(this, DataStorage), this);
        getCommand("getskinurl").setExecutor(new Command(this, DataStorage));
    }
    @Override
    public void onDisable() { }
}
