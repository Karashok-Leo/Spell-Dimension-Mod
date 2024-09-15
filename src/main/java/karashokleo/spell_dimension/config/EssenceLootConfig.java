package karashokleo.spell_dimension.config;

import net.mehvahdjukaar.dummmmmmy.Dummmmmmy;
import net.minecraft.entity.EntityType;
import net.minecraft.util.math.random.Random;

import java.util.List;

public class EssenceLootConfig
{
    public static final BaseConfig BASE_CONFIG = new BaseConfig(
            List.of(Dummmmmmy.TARGET_DUMMY.get()),
            0.1F,
            100, 200,
            0.90F, 0.99F
    );

    public static final EcEntry[] EC_ENTRIES = {
            new EcEntry(9, 1, 20),
            new EcEntry(3, 20, 40),
            new EcEntry(1, 40, 60)
    };
    public static final int EL_WEIGHT = 3;
    public static final int MD_WEIGHT = 2;

    public static final LootPool CHEST_POOL = new LootPool(1, 3, 160);
    public static final LootPool ENTITY_POOL = new LootPool(0, 2, 250);

    public record BaseConfig(
            List<EntityType<?>> blacklist,
            float dropChance,
            int intermediateLevel,
            int advancedLevel,
            float intermediateChance,
            float advancedChance
    )
    {
        public int getRandomGrade(Random random, int mobLevel)
        {
            int maxGrade = 0;
            if (mobLevel >= intermediateLevel) maxGrade++;
            if (mobLevel >= advancedLevel) maxGrade++;

            int randomGrade = 0;
            float f = random.nextFloat();
            if (f >= intermediateChance) randomGrade++;
            if (f >= advancedChance) randomGrade++;

            return Math.min(maxGrade, randomGrade);
//            if (mobLevel < intermediateLevel) return 0;
//            else if (mobLevel < advancedLevel)
//            {
//                if (random.nextFloat() < intermediateChance) return 0;
//                else return 1;
//            } else
//            {
//                float f = random.nextFloat();
//                if (f < intermediateChance) return 0;
//                else if (f < advancedChance) return 1;
//                else return 2;
//            }
        }
    }

    public record EcEntry(int weight, int minThreshold, int maxThreshold)
    {
    }

    public record LootPool(int minRolls, int maxRolls, int emptyWeight)
    {
    }
}
