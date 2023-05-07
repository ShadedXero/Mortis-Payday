package me.none030.mortispayday.config;

import me.none030.mortispayday.manager.MainManager;

public class ConfigManager {

    private final MainManager mainManager;
    private final ItemConfig itemConfig;
    private final MainConfig mainConfig;
    private final PaydayConfig paydayConfig;

    public ConfigManager(MainManager mainManager) {
        this.mainManager = mainManager;
        this.itemConfig = new ItemConfig(this);
        this.mainConfig = new MainConfig(this);
        this.paydayConfig = new PaydayConfig(this);
    }

    public MainManager getMainManager() {
        return mainManager;
    }

    public ItemConfig getItemConfig() {
        return itemConfig;
    }

    public MainConfig getMainConfig() {
        return mainConfig;
    }

    public PaydayConfig getPaydayConfig() {
        return paydayConfig;
    }
}
