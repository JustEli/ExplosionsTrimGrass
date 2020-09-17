package me.justeli.trim;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.PluginEnableEvent;

/**
 * Created by Eli on August 09, 2020.
 * CreepersTrimGrass: me.justeli.trim
 */
public class Integration
        implements Listener
{
    @EventHandler
    public void on (PluginEnableEvent e)
    {
        switch (e.getPlugin().getName())
        {
            case "WorldGuard":
                Plugin.WORLD_GUARD.setLoaded(true);
                return;
            case "GriefPrevention":
                Plugin.GRIEF_PREVENTION.setLoaded(true);
                return;
        }
    }

    public enum Plugin
    {
        GRIEF_PREVENTION (false, true),
        WORLD_GUARD (false, false),
        ;

        // Is loaded as a plugin.
        private boolean loaded;

        // Is enabled in the config.
        private boolean enabled;

        Plugin (boolean loaded, boolean enabled)
        {
            this.loaded = loaded;
            this.enabled = enabled;
        }

        public void setLoaded (boolean loaded)
        {
            this.loaded = loaded;
        }

        public void setEnabled (boolean enabled)
        {
            this.enabled = enabled;
        }

        public boolean isActive ()
        {
            return this.loaded && this.enabled;
        }
    }
}
