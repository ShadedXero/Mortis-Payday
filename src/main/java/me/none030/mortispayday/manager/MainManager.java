package me.none030.mortispayday.manager;

import me.none030.mortispayday.MortisPayday;
import me.none030.mortispayday.config.ConfigManager;
import me.none030.mortispayday.items.ItemManager;
import me.none030.mortispayday.payday.PaydayManager;
import org.bukkit.Bukkit;
import org.bukkit.event.HandlerList;

public class MainManager {

    private final MortisPayday plugin = MortisPayday.getInstance();
    private ItemManager itemManager;
    private PaydayManager paydayManager;
    private ConfigManager configManager;

    public MainManager() {
        this.itemManager = new ItemManager();
        this.configManager = new ConfigManager(this);
        plugin.getServer().getPluginCommand("payday").setExecutor(new PaydayCommand(this));
    }

    public void reload() {
        HandlerList.unregisterAll(plugin);
        Bukkit.getScheduler().cancelTasks(plugin);
        setItemManager(new ItemManager());
        setConfigManager(new ConfigManager(this));
    }

    public ItemManager getItemManager() {
        return itemManager;
    }

    public void setItemManager(ItemManager itemManager) {
        this.itemManager = itemManager;
    }

    public PaydayManager getPaydayManager() {
        return paydayManager;
    }

    public void setPaydayManager(PaydayManager paydayManager) {
        this.paydayManager = paydayManager;
    }

    public ConfigManager getConfigManager() {
        return configManager;
    }

    public void setConfigManager(ConfigManager configManager) {
        this.configManager = configManager;
    }
}
