package me.justeli.trim.integration;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.ryanhamshire.GriefPrevention.Claim;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;

/**
 * Created by Eli on April 29, 2021.
 * CreepersTrimGrass: me.justeli.trim.integration
 */
public class WorldGuard
{
    private static final RegionQuery query = com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

    public static boolean isRegionAt (Location location)
    {
        if (Integration.isGriefPreventionLoaded())
        {
            Claim claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
            if (claim != null && claim.isAdminClaim())
            {
                return true;
            }
        }

        if (!Integration.isWorldGuardLoaded())
            return false;

        if (location.getWorld() == null)
            return false;

        return query.getApplicableRegions(BukkitAdapter.adapt(location)).size() > 0;
    }
}
