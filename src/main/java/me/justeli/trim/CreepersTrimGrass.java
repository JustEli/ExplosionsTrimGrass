package me.justeli.trim;

import me.justeli.trim.api.Register;
import me.justeli.trim.command.GeneralCommand;
import me.justeli.trim.config.ConfigCache;
import me.justeli.trim.handler.DamageBlocker;
import me.justeli.trim.integration.Integration;
import me.justeli.trim.handler.TrimEffect;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Eli on 9 dec. 2019.
 * spigotPlugins: me.justeli.trim
 */
public class CreepersTrimGrass extends JavaPlugin
{
    private ConfigCache configCache;

    @Override
    public void onEnable ()
    {
        configCache = new ConfigCache(this);
        configCache.init();

        Register register = new Register(this);

        register.events(new TrimEffect(this), new Integration(), new DamageBlocker(this));

        new GeneralCommand(this);

        register.metrics(11185, metric ->
        {
            metric.add("totalBlockTransformers", configCache.totalTransformers());
            metric.add("disableDamageToNonMobs", configCache.disableDamageToNonMobs());
            metric.add("installedWorldGuard", Integration.isWorldGuardLoaded());
            metric.add("installedGriefPrevention", Integration.isWorldGuardLoaded());
        });
    }

    public ConfigCache getConfigCache ()
    {
        return configCache;
    }
}
