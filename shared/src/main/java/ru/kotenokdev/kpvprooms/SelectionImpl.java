package ru.kotenokdev.kpvprooms;

import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;

public class SelectionImpl implements Selection{

    private Location pos1;
    private Location pos2;

    public SelectionImpl(Location pos1, Location pos2){
        this.pos1 = pos1;
        this.pos2 = pos2;
    }

    public SelectionImpl(World world, int x1, int y1, int z1, int x2, int y2, int z2){
        this.pos1 = new Location(world, x1, y1, z1);
        this.pos2 = new Location(world, x2, y2, z2);
    }

    public Location getFirstPoint(){
        return pos1;
    }

    public Location getSecondPoint(){
        return pos2;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of("pos1", pos1, "pos2", pos2);
    }

    public static Selection deserialize(Map<String, Object> map) throws IllegalArgumentException{
        Object pos1Primitive = map.get("pos1");
        Object pos2Primitive = map.get("pos2");
        if (!(pos1Primitive instanceof Location)){
            throw new IllegalArgumentException("Cannot convert primitive variant of pos1 to Location! It means that selection saved uncorrectly!");
        }
        if (!(pos2Primitive instanceof Location)){
            throw new IllegalArgumentException("Cannot convert primitive variant of pos1 to Location! It means that selection saved uncorrectly!");
        }
        Selection result = new SelectionImpl((Location) pos1Primitive, (Location) pos2Primitive);
        String reasonToFail = KPvPRooms.getAPI().getObjectValidator().validate(result, false);
        if (reasonToFail != null){
            throw new IllegalArgumentException("Cannot complete creation of selection: "+reasonToFail);
        }
        return result;
    }
}
