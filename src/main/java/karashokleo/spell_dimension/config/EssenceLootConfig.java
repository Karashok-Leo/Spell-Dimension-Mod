package karashokleo.spell_dimension.config;

import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class EssenceLootConfig
{
    public static final BaseConfig BASE_CONFIG = new BaseConfig(0.8F, 0.9F, 0.99F, List.of(Dummmmmmy.TARGET_DUMMY.get()));

    public static final int EL_WEIGHT = 6;
    public static final int MD_WEIGHT = 3;
    public static final EcEntry[] EC_ENTRIES = {
            new EcEntry(9, 1, 20),
            new EcEntry(3, 20, 40),
            new EcEntry(1, 40, 60)
    };

    public static final LootPool CHEST_POOL = new LootPool(1, 3, 100);
    public static final LootPool ENTITY_POOL = new LootPool(0, 2, 180);

    public record BaseConfig(float dropChance, float advancedChance, float perfectChance, List<EntityType<?>> blacklist)
    {
        public int getRandomGrade(Random random)
        {
            float f = random.nextFloat();
            if (f < advancedChance) return 0;
            else if (f < perfectChance) return 1;
            else return 2;
        }
    }

    public record EcEntry(int weight, int minThreshold, int maxThreshold)
    {
    }

    public record LootPool(int minRolls, int maxRolls, int emptyWeight)
    {
    }
}
