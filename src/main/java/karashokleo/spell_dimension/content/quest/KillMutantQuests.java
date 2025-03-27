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
                                SDBags.UNCOMMON_MATERIAL::getStack
                        )
                )
                .addTag(AllTags.MAIN)
                .addDependencies(BaseQuests.KILL_TRAIT)
                .register();
        KILL_MUTANT_SKELETON = QuestBuilder.of(
                        "kill_mutant_skeleton",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_SKELETON_ENTITY_TYPE::get,
                                ModRegistry.MUTANT_SKELETON_SKULL_ITEM::get,
                                SDBags.UNCOMMON_MATERIAL::getStack
                        )
                )
                .addTag(AllTags.MAIN)
                .addDependencies(BaseQuests.KILL_TRAIT)
                .register();
        KILL_MUTANT_CREEPER = QuestBuilder.of(
                        "kill_mutant_creeper",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_CREEPER_ENTITY_TYPE::get,
                                ModRegistry.CREEPER_SHARD_ITEM::get,
                                SDBags.UNCOMMON_GEAR::getStack
                        )
                )
                .addTag(AllTags.MAIN)
                .addDependencies(BaseQuests.KILL_TRAIT)
                .register();
        KILL_MUTANT_ENDERMAN = QuestBuilder.of(
                        "kill_mutant_enderman",
                        new SimpleLootItemQuest(
                                ModRegistry.MUTANT_ENDERMAN_ENTITY_TYPE::get,
                                ModRegistry.ENDERSOUL_HAND_ITEM::get,
                                SDBags.UNCOMMON_BOOK::getStack
                        )
                )
                .addTag(AllTags.MAIN)
                .addDependencies(BaseQuests.KILL_TRAIT)
                .register();
    }
}
