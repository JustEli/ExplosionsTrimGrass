package me.justeli.trim.commands;

import cloud.commandframework.annotations.Argument;
import cloud.commandframework.annotations.CommandMethod;
import cloud.commandframework.annotations.CommandPermission;
import cloud.commandframework.annotations.specifier.Completions;
import community.leaf.tasks.bukkit.BukkitTaskSource;
import me.justeli.trim.CreepersTrimGrass;
import me.justeli.trim.api.Util;
import org.bukkit.command.CommandSender;
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
        async().run(() -> this.latestVersion = Util.getLatestVersion("JustEli/CreepersTrimGrass"));
    }

    @Override
    public Plugin plugin()
    {
        return instance;
    }

    @CommandMethod("creeperstrimgrass [argument]")
    @CommandPermission("creeperstrimgrass.command")
    public void ctg (CommandSender sender,
            @Argument("argument") @Completions("help, reload, version") String argument)
    {
        if (argument == null || argument.equalsIgnoreCase("help"))
        {
            sender.sendMessage("/ctg help");
            sender.sendMessage("/ctg reload");
            sender.sendMessage("/ctg version");
            return;
        }

        switch (argument.toLowerCase())
        {
            case "reload":
                sender.sendMessage(Util.color("&eConfig has been reloaded in &a" + instance.getConfigCache().reload() + "ms&e."));
                return;
            case "version":
                sender.sendMessage(Util.color("&eCurrently running version: &f" + instance.getDescription().getVersion()));
                sender.sendMessage(Util.color("&eLatest version: &f" + latestVersion));
        }
    }
}
