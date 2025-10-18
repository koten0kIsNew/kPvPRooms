package ru.kotenokdev.kpvprooms.config;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.kotenokdev.kpvprooms.KPvPRooms;
import ru.kotenokdev.kpvprooms.PvPRoom;
import ru.kotenokdev.kpvprooms.PvPRoomImpl;

import java.io.File;
import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

public class RoomsFile {

    private FileConfiguration config;

    public Set<PvPRoom> read(){
        Set<PvPRoom> result = new HashSet<>(8);
        for (String roomname : config.getKeys(false)){
            result.add(config.getSerializable(roomname, PvPRoomImpl.class));
        }
        return result;
    }

    public void write(Set<PvPRoom> rooms){
        reload(false);
        int i = 0;
        for (PvPRoom room : rooms){
            config.set("room"+i, room);
            i++;
        }
        try {
            config.save(new File(KPvPRooms.getAPI().getDataFolder(), "rooms.yml"));
        } catch (IOException e){
            e.printStackTrace();
        }
    }

    public RoomsFile reload(boolean mustExists){
        File file = new File(KPvPRooms.getAPI().getDataFolder(), "rooms.yml");
        if (file.exists() != mustExists){
            file.delete();
            try {
                file.createNewFile();
            } catch (IOException e){
                e.printStackTrace();
                return this;
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        return this;
    }
}
