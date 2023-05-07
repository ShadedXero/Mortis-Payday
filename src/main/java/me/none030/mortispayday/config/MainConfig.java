package me.none030.mortispayday.config;

import me.none030.mortispayday.payday.PaydayManager;
import me.none030.mortispayday.payday.PaydaySettings;
import me.none030.mortispayday.payday.RebuildMode;
import me.none030.mortispayday.payday.WarRestriction;
import me.none030.mortispayday.utils.MessageUtils;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;

public class MainConfig extends Config {

    public MainConfig(ConfigManager configManager) {
        super("config.yml", configManager);
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        loadSettings(config);
        getConfigManager().getMainManager().getPaydayManager().addMessages(loadMessages(config.getConfigurationSection("messages")));
    }

    private void loadSettings(FileConfiguration config) {
        String paydayId = config.getString("default-payday");
        if (paydayId == null) {
            return;
        }
        ConfigurationSection wartime = config.getConfigurationSection("wartime");
        if (wartime == null) {
            return;
        }
        RebuildMode rebuildMode;
        try {
            rebuildMode = RebuildMode.valueOf(wartime.getString("rebuild-mode"));
        }catch (IllegalArgumentException exp) {
            return;
        }
        long rebuildDays = wartime.getLong("rebuild-period");
        long productionPercentage = wartime.getLong("production-percentage");
        boolean nationRestrict = config.getBoolean("payday-nation-restrict");
        boolean townRestrict = config.getBoolean("payday-town-restrict");
        boolean townConqueredRestrict = config.getBoolean("payday-town-conquered-restrict");
        long conqueredDays = config.getLong("conquered-restrict-days");
        WarRestriction warRestriction;
        try {
            warRestriction = WarRestriction.valueOf(config.getString("payday-war-restrict"));
        }catch (IllegalArgumentException exp) {
            return;
        }
        boolean capitalWarRestrict = config.getBoolean("payday-capital-war-restrict");
        boolean nationDemocracy = config.getBoolean("payday-nation-democracy");
        boolean townDemocracy = config.getBoolean("payday-town-democracy");
        PaydaySettings settings = new PaydaySettings(paydayId, rebuildMode, rebuildDays, productionPercentage, nationRestrict, townRestrict, townConqueredRestrict, conqueredDays, warRestriction, capitalWarRestrict, nationDemocracy, townDemocracy);
        getConfigManager().getMainManager().setPaydayManager(new PaydayManager(settings));
        MessageUtils menuTitle = new MessageUtils(config.getString("menu-title"));
        getConfigManager().getMainManager().getPaydayManager().addMessage("MENU_TITLE", menuTitle.color());
    }
}
