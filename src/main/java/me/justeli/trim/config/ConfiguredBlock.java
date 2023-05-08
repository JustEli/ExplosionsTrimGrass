package me.justeli.trim.config;

import org.bukkit.Material;

import java.util.Map;
import java.util.NavigableMap;
import java.util.SplittableRandom;
import java.util.TreeMap;

/* Eli @ April 29, 2021 (me.justeli.trim.config) */
public class ConfiguredBlock
{
    private final Material material;
    private final boolean underSeaLevelOnly;
    private final boolean disabledInClaims;
    private final boolean disabledInRegions;
    private final NavigableMap<Double, Material> transformations = new TreeMap<>();

    private static final SplittableRandom RANDOM = new SplittableRandom();

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
        Map.Entry<Double, Material> map = this.transformations.floorEntry(RANDOM.nextDouble());
        return map == null? null : map.getValue();
    }
}
