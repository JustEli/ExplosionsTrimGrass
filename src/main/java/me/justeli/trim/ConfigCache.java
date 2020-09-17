package me.justeli.trim;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Created by Eli on August 09, 2020.
 * CreepersTrimGrass: me.justeli.trim
 */
public class ConfigCache
{
    private final CreepersTrimGrass instance;

    public ConfigCache (CreepersTrimGrass instance)
    {
        this.instance = instance;
    }

    private final HashMap<Material, HashMap<Material, Double>> conversion = new HashMap<>();
    private final HashMap<Material, Boolean> userSeaLevelOnly = new HashMap<>();
    private final HashMap<Material, Boolean> disableInClaims = new HashMap<>();
    private final HashMap<String, Set<Material>> definitions = new HashMap<>();
    private final AtomicBoolean disableDamageToNonMobs = new AtomicBoolean();

    public Set<Material> getAffectedBlocks ()
    {
        return conversion.keySet();
    }

    public Material getRandomConversion (Material material)
    {
        if (!getAffectedBlocks().contains(material))
            return null;


    }


    public void init ()
    {
        instance.saveDefaultConfig();
        load();
    }

    public long reload ()
    {
        long current = System.currentTimeMillis();

        instance.reloadConfig();
        load();

        return System.currentTimeMillis() - current;
    }

    private void load ()
    {
        FileConfiguration config = instance.getConfig();

        Integration.Plugin.WORLD_GUARD.setEnabled(!config.getBoolean("disableInRegions"));
        disableDamageToNonMobs.set(config.getBoolean("disableDamageToNonMobs"));

        ConfigurationSection section = config.getConfigurationSection("definitions");
        if (section != null)
        {
            for (String key : section.getKeys(false))
            {
                List<String> materialList = section.getStringList(key);
                Set<Material> converted = new HashSet<>();

                for (String material : materialList)
                {
                    try
                    {
                        converted.add(Material.valueOf(material.toUpperCase()));
                    }
                    catch (EnumConstantNotPresentException exception)
                    {
                        instance.getLogger().warning("Found '" + material.toUpperCase() + "' in the definition list of '"
                                + key.toUpperCase() + ",' but it is not a material. Skipped.");
                    }
                }

                definitions.put(key.toUpperCase(), converted);
            }
        }

        // ...
    }
}
