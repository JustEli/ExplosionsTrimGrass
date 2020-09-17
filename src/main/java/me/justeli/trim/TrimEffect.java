package me.justeli.trim;

import com.sk89q.worldguard.WorldGuard;
import com.sk89q.worldguard.protection.regions.RegionContainer;
import com.sk89q.worldguard.protection.regions.RegionQuery;
import me.ryanhamshire.GriefPrevention.GriefPrevention;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Creeper;
import org.bukkit.entity.Mob;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityExplodeEvent;

/**
 * Created by Eli on 4 jan 2017.
 * Survival Rocks: me.justeli.trim
 */
public class TrimEffect
        implements Listener
{
    private final CreepersTrimGrass instance;

    public TrimEffect (CreepersTrimGrass instance)
    {
        this.instance = instance;
    }

    @EventHandler
    public void on (EntityDamageByEntityEvent e)
    {
        if (!(e.getEntity() instanceof Mob) && e.getDamager() instanceof Creeper)
            e.setCancelled(true);
    }

    @EventHandler (priority = EventPriority.LOWEST)
    public void on (EntityExplodeEvent e)
    {
        if (!(e.getEntity() instanceof Creeper))
            return;

        if (Integration.Plugin.WORLD_GUARD.isActive())
        {
            RegionContainer container = WorldGuard.getInstance().getPlatform().getRegionContainer();
            RegionQuery query = container.createQuery();
            query.getApplicableRegions().si
            return;
        }

        World world = e.getEntity().getWorld();

        for (Block block : e.blockList())
        {
            if (block.getType() == Material.GRASS_BLOCK || block.getType() == Material.MYCELIUM)
            {
                block.setType(Math.random() <= 0.08? Material.COARSE_DIRT : Material.DIRT);

                Location location = block.getLocation().clone().add(0.5, 1.1, 0.5);
                world.spawnParticle(Particle.BLOCK_CRACK, location, 30, 0.5, 0, 0.5, Material.GRASS.createBlockData());
            }
            else if (block.getType() == Material.STONE)
            {
                block.setType(Math.random() <= 0.01? Material.INFESTED_COBBLESTONE : Material.COBBLESTONE);

                Location location = block.getLocation().clone().add(0.5, 1.1, 0.5);
                world.spawnParticle(Particle.BLOCK_CRACK, location, 5, 0.5, 0, 0.5, Material.COBBLESTONE.createBlockData());
            }
            else
            {
                switch (block.getType())
                {
                    case OAK_SAPLING:
                    case SPRUCE_SAPLING:
                    case BIRCH_SAPLING:
                    case ACACIA_SAPLING:
                    case JUNGLE_SAPLING:
                    case DARK_OAK_SAPLING:
                    case GRASS:
                    case FERN:
                    case LARGE_FERN:
                    case TALL_GRASS:
                    case DEAD_BUSH:
                    case DANDELION:
                    case POPPY:
                    case BLUE_ORCHID:
                    case ALLIUM:
                    case AZURE_BLUET:
                    case RED_TULIP:
                    case PINK_TULIP:
                    case WHITE_TULIP:
                    case ORANGE_TULIP:
                    case OXEYE_DAISY:
                    case BROWN_MUSHROOM:
                    case RED_MUSHROOM:
                    case SUNFLOWER:
                    case LILAC:
                    case ROSE_BUSH:
                    case PEONY:
                    case POTATOES:
                    case WHEAT:
                    case CARROTS:
                    case MELON_STEM:
                    case PUMPKIN_STEM:
                    case BEETROOTS:
                    case SNOW:

                    case ACACIA_LEAVES:
                    case BIRCH_LEAVES:
                    case DARK_OAK_LEAVES:
                    case OAK_LEAVES:
                    case JUNGLE_LEAVES:
                    case SPRUCE_LEAVES:
                    {
                        Material type = block.getType();
                        Location location = block.getLocation().clone().add(0.5, 0.5, 0.5);

                        if (Math.random() < e.getYield())
                        {
                            block.breakNaturally();
                            world.playEffect(location, Effect.STEP_SOUND, type);
                        }
                        else
                        {
                            block.setType(Material.AIR);
                            world.spawnParticle(Particle.BLOCK_CRACK, location, 30, 0.5, 0.5, 0.5, type.createBlockData());
                        }
                    }
                    default:
                        break;
                }
            }
        }

        e.blockList().clear();
    }

    private boolean isClaimAt (Location location)
    {
        if (!Integration.Plugin.GRIEF_PREVENTION.isActive())
            return false;

        return GriefPrevention.instance.dataStore.getClaimAt(location, true, null) != null;
    }
}
