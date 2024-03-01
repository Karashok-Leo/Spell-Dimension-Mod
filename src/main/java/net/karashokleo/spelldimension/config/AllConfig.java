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

    public int spell_essence_cool_down = 40;
    public int mage_medal_cool_down = 200;

    public String[] loot_blacklist = {"dummmmmmy:target_dummy"};

    @ConfigEntry.Gui.CollapsibleObject
    public ConvergeConfig converge = new ConvergeConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public BlazingMarkConfig blazing_mark = new BlazingMarkConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public NucleusConfig nucleus = new NucleusConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public FrostedConfig frosted = new FrostedConfig();

    @ConfigEntry.Gui.CollapsibleObject
    public LootConfig loot = new LootConfig();

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

    public static class LootConfig
    {
        @ConfigEntry.Gui.CollapsibleObject
        public ChestLoot chest_loot = new ChestLoot();
        @ConfigEntry.Gui.CollapsibleObject
        public MobLoot mob_loot = new MobLoot();

        public static class ChestLoot
        {
            public int min_rolls = 0;
            public int max_rolls = 3;
            public int empty_weight = 140;
            @ConfigEntry.Gui.CollapsibleObject
            public LootEntry common_loot = new LootEntry(9, 1, 20);
            @ConfigEntry.Gui.CollapsibleObject
            public LootEntry uncommon_loot = new LootEntry(3, 20, 40);
            @ConfigEntry.Gui.CollapsibleObject
            public LootEntry rare_loot = new LootEntry(1, 40, 60);

            public static class LootEntry
            {
                public int weight;
                public int min_threshold;
                public int max_threshold;

                public LootEntry(int weight, int min_threshold, int max_threshold)
                {
                    this.weight = weight;
                    this.min_threshold = min_threshold;
                    this.max_threshold = max_threshold;
                }
            }
        }

        public static class MobLoot
        {
            public float drop_chance = 0.8F;
            public float grade_0_1 = 0.9F;
            public float grade_1_2 = 0.99F;
        }
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
