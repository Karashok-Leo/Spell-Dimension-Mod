package karashokleo.spell_dimension.content.quest;

import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
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
                .addEnFeedback("The ancient champion lies dormant in the underground grave, and the power of the night takes over its body. When it returns to the earth, you see the power of the night overflowing. It is no longer alone. As the battle sweeps on, the champion's heart breastplate will give you shelter.")
                .addZhFeedback("远古的英雄沉眠于地下陵墓之中，属于黑夜的力量占据了它的身体。当那副身躯回归大地，你看到黑夜的力量从中溢出。它不再是孤单一人。战斗席卷而去，属于英雄的护心镜将给予你庇护。")
                .toEntry("boss/old_champion")
                .addTag(AllTags.MAIN)
                .addDependencies(KillMutantQuests.KILL_MUTANT_ZOMBIE)
                .register();
        KILL_DECAYING_KING = QuestBuilder.of(
                        "kill_decaying_king",
                        new SimpleLootItemQuest(
                                () -> Registries.ENTITY_TYPE.get(new Identifier("soulsweapons:accursed_lord_boss")),
                                () -> AllItems.ACCURSED_BLACKSTONE,
                                SDBags.RARE_MATERIAL::getStack
                        )
                )
                .addEnFeedback("He watched in despair as his kingdom was engulfed in lava, the decaying crown shimmering weakly above him. He vaguely understands that perhaps this is the best end for him...")
                .addZhFeedback("他绝望地看着自己的王国被熔岩吞噬，腐朽的王冠在他头上闪烁着微弱的光芒。他隐约明白，也许这就是对他来说最好的结局...")
                .toEntry("boss/decaying_king")
                .addTag(AllTags.MAIN)
                .addDependencies(KillMutantQuests.KILL_MUTANT_ZOMBIE)
                .register();
        KILL_ELDER_GUARDIAN = QuestBuilder.of(
                        "kill_elder_guardian",
                        new SimpleLootItemQuest(
                                () -> EntityType.ELDER_GUARDIAN,
                                () -> AllItems.ABYSS_GUARD,
                                SDBags.RARE_BOOK::getStack
                        )
                )
                .addEnFeedback("You sensed the gaze that originated from the abyss. So you dove to the bottom of the sea, and in the monument you defeated it. But you never understood whether its gaze was watching you or looking into the infinite depths.")
                .addZhFeedback("你察觉到了那源自深渊的凝视。于是你潜入海底，在神殿中你击败了它。但你始终不明白，它的目光究竟是在注视你，还是望向那片无垠深海。")
                .toEntry("boss/guardian")
                .addTag(AllTags.MAIN)
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
                .addEnFeedback("The ultimate undead from hell, trying to destroy everything living within sight. Killing it takes a lot out of you, but it's worth it.")
                .addZhFeedback("来自地狱的究极亡灵，试图将视线所及的一切活物毁灭殆尽。杀死它耗费了你不少的心力，但是这一切都是值得的。")
                .toEntry("boss/wither")
                .addTag(AllTags.MAIN)
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
                .addEnFeedback("The leader of pillagers, a powerful mage who wields the primal magic. You thought he had faded into the dust of history, yet now he has reappeared. But his power is not what it once was, and he is doomed to perish under your spells.")
                .addZhFeedback("掠夺者们的首领，掌握着始源魔法的强大法师。你本以为他已随历史尘烟消散，然而现在他又出现了。只是他的力量已经大不如从前，注定泯灭在你的法术之下。")
                .toEntry("boss/invoker")
                .addTag(AllTags.MAIN)
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
                .addEnFeedback("Born in sculk veins, it symbolizes darkness and fear. You hold its still-beating heart in your hand, said to be the key to a certain portal.")
                .addZhFeedback("于幽匿脉络中诞生，它象征着黑暗与恐惧。你手握着它仍未停止跳动的心脏，据说那是打开某扇门的钥匙。")
                .toEntry("boss/warden")
                .addTag(AllTags.MAIN)
                .addDependencies(KillMutantQuests.KILL_MUTANT_CREEPER)
                .register();
    }
}
