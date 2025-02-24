package karashokleo.spell_dimension.content.object;

import com.kyanite.deeperdarker.content.DDEntities;
import fuzs.illagerinvasion.init.ModRegistry;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.RandomUtil;
import net.minecraft.entity.EntityType;
import net.minecraft.registry.tag.EntityTypeTags;
import net.minecraft.util.math.random.Random;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum WaveFactory
{
    DEFAULT_EASY(0, WavesFiller::fillDefaultEasy),
    ZOMBIE_10(10, WavesFiller::fillZombies),
    ZOMBIE_20(20, WavesFiller::fillZombies),
    ZOMBIE_30(30, WavesFiller::fillZombies),
    ZOMBIE_40(40, WavesFiller::fillZombies),
    ZOMBIE_50(50, WavesFiller::fillZombies),
    SKELETON_10(10, WavesFiller::fillSkeletons),
    SKELETON_20(20, WavesFiller::fillSkeletons),
    SKELETON_30(30, WavesFiller::fillSkeletons),
    SKELETON_40(40, WavesFiller::fillSkeletons),
    SKELETON_50(50, WavesFiller::fillSkeletons),
    UNDEAD_60(60, WavesFiller::fillUndeads),
    UNDEAD_70(70, WavesFiller::fillUndeads),
    UNDEAD_80(80, WavesFiller::fillUndeads),
    UNDEAD_90(90, WavesFiller::fillUndeads),
    RAIDER_12(12, WavesFiller::fillRaiders),
    RAIDER_24(24, WavesFiller::fillRaiders),
    RAIDER_36(36, WavesFiller::fillRaiders),
    RAIDER_48(48, WavesFiller::fillRaiders),
    RAIDER_60(60, WavesFiller::fillRaidersDifficult),
    RAIDER_72(72, WavesFiller::fillRaidersDifficult),
    RAIDER_84(84, WavesFiller::fillRaidersDifficult),
    RAIDER_96(96, WavesFiller::fillRaidersDifficult),
    NETHER_30(30, WavesFiller::fillNether),
    NETHER_45(45, WavesFiller::fillNether),
    NETHER_60(60, WavesFiller::fillNether),
    NETHER_75(75, WavesFiller::fillNether),
    NETHER_90(90, WavesFiller::fillNether),
    SCULK_60(60, WavesFiller::fillSculk),
    SCULK_75(75, WavesFiller::fillSculk),
    SCULK_90(90, WavesFiller::fillSculk),
    MELEE_60(60, WavesFiller::fillMelee),
    MELEE_75(75, WavesFiller::fillMelee),
    MELEE_90(90, WavesFiller::fillMelee),
    RANGED_60(60, WavesFiller::fillRanged),
    RANGED_75(75, WavesFiller::fillRanged),
    RANGED_90(90, WavesFiller::fillRanged),
    MUTANT(50, WavesFiller::fillMutant),
    ADVZ(50, WavesFiller::fillAdventureZ),
    SW(50, WavesFiller::fillSoulsWeapons),
    BOSS_RUSH(99, WavesFiller::fillBossRush),
    ;
    private final int minLevel;
    private final WavesFiller wavesFiller;

    WaveFactory(int minLevel, WavesFiller wavesFiller)
    {
        this.minLevel = minLevel;
        this.wavesFiller = wavesFiller;
    }

    public static WaveFactory getRandom(Random random, int level)
    {
        List<WaveFactory> candidates = Arrays.stream(WaveFactory.values()).filter(factory -> level >= factory.minLevel).toList();
        return RandomUtil.randomFromList(random, candidates);
    }

    public void fillWaves(Random random, ArrayList<Wave> waves)
    {
        this.wavesFiller.fillWaves(this.minLevel, random, waves);
    }

    @FunctionalInterface
    interface WavesFiller
    {
        void fillWaves(int level, Random random, ArrayList<Wave> waves);

        static void fillDefaultEasy(int level, Random random, ArrayList<Wave> waves)
        {

            waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 2));
            waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 2));
            waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 4));
            waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 4));
        }

        static void fillZombies(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = 3 + (level - 10) / 10;
            int waveBaseCount = level % 20 == 0 ? 8 : 6;
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), waveBaseCount + i * 2));
        }

        static void fillSkeletons(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = 3 + (level - 10) / 10;
            int waveBaseCount = level % 20 == 0 ? 8 : 6;
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), waveBaseCount + i * 2));
        }

        static void fillUndeads(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = 3 + (level - 10) / 10;
            int waveBaseCount = level % 20 == 0 ? 8 : 6;
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(random.nextBoolean() ? AllTags.ZOMBIES : AllTags.SKELETONS), waveBaseCount + i * 2));
        }

        static void fillRaiders(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = 3 + level / 30;
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_VANILLA), 6 + random.nextInt(i + 1)));
            waves.add(new Wave(Summoner.fromEntityType(EntityType.RAVAGER), random.nextInt(3) + 1));
            waves.add(new Wave(Summoner.fromEntityType(EntityType.EVOKER), random.nextInt(4) + 1));
        }

        static void fillRaidersDifficult(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = random.nextBetween(2, 4);
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(EntityTypeTags.RAIDERS), 10 + random.nextInt(i * 2 + 1)));
            for (int i = 0; i < 6 - waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_INVADE), 14 + random.nextInt(i * 2 + 1)));
            waves.add(new Wave(Summoner.fromEntityType(ModRegistry.FIRECALLER_ENTITY_TYPE.get()), 3 + random.nextInt(2)));
            waves.add(new Wave(Summoner.fromEntityType(ModRegistry.INVOKER_ENTITY_TYPE.get()), 1 + random.nextInt(2)));
        }

        static void fillNether(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = random.nextBetween(3, 5) + random.nextInt((level - 30) / 10 + 1);
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.NETHER), 4 + i * 2));
        }

        static void fillSculk(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = random.nextBetween(3, 5) + random.nextInt((level - 45) / 10 + 1);
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.SCULK), 4 + i * 2));
            if (random.nextBoolean())
                waves.add(new Wave(Summoner.fromEntityType(EntityType.WARDEN), 1));
            if (random.nextBoolean())
                waves.add(new Wave(Summoner.fromEntityType(DDEntities.STALKER), 1));
        }

        static void fillMelee(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = random.nextBetween(3, 5) + random.nextInt((level - 30) / 10 + 1);
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(LHTags.MELEE_WEAPON_TARGET), 6 + i * 2));
        }

        static void fillRanged(int level, Random random, ArrayList<Wave> waves)
        {
            int waveNum = random.nextBetween(3, 5) + random.nextInt((level - 30) / 10 + 1);
            for (int i = 0; i < waveNum; i++)
                waves.add(new Wave(Summoner.fromTag(LHTags.RANGED_WEAPON_TARGET), 6 + i * 2));
        }

        static void fillMutant(int level, Random random, ArrayList<Wave> waves)
        {
            waves.add(new Wave(Summoner.fromEntityType(fuzs.mutantmonsters.init.ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE.get()), random.nextBetween(2, 4)));
            waves.add(new Wave(Summoner.fromEntityType(fuzs.mutantmonsters.init.ModRegistry.MUTANT_SKELETON_ENTITY_TYPE.get()), random.nextBetween(2, 4)));
            waves.add(new Wave(Summoner.fromEntityType(fuzs.mutantmonsters.init.ModRegistry.MUTANT_CREEPER_ENTITY_TYPE.get()), random.nextBetween(2, 4)));
            waves.add(new Wave(Summoner.fromEntityType(fuzs.mutantmonsters.init.ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE.get()), random.nextBetween(2, 4)));
        }

        static void fillAdventureZ(int level, Random random, ArrayList<Wave> waves)
        {
            for (int i = 0; i < random.nextBetween(3, 6); i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.ADVZ_MONSTER), random.nextBetween(5, 10)));
        }

        static void fillSoulsWeapons(int level, Random random, ArrayList<Wave> waves)
        {
            for (int i = 0; i < random.nextBetween(3, 6); i++)
                waves.add(new Wave(Summoner.fromTag(AllTags.SW_MONSTER), random.nextBetween(5, 10)));
        }

        static void fillBossRush(int level, Random random, ArrayList<Wave> waves)
        {
            for (int i = 0; i < level / 10; i++)
                waves.add(new Wave(Summoner.fromTag(LHTags.SEMIBOSS), 1));
        }
    }
}
