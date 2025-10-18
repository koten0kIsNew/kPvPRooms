package ru.kotenokdev.kpvprooms;

import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerializable;

public interface Selection extends ConfigurationSerializable {

    public Location getFirstPoint();

    public Location getSecondPoint();
}
