package net.karashokleo.spelldimension.config.mod_config;

public class LootConfig
{
    public FunctionConfig function = new FunctionConfig();

    public EssenceLoot essence_loot = new EssenceLoot();

    public static class FunctionConfig
    {
        public float generic_chance = 0.16F;
        public FunctionConfig.LootPool chest_pool = new FunctionConfig.LootPool(1, 3, 100, new FunctionConfig.LootEntry[]{
                new FunctionConfig.LootEntry(9, 1, 20),
                new FunctionConfig.LootEntry(3, 20, 40),
                new FunctionConfig.LootEntry(1, 40, 60)
        });
        public FunctionConfig.LootPool entity_pool = new FunctionConfig.LootPool(0, 2, 180, new FunctionConfig.LootEntry[]{
                new FunctionConfig.LootEntry(9, 1, 20),
                new FunctionConfig.LootEntry(3, 20, 40),
                new FunctionConfig.LootEntry(1, 40, 60)
        });

        public static class LootPool
        {
            public int min_rolls;
            public int max_rolls;
            public int empty_weight;
            public FunctionConfig.LootEntry[] entries;

            public LootPool(int min_rolls, int max_rolls, int empty_weight, FunctionConfig.LootEntry[] entries)
            {
                this.min_rolls = min_rolls;
                this.max_rolls = max_rolls;
                this.empty_weight = empty_weight;
                this.entries = entries;
            }
        }

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

    public static class EssenceLoot
    {
        public float drop_chance = 0.8F;
        public float grade_0_1 = 0.9F;
        public float grade_1_2 = 0.99F;
    }
}
