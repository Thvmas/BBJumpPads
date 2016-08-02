package com.beanblockz.jumppads.command;

import org.bukkit.command.CommandSender;

public abstract class SubCommand {

    private final String permission;
    private final String[] names;

    protected SubCommand(String permission, String... names) {
        this.permission = permission;
        this.names = names;
    }

    protected abstract void execute(CommandSender sender, String[] args);

    public String getPermission() {
        return "bbjumppads." + permission;
    }

    public String[] getNames() {
        return names;
    }
}