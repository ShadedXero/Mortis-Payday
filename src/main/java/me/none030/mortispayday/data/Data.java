package me.none030.mortispayday.data;

import com.palmergames.bukkit.towny.object.Town;
import com.palmergames.bukkit.towny.object.metadata.StringDataField;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.io.BukkitObjectInputStream;
import org.bukkit.util.io.BukkitObjectOutputStream;
import org.jetbrains.annotations.NotNull;
import org.yaml.snakeyaml.external.biz.base64Coder.Base64Coder;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public abstract class Data {

    private final Town town;

    public Data(Town town) {
        this.town = town;
    }

    public void set(String key, String value) {
        StringDataField data = new StringDataField(key, value);
        if (value != null) {
            town.addMetaData(data);
        }else {
            town.removeMetaData(data);
        }
    }

    public String get(String key) {
        StringDataField data = town.getMetadata(key, StringDataField.class);
        if (data == null) {
            return null;
        }
        return data.getValue();
    }

    public String serialize(@NotNull Inventory inventory) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            BukkitObjectOutputStream dataOutput = new BukkitObjectOutputStream(outputStream);
            dataOutput.writeInt(inventory.getSize());
            for (int i = 0; i < inventory.getSize(); i++) {
                dataOutput.writeObject(inventory.getItem(i));
            }
            dataOutput.close();
            return Base64Coder.encodeLines(outputStream.toByteArray());
        } catch (Exception exp) {
            exp.printStackTrace();
        }
        return null;
    }

    public Inventory deserialize(@NotNull String data, InventoryHolder holder, String title) {
        try {
            ByteArrayInputStream inputStream = new ByteArrayInputStream(Base64Coder.decodeLines(data));
            BukkitObjectInputStream dataInput = new BukkitObjectInputStream(inputStream);
            Inventory inventory = Bukkit.getServer().createInventory(holder, dataInput.readInt(), Component.text(title));
            for (int i = 0; i < inventory.getSize(); i++) {
                inventory.setItem(i, (ItemStack) dataInput.readObject());
            }
            dataInput.close();
            return inventory;
        } catch (ClassNotFoundException | IOException exp) {
            exp.printStackTrace();
        }
        return null;
    }

    public Town getTown() {
        return town;
    }
}
