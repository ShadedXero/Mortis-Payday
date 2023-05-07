package me.none030.mortispayday.config;

import me.none030.mortispayday.payday.Payday;
import me.none030.mortispayday.utils.ItemEditor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class PaydayConfig extends Config {

    public PaydayConfig(ConfigManager configManager) {
        super("payday.yml", configManager);
    }

    @Override
    public void loadConfig() {
        File file = saveConfig();
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);
        loadPaydays(config.getConfigurationSection("paydays"));
    }

    private void loadPaydays(ConfigurationSection paydays) {
        if (paydays == null) {
            return;
        }
        for (String paydayId : paydays.getKeys(false)) {
            ConfigurationSection section = paydays.getConfigurationSection(paydayId);
            if (section == null) {
                continue;
            }
            String timer = section.getString("timer");
            List<ItemStack> resourceList = new ArrayList<>();
            ConfigurationSection resources = section.getConfigurationSection("resources");
            if (resources != null) {
                for (String itemId : resources.getKeys(false)) {
                    ItemStack item = getConfigManager().getMainManager().getItemManager().getItem(itemId);
                    if (item == null) {
                        continue;
                    }
                    int amount = resources.getInt(itemId);
                    item.setAmount(amount);
                    resourceList.add(item);
                }
            }
            ConfigurationSection commands = section.getConfigurationSection("commands");
            if (commands != null) {
                for (String itemId : commands.getKeys(false)) {
                    ItemStack item = getConfigManager().getMainManager().getItemManager().getItem(itemId);
                    if (item == null) {
                        continue;
                    }
                    String command = commands.getString(itemId);
                    if (command == null) {
                        continue;
                    }
                    ItemEditor editor = new ItemEditor(item);
                    editor.addPersistentData("MortisPayday", command);
                    resourceList.add(editor.getItem());
                }
            }
            Payday payday = new Payday(paydayId, timer, resourceList);
            getConfigManager().getMainManager().getPaydayManager().getPaydays().add(payday);
            getConfigManager().getMainManager().getPaydayManager().getPaydayById().put(paydayId, payday);
        }
    }
}
