package com.chillcraft.skinviewer.skinviewer;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

public class Command implements CommandExecutor {
    SkinViewer plugin;
    SkinViewerDataStorage DataStorage;
    public Command(SkinViewer p, SkinViewerDataStorage storage) {
        plugin = p;
        DataStorage = storage;
    }
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull org.bukkit.command.Command command, @NotNull String label, @NotNull String[] args) {
        FileConfiguration configuration = new YamlConfiguration();
        String result = null;

        if (args.length == 1 && sender instanceof ConsoleCommandSender) {
            File PlayerData = new File(plugin.getDataFolder() + "/data/" + args[0]);
            String URL = DataStorage.GetSkinURL(args[0]);
            if (!PlayerData.exists()) {
                configuration.set("url", URL);
                try {
                    configuration.save(PlayerData);
                    result = URL;
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            } else {
                try {
                    configuration.load(PlayerData);
                    result = configuration.getString("url");
                } catch (IOException | InvalidConfigurationException e) {
                    throw new RuntimeException(e);
                }
            }
            sender.sendMessage(result);
        }
        return false;
    }
}
