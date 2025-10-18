package ru.kotenokdev.kpvprooms.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.KPvPRooms;
import ru.kotenokdev.kpvprooms.config.Config;

public class ReloadCommand implements SubCommand{

    private KPvPRooms api;
    private Config conf;

    public ReloadCommand(KPvPRooms api){
        this.api = api;
        conf = api.getConf();
    }

    @Override
    public void cmd(Player sender, String[] args) {
        asConsole(sender, args);
    }

    @Override
    public void asConsole(CommandSender sender, String[] args) {
        api.setConf(new Config());
        api.setRooms(api.createRoomsFile().reload(true).read());
        sender.sendMessage(conf.getReloadSuccess());
    }
}
