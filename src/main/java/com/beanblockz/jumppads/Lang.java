package com.beanblockz.jumppads;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public enum Lang {

    // format
    PREFIX("&5[BBJumpPads]&f "),

    // verbose
    INVALID_COMMAND("&cInvalid command!"),
    NO_PERMISSION("&cYou do not have permission to do that!"),

    // command
    HELP("Command help:" +
            "\nAliases: /jumppads, /jp" +
            "\n/jp - shows this message" +
            "\n/jp version - shows the current plugin version" +
            "\n/jp reload - reloads the messages config"),
    RELOADED("Reloaded plugin!"),

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
