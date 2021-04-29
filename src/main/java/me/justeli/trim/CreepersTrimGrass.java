package me.justeli.trim;

import me.justeli.trim.api.Register;
import me.justeli.trim.commands.GeneralCommand;
import me.justeli.trim.config.ConfigCache;
import me.justeli.trim.handlers.DamageBlocker;
import me.justeli.trim.handlers.Integration;
import me.justeli.trim.handlers.TrimEffect;
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
        register.commands(new GeneralCommand(this));
        register.metrics(11185, metric ->
        {
            metric.add("amountOfTransformers", 7);
        });
    }

    public ConfigCache getConfigCache ()
    {
        return configCache;
    }
}
