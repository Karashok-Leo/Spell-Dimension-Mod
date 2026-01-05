package karashokleo.spell_dimension.content.quest;

import fuzs.mutantmonsters.init.ModRegistry;
import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllTags;

public class KillMutantQuests
{
    public static SimpleLootItemQuest KILL_MUTANT_ZOMBIE;
    public static SimpleLootItemQuest KILL_MUTANT_SKELETON;
    public static SimpleLootItemQuest KILL_MUTANT_CREEPER;
    public static SimpleLootItemQuest KILL_MUTANT_ENDERMAN;

    public static void register()
    {
        KILL_MUTANT_ZOMBIE = QuestBuilder.of(
                "kill_mutant_zombie",
                new SimpleLootItemQuest(
                    ModRegistry.MUTANT_ZOMBIE_ENTITY_TYPE::get,
                    ModRegistry.HULK_HAMMER_ITEM::get,
                    SDBags.UNCOMMON$MATERIAL::getStack
                )
            )
            .addEnFeedback("The otherworldly conflict caused the Zombie to mutate, and it gained the power to return from the dead. However, the flames still work wonders on it.")
            .addZhFeedback("异界的冲突让僵尸产生了异变，它获得了从死亡中回归的力量。不过，火焰依旧对它有奇效。")
            .toEntry("boss/t5/mutant_zombie")
            .addTag(AllTags.MAIN, AllTags.SKIPPABLE)
            .addDependencies(BaseQuests.KILL_TRAIT)
            .register();
        KILL_MUTANT_SKELETON = QuestBuilder.of(
                "kill_mutant_skeleton",
                new SimpleLootItemQuest(
                    ModRegistry.MUTANT_SKELETON_ENTITY_TYPE::get,
                    ModRegistry.MUTANT_SKELETON_SKULL_ITEM::get,
                    SDBags.UNCOMMON$MATERIAL::getStack
                )
            )
            .addEnFeedback("The otherworldly conflict caused the Skeleton to mutate, and its massive bony body is a wonder to behold. You pick up the bones that burst to the ground when it died, they give off an ominous aura that might come in handy later.")
            .addZhFeedback("异界的冲突让骷髅产生了异变，它的庞大骨躯令人啧啧称奇。你拾起了它死亡时爆落一地的骨头，它们散发着不祥气息，或许以后还用得上。")
            .toEntry("boss/t5/mutant_skeleton")
            .addTag(AllTags.MAIN, AllTags.SKIPPABLE)
            .addDependencies(BaseQuests.KILL_TRAIT)
            .register();
        KILL_MUTANT_CREEPER = QuestBuilder.of(
                "kill_mutant_creeper",
                new SimpleLootItemQuest(
                    ModRegistry.MUTANT_CREEPER_ENTITY_TYPE::get,
                    ModRegistry.CREEPER_SHARD_ITEM::get,
                    SDBags.UNCOMMON$GEAR::getStack
                )
            )
            .addEnFeedback("The otherworldly conflict caused the Creeper to mutate, and it no longer needs to ignite itself to cause an explosion. The egg it left behind after its death was broken by you, and transformed into a highly unstable shard. You realize that perhaps this is the way to break the Arena suppression.")
            .addZhFeedback("异界的冲突让苦力怕产生了异变，它不再需要点燃自己就能引发爆炸。它死后遗留下来的蛋被你打破，化作一个极不稳定的碎片。你意识到，也许这正是领域压制的破解之道。")
            .toEntry("boss/t5/mutant_creeper")
            .addTag(AllTags.MAIN, AllTags.SKIPPABLE)
            .addDependencies(BaseQuests.KILL_TRAIT)
            .register();
        KILL_MUTANT_ENDERMAN = QuestBuilder.of(
                "kill_mutant_enderman",
                new SimpleLootItemQuest(
                    ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE::get,
                    ModRegistry.ENDERSOUL_HAND_ITEM::get,
                    SDBags.UNCOMMON$BOOK::getStack
                )
            )
            .addEnFeedback("The otherworldly conflict caused the Enderman to mutate, with new arms growing from its torso and the ender power no longer confined to its body.")
            .addZhFeedback("异界的冲突让末影人产生了异变，它的躯干上生长出了新的手臂，末影的力量也不再局限于它的身体中。")
            .toEntry("boss/t5/mutant_enderman")
            .addTag(AllTags.MAIN, AllTags.SKIPPABLE)
            .addDependencies(BaseQuests.KILL_TRAIT)
            .register();
    }
}
