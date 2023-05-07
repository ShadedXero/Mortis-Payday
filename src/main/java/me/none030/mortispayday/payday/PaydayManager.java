package me.none030.mortispayday.payday;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import me.none030.mortispayday.MortisPayday;
import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.manager.Manager;
import me.none030.mortispayday.payday.listeners.EventWarListener;
import me.none030.mortispayday.payday.listeners.FlagWarListener;
import me.none030.mortispayday.payday.listeners.SiegeWarListener;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class PaydayManager extends Manager {

    private final MortisPayday plugin = MortisPayday.getInstance();
    private final PaydaySettings settings;
    private final List<Payday> paydays;
    private final HashMap<String, Payday> paydayById;
    private final HashMap<UUID, Integer> inMenuCoolDown;

    public PaydayManager(PaydaySettings settings) {
        this.settings = settings;
        this.paydays = new ArrayList<>();
        this.paydayById = new HashMap<>();
        this.inMenuCoolDown = new HashMap<>();
        plugin.getServer().getPluginManager().registerEvents(new PaydayListener(this), plugin);
        if (plugin.hasSiegeWar()) {
            plugin.getServer().getPluginManager().registerEvents(new SiegeWarListener(this), plugin);
        }
        if (plugin.hasEventWar()) {
            plugin.getServer().getPluginManager().registerEvents(new EventWarListener(this), plugin);
        }
        if (plugin.hasFlagWar()) {
            plugin.getServer().getPluginManager().registerEvents(new FlagWarListener(this), plugin);
        }
        check();
    }

    private void check() {
        PaydayManager paydayManager = this;
        new BukkitRunnable() {
            @Override
            public void run() {
                inMenuCoolDown.clear();
                for (Town town : TownyAPI.getInstance().getTowns()) {
                    PaydayData data = new PaydayData(town);
                    if (data.getId() == null) {
                        String paydayId = settings.getDefaultPaydayId();
                        Payday payday = paydayById.get(paydayId);
                        if (payday == null) {
                            continue;
                        }
                        data.create(payday.getId(), getEmptyResource(data), payday.getTime());
                    }
                    Payday payday = paydayById.get(data.getId());
                    if (payday == null) {
                        data.setId(null);
                        continue;
                    }
                    LocalDateTime rebuildTimer = data.getRebuildTimer();
                    if (rebuildTimer != null) {
                        if (rebuildTimer.isBefore(LocalDateTime.now())) {
                            payday.generateResources(data, paydayManager, settings.getProductionPercentage());
                            data.setRebuildTimer(null);
                        }
                    }else {
                        if (data.getTimer().isBefore(LocalDateTime.now())) {
                            payday.generateResources(data, paydayManager);
                            data.setTimer(payday.getTime());
                        }
                    }
                }
            }
        }.runTaskTimer(plugin, 0L, 20L);
    }

    public String getEmptyResource(PaydayData data) {
        Inventory inv = Bukkit.createInventory(null, 54, Component.text(getMessage("MENU_TITLE").replace("%town_name%", data.getTown().getName())));
        return data.serialize(inv);
    }

    public PaydaySettings getSettings() {
        return settings;
    }

    public List<Payday> getPaydays() {
        return paydays;
    }

    public HashMap<String, Payday> getPaydayById() {
        return paydayById;
    }

    public HashMap<UUID, Integer> getInMenuCoolDown() {
        return inMenuCoolDown;
    }
}
