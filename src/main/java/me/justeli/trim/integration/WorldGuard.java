package me.justeli.trim.integration;

import com.sk89q.worldedit.bukkit.BukkitAdapter;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Location;

/* Eli @ April 29, 2021 (me.justeli.trim.integration) */
public class WorldGuard
{
    private static final RegionQuery QUERY =
        com.sk89q.worldguard.WorldGuard.getInstance().getPlatform().getRegionContainer().createQuery();

    public static boolean isRegionAt (Location location)
    {
        if (Integration.isGriefPreventionLoaded())
        {
            var claim = GriefPrevention.instance.dataStore.getClaimAt(location, true, null);
            if (claim != null && claim.isAdminClaim())
            {
                return true;
            }
        }

        if (!Integration.isWorldGuardLoaded())
            return false;

        if (location.getWorld() == null)
            return false;

        return QUERY.getApplicableRegions(BukkitAdapter.adapt(location)).size() > 0;
    }
}
