package me.justeli.trim.handlers;

import me.justeli.trim.CreepersTrimGrass;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Mob;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 * Created by Eli on April 29, 2021.
 * CreepersTrimGrass: me.justeli.trim.handlers
 */
public class DamageBlocker implements Listener
{
    private final CreepersTrimGrass instance;
    public DamageBlocker (CreepersTrimGrass instance)
    {
        this.instance = instance;
    }

    @EventHandler
    public void onNonMobDamage (EntityDamageByEntityEvent event)
    {
        if (instance.getConfigCache().disableDamageToNonMobs())
            return;

        if (event.getDamager() instanceof Creeper && !(event.getEntity() instanceof Mob) && !(event.getEntity() instanceof Player))
        {
            event.setCancelled(true);
        }
    }
}
