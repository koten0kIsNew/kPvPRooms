package ru.kotenokdev.kpvprooms;

import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;
import ru.kotenokdev.kpvprooms.listener.RoomsListener;
import ru.kotenokdev.kpvprooms.manager.RoomsScheduler;
import ru.kotenokdev.kpvprooms.utils.ConfigUtils;
import ru.kotenokdev.kpvprooms.utils.ObjectValidator;
import ru.kotenokdev.kpvprooms.utils.RoomsUtils;
import ru.kotenokdev.kpvprooms.utils.WorldEditUtils;

import java.io.File;
import java.util.Set;

public interface PvPRoomsAPI {

    public ObjectValidator getObjectValidator();

    public File getDataFolder();

    public void saveResource(String name, boolean doReplace);

    public ConfigUtils getConfigUtils();

    public RoomsUtils getRoomsUtils();

    public Set<PvPRoom> getRooms();

    public JavaPlugin asJavaPlugin();

    public RoomsListener getRoomListener();

    public RoomsScheduler getRoomScheduler();

    public Location getLobby();

    public WorldEditUtils getWorldEditUtils();
}
