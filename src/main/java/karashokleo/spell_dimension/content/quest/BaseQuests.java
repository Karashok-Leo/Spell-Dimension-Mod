package karashokleo.spell_dimension.content.quest;

import karashokleo.l2hostility.L2Hostility;
import karashokleo.l2hostility.content.item.ConsumableItems;
import karashokleo.spell_dimension.config.AttributeModifier;
import karashokleo.spell_dimension.content.quest.base.SimpleAdvancementQuest;
import karashokleo.spell_dimension.content.quest.base.SimpleItemQuest;
import karashokleo.spell_dimension.content.quest.special.FirstDayQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.util.Identifier;
import net.spell_power.api.SpellPowerMechanics;

public class BaseQuests
{
    public static FirstDayQuest FIRST_DAY;
    public static SimpleAdvancementQuest CHOOSE_PATH;
    public static SimpleAdvancementQuest KILL_TRAIT;
    public static SimpleItemQuest HOSTILITY_ORB;

    public static void register()
    {
        FIRST_DAY = QuestBuilder.of("first_day", new FirstDayQuest(
                SDBags.MIDAS::getStack
            ))
            .addEnTitle("The Will Live")
            .addZhTitle("求生意志")
            .addEnDesc("Continuous survival for half a day without death (10 minutes)")
            .addZhDesc("不死亡的情况下连续存活半天（10分钟）")
            .addEnFeedback("You survived, in this strange environment full of magic. It's a good start, but doesn't necessarily portend a good ending. Hang in there, the true shape of this world will be revealed by your own hands.")
            .addZhFeedback("你活下来了，在这个充满魔力的陌生环境。这是一个好的开始，却不一定预示了一个好的结局。坚持下去吧，这个世界真实的模样将被你亲手揭开。")
            .addTag(AllTags.MAIN, AllTags.BEGINNING)
            .register();
        CHOOSE_PATH = QuestBuilder.of("choose_path", new SimpleAdvancementQuest(
                new Identifier("rpg_series:classes"),
                () -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                    new AttributeModifier(SpellPowerMechanics.CRITICAL_CHANCE.attribute, 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE).toELM()
                ),
                "advancements.rpg_series.classes",
                "spell_engine:spell_binding"
            ))
            .addEnFeedback("The rich magical environment and the inability to use magic directly in your physical body is driving you crazy, and it looks like you'll have to cast spells through these spell books. Good thing you have enough knowledge. You will need stronger mediums in the future.")
            .addZhFeedback("富裕的魔力环境和无法直接使用魔力的体质让你抓狂，看来你只能通过这些法术书来施法了。好在你有足够的知识。未来你会需要更强的媒介。")
            .toEntry("mage/bind")
            .addTag(AllTags.MAIN, AllTags.SKIPPABLE)
            .addDependencies(FIRST_DAY)
            .register();
        KILL_TRAIT = QuestBuilder.of("kill_trait", new SimpleAdvancementQuest(
                L2Hostility.id("hostility/kill_first"),
                () -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                    new AttributeModifier(SpellPowerMechanics.CRITICAL_DAMAGE.attribute, 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE).toELM()
                ),
                "advancements.l2hostility.kill_first",
                "minecraft:zombie_head"
            ))
            .addEnFeedback("It seems that with your arrival, the balance of the world has been upset, and the monsters will gradually become stronger, just like you.")
            .addZhFeedback("似乎，随着你的到来，这个世界的平衡被打破了，怪物将会和你一样逐渐变强。")
            .toEntry("power/hostility")
            .addTag(AllTags.MAIN)
            .addDependencies(FIRST_DAY)
            .register();
        HOSTILITY_ORB = QuestBuilder.of(
                "hostility_orb",
                new SimpleItemQuest(
                    () -> ConsumableItems.HOSTILITY_ORB,
                    () -> AllItems.ENLIGHTENING_ESSENCE.getStack(
                        new AttributeModifier(SpellPowerMechanics.HASTE.attribute, 0.05, EntityAttributeModifier.Operation.MULTIPLY_BASE).toELM()
                    )
                )
            )
            .addEnFeedback("Perhaps because of some kind of balance, when you kill a powerful enough monster, the magic of chaos condenses into this precious orb, which can purify a large area. Is this a choice the world has given you, or is it a salvation?")
            .addZhFeedback("或许因为某种平衡，当你击杀了足够强大的怪物后，混沌的魔力凝结成了这颗宝珠，它可以净化一大片区域。这是世界给你的一种选择，还是一种救赎？")
            .toEntry("power/hostility")
            .addTag(AllTags.MAIN)
            .addDependencies(FIRST_DAY)
            .register();
    }
}
