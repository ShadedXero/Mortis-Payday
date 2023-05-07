package me.none030.mortispayday.payday;

import me.none030.mortispayday.data.PaydayData;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.time.LocalDateTime;
import java.util.List;

public class Payday {

    private final String id;
    private final long seconds;
    private final List<ItemStack> resources;

    public Payday(String id, String time, List<ItemStack> resources) {
        this.id = id;
        this.seconds = getSeconds(time);
        this.resources = resources;
    }

    public void generateResources(PaydayData data, PaydayManager paydayManager) {
        Inventory inv = data.deserialize(data.getResources(), null, paydayManager.getMessage("MENU_TITLE"));
        if (inv.firstEmpty() == -1) {
            return;
        }
        for (ItemStack item : resources) {
            inv.addItem(item.clone());
            if (inv.firstEmpty() == -1) {
                break;
            }
        }
        data.setResources(data.serialize(inv));
    }

    public void generateResources(PaydayData data, PaydayManager paydayManager, long percentage) {
        Inventory inv = data.deserialize(data.getResources(), null, paydayManager.getMessage("MENU_TITLE"));
        if (inv.firstEmpty() == -1) {
            return;
        }
        for (ItemStack item : resources) {
            ItemStack cloned = item.clone();
            cloned.setAmount(1);
            int finalAmount = (int) ((item.getAmount() * percentage) / 100);
            if (finalAmount == 0) {
                continue;
            }
            for (int i = 0; i < finalAmount; i++) {
                inv.addItem(cloned);
                if (inv.firstEmpty() == -1) {
                    break;
                }
            }
        }
        data.setResources(data.serialize(inv));
    }

    public LocalDateTime getTime() {
        return LocalDateTime.now().plusSeconds(seconds);
    }

    public long getSeconds(String rawTime) {
        if (rawTime.contains("s")) {
            return Long.parseLong(rawTime.replace("s", ""));
        }
        if (rawTime.contains("m")) {
            long time = Long.parseLong(rawTime.replace("m", ""));
            return time * 60;
        }
        if (rawTime.contains("h")) {
            long time = Long.parseLong(rawTime.replace("h", ""));
            return time * 60 * 60;
        }
        return 0;
    }
    public String getId() {
        return id;
    }

    public long getSeconds() {
        return seconds;
    }

    public List<ItemStack> getResources() {
        return resources;
    }

}
