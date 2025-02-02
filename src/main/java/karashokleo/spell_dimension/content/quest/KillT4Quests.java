package karashokleo.spell_dimension.content.quest;

import karashokleo.l2hostility.content.item.TrinketItems;
import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class KillT4Quests
{
    public static SimpleLootItemQuest KILL_OLD_CHAMPION;
    public static SimpleLootItemQuest KILL_DECAYING_KING;
    public static SimpleLootItemQuest KILL_ELDER_GUARDIAN;
    public static SimpleLootItemQuest KILL_WITHER;
    public static SimpleLootItemQuest KILL_INVOKER;
    public static SimpleLootItemQuest KILL_WARDEN;

    public static void register()
    {
        KILL_OLD_CHAMPION = QuestBuilder.of(
                        "kill_old_champion",
                        new SimpleLootItemQuest(
                                List.of(
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:draugr_boss")),
                                        () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:night_shade"))
                                ),
                                List.of(() -> Registries.ITEM.get(new Identifier("soulsweapons:essence_of_eventide"))),
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .toEntry("boss/old_champion")
                .addDependencies(KillMutantQuests.KILL_MUTANT_ZOMBIE)
                .register();
        KILL_DECAYING_KING = QuestBuilder.of(
                        "kill_decaying_king",
                        new SimpleLootItemQuest(
                                () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:accursed_lord_boss")),
                                () -> AllItems.ACCURSED_BLACKSTONE,
                                TrinketItems.LOOT_1::getDefaultStack
                        )
                )
                .toEntry("boss/decaying_king")
                .addDependencies(KillMutantQuests.KILL_MUTANT_ZOMBIE)
                .register();
        KILL_ELDER_GUARDIAN = QuestBuilder.of(
                        "kill_elder_guardian",
                        new SimpleLootItemQuest(
                                () -> EntityType.ELDER_GUARDIAN,
                                () -> AllItems.ABYSS_GUARD,
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .toEntry("boss/guardian")
                .addDependencies(KillMutantQuests.KILL_MUTANT_SKELETON)
                .register();
        KILL_WITHER = QuestBuilder.of(
                        "kill_wither",
                        new SimpleLootItemQuest(
                                () -> EntityType.WITHER,
                                () -> Items.NETHER_STAR,
                                SDBags.RARE_BOOK::getStack
                        )
                )
                .toEntry("boss/wither")
                .addDependencies(KillMutantQuests.KILL_MUTANT_SKELETON)
                .register();
        KILL_INVOKER = QuestBuilder.of(
                        "kill_invoker",
                        new SimpleLootItemQuest(
                                "illagerinvasion:invoker",
                                "illagerinvasion:primal_essence",
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .toEntry("boss/invoker")
                .addDependencies(KillMutantQuests.KILL_MUTANT_CREEPER)
                .register();
        KILL_WARDEN = QuestBuilder.of(
                        "kill_warden",
                        new SimpleLootItemQuest(
                                () -> EntityType.WARDEN,
                                () -> Registries.ITEM.get(new Identifier("deeperdarker:heart_of_the_deep")),
                                SDBags.RARE_GEAR::getStack
                        )
                )
                .toEntry("boss/warden")
                .addDependencies(KillMutantQuests.KILL_MUTANT_CREEPER)
                .register();
    }
}
