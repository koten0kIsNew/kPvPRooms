package ru.kotenokdev.kpvprooms;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;

import java.util.Map;

import lombok.Getter;

public class RoomEntryImpl implements RoomEntry{

    @Getter private Selection selection;
    @Getter private Material material;
    private transient World world;
    private transient boolean isClosed;

    private RoomEntryImpl(Selection selection, Material material){
        this.selection = selection;
        this.material = material;
        this.world = selection.getFirstPoint().getWorld();
        Location pos2 = selection.getSecondPoint();
        pos2.setX(pos2.getX() + 1);
        pos2.setY(pos2.getY() + 1);
        pos2.setZ(pos2.getZ() + 1);
        this.open();
    }

    public RoomEntryImpl(Location tmpSelection){
        this(new SelectionImpl(tmpSelection, tmpSelection), Material.BARRIER);
    }

    @Override
    public Location getFirstPoint() {
        return getSelection().getFirstPoint();
    }

    @Override
    public Location getSecondPoint() {
        return getSelection().getSecondPoint();
    }

    @Override
    public final void open() {
        fill(Material.AIR);
        isClosed = false;
    }

    @Override
    public void close() {
        fill(material);
        isClosed = true;
    }

    @Override
    public boolean isClosed() {
        return isClosed;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of("selection", selection, "block", material.toString().toLowerCase());
    }

    public static RoomEntry deserialize(Map<String, Object> map) throws IllegalArgumentException{
        Object selectionPrimitive = map.get("selection");
        Object materialPrimitive = map.get("block");
        if (!(selectionPrimitive instanceof Selection)){
            throw new IllegalArgumentException("Cannot convert primitive variant of selection to Selection! It means that pvp room saved uncorrectly!");
        }
        if (!(materialPrimitive instanceof String)){
            throw new IllegalArgumentException("Cannot convert primitive variant of material to String! It means that pvp room saved uncorrectly!");
        }
        Material mat;
        try {
            mat = Material.valueOf(((String) materialPrimitive).toUpperCase());
        }catch (Exception e){
            throw new IllegalArgumentException("Cannot convert String marked as Material to Material! It means that pvp room saved uncorrectly!");
        }
        RoomEntry entry = new RoomEntryImpl((Selection) selectionPrimitive, mat);
        String reasonToFail = KPvPRooms.getAPI().getObjectValidator().validate(entry);
        if (reasonToFail != null){
            throw new IllegalArgumentException("Error while validating object: "+reasonToFail);
        }
        return entry;
    }

    private void fill(Material material){
        int minX = getFirstPoint().getBlockX();
        int minY = getFirstPoint().getBlockY();
        int minZ = getFirstPoint().getBlockZ();
        int maxX = getSecondPoint().getBlockX();
        int maxY = getSecondPoint().getBlockY();
        int maxZ = getSecondPoint().getBlockZ();
        for (int x = minX; x < maxX;x++)
            for (int y = minY; y < maxY;y++)
                for (int z = minZ; z < maxZ;z++)
                    new Location(world, x, y, z).getBlock().setType(material);
    }
}