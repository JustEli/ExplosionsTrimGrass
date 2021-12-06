package me.justeli.trim.command;

import community.leaf.tasks.bukkit.BukkitTaskSource;
import me.justeli.dynamiccommands.spigot.Command;
import me.justeli.dynamiccommands.spigot.DynamicCommands;
import me.justeli.trim.CreepersTrimGrass;
import me.justeli.trim.api.Util;
import org.bukkit.plugin.Plugin;

/**
 * Created by Eli on August 09, 2020.
 * CreepersTrimGrass: me.justeli.trim
 */
public class GeneralCommand
        implements BukkitTaskSource
{
    private final CreepersTrimGrass instance;
    private String latestVersion;

    public GeneralCommand (CreepersTrimGrass instance)
    {
        this.instance = instance;
        async().run(() ->
        {
            this.latestVersion = Util.getLatestVersion("JustEli/CreepersTrimGrass");

            if (this.latestVersion == null)
            {
                this.latestVersion = instance.getDescription().getVersion();
                return;
            }

            if (!instance.getDescription().getVersion().equals(this.latestVersion))
            {
                instance.getLogger().warning(" ------------------------------------------------------------------");
                instance.getLogger().warning("   You're running an outdated version of CreepersTrimGrass.");
                instance.getLogger().warning("   The version installed is " + instance.getDescription().getVersion()
                        + ", while " + this.latestVersion + " is out!");
                instance.getLogger().warning("   https://www.spigotmc.org/resources/creepers-trim-grass.73305/");
                instance.getLogger().warning(" ------------------------------------------------------------------");
            }
        });

        DynamicCommands dynamicCommands = DynamicCommands.of(instance);

        Command command = Command.of("ctg", "creeperstrimgrass").permission("creeperstrimgrass.admin");

        command.staticArgument("help").executes(details ->
        {
            details.sender().sendMessage(Util.color("&e/creeperstrimgrass help &f- show this page"));
            details.sender().sendMessage(Util.color("&e/creeperstrimgrass reload &f- reload config settings"));
            details.sender().sendMessage(Util.color("&e/creeperstrimgrass version &f- check for updates"));
        });

        command.staticArgument("reload").executes(details ->
        {
            details.sender().sendMessage(Util.color("&eConfig has been reloaded in &a" + instance.getConfigCache().reload() + "ms&e."));
        });

        command.staticArgument("version").executes(details ->
        {
            details.sender().sendMessage(Util.color("&eCurrently running version: &f" + instance.getDescription().getVersion()));
            details.sender().sendMessage(Util.color("&eLatest version: &f" + latestVersion));
        });

        command.register(dynamicCommands);
    }

    @Override
    public Plugin plugin()
    {
        return instance;
    }
}
