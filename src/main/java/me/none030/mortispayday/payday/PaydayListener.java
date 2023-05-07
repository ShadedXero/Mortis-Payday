package me.none030.mortispayday.payday;

import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.object.Town;
import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.utils.MessageUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryDragEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class PaydayListener implements Listener {

    private final PaydayManager paydayManager;

    public PaydayListener(PaydayManager paydayManager) {
        this.paydayManager = paydayManager;
    }

    @EventHandler
    public void onTownCreate(NewTownEvent e) {
        Town town = e.getTown();
        PaydayData data = new PaydayData(town);
        if (data.getId() == null) {
            String paydayId = paydayManager.getSettings().getDefaultPaydayId();
            Payday payday = paydayManager.getPaydayById().get(paydayId);
            if (payday == null) {
                return;
            }
            data.create(payday.getId(), paydayManager.getEmptyResource(data), payday.getTime());
        }
    }

    @EventHandler
    public void onMenuDrag(InventoryDragEvent e) {
        Inventory inv = e.getInventory();
        if (inv.getHolder() instanceof PaydayMenu || inv.getHolder() instanceof PaydayMenu) {
            e.setCancelled(true);
            return;
        }
        if (!(inv.getHolder() instanceof PaydayMenu)) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void onMenuClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory inv = e.getInventory();
        Inventory clickedInv = e.getClickedInventory();
        if (inv.getHolder() instanceof PaydayMenu) {
            if (e.isShiftClick()) {
                e.setCancelled(true);
                return;
            }
        }
        if (clickedInv != null && clickedInv.getHolder() instanceof PaydayMenu) {
            if (e.isShiftClick()) {
                e.setCancelled(true);
                return;
            }
        }else {
            return;
        }
        e.setCancelled(true);
        if (e.getCursor() != null && !e.getCursor().getType().isAir()) {
            return;
        }
        Integer tries = paydayManager.getInMenuCoolDown().get(player.getUniqueId());
        if (tries != null && tries >= 3) {
            MessageUtils editor = new MessageUtils("&cPlease slow down");
            player.sendMessage(editor.color());
            return;
        }
        PaydayMenu menu = (PaydayMenu) clickedInv.getHolder();
        ItemStack cursor = menu.click(player, e.getRawSlot());
        player.setItemOnCursor(cursor);
        if (paydayManager.getInMenuCoolDown().get(player.getUniqueId()) == null) {
            paydayManager.getInMenuCoolDown().put(player.getUniqueId(), 1);
        }else {
            int number = paydayManager.getInMenuCoolDown().get(player.getUniqueId());
            paydayManager.getInMenuCoolDown().put(player.getUniqueId(), number + 1);
        }
    }
}
