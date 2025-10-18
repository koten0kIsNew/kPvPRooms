package ru.kotenokdev.kpvprooms.command;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public interface SubCommand {

    public void cmd(Player sender, String[] args);

    public void asConsole(CommandSender sender, String[] args);
}
