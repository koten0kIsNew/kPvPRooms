package ru.kotenokdev.kpvprooms;

import org.bukkit.entity.Player;

import java.util.Set;

public interface PvPRoom extends Selection {

    public Set<Player> getPlayers();

    public RoomEntry getEntry();

    public int getTime();

    public void setTime(int time);

    public boolean isClosed();

    public boolean addPlayer(Player player);

    public void removePlayer(Player player);

    public boolean isEnding();

    public void setEnding(boolean ending);

    public boolean isFull();

    public Selection getSelection();
}
