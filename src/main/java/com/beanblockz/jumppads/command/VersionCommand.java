package com.beanblockz.jumppads.command;

import com.beanblockz.jumppads.Lang;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class VersionCommand extends SubCommand {

    private final String message;

    protected VersionCommand(Plugin plugin) {
        super("version", "version", "v");
        String prefix = Lang.PREFIX.getMessage();
        prefix = ChatColor.translateAlternateColorCodes('&', prefix);
        this.message = prefix + plugin.getDescription().getVersion();
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        sender.sendMessage(message);
    }
}
