package ru.kotenokdev.kpvprooms;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerQuitEvent;
import ru.kotenokdev.kpvprooms.listener.RoomsListener;

import java.util.Set;

public class Listener implements org.bukkit.event.Listener {

    private RoomsListener listener;
    private Set<PvPRoom> rooms;

    public Listener(KPvPRooms api){
        Bukkit.getPluginManager().registerEvents(this, api);
        rooms = api.getRooms();
        listener = api.getRoomListener();
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent event){
        Player player = event.getPlayer();
        for (PvPRoom room : rooms){
            if (room.getPlayers().contains(player)){
                listener.playerQuit(room, player);
                break;
            }
        }
    }
}
