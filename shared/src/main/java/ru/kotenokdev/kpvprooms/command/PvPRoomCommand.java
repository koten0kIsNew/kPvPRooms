package ru.kotenokdev.kpvprooms.command;

import org.bukkit.command.*;
import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.KPvPRooms;
import ru.kotenokdev.kpvprooms.config.Config;

import java.util.List;
import java.util.Map;

public class PvPRoomCommand implements CommandExecutor, TabCompleter {

    private Config conf;
    private Map<String, SubCommand> sub;

    public PvPRoomCommand(KPvPRooms api, Map<String, SubCommand> sub){
        this.conf = api.getConf();
        this.sub = sub;
        PluginCommand cmd = api.getCommand("pvprooms");
        cmd.setExecutor(this);
        cmd.setTabCompleter(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!sender.hasPermission("kpvprooms.admin")){
            sender.sendMessage(conf.getNoPerms());
            return false;
        }
        if (args.length < 1){
            sender.sendMessage(conf.getUsage());
            return false;
        }
        SubCommand command = sub.get(args[0]);
        if (command == null){
            sender.sendMessage(conf.getUsage());
            return false;
        }
        if (sender instanceof Player) command.cmd((Player) sender, args);
        else command.asConsole(sender, args);
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender.hasPermission("kpvprooms.admin") && args.length <= 1)
            return List.of("create", "reload");
        return null;
    }
}
