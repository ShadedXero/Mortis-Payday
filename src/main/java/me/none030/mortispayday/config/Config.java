package me.none030.mortispayday.config;

import me.none030.mortispayday.MortisPayday;
import me.none030.mortispayday.utils.MessageUtils;
import org.bukkit.configuration.ConfigurationSection;

import java.io.File;
import java.util.HashMap;

public abstract class Config {

    private final MortisPayday plugin = MortisPayday.getInstance();
    private final String fileName;
    private final ConfigManager configManager;

    public Config(String fileName, ConfigManager configManager) {
        this.fileName = fileName;
        this.configManager = configManager;
        loadConfig();
    }

    public abstract void loadConfig();

    public HashMap<String, String> loadMessages(ConfigurationSection messages) {
        HashMap<String, String> messageById = new HashMap<>();
        if (messages == null) {
            return messageById;
        }
        for (String key : messages.getKeys(false)) {
            String id = key.replace("-", "_").toUpperCase();
            String message = messages.getString(key);
            MessageUtils editor = new MessageUtils(message);
            messageById.put(id, editor.color());
        }
        return messageById;
    }

    public File saveConfig() {
        File file = new File(plugin.getDataFolder(), fileName);
        if (!file.exists()) {
            plugin.saveResource(fileName, true);
        }
        return file;
    }

    public MortisPayday getPlugin() {
        return plugin;
    }

    public String getFileName() {
        return fileName;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }
}
