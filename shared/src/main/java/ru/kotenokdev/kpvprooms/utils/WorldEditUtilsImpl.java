package ru.kotenokdev.kpvprooms.utils;

import com.sk89q.worldedit.IncompleteRegionException;
import com.sk89q.worldedit.LocalSession;
import com.sk89q.worldedit.WorldEdit;
import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldedit.math.BlockVector3;
import com.sk89q.worldedit.regions.Region;
import org.bukkit.entity.Player;
import ru.kotenokdev.kpvprooms.Selection;
import ru.kotenokdev.kpvprooms.SelectionImpl;

public class WorldEditUtilsImpl implements WorldEditUtils{

    @Override
    public Selection getSelection(Player player) {
        com.sk89q.worldedit.entity.Player adapted = BukkitAdapter.adapt(player);
        LocalSession session = WorldEdit.getInstance().getSessionManager().getIfPresent(adapted);
        if (session == null) return null;
        Region region;
        try {
            region = session.getSelection(session.getSelectionWorld());
        } catch (IncompleteRegionException e) {
            return null;
        }
        BlockVector3 min = region.getMinimumPoint();
        BlockVector3 max = region.getMaximumPoint();
        return new SelectionImpl(player.getWorld(), min.getX(), min.getY(), min.getZ(), max.getX(), max.getY(), max.getZ());
    }
}
