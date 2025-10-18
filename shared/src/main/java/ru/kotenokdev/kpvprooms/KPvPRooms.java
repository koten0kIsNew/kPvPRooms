package ru.kotenokdev.kpvprooms;

import lombok.Getter;
import lombok.Setter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.plugin.java.JavaPlugin;
import ru.kotenokdev.kpvprooms.command.CreateCommand;
import ru.kotenokdev.kpvprooms.command.PvPRoomCommand;
import ru.kotenokdev.kpvprooms.command.ReloadCommand;
import ru.kotenokdev.kpvprooms.config.Config;
import ru.kotenokdev.kpvprooms.config.RoomsFile;
import ru.kotenokdev.kpvprooms.listener.RoomsListener;
import ru.kotenokdev.kpvprooms.listener.RoomsListenerImpl;
import ru.kotenokdev.kpvprooms.manager.RoomsScheduler;
import ru.kotenokdev.kpvprooms.manager.RoomsSchedulerImpl;
import ru.kotenokdev.kpvprooms.utils.*;

import java.util.Map;
import java.util.Set;

public class KPvPRooms extends JavaPlugin implements PvPRoomsAPI {

    @Getter private static PvPRoomsAPI API;
    @Getter @Setter private ObjectValidator objectValidator;
    @Getter @Setter private ConfigUtils configUtils;
    @Getter @Setter private Config conf;
    @Getter @Setter private Set<PvPRoom> rooms;
    @Getter @Setter private RoomsListener roomListener;
    @Getter @Setter private RoomsScheduler roomScheduler;
    @Getter @Setter private RoomsUtils roomsUtils;
    @Getter @Setter private WorldEditUtils worldEditUtils;

    public void onEnable(){
        API = this;
        ConfigurationSerialization.registerClass(SelectionImpl.class);
        ConfigurationSerialization.registerClass(RoomEntryImpl.class);
        ConfigurationSerialization.registerClass(PvPRoomImpl.class);
        conf = new Config();
        objectValidator = new ObjectValidatorImpl();
        rooms = createRoomsFile().reload(true).read();
        configUtils = new ConfigUtilsImpl();
        roomsUtils = new RoomsUtilsImpl();
        if (Bukkit.getPluginManager().isPluginEnabled("WorldEdit")) worldEditUtils = new WorldEditUtilsImpl();
        roomListener = new RoomsListenerImpl(this);
        roomScheduler = new RoomsSchedulerImpl();
        roomScheduler.run(this);
        new PvPRoomCommand(this, Map.of("create", new CreateCommand(this), "reload", new ReloadCommand(this)));
        new Listener(this);
    }

    @Override
    public JavaPlugin asJavaPlugin() {
        return this;
    }

    @Override
    public Location getLobby() {
        return conf.getLobby();
    }

    public final RoomsFile createRoomsFile(){
        return new RoomsFile();
    }
}
