package net.karashokleo.spelldimension.config;

import me.shedaniel.autoconfig.AutoConfig;
import me.shedaniel.autoconfig.ConfigData;
import me.shedaniel.autoconfig.annotation.Config;
import me.shedaniel.autoconfig.annotation.ConfigEntry;
import me.shedaniel.autoconfig.serializer.GsonConfigSerializer;
import net.karashokleo.spelldimension.SpellDimension;

@Config(name = SpellDimension.MOD_ID)
public class AllConfig implements ConfigData
{
    @ConfigEntry.Gui.Excluded
    public static AllConfig INSTANCE;

    public String[] loot_blacklist = {"dummmmmmy:target_dummy"};

    @ConfigEntry.Gui.CollapsibleObject
    public ConvergeConfig converge = new ConvergeConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public BlazingMarkConfig blazing_mark = new BlazingMarkConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public NucleusConfig nucleus = new NucleusConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public FrostedConfig frosted = new FrostedConfig();

    public static void register()
    {
        AutoConfig.register(AllConfig.class, GsonConfigSerializer::new);
        INSTANCE = AutoConfig.getConfigHolder(AllConfig.class).getConfig();
    }

    public static class ConvergeConfig
    {
        @ConfigEntry.Gui.CollapsibleObject
        public Damage damage = new Damage(4, 0.6, 1.2);
    }

    public static class BlazingMarkConfig
    {
        public int total_duration = 200;
        public int trigger_duration = 100;
        public int max_damage = 30;
        public float proportion = 0.5F;
    }

    public static class NucleusConfig
    {
        @ConfigEntry.Gui.CollapsibleObject
        public Damage damage = new Damage(8, 0.8, 1.1);
    }

    public static class FrostedConfig
    {
        @ConfigEntry.Gui.CollapsibleObject
        public Damage damage = new Damage(2, 0.4, 1.2);
    }

    public static class Damage
    {
        public double addition;
        public double multiplier;
        public double base;

        public Damage(double addition, double multiplier, double base)
        {
            this.addition = addition;
            this.multiplier = multiplier;
            this.base = base;
        }
    }
}
