package me.justeli.trim;

import me.justeli.api.shaded.commands.Command;
import me.justeli.api.shaded.commands.CommandDetails;
import me.justeli.api.shaded.commands.Commander;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

/**
 * Created by Eli on August 09, 2020.
 * CreepersTrimGrass: me.justeli.trim
 */
public class ReloadCommand implements Commander
{
    private final CreepersTrimGrass instance;

    public ReloadCommand (CreepersTrimGrass instance)
    {
        this.instance = instance;
    }

    @Command (aliases = "creeperstrimgrass", permission = "creeperstrimgrass.command")
    public boolean ctg (CommandDetails details)
    {
        CommandSender sender = details.getSender();
        String[] args = details.getArgs();

        if (args.length == 0 || args[0].equalsIgnoreCase("help"))
        {
            sender.sendMessage("/ctg help");
            sender.sendMessage("/ctg reload");
            sender.sendMessage("/ctg version");
            return true;
        }

        switch (args[0].toLowerCase())
        {
            case "reload":
                sender.sendMessage(ChatColor.YELLOW + "Config has been reloaded in "
                        + ChatColor.GREEN + instance.getConfigCache().reload() + "ms" + ChatColor.YELLOW + ".");
                return true;
            case "version":
                sender.sendMessage(ChatColor.YELLOW + "Currently running version: " + ChatColor.WHITE + instance.getPluginLoader().);
            default:
                sender.sendMessage(ChatColor.RED + "No argument found for ");
                return true;
        }
    }
}
