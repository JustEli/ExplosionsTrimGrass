package me.justeli.trim.api;

import community.leaf.tasks.bukkit.BukkitTaskSource;
import me.justeli.trim.CreepersTrimGrass;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import java.util.concurrent.TimeUnit;
import java.util.function.Consumer;

/**
 * Created by Eli on April 29, 2021.
 * CreepersTrimGrass: me.justeli.trim.api
 */
public class Register implements BukkitTaskSource
{
    private final CreepersTrimGrass instance;

    public Register (CreepersTrimGrass instance)
    {
        this.instance = instance;
    }

    @Override
    public Plugin plugin ()
    {
        return instance;
    }

    public void events (Listener... listeners)
    {
        PluginManager manager = instance.getServer().getPluginManager();
        for (Listener listener : listeners)
        {
            manager.registerEvents(listener, instance);
        }
    }

    public void metrics (final int pluginId, final Consumer<Metric> consumer)
    {
        async().delay(1, TimeUnit.MINUTES).run(() ->
        {
            Metrics metrics = new Metrics(instance, pluginId);
            consumer.accept(new Metric(metrics));
        });
    }

    public static class Metric
    {
        private final Metrics metrics;

        public Metric (Metrics metrics)
        {
            this.metrics = metrics;
        }

        public void add (String key, Object value)
        {
            metrics.addCustomChart(new SimplePie(key, value::toString));
        }
    }
}
