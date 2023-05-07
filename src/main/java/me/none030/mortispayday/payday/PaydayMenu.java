package me.none030.mortispayday.payday;

import me.none030.mortispayday.data.PaydayData;
import me.none030.mortispayday.utils.ItemEditor;
import me.none030.mortispayday.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

public class PaydayMenu implements InventoryHolder {

    private final PaydayManager paydayManager;
    private final PaydayData data;
    private final Inventory menu;

    public PaydayMenu(PaydayManager paydayManager, PaydayData data) {
        this.paydayManager = paydayManager;
        this.data = data;
        String resources = data.getResources();
        if (resources != null) {
            this.menu = data.deserialize(data.getResources(), this, paydayManager.getMessage("MENU_TITLE").replace("%town_name%", data.getTown().getName()));
        }else {
            String menuData = paydayManager.getEmptyResource(data);
            data.setResources(menuData);
            this.menu = data.deserialize(menuData, this, paydayManager.getMessage("MENU_TITLE").replace("%town_name%", data.getTown().getName()));
        }
    }

    public void update() {
        Inventory inv = data.deserialize(data.getResources(), this, paydayManager.getMessage("MENU_TITLE").replace("%town_name%", data.getTown().getName()));
        for (int i = 0; i < menu.getSize(); i++) {
            menu.setItem(i, inv.getItem(i));
        }
    }

    public ItemStack click(Player player, int slot) {
        Inventory inv = data.deserialize(data.getResources(), this, paydayManager.getMessage("MENU_TITLE").replace("%town_name%", data.getTown().getName()));
        ItemStack item = inv.getItem(slot);
        if (item == null || item.getType().isAir()) {
            return null;
        }
        ItemEditor editor = new ItemEditor(item);
        String command = editor.getPersistentData("MortisPayday");
        inv.setItem(slot, new ItemStack(Material.AIR));
        if (command != null) {
            MessageUtils utils = new MessageUtils(command);
            utils.replace("%player_name%", player.getName());
            for (int i = 0; i < item.getAmount(); i++) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), utils.getMessage());
            }
        }
        data.setResources(data.serialize(inv));
        update();
        if (command != null) {
            return null;
        }else {
            return item;
        }
    }

    public void open(Player player) {
        player.openInventory(menu);
    }

    public void close(Player player) {
        player.closeInventory();
    }

    public PaydayManager getPaydayManager() {
        return paydayManager;
    }

    public PaydayData getData() {
        return data;
    }

    @Override
    public @NotNull Inventory getInventory() {
        return menu;
    }
}
