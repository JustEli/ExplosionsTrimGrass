package me.justeli.trim.event;

import me.justeli.trim.integration.GriefPrevention;
import me.justeli.trim.integration.Integration;
import me.justeli.trim.integration.WorldGuard;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.List;

/* Eli @ April 29, 2021 (me.justeli.trim) */
public class CreeperTrimEvent
    extends Event
{
    private final EntityExplodeEvent event;
    private final boolean belowSeaLevel;
    private final boolean insideClaim;
    private final boolean inRegion;

    public CreeperTrimEvent (EntityExplodeEvent event)
    {
        this.event = event;
        this.belowSeaLevel = event.getLocation().getY() < 64;
        this.insideClaim = Integration.isGriefPreventionLoaded() && GriefPrevention.isClaimAt(event.getLocation());
        this.inRegion = Integration.isWorldGuardLoaded() && WorldGuard.isRegionAt(event.getLocation());
    }

    public boolean isBelowSeaLevel ()
    {
        return belowSeaLevel;
    }

    public boolean isInsideClaim ()
    {
        return insideClaim;
    }

    public boolean isInRegion ()
    {
        return inRegion;
    }

    public List<Block> getBlockList ()
    {
        return event.blockList();
    }

    public Location getLocation ()
    {
        return event.getLocation();
    }

    public Creeper getCreeper ()
    {
        return (Creeper) event.getEntity();
    }


    private static final HandlerList HANDLER_LIST = new HandlerList();

    @Override
    public HandlerList getHandlers ()
    {
        return HANDLER_LIST;
    }

    public static HandlerList getHandlerList ()
    {
        return HANDLER_LIST;
    }
}
