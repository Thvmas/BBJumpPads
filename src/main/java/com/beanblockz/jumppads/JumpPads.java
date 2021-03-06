package com.beanblockz.jumppads;

import com.beanblockz.jumppads.command.CommandBus;
import io.sponges.configutils.Config;
import io.sponges.configutils.ConfigLoader;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.Arrays;

public class JumpPads extends JavaPlugin {

    @Override
    public void onEnable() {
        // we can assume if the data folder exists the config does too
        if (!getDataFolder().exists()) {
            saveDefaultConfig();
        }
        loadMessages();

        PluginCommand command = getCommand("bbjumppads");
        command.setExecutor(new CommandBus(this));
        command.setAliases(Arrays.asList("jumppads", "jp"));

        PluginManager pluginManager = getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(this), this);
    }

    /**
     * Method to load/reload messages for the Lang enum.
     * Public access because it is used in the ReloadCommand.
     */
    public void loadMessages() {
        Config messagesConfig;
        try {
            messagesConfig = ConfigLoader.load(this, getDataFolder(), "messages.yml");
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }
        FileConfiguration config = messagesConfig.getConfiguration();
        boolean updated = false;
        for (Lang lang : Lang.values()) {
            String configName = lang.getConfigName();
            if (!config.contains(configName)) {
                config.set(configName, lang.getMessage());
                if (!updated) {
                    updated = true;
                }
                continue;
            }
            String configValue = config.getString(configName);
            lang.setMessage(configValue);
        }
        if (updated) {
            try {
                messagesConfig.save();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
