package me.justeli.trim.api;

import me.justeli.trim.CreepersTrimGrass;
import org.bstats.bukkit.Metrics;
import org.bukkit.event.Listener;

import java.util.function.Consumer;

/* Eli @ April 29, 2021 (me.justeli.trim.api) */
public class Register
{
    private final CreepersTrimGrass plugin;

    public Register (CreepersTrimGrass plugin)
    {
        this.plugin = plugin;
    }

    public void events (Listener... listeners)
    {
        var manager = plugin.getServer().getPluginManager();
        for (Listener listener : listeners)
        {
            manager.registerEvents(listener, plugin);
        }
    }

    public void metrics (final int pluginId, final Consumer<Metric> consumer)
    {
        CreepersTrimGrass.ASYNC_EXECUTOR.submit(() ->
        {
            Metrics metrics = new Metrics(plugin, pluginId);
            consumer.accept(new Metric(metrics));
        });
    }
}
