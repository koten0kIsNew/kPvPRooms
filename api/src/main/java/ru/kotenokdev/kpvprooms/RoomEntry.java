package ru.kotenokdev.kpvprooms;

import org.bukkit.Material;

public interface RoomEntry extends Selection {

    public Material getMaterial();

    public void open();

    public void close();

    public boolean isClosed();

    public Selection getSelection();
}
