package karashokleo.spell_dimension.config;

import karashokleo.spell_dimension.init.AllSpells;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.random.Random;

import java.util.Set;

public class EssenceLootConfig
{
    public static final BaseConfig BASE_CONFIG = new BaseConfig(
        Set.of(AllSpells.SOUL_COMMAND),
        0.2F,
        160, 560,
        0.30F, 0.08F
    );

    public static final int EC_WEIGHT = 7;
    public static final int EL_WEIGHT = 1;
    public static final int MD_WEIGHT = 2;

    public static final LootPool CHEST_POOL = new LootPool(1, 3, 120);
    public static final LootPool ENTITY_POOL = new LootPool(0, 2, 210);

    public record BaseConfig(
        Set<Identifier> spellBlacklist,
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
            if (mobLevel >= intermediateLevel)
            {
                maxGrade++;
            }
            if (mobLevel >= advancedLevel)
            {
                maxGrade++;
            }

            int randomGrade = 0;
            float f = random.nextFloat();
            if (f <= intermediateChance)
            {
                randomGrade++;
            }
            if (f <= advancedChance)
            {
                randomGrade++;
            }

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

    public record LootPool(int minRolls, int maxRolls, int emptyWeight)
    {
    }
}
