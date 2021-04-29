package me.justeli.trim.integration;

import org.bukkit.Location;

/**
 * Created by Eli on April 29, 2021.
 * CreepersTrimGrass: me.justeli.trim.integration
 */
public class GriefPrevention
{
    public static boolean isClaimAt (Location location)
    {
        if (!Integration.isGriefPreventionLoaded())
            return false;

        return me.ryanhamshire.GriefPrevention.GriefPrevention.instance.dataStore.getClaimAt(location, true, null) != null;
    }
}
