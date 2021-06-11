package me.justeli.trim.handlers;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import community.leaf.tasks.bukkit.BukkitTaskSource;
import me.justeli.trim.CreepersTrimGrass;
import me.justeli.trim.config.ConfiguredBlock;
import me.justeli.trim.event.CreeperTrimEvent;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Eli on 4 jan 2017.
 * Survival Rocks: me.justeli.trim
 */
public class TrimEffect
        implements Listener, BukkitTaskSource
{
    private final CreepersTrimGrass instance;

    public TrimEffect (CreepersTrimGrass instance)
    {
        this.instance = instance;
    }

    @Override
    public Plugin plugin ()
    {
        return instance;
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void explodeEvent (EntityExplodeEvent event)
    {
        if (!(event.getEntity() instanceof Creeper))
            return;

        instance.getServer().getPluginManager().callEvent(new CreeperTrimEvent(event));
    }

    @EventHandler
    public void creeperTrimEvent (CreeperTrimEvent event)
    {
        World world = event.getLocation().getWorld();
        if (world == null)
            return;

        final List<Block> blockList = new ArrayList<>(event.getBlockList());
        sync().delay(1).ticks().run(() ->
        {
            for (Block block : blockList)
            {
                ConfiguredBlock configuredBlock = instance.getConfigCache().getConfiguredBlock(block.getType());
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
        });

        event.getBlockList().clear();
    }
}
