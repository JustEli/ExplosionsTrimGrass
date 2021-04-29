package me.justeli.trim.integration;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Eli on August 09, 2020.
 * CreepersTrimGrass: me.justeli.trim
 */
public class Integration
        implements Listener
{
    private static final AtomicBoolean worldGuardLoaded = new AtomicBoolean(false);
    private static final AtomicBoolean griefPreventionLoaded = new AtomicBoolean(false);

    @EventHandler
    public void on (PluginEnableEvent event)
    {
        switch (event.getPlugin().getName())
        {
            case "WorldGuard":
                worldGuardLoaded.set(true);
                return;
            case "GriefPrevention":
                griefPreventionLoaded.set(true);
        }
    }

    public static boolean isGriefPreventionLoaded ()
    {
        return griefPreventionLoaded.get();
    }

    public static boolean isWorldGuardLoaded ()
    {
        return worldGuardLoaded.get();
    }
}
