package me.justeli.trim;

import com.sk89q.worldguard.bukkit.WorldGuardPlugin;
import me.justeli.api.shaded.commands.Commander;
import me.justeli.api.shaded.commands.DynamicCommandRegistry;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Arrays;

/**
 * Created by Eli on 9 dec. 2019.
 * spigotPlugins: me.justeli.trim
 */
public class CreepersTrimGrass extends JavaPlugin
{
    // todo disable coarse dirt in claims

    private TrimEffect trimEffect;
    private ConfigCache configCache;
    private ReloadCommand reloadCommand;
    private Integration integration;

    @Override
    public void onEnable ()
    {
        trimEffect = new TrimEffect(this);
        configCache = new ConfigCache(this);
        reloadCommand = new ReloadCommand(this);
        integration = new Integration();

        configCache.init();

        registerEvents(trimEffect, integration);
        registerCommands(reloadCommand);
    }


    private void registerEvents (Listener... listeners)
    {
        PluginManager manager = getServer().getPluginManager();
        Arrays.stream(listeners).forEach(listener -> manager.registerEvents(listener, this));
    }

    private void registerCommands (Commander... commanders)
    {
        DynamicCommandRegistry registry = new DynamicCommandRegistry(this);
        Arrays.stream(commanders).forEach(registry::register);
    }


    public TrimEffect getTrimEffect ()
    {
        return trimEffect;
    }

    public ConfigCache getConfigCache ()
    {
        return configCache;
    }

    public ReloadCommand getReloadCommand ()
    {
        return reloadCommand;
    }

    public Integration getIntegration ()
    {
        return integration;
    }
}
