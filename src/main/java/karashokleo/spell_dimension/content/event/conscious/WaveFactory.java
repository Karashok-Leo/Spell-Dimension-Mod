package karashokleo.spell_dimension.content.event.conscious;

import fuzs.illagerinvasion.init.ModRegistry;
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
    DEFAULT_EASY(0)
            {
                @Override
                public void fillWaves(ArrayList<Wave> waves)
                {
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 2));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 2));
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 4));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 4));
                }
            },
    ZOMBIE(9)
            {
                @Override
                public void fillWaves(ArrayList<Wave> waves)
                {
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 6));
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 8));
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 10));
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 12));
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 14));
                    waves.add(new Wave(Summoner.fromTag(AllTags.ZOMBIES), 16));
                }
            },
    SKELETON(10)
            {
                @Override
                public void fillWaves(ArrayList<Wave> waves)
                {
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 6));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 8));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 10));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 12));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 14));
                    waves.add(new Wave(Summoner.fromTag(AllTags.SKELETONS), 16));
                }
            },
    RAID(9)
            {
                @Override
                public void fillWaves(ArrayList<Wave> waves)
                {
                    waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_VANILLA), 6));
                    waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_VANILLA), 8));
                    waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_VANILLA), 10));
                    waves.add(new Wave(Summoner.fromEntityType(EntityType.RAVAGER), 3));
                    waves.add(new Wave(Summoner.fromEntityType(EntityType.EVOKER), 4));
                }
            },
    RAID_DIFFICULT(66)
            {
                @Override
                public void fillWaves(ArrayList<Wave> waves)
                {
                    waves.add(new Wave(Summoner.fromTag(EntityTypeTags.RAIDERS), 10));
                    waves.add(new Wave(Summoner.fromTag(EntityTypeTags.RAIDERS), 12));
                    waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_INVADE), 14));
                    waves.add(new Wave(Summoner.fromTag(AllTags.RAIDERS_INVADE), 16));
                    waves.add(new Wave(Summoner.fromEntityType(ModRegistry.FIRECALLER_ENTITY_TYPE.get()), 4));
                    waves.add(new Wave(Summoner.fromEntityType(ModRegistry.INVOKER_ENTITY_TYPE.get()), 1));
                }
            },

    ;
    private final int minLevel;

    WaveFactory(int minLevel)
    {
        this.minLevel = minLevel;
    }

    public static WaveFactory getRandom(Random random, int level)
    {
        List<WaveFactory> candidates = Arrays.stream(WaveFactory.values()).filter(factory -> level >= factory.minLevel).toList();
        return RandomUtil.randomFromList(random, candidates);
    }

    public abstract void fillWaves(ArrayList<Wave> waves);
}
