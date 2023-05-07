package me.none030.mortispayday.utils;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.legacy.LegacyComponentSerializer;
import org.bukkit.ChatColor;

import java.util.Objects;

public class MessageUtils {

    private String message;

    public MessageUtils(String message) {
        this.message = Objects.requireNonNullElse(message, "");
    }

    public MessageUtils(Component message) {
        this.message = LegacyComponentSerializer.legacyAmpersand().serialize(message);
    }

    public String color() {
        setMessage(ChatColor.translateAlternateColorCodes('&', message));
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    public String replace(String value, String replacement) {
        setMessage(message.replace(value, replacement));
        return message.replace(value, replacement);
    }

    private void setMessage(String message) {
        this.message = message;
    }

    public String getMessage() {
        return message;
    }
}
