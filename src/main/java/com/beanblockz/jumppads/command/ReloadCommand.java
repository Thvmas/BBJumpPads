package com.beanblockz.jumppads.command;

import com.beanblockz.jumppads.JumpPads;
import com.beanblockz.jumppads.Lang;
import org.bukkit.command.CommandSender;

public class ReloadCommand extends SubCommand {

    private final JumpPads jumpPads;

    protected ReloadCommand(JumpPads jumpPads) {
        super("reload", "reload", "r");
        this.jumpPads = jumpPads;
    }

    @Override
    protected void execute(CommandSender sender, String[] args) {
        jumpPads.loadMessages();
        Lang.RELOADED.send(sender);
    }
}
