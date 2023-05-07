package me.none030.mortispayday.payday.listeners;

import com.gmail.goosius.siegewar.events.SiegeEndEvent;
import com.gmail.goosius.siegewar.objects.Siege;
import com.palmergames.bukkit.towny.object.Town;
import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.payday.PaydayManager;
import me.none030.mortispayday.payday.PaydaySettings;
import me.none030.mortispayday.payday.RebuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class SiegeWarListener implements Listener {

    private final PaydayManager paydayManager;

    public SiegeWarListener(PaydayManager paydayManager) {
        this.paydayManager = paydayManager;
    }

    @EventHandler
    public void onSiegeWarEnd(SiegeEndEvent e) {
        Siege siege = e.getSiege();
        Town town = siege.getTown();
        PaydaySettings settings = paydayManager.getSettings();
        RebuildMode mode = settings.getWarRebuildMode();
        if (mode.equals(RebuildMode.NONE)) {
            return;
        }
        boolean win = siege.getSiegeWinner().name().equals(town.getName());
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
