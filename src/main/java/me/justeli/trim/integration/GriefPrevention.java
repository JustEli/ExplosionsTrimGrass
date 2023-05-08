package me.justeli.trim.integration;

import org.bukkit.Location;

/* Eli @ April 29, 2021 (me.justeli.trim.integration) */
public class GriefPrevention
{
    public static boolean isClaimAt (Location location)
    {
        if (!Integration.isGriefPreventionLoaded())
            return false;

        return me.ryanhamshire.GriefPrevention.GriefPrevention.instance.dataStore.getClaimAt(location, true, null) != null;
    }
}
