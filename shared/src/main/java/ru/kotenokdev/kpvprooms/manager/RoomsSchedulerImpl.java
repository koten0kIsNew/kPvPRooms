package ru.kotenokdev.kpvprooms.manager;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.PvPRoom;
import ru.kotenokdev.kpvprooms.PvPRoomsAPI;
import ru.kotenokdev.kpvprooms.listener.RoomsListener;
import ru.kotenokdev.kpvprooms.utils.RoomsUtils;

import java.util.Set;

public class RoomsSchedulerImpl implements RoomsScheduler{

    @Override
    public void run(PvPRoomsAPI api) {
        Set<PvPRoom> rooms = api.getRooms();
        RoomsListener listener = api.getRoomListener();
        RoomsUtils roomsUtils = api.getRoomsUtils();
        Bukkit.getScheduler().runTaskTimer(api.asJavaPlugin(), () -> {
            for (PvPRoom room : rooms){
                for (Player p : Bukkit.getOnlinePlayers()){
                    boolean havePlayer = room.getPlayers().contains(p);
                    if (roomsUtils.inRoom(room, p.getLocation())){
                        if (!havePlayer && !p.isDead() && !listener.playerJoin(room, p)){
                            p.teleport(api.getLobby());
                            continue;
                        }
                        if (p.isDead() && havePlayer) listener.playerQuit(room, p);
                        continue;
                    }
                    if (havePlayer){
                        listener.playerQuit(room, p);
                    }
                }


                if (room.isFull() && !room.isClosed()) listener.pvpStart(room);
                int time = room.getTime();
                if (time > -1) {
                    time--;
                    listener.roomTick(room, time);
                }
            }
        }, 20L, 20L);
    }
}
