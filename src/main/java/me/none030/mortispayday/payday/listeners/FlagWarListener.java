package me.none030.mortispayday.payday.listeners;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Town;
import io.github.townyadvanced.flagwar.events.CellAttackCanceledEvent;
import io.github.townyadvanced.flagwar.objects.CellUnderAttack;
import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.payday.PaydayManager;
import me.none030.mortispayday.payday.PaydaySettings;
import me.none030.mortispayday.payday.RebuildMode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class FlagWarListener implements Listener {

    private final PaydayManager paydayManager;

    public FlagWarListener(PaydayManager paydayManager) {
        this.paydayManager = paydayManager;
    }

    @EventHandler
    public void onFlagWarCanceled(CellAttackCanceledEvent e) {
        CellUnderAttack attack = e.getCell();
        Town town = TownyAPI.getInstance().getTown(attack.getFlagBaseBlock().getLocation());
        PaydaySettings settings = paydayManager.getSettings();
        RebuildMode mode = settings.getWarRebuildMode();
        PaydayData data = new PaydayData(town);
        if (mode.equals(RebuildMode.NONE) || mode.equals(RebuildMode.BOTH)) {
            data.setRebuildTimer(settings.getRebuildTime());
        }
    }
}
