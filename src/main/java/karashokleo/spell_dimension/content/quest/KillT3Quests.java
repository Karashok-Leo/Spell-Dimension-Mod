package karashokleo.spell_dimension.content.quest;

import com.obscuria.aquamirae.registry.AquamiraeEntities;
import com.obscuria.aquamirae.registry.AquamiraeItems;
import karashokleo.spell_dimension.content.quest.base.SimpleLootItemQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllTags;
import net.adventurez.init.EntityInit;
import net.adventurez.init.ItemInit;

public class KillT3Quests
{
    public static SimpleLootItemQuest KILL_MOON_KNIGHT;
    public static SimpleLootItemQuest KILL_BLACKSTONE_GOLEM;
    public static SimpleLootItemQuest KILL_CAPTAIN_CORNELIA;
    public static SimpleLootItemQuest KILL_CHAOS_MONARCH;
    public static SimpleLootItemQuest KILL_RETURNING_KNIGHT;
    public static SimpleLootItemQuest KILL_STALKER;

    public static void register()
    {
        KILL_MOON_KNIGHT = QuestBuilder.of(
                "kill_moon_knight",
                new SimpleLootItemQuest(
                    "soulsweapons:moonknight",
                    "soulsweapons:essence_of_luminescence",
                    SDBags.RARE$BOOK::getStack
                )
            )
            .addEnFeedback("His entire body was covered in deep dark runes, his skin fused with his armor, his soul banished, leaving only a shell containing raw power. That armor, now a prison for the lingering power of moonlight, transforming him into a puppet. As night falls, he comes at you wielding a giant hammer, and you make your choice.")
            .addZhFeedback("他全身布满了深邃的黑暗符文，肌肤与盔甲融为一体，灵魂已被放逐，仅剩一具蕴含原始力量的躯壳。那副盔甲，如今已成为囚禁残留月光之力的牢笼，化作一具傀儡。夜幕降临，他挥舞着巨锤向你袭来，而你也做出了自己的选择。")
            .toEntry("boss/t3/moon_knight")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT4Quests.KILL_OLD_CHAMPION)
            .register();
        KILL_BLACKSTONE_GOLEM = QuestBuilder.of(
                "kill_blackstone_golem",
                new SimpleLootItemQuest(
                    () -> EntityInit.BLACKSTONE_GOLEM,
                    () -> ItemInit.BLACKSTONE_GOLEM_HEART,
                    SDBags.RARE$BOOK::getStack
                )
            )
            .addEnFeedback("The ancient golem summoned through the ritual seems to have been revived from the world's deepest memories. From that core, you can feel a powerful force surging.")
            .addZhFeedback("通过仪式召唤出的古代傀儡，仿佛是从世界深处的记忆中复苏而来。从那颗核心中,你能感受到一股强大的力量在涌动。")
            .toEntry("boss/t3/blackstone_golem")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT4Quests.KILL_DECAYING_KING)
            .register();
        KILL_CAPTAIN_CORNELIA = QuestBuilder.of(
                "kill_captain_cornelia",
                new SimpleLootItemQuest(
                    () -> AquamiraeEntities.CAPTAIN_CORNELIA,
                    () -> AquamiraeItems.FROZEN_KEY,
                    SDBags.RARE$GEAR::getStack
                )
            )
            .addEnFeedback("The departed soul was dragged into the boundless abyss, and cold magic penetrated every ounce of her soul. By using the power of the Abyssal Guardian to unseal her and blowing the horn, that departed king of the sea returned from the abyss on the sound of the horn. You ended her life, but for her it was a relief.")
            .addZhFeedback("逝去的灵魂被拖入无边的深渊，冰冷的魔力渗透了她每一丝灵魂。借助深渊守护之力解除她的封印，吹响号角，那位逝去的海上王者便乘着号角之声从深渊中归来。你终结了她的生命，对她而言，这却是一种解脱。")
            .toEntry("boss/t3/captain")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT4Quests.KILL_ELDER_GUARDIAN)
            .register();
        KILL_CHAOS_MONARCH = QuestBuilder.of(
                "kill_chaos_monarch",
                new SimpleLootItemQuest(
                    "soulsweapons:chaos_monarch",
                    "soulsweapons:chaos_crown",
                    SDBags.RARE$GEAR::getStack
                )
            )
            .addEnFeedback("Not everyone can harness the power of Chaos, and anyone who touches its edge will be consumed by it. You have no intention of fighting him, but the crown that contains the power of Chaos is yours for the taking.")
            .addZhFeedback("并非所有人都能驾驭混沌之力，凡触及其边缘者，终将被其吞噬。你原无意与他交锋，但那顶蕴含混沌力量的冠冕，你志在必得。")
            .toEntry("boss/t3/chaos_monarch")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT4Quests.KILL_WITHER)
            .register();
        KILL_RETURNING_KNIGHT = QuestBuilder.of(
                "kill_returning_knight",
                new SimpleLootItemQuest(
                    "soulsweapons:returning_knight",
                    "soulsweapons:arkenstone",
                    SDBags.RARE$MATERIAL::getStack
                )
            )
            .addEnFeedback("While there are few warriors who will be remembered by the world, those who display fearless courage are as numerous as the stars. He wielded his sword and slashed his path toward you. He does not represent a particular warrior, but once symbolized courage itself.")
            .addZhFeedback("虽然能被世人铭记的勇士寥寥无几，但展现无畏勇气的人却如繁星般众多。他挥舞着剑，将道道剑光向你劈来。他并非代表某一位特定的勇士，而是曾经象征着勇气本身。")
            .toEntry("boss/t3/returning_knight")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT4Quests.KILL_INVOKER)
            .register();
        KILL_STALKER = QuestBuilder.of(
                "kill_stalker",
                new SimpleLootItemQuest(
                    "deeperdarker:stalker",
                    "deeperdarker:soul_crystal",
                    SDBags.RARE$MATERIAL::getStack
                )
            )
            .addEnFeedback("The vases that were once used for offerings in the ancient temple in the deeper dark contained a mysterious power. However, to this day, that power has been corrupted by the sculk and has become an extension of the dark will. It emerged from the broken vase and attempted to devour you. It is a pity that it did not succeed in its attempt.")
            .addZhFeedback("幽匿深处的古代神殿里，曾用于供奉的花瓶中蕴藏着神秘的力量。然而，时至今日，这股力量已被幽匿侵蚀，成为了幽匿意志的延伸。它从破碎的花瓶中涌现，试图将你吞噬。只可惜，它的企图并未得逞。")
            .toEntry("boss/t3/stalker")
            .addTag(AllTags.MAIN)
            .addDependencies(KillT4Quests.KILL_WARDEN)
            .register();
    }
}
