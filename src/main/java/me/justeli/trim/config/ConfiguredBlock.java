package me.justeli.trim.config;

import org.bukkit.Material;

import java.util.Map;
import java.util.NavigableMap;
import java.util.Random;
import java.util.TreeMap;

/**
 * Created by Eli on April 29, 2021.
 * CreepersTrimGrass: me.justeli.trim.config
 */
public class ConfiguredBlock
{
    private final Material material;
    private final boolean underSeaLevelOnly;
    private final boolean disabledInClaims;
    private final boolean disabledInRegions;
    private final NavigableMap<Double, Material> transformations = new TreeMap<>();

    private final Random random = new Random();

    public ConfiguredBlock (Material material,
            boolean underSeaLevelOnly,
            boolean disabledInClaims,
            boolean disabledInRegions,
            Map<String, Object> transformations)
    {
        this.material = material;
        this.underSeaLevelOnly = underSeaLevelOnly;
        this.disabledInClaims = disabledInClaims;
        this.disabledInRegions = disabledInRegions;

        double adding = 0;
        for (Map.Entry<String, Object> map : transformations.entrySet())
        {
            this.transformations.put(adding, Material.matchMaterial(map.getKey().toUpperCase()));
            adding += (double) map.getValue();
        }
        this.transformations.put(adding, null);
    }

    public Material getMaterial ()
    {
        return material;
    }

    public boolean isUnderSeaLevelOnly ()
    {
        return underSeaLevelOnly;
    }

    public boolean isDisabledInClaims ()
    {
        return disabledInClaims;
    }

    public boolean isDisabledInRegions ()
    {
        return disabledInRegions;
    }

    public Material getRandomTransform ()
    {
        Map.Entry<Double, Material> map = transformations.floorEntry(random.nextDouble());
        return map == null? null : map.getValue();
    }
}
