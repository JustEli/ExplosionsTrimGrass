package me.justeli.trim.command;

import me.justeli.dynamiccommands.spigot.Command;
import me.justeli.dynamiccommands.spigot.DynamicCommands;
import me.justeli.trim.CreepersTrimGrass;
import me.justeli.trim.api.Util;

/* Eli @ August 09, 2020 (me.justeli.trim) */
public class GeneralCommand
{
    private String latestVersion;

    public GeneralCommand (CreepersTrimGrass plugin)
    {
        CreepersTrimGrass.ASYNC_EXECUTOR.submit(() ->
        {
            this.latestVersion = Util.getLatestVersion("JustEli/CreepersTrimGrass");

            if (this.latestVersion == null)
            {
                this.latestVersion = plugin.getDescription().getVersion();
                return;
            }

            if (!plugin.getDescription().getVersion().equals(this.latestVersion))
            {
                plugin.getLogger().warning(" ------------------------------------------------------------------");
                plugin.getLogger().warning("   You're running an outdated version of CreepersTrimGrass.");
                plugin.getLogger().warning("   The version installed is " + plugin.getDescription().getVersion()
                        + ", while " + this.latestVersion + " is out!");
                plugin.getLogger().warning("   https://www.spigotmc.org/resources/creepers-trim-grass.73305/");
                plugin.getLogger().warning(" ------------------------------------------------------------------");
            }
        });

        DynamicCommands dynamicCommands = DynamicCommands.of(plugin);

        Command command = Command.of("ctg", "creeperstrimgrass").permission("creeperstrimgrass.admin");

        command.staticArgument("help").executes(details ->
        {
            details.sender().sendMessage(Util.color("&e/ctg help &f- show this page"));
            details.sender().sendMessage(Util.color("&e/ctg reload &f- reload config settings"));
            details.sender().sendMessage(Util.color("&e/ctg version &f- check for updates"));
        });

        command.staticArgument("reload").executes(details ->
        {
            details.sender().sendMessage(Util.color("&eConfig has been reloaded in &a" + plugin.getConfigCache().reload() + "ms&e."));
        });

        command.staticArgument("version").executes(details ->
        {
            details.sender().sendMessage(Util.color("&eInstalled version: &f" + plugin.getDescription().getVersion()));
            details.sender().sendMessage(Util.color("&eLatest version: &f" + this.latestVersion));
        });

        command.register(dynamicCommands);
    }
}
