package com.beanblockz.jumppads;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public enum Lang {

    PREFIX("&5[BBJumpPads]&f "),

    INVALID_COMMAND("&cInvalid command!"),
    NO_PERMISSION("&cYou do not have permission to do that!"),

    HELP("Command help:\n/"), // TODO help message
    RELOADED("Reloaded plugin!")

    ;

    private final Map<Lang, String> messageCache = new HashMap<>();

    private String message;
    private final String configName;

    Lang(String message) {
        this.message = message;
        this.configName = name().toLowerCase().replace("_", "-");
    }

    private String translateColors(String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    @Override
    public String toString() {
        if (messageCache.containsKey(this)) {
            return messageCache.get(this);
        }
        String message = translateColors(PREFIX.message + this.message);
        messageCache.put(this, message);
        return message;
    }

    public void send(CommandSender sender) {
        sender.sendMessage(toString());
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getConfigName() {
        return configName;
    }
}
