package net.karashokleo.spelldimension.config.mod_config;

public class LootConfig
{
    public RandomEssenceConfig random_essence = new RandomEssenceConfig();

    public EssenceLoot essence_loot = new EssenceLoot();

    public static class RandomEssenceConfig
    {
        public float generic_chance = 0.16F;
        public RandomEssenceConfig.LootPool chest_pool = new RandomEssenceConfig.LootPool(1, 3, 100, 6, 3, new EcEntry[]{
                new EcEntry(9, 1, 20),
                new EcEntry(3, 20, 40),
                new EcEntry(1, 40, 60)
        });
        public RandomEssenceConfig.LootPool entity_pool = new RandomEssenceConfig.LootPool(0, 2, 180, 6, 3, new EcEntry[]{
                new EcEntry(9, 1, 20),
                new EcEntry(3, 20, 40),
                new EcEntry(1, 40, 60)
        });

        public static class LootPool
        {
            public int min_rolls;
            public int max_rolls;
            public int empty_weight;
            public int el_weight;
            public int md_weight;
            public EcEntry[] entries;

            public LootPool(int min_rolls, int max_rolls, int empty_weight, int el_weight, int md_weight, EcEntry[] entries)
            {
                this.min_rolls = min_rolls;
                this.max_rolls = max_rolls;
                this.empty_weight = empty_weight;
                this.el_weight = el_weight;
                this.md_weight = md_weight;
                this.entries = entries;
            }
        }

        public static class EcEntry
        {
            public int weight;
            public int min_threshold;
            public int max_threshold;

            public EcEntry(int weight, int min_threshold, int max_threshold)
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
