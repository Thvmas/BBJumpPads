package com.beanblockz.jumppads.command;

import com.beanblockz.jumppads.JumpPads;
import com.beanblockz.jumppads.Lang;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import java.util.HashMap;
import java.util.Map;

public class CommandBus implements CommandExecutor {

    private final Map<String, SubCommand> commands = new HashMap<>();

    public CommandBus(JumpPads plugin) {
        register(
                new VersionCommand(plugin),
                new ReloadCommand(plugin)
        );
    }

    private void register(SubCommand... commands) {
        for (SubCommand command : commands) {
            for (String name : command.getNames()) {
                this.commands.put(name.toLowerCase(), command);
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] args) {
        if (!command.getName().equalsIgnoreCase("bbjumppads")) {
            return false;
        }
        if (args.length == 0) {
            Lang.HELP.send(sender);
            return true;
        }
        String subCommand = args[0].toLowerCase();
        if (!commands.containsKey(subCommand)) {
            Lang.INVALID_COMMAND.send(sender);
            return true;
        }
        SubCommand cmd = commands.get(subCommand);
        if (!sender.hasPermission(cmd.getPermission())) {
            Lang.NO_PERMISSION.send(sender);
            return true;
        }
        String[] newArgs = new String[args.length - 1];
        System.arraycopy(args, 1, newArgs, 0, args.length - 1);
        cmd.execute(sender, newArgs);
        return true;
    }
}
