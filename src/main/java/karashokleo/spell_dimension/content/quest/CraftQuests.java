package karashokleo.spell_dimension.content.quest;

import karashokleo.enchantment_infusion.init.EIItems;
import karashokleo.spell_dimension.content.quest.base.SimpleItemQuest;
import karashokleo.spell_dimension.content.quest.base.SimpleTagIngredientQuest;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

import java.util.List;

public class CraftQuests
{
    public static SimpleTagIngredientQuest FORGE_0;
    public static SimpleTagIngredientQuest FORGE_1;
    public static SimpleTagIngredientQuest FORGE_2;
    public static SimpleItemQuest RANDOM_MATERIAL_0;
    public static SimpleItemQuest RANDOM_MATERIAL_1;
    public static SimpleItemQuest RANDOM_MATERIAL_2;
    public static SimpleItemQuest DOGE;
    public static SimpleItemQuest FROGE;
    public static SimpleItemQuest ENCHANTMENT_INFUSION;
    public static SimpleItemQuest SPELL_SCROLL;

    public static void register()
    {
        FORGE_0 = QuestBuilder.of(
                        "forge_0",
                        new SimpleTagIngredientQuest(
                                AllTags.FORGE_CONTROLLERS.get(0),
                                SDBags.ARTIFACT::getStack
                        )
                )
                .addEnDesc("Craft any level 1 Forge Controller and build a level 1 Alloy Forge")
                .addZhDesc("合成任意1级冶炼控制器，并建造1级合金冶炼炉")
                .toEntry("resource/alloy")
                .addTag(AllTags.MAIN)
                .addDependencies(BaseQuests.HOSTILITY_ORB)
                .register();
        FORGE_1 = QuestBuilder.of(
                        "forge_1",
                        new SimpleTagIngredientQuest(
                                AllTags.FORGE_CONTROLLERS.get(1),
                                SDBags.ARING::getStack
                        )
                )
                .addEnDesc("Craft any level 2 Forge Controller and build a level 2 Alloy Forge")
                .addZhDesc("合成任意2级冶炼控制器，并建造2级合金冶炼炉")
                .toEntry("resource/alloy")
                .addTag(AllTags.MAIN)
                .addDependencies(FORGE_0)
                .register();
        FORGE_2 = QuestBuilder.of(
                        "forge_2",
                        new SimpleTagIngredientQuest(
                                AllTags.FORGE_CONTROLLERS.get(2),
                                SDBags.ARTIFACT::getStack
                        )
                )
                .addEnDesc("Craft any level 3 Forge Controller and build a level 3 Alloy Forge")
                .addZhDesc("合成任意3级冶炼控制器，并建造3级合金冶炼炉")
                .toEntry("resource/alloy")
                .addTag(AllTags.MAIN)
                .addDependencies(FORGE_1)
                .register();
        RANDOM_MATERIAL_0 = QuestBuilder.of(
                        "random_material_0",
                        new SimpleItemQuest(
                                () -> AllItems.RANDOM_MATERIAL.get(2),
                                SDBags.ARING::getStack
                        )
                )
                .addTag(AllTags.BRANCH)
                .addDependencies(FORGE_0)
                .register();
        RANDOM_MATERIAL_1 = QuestBuilder.of(
                        "random_material_1",
                        new SimpleItemQuest(
                                () -> AllItems.RANDOM_MATERIAL.get(3),
                                SDBags.ARTIFACT::getStack
                        )
                )
                .addTag(AllTags.BRANCH)
                .addDependencies(FORGE_1)
                .register();
        RANDOM_MATERIAL_2 = QuestBuilder.of(
                        "random_material_2",
                        new SimpleItemQuest(
                                () -> AllItems.RANDOM_MATERIAL.get(4),
                                SDBags.ARING::getStack
                        )
                )
                .addTag(AllTags.BRANCH)
                .addDependencies(FORGE_2)
                .register();
        DOGE = QuestBuilder.of(
                        "doge",
                        new SimpleItemQuest(
                                () -> Registries.ITEM.get(new Identifier("mythicmetals:doge")),
                                SDBags.ARTIFACT::getStack
                        )
                )
                .addEnTitle("Doge:)")
                .addZhTitle("Doge:)")
                .addEnDesc("Use a level 3 Alloy Forge to craft")
                .addZhDesc("使用3级冶炼炉合成")
                .addTag(AllTags.BRANCH, AllTags.CHALLENGE)
                .addDependencies(FORGE_2)
                .register();
        FROGE = QuestBuilder.of(
                        "froge",
                        new SimpleItemQuest(
                                () -> Registries.ITEM.get(new Identifier("mythicmetals:froge")),
                                SDBags.ARING::getStack
                        )
                )
                .addEnTitle("Croak~")
                .addZhTitle("呱呱乐")
                .addEnDesc("Use a level 3 Alloy Forge to craft")
                .addZhDesc("使用3级冶炼炉合成")
                .addTag(AllTags.BRANCH, AllTags.CHALLENGE)
                .addDependencies(FORGE_2)
                .register();
        ENCHANTMENT_INFUSION = QuestBuilder.of(
                        "enchantment_infusion",
                        new SimpleItemQuest(
                                List.of(
                                        () -> EIItems.INFUSION_PEDESTAL_ITEM,
                                        () -> EIItems.INFUSION_TABLE_ITEM
                                ),
                                SDBags.ARTIFACT::getStack
                        )
                )
                .toEntry("power/enchant")
                .addTag(AllTags.MAIN)
                .addDependencies(BaseQuests.HOSTILITY_ORB)
                .register();
        SPELL_SCROLL = QuestBuilder.of(
                        "spell_scroll",
                        new SimpleItemQuest(
                                () -> AllItems.SPELL_SCROLL,
                                SDBags.ARING::getStack
                        )
                )
                .toEntry("mage/scroll")
                .addTag(AllTags.MAIN)
                .addDependencies(ENCHANTMENT_INFUSION)
                .register();
    }
}
