package ru.kotenokdev.kpvprooms.utils;

import org.bukkit.Location;
import org.bukkit.Material;
import ru.kotenokdev.kpvprooms.RoomEntry;
import ru.kotenokdev.kpvprooms.Selection;

public class ObjectValidatorImpl implements ObjectValidator{

    @Override
    public String validate(RoomEntry entry) {
        Material material = entry.getMaterial();
        if (material.isAir() || !material.isBlock()){
            return "Material of RoomEntry must be a block!";
        }
        return null;
    }

    @Override
    public String validate(Selection selection, boolean nothing) {
        Location pos1 = selection.getFirstPoint();
        Location pos2 = selection.getSecondPoint();
        if (!pos1.getWorld().equals(pos2.getWorld())){
            return "Selection in PvPRoom must be in one world";
        }
        int firstX = pos1.getBlockX();
        int firstY = pos1.getBlockY();
        int firstZ = pos1.getBlockZ();
        int secondX = pos2.getBlockX();
        int secondY = pos2.getBlockY();
        int secondZ = pos2.getBlockZ();
        int minX = Math.min(firstX, secondX);
        int minY = Math.min(firstY, secondY);
        int minZ = Math.min(firstZ, secondZ);
        int maxX = Math.max(firstX, secondX);
        int maxY = Math.max(firstY, secondY);
        int maxZ = Math.max(firstZ, secondZ);
        pos1.setX(minX);
        pos1.setY(minY);
        pos1.setZ(minZ);
        pos2.setX(maxX);
        pos2.setY(maxY);
        pos2.setZ(maxZ);
        return null;
    }
}
