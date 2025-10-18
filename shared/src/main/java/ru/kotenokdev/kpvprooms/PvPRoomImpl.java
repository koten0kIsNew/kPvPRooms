package ru.kotenokdev.kpvprooms;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class PvPRoomImpl implements PvPRoom{

    @Getter private transient Set<Player> players;
    @Getter @Setter private transient int time;
    @Getter @Setter private transient boolean ending;
    @Getter private RoomEntry entry;
    @Getter private Selection selection;

    private PvPRoomImpl(Selection selection, RoomEntry entry){
        this.selection = selection;
        this.entry = entry;
        this.time = 0;
        this.players = new HashSet<>(2);
        this.ending = false;
    }

    public PvPRoomImpl(Selection selection, Location tmp){
        this(selection, new RoomEntryImpl(tmp));
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
    public boolean isClosed() {
        return entry.isClosed();
    }

    @Override
    public boolean addPlayer(Player player){
        if (isClosed()){
            return false;
        }
        players.add(player);
        return true;
    }

    @Override
    public void removePlayer(Player player) {
        players.remove(player);
    }

    @Override
    public boolean isFull(){
        return players.size() >= 2;
    }

    @Override
    public Map<String, Object> serialize() {
        return Map.of("selection", selection, "entry", entry);
    }

    public static PvPRoom deserialize(Map<String, Object> map) throws IllegalArgumentException{
        Object selectionPrimitive = map.get("selection");
        Object entryPrimitive = map.get("entry");
        if (!(selectionPrimitive instanceof Selection)){
            throw new IllegalArgumentException("Cannot convert primitive variant of selection to Selection! It means that pvp room saved uncorrectly!");
        }
        if (!(entryPrimitive instanceof RoomEntry)){
            throw new IllegalArgumentException("Cannot convert primitive variant of entry to RoomEntry! It means that pvp room saved uncorrectly!");
        }
        return new PvPRoomImpl((Selection) selectionPrimitive, (RoomEntry) entryPrimitive);
    }
}
