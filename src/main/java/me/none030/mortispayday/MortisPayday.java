package me.none030.mortispayday;

import me.none030.mortispayday.manager.MainManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MortisPayday extends JavaPlugin {

    private static MortisPayday Instance;
    private MainManager mainManager;
    private boolean eventWar;
    private boolean siegeWar;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        mainManager = new MainManager();
        eventWar = getServer().getPluginManager().getPlugin("EventWar") != null;
        siegeWar = getServer().getPluginManager().getPlugin("SiegeWar") != null;
    }

    public static MortisPayday getInstance() {
        return Instance;
    }

    public MainManager getMainManager() {
        return mainManager;
    }

    public boolean hasEventWar() {
        return eventWar;
    }

    public boolean hasSiegeWar() {
        return siegeWar;
    }
}
