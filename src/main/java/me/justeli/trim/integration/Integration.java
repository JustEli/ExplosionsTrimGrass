package me.justeli.trim.integration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/* Eli @ August 09, 2020 (me.justeli.trim) */
public class Integration
    implements Listener
{
    private static final AtomicBoolean WORLD_GUARD_LOADED = new AtomicBoolean(false);
    private static final AtomicBoolean GRIEF_PREVENTION_LOADED = new AtomicBoolean(false);

    @EventHandler
    public void onPluginEnableEvent (PluginEnableEvent event)
    {
        switch (event.getPlugin().getName())
        {
            case "WorldGuard":
                WORLD_GUARD_LOADED.set(true);
                return;
            case "GriefPrevention":
                GRIEF_PREVENTION_LOADED.set(true);
        }
    }

    public static boolean isGriefPreventionLoaded ()
    {
        return GRIEF_PREVENTION_LOADED.get();
    }

    public static boolean isWorldGuardLoaded ()
    {
        return WORLD_GUARD_LOADED.get();
    }
}
