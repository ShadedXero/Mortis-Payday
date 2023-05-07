package me.none030.mortispayday.items;

import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

public class ItemManager {

    private final HashMap<String, ItemStack> itemById;

    public ItemManager() {
        this.itemById = new HashMap<>();
    }

    public ItemStack getItem(String id) {
       ItemStack item = itemById.get(id);
       if (item == null) {
           return null;
       }
       return item.clone();
    }

    public void addItem(String id, ItemStack item) {
        itemById.put(id, item);
    }

    public HashMap<String, ItemStack> getItemById() {
        return itemById;
    }
}
