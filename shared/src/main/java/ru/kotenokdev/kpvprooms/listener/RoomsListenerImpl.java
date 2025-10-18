package ru.kotenokdev.kpvprooms.listener;

import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.KPvPRooms;
import ru.kotenokdev.kpvprooms.PvPRoom;
import ru.kotenokdev.kpvprooms.config.Config;
import ru.kotenokdev.kpvprooms.utils.ConfigUtils;

public class RoomsListenerImpl implements RoomsListener{

    private Config config;
    private ConfigUtils configUtils;
    private KPvPRooms api;

    public RoomsListenerImpl(KPvPRooms api){
        this.config = api.getConf();
        this.configUtils = api.getConfigUtils();
        this.api = api;
    }

    @Override
    public boolean playerJoin(PvPRoom room, Player player) {
        if (!room.addPlayer(player)){
            configUtils.sendMessage(player, config.getRoomIsFullMessage());
            return false;
        }
        configUtils.sendMessage(player, config.getRoomJoinedMessage());
        return true;
    }

    @Override
    public boolean playerQuit(PvPRoom room, Player player) {
        room.removePlayer(player);
        configUtils.sendMessage(player, config.getRoomLeftMessage());
        if (room.isEnding()) roomOpen(room);
        else if (room.isClosed()) pvpEnd(room);
        return false;
    }

    @Override
    public void pvpStart(PvPRoom room) {
        for (Player p : room.getPlayers()){
            configUtils.sendMessage(p, config.getPvpStartedMessage());
        }
        room.getEntry().close();
        roomTick(room, config.getTimeToPvP());
    }

    @Override
    public void pvpEnd(PvPRoom room) {
        for (Player p : room.getPlayers()){
            configUtils.sendMessage(p, config.getPvpStoppedMessage());
        }
        room.setEnding(true);
        roomTick(room, config.getTimeToTakeItems());
    }

    @Override
    public void roomOpen(PvPRoom room) {
        room.setTime(-1);
        room.setEnding(false);
        room.getEntry().open();
    }

    @Override
    public void roomTick(PvPRoom room, int newTime) {
        room.setTime(newTime);
        if (room.isEnding()){
            if (config.getNotifyWhileTakingItems().contains(newTime)){
                notifyPlayers(room, config.getRemainTimeMessage(), newTime);
            }
            if (newTime < 1){
                roomOpen(room);
                for (Player p : room.getPlayers()) p.teleport(api.getLobby());
            }
            return;
        }
        if (config.getNotifyWhilePvP().contains(newTime)){
            notifyPlayers(room, config.getTimeRemainMessage(), newTime);
        }
        if (newTime < 1){
            for (Player p : room.getPlayers()){
                configUtils.sendMessage(p, config.getKickedOutMessage());
                p.teleport(api.getLobby());
            }
            roomOpen(room);
        }
    }

    private void notifyPlayers(PvPRoom room, String message, int time){
        for (Player p : room.getPlayers()){
            configUtils.sendMessage(p, message, "time", time+"");
        }
    }
}
