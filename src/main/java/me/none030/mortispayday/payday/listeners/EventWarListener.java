package me.none030.mortispayday.payday.listeners;

import com.palmergames.bukkit.towny.object.Town;
import io.github.townyadvanced.eventwar.events.EventWarEndEvent;
import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.payday.PaydayManager;
import me.none030.mortispayday.payday.PaydaySettings;
import me.none030.mortispayday.payday.RebuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import java.util.List;

public class EventWarListener implements Listener {

    private final PaydayManager paydayManager;

    public EventWarListener(PaydayManager paydayManager) {
        this.paydayManager = paydayManager;
    }

    @EventHandler
    public void onEventWarEnd(EventWarEndEvent e) {
        List<Town> towns = e.getWarringTowns();
        for (Town town : towns) {
            PaydaySettings settings = paydayManager.getSettings();
            RebuildMode mode = settings.getWarRebuildMode();
            if (mode.equals(RebuildMode.NONE)) {
                return;
            }
            boolean win;
            Town winningTown = e.getWinningTown();
            if (winningTown != null) {
                win = winningTown.getName().equals(town.getName());
            }else {
                win = false;
            }
            PaydayData data = new PaydayData(town);
            if (mode.equals(RebuildMode.BOTH)) {
                data.setRebuildTimer(settings.getRebuildTime());
                return;
            }
            if (mode.equals(RebuildMode.WIN)) {
                if (win) {
                    data.setRebuildTimer(settings.getRebuildTime());
                }
                return;
            }
            if (mode.equals(RebuildMode.LOSE)) {
                if (!win) {
                    data.setRebuildTimer(settings.getRebuildTime());
                }
            }
        }
    }
}
