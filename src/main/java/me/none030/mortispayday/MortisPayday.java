package me.none030.mortispayday;

import me.none030.mortispayday.manager.MainManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class MortisPayday extends JavaPlugin {

    private static MortisPayday Instance;
    private boolean eventWar;
    private boolean siegeWar;
    private boolean flagWar;
    private MainManager mainManager;

    @Override
    public void onEnable() {
        // Plugin startup logic
        Instance = this;
        eventWar = getServer().getPluginManager().getPlugin("EventWar") != null;
        siegeWar = getServer().getPluginManager().getPlugin("SiegeWar") != null;
        flagWar = getServer().getPluginManager().getPlugin("FlagWar") != null;
        mainManager = new MainManager();
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

    public boolean hasFlagWar() {
        return flagWar;
    }
}
