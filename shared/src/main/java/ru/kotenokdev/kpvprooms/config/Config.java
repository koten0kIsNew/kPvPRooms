package ru.kotenokdev.kpvprooms.config;

import lombok.Getter;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import ru.kotenokdev.kpvprooms.KPvPRooms;
import ru.kotenokdev.kpvprooms.PvPRoomsAPI;

import java.io.File;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Config {

    @Getter private int timeToPvP;
    @Getter private int timeToTakeItems;
    @Getter private Set<Integer> notifyWhilePvP;
    @Getter private Set<Integer> notifyWhileTakingItems;
    @Getter private Location lobby;

    @Getter private String roomIsFullMessage;
    @Getter private String roomJoinedMessage;
    @Getter private String roomLeftMessage;
    @Getter private String pvpStartedMessage;
    @Getter private String pvpStoppedMessage;
    @Getter private String timeRemainMessage;
    @Getter private String remainTimeMessage;
    @Getter private String kickedOutMessage;
    @Getter private String prefix;
    @Getter private String onlyPlayers;
    @Getter private String usage;
    @Getter private String noPerms;
    @Getter private String reloadSuccess;
    @Getter private String noSelection;
    @Getter private String createSuccess;
    @Getter private String changeInConfig;

    public Config(){
        PvPRoomsAPI api = KPvPRooms.getAPI();
        File file = new File(api.getDataFolder(), "config.yml");
        if (!file.exists()){
            api.saveResource("config.yml", false);
        }
        FileConfiguration config = YamlConfiguration.loadConfiguration(file);

        timeToPvP = config.getInt("time-to-pvp", 300);
        timeToTakeItems = config.getInt("time-to-take-items", 15);

        ConfigurationSection lobbySection = config.getConfigurationSection("lobby");
        if (lobbySection != null){
            lobby = new Location(Bukkit.getWorld(lobbySection.getString("world")), lobbySection.getInt("x", 0), lobbySection.getInt("y", 64)
                    , lobbySection.getInt("z", 0));
        }

        List<Integer> whilePvP = config.getIntegerList("notify-while-pvp");
        if (whilePvP != null){
            notifyWhilePvP = new HashSet<>(whilePvP);
        }
        else notifyWhilePvP = new HashSet<>();

        List<Integer> whileTakingItems = config.getIntegerList("notify-while-taking-items");
        if (whileTakingItems != null){
            notifyWhileTakingItems = new HashSet<>(whileTakingItems);
        }
        else notifyWhileTakingItems = new HashSet<>();

        ConfigurationSection messagesSection = config.getConfigurationSection("messages");
        if (messagesSection != null){
            String p = "${prefix}";
            prefix = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("prefix", ""));
            roomIsFullMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("room-is-full", "").replace(p, prefix));
            roomJoinedMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("player-joined", "").replace(p, prefix));
            roomLeftMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("player-left", "").replace(p, prefix));
            pvpStartedMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("pvp-started", "").replace(p, prefix));
            pvpStoppedMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("pvp-stopped", "").replace(p, prefix));
            timeRemainMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("time-remain", "").replace(p, prefix));
            remainTimeMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("remain-time", "").replace(p, prefix));
            kickedOutMessage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("kicked-out", "").replace(p, prefix));
            onlyPlayers = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("only-players", "").replace(p, prefix));
            usage = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("usage", "").replace(p, prefix));
            noPerms = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("no-perms", "").replace(p, prefix));
            reloadSuccess = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("reload-success", "").replace(p, prefix));
            noSelection = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("no-selection", "").replace(p, prefix));
            createSuccess = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("create-success", "").replace(p, prefix));
            changeInConfig = ChatColor.translateAlternateColorCodes('&', messagesSection.getString("change-in-config", "").replace(p, prefix));
        }
    }
}
