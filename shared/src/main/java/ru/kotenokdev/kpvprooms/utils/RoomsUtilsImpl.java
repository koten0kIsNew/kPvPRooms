package ru.kotenokdev.kpvprooms.utils;

import org.bukkit.Location;
import ru.kotenokdev.kpvprooms.PvPRoom;

public class RoomsUtilsImpl implements RoomsUtils{

    @Override
    public boolean inRoom(PvPRoom room, Location location) {
        Location min = room.getFirstPoint();
        if (!min.getWorld().equals(location.getWorld())) return false;
        Location max = room.getSecondPoint();
        int x = location.getBlockX();
        int y = location.getBlockY();
        int z = location.getBlockZ();
        return (x >= min.getBlockX() && x <= max.getBlockX()) &&
                (y >= min.getBlockY() && y <= max.getBlockY()) && (z >= min.getBlockZ() && z <= max.getBlockZ());
    }
}
