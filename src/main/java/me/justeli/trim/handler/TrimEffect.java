package me.justeli.trim.handler;

import me.justeli.trim.CreepersTrimGrass;
import me.justeli.trim.config.ConfiguredBlock;
import me.justeli.trim.event.CreeperTrimEvent;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;

import java.util.ArrayList;
import java.util.List;

/* Eli @ January 4, 2017 (me.justeli.trim) */
public class TrimEffect
    implements Listener
{
    private final CreepersTrimGrass plugin;

    public TrimEffect (CreepersTrimGrass plugin)
    {
        this.plugin = plugin;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void onEntityExplodeEvent (EntityExplodeEvent event)
    {
        if (!(event.getEntity() instanceof Creeper))
            return;

        plugin.getServer().getPluginManager().callEvent(new CreeperTrimEvent(event));
    }

    @EventHandler
    public void onCreeperTrimEvent (CreeperTrimEvent event)
    {
        var world = event.getLocation().getWorld();
        if (world == null)
            return;

        final List<Block> blockList = new ArrayList<>(event.getBlockList());
        plugin.getServer().getScheduler().scheduleSyncDelayedTask(plugin, () ->
        {
            for (Block block : blockList)
            {
                ConfiguredBlock configuredBlock = plugin.getConfigCache().getConfiguredBlock(block.getType());
                if (configuredBlock == null)
                    continue;

                if (configuredBlock.isDisabledInClaims() && event.isInsideClaim())
                    continue;

                if (configuredBlock.isDisabledInRegions() && event.isInRegion())
                    continue;

                if (configuredBlock.isUnderSeaLevelOnly() && !event.isBelowSeaLevel())
                    continue;

                Material setTo = configuredBlock.getRandomTransform();
                if (setTo == null || setTo == block.getType())
                    continue;

                if (setTo == Material.AIR)
                {
                    world.playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());
                    block.breakNaturally();
                }
                else if (block.getLocation().getBlock().getRelative(BlockFace.UP).getType() == Material.AIR)
                {
                    Location location = block.getLocation().clone().add(0.5, 1.05, 0.5);
                    world.spawnParticle(Particle.BLOCK_CRACK, location, 30, 0.5, 0, 0.5, block.getType().createBlockData());
                    block.setType(setTo);
                }
            }
        }, 1);

        event.getBlockList().clear();
    }
}
