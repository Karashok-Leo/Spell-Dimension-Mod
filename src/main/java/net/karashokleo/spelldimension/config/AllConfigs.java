package net.karashokleo.spelldimension.config;

import net.karashokleo.spelldimension.SpellDimension;
import net.karashokleo.spelldimension.config.mod_config.*;
import net.karashokleo.spelldimension.util.AttributeUtil;
import net.tinyconfig.ConfigManager;

public class AllConfigs
{
    public static ConfigManager<ModifiersConfig> modifiers = new ConfigManager<>
            ("modifiers", new ModifiersConfig())
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();
    public static ConfigManager<DamageConfig> converge = new ConfigManager<>
            ("converge", new DamageConfig(4, 0.6, 1.2))
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();
    public static ConfigManager<BlazingMarkConfig> blazing_mark = new ConfigManager<>
            ("blazing_mark", new BlazingMarkConfig())
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();
    public static ConfigManager<DamageConfig> nucleus = new ConfigManager<>
            ("nucleus", new DamageConfig(8, 0.8, 1.1))
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();
    public static ConfigManager<DamageConfig> frosted = new ConfigManager<>
            ("frosted", new DamageConfig(2, 0.4, 1.2))
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();
    public static ConfigManager<LootConfig> loot = new ConfigManager<>
            ("loot", new LootConfig())
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();
    public static ConfigManager<MiscConfig> misc = new ConfigManager<>
            ("misc", new MiscConfig())
            .builder()
            .setDirectory(SpellDimension.MOD_ID)
            .sanitize(true)
            .build();

    public static void refresh()
    {
        modifiers.refresh();
        converge.refresh();
        blazing_mark.refresh();
        nucleus.refresh();
        frosted.refresh();
        loot.refresh();
        misc.refresh();

        AttributeUtil.initColorMap();
    }
}
