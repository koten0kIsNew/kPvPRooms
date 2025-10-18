package ru.kotenokdev.kpvprooms.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.*;
import ru.kotenokdev.kpvprooms.config.Config;
import ru.kotenokdev.kpvprooms.utils.WorldEditUtils;

public class CreateCommand implements SubCommand{

    private KPvPRooms api;
    private Config conf;

    public CreateCommand(KPvPRooms api){
        this.api = api;
        conf = api.getConf();
    }

    @Override
    public void cmd(Player sender, String[] args) {
        WorldEditUtils weutils = api.getWorldEditUtils();
        Selection selection;
        if (weutils != null)
            selection = weutils.getSelection(sender);
        else {
            selection = new SelectionImpl(sender.getLocation(), sender.getLocation());
        }
        if (selection == null){
            sender.sendMessage(conf.getNoSelection());
            return;
        }
        PvPRoom room = new PvPRoomImpl(selection, sender.getLocation());
        api.getRooms().add(room);
        api.createRoomsFile().write(api.getRooms());
        sender.sendMessage(conf.getCreateSuccess());
        sender.sendMessage(conf.getChangeInConfig());
    }

    @Override
    public void asConsole(CommandSender sender, String[] args) {
        sender.sendMessage(conf.getOnlyPlayers());
    }
}
