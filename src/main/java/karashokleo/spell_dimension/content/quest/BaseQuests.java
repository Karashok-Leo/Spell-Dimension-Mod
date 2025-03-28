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
                .toEntry("mage/bind")
                .addTag(AllTags.MAIN)
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
                .toEntry("power/hostility")
                .addTag(AllTags.MAIN)
                .addDependencies(FIRST_DAY)
                .register();
    }
}
