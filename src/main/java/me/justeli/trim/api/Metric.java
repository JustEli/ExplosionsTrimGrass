package me.justeli.trim.api;

import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;

/* Eli @ April 29, 2021 (me.justeli.trim.api) */
public class Metric
{
    private final Metrics metrics;

    public Metric (Metrics metrics)
    {
        this.metrics = metrics;
    }

    public void add (String key, Object value)
    {
        this.metrics.addCustomChart(new SimplePie(key, value::toString));
    }
}
