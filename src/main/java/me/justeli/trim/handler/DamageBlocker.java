package me.justeli.trim.handler;

import me.justeli.trim.CreepersTrimGrass;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/* Eli @ April 29, 2021 (me.justeli.trim.handlers) */
public class DamageBlocker
    implements Listener
{
    private final CreepersTrimGrass plugin;

    public DamageBlocker (CreepersTrimGrass plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler
    public void onEntityDamageByEntityEvent (EntityDamageByEntityEvent event)
    {
        if (plugin.getConfigCache().disableDamageToNonMobs())
            return;

        if (event.getDamager() instanceof Creeper && !(event.getEntity() instanceof Mob) && !(event.getEntity() instanceof Player))
        {
            event.setCancelled(true);
        }
    }
}
