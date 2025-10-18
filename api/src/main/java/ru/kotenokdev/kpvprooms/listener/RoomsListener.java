package ru.kotenokdev.kpvprooms.listener;

import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.PvPRoom;

public interface RoomsListener {

    public boolean playerJoin(PvPRoom room, Player player);

    public boolean playerQuit(PvPRoom room, Player player);

    public void pvpStart(PvPRoom room);

    public void pvpEnd(PvPRoom room);

    public void roomOpen(PvPRoom room);

    public void roomTick(PvPRoom room, int newTime);
}
