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
                .addEnFeedback("Even in the age of magic, the simple alloy forge has not been completely forgotten, and you should be thankful that you still remember some of the physics and can build it using simple machinations.")
                .addZhFeedback("即使身处魔法时代，简单的冶炼炉也没有完全被忘记，你应该庆幸自己仍记得部分物理知识，使用简单的机巧就能把它打造出来。")
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
                .addEnFeedback("A forge controller made with stronger and more magically compatible materials, with which you can start crafting some truly magic-related items.")
                .addZhFeedback("使用更加坚固和魔法兼容性更好的材料制作的冶炼炉控制器，使用它你可以开始制作一些真正与魔法相关的物品了。")
                .toEntry("resource/alloy")
                .addTag(AllTags.MAIN, AllTags.SKIPPABLE)
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
                .addEnFeedback("An alloy forge built using advanced materials is the limit of your ability. But it's enough to smelt the strongest materials that you can use.")
                .addZhFeedback("使用高级材料打造的冶炼炉，这已经是你能力的极限。不过这足够了，使用它足以冶炼你所能使用的最强材料。")
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
                .addEnFeedback("A mix obtained by recasting a rare quality item back to its original form, which you can re-separate into new materials.")
                .addZhFeedback("将罕见品质的物品回炉重铸得到的混合品，你可以把它重新分离成新的材料。")
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
                .addEnFeedback("Recasting epic quality items back to recast the resulting mix, you find that the amount of material they can separate out is minimal.")
                .addZhFeedback("将史诗品质的物品回炉重铸得到的混合品，你发现它们可以分离出的材料数量极少。")
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
                .addEnFeedback("It can only be obtained through the highest level forge. You can only get a small amount of material from it, and that's as far as you go.")
                .addZhFeedback("你只能使用最高级的冶炼炉来获得它。从中只能获得少量的材料，这已经是你的极限了。")
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
                .addEnFeedback("You got this record by chance, and you can hear from the music on it that it's not of this world.")
                .addZhFeedback("你偶然得到了这张唱片，从其中的音乐可以听出它并不属于这个世界。")
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
                .addEnFeedback("You put a bunch of top tier materials together to try and take your chances, but instead you get something that has no idea what purpose. Is that a frog?")
                .addZhFeedback("你将一大堆顶级材料放到一起试图碰碰运气，但是却获得了一个不知有何用途的东西。那是一只青蛙？")
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
                .addEnFeedback("You can't infuse magic directly into an item, but by channeling the magic that is free from nature through the Infusion Pedestals, you have managed to do so. In this magic-rich environment, the process only takes a few seconds enough to complete.")
                .addZhFeedback("你无法直接将魔力灌注到物品上，但通过灌注基座引导游离于自然中的魔力，你成功做到了这一点。在这个魔力充足的环境，这个过程只需要几秒足以完成。")
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
                .addEnFeedback("A good medium for releasing magic directly from the body, the slightest tremor of magic can be felt when touched. Spells are instantly released simply by meditating on the secret message on them. Perhaps they could be stowed in a container?")
                .addZhFeedback("直接释放体内魔力的良好媒介，触及时能感受到魔力的细微震颤。仅需默念上面的密文，法术即刻释放。也许可以把它们收纳至一个容器中？")
                .toEntry("mage/scroll")
                .addTag(AllTags.MAIN)
                .addDependencies(ENCHANTMENT_INFUSION)
                .register();
    }
}
