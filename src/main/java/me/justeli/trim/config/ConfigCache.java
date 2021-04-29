package me.justeli.trim.config;

import me.justeli.trim.CreepersTrimGrass;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import java.util.Collections;
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

    private final AtomicBoolean disableDamageToNonMobs = new AtomicBoolean();

    public boolean disableDamageToNonMobs ()
    {
        return disableDamageToNonMobs.get();
    }

    private final HashMap<Material, ConfiguredBlock> configuredBlocks = new HashMap<>();
    private final HashMap<String, Set<Material>> definitions = new HashMap<>();

    public ConfiguredBlock getConfiguredBlock (Material material)
    {
        return configuredBlocks.computeIfAbsent(material, empty -> null);
    }

    private Set<Material> getBlockFromName (String name)
    {
        try
        {
            return definitions.computeIfAbsent(name.toUpperCase(), empty ->
                    new HashSet<>(Collections.singleton(Material.matchMaterial(name.toUpperCase()))));
        }
        catch (EnumConstantNotPresentException exception)
        {
            instance.getLogger().warning(String.format("Found '%s' in 'creeperTransformBlocks', but it is not a defined block. Skipped.",
                    name.toUpperCase()));
            return null;
        }
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
        configuredBlocks.clear();
        definitions.clear();
        load();

        return System.currentTimeMillis() - current;
    }

    private void load ()
    {
        FileConfiguration config = instance.getConfig();

        disableDamageToNonMobs.set(config.getBoolean("disableDamageToNonMobs", true));
        setDefinitions(config.getConfigurationSection("blockDefinitions"));
        setTransformBlocks(config);
    }

    private void setDefinitions (ConfigurationSection section)
    {
        if (section == null)
            return;

        for (String key : section.getKeys(false))
        {
            List<String> materialList = section.getStringList(key);
            Set<Material> converted = new HashSet<>();

            for (String material : materialList)
            {
                try
                {
                    converted.add(Material.matchMaterial(material.toUpperCase()));
                }
                catch (EnumConstantNotPresentException exception)
                {
                    instance.getLogger().warning(String.format("Found '%s' in the definition list of '%s', but it is not a material. Skipped.",
                            material.toUpperCase(), key.toUpperCase()));
                }
            }

            definitions.put(key.toUpperCase(), converted);
        }
    }

    private void setTransformBlocks (FileConfiguration config)
    {
        ConfigurationSection section = config.getConfigurationSection("creeperTransformBlocks");
        if (section == null)
            return;

        boolean underSeaLevelOnly = config.getBoolean("defaultValues.underSeaLevelOnly", false);
        boolean disableInClaims = config.getBoolean("defaultValues.disableInClaims", false);
        boolean disableInRegions = config.getBoolean("defaultValues.disableInRegions", true);

        for (String key : section.getKeys(false))
        {
            Set<Material> materials = getBlockFromName(key.toUpperCase());
            ConfigurationSection part = section.getConfigurationSection(key);
            if (materials == null || part == null)
                continue;

            ConfigurationSection conversion = part.getConfigurationSection("conversion");
            if (conversion == null)
                continue;

            for (Material material : materials)
            {
                configuredBlocks.put(material, new ConfiguredBlock(material,
                        part.getBoolean("underSeaLevelOnly", underSeaLevelOnly),
                        part.getBoolean("disableInClaims", disableInClaims),
                        part.getBoolean("disableInRegions", disableInRegions),
                        conversion.getValues(false)));
            }
        }
    }
}
