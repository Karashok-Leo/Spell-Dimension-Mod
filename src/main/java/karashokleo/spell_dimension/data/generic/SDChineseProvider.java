package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.data.loot_bag.SDContents;
import karashokleo.spell_dimension.init.AllGroups;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;

public class SDChineseProvider extends FabricLanguageProvider
{
    public SDChineseProvider(FabricDataOutput dataOutput)
    {
        super(dataOutput, "zh_cn");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder)
    {
        for (SDTexts value : SDTexts.values())
            builder.add(value.getKey(), value.getZh());
        addGroupTranslation(builder);
        addItemTranslation(builder);
        addStatusEffectTranslation(builder);
        addLootBagTranslation(builder);
    }

    private static void addGroupTranslation(TranslationBuilder builder)
    {
        builder.add(AllGroups.BOOKS_GROUP_KEY, "咒次元: 法术书");
        builder.add(AllGroups.SPELL_SCROLLS_GROUP_KEY, "咒次元: 法术卷轴");
        builder.add(AllGroups.QUEST_SCROLLS_GROUP_KEY, "咒次元: 任务卷轴");
        builder.add(AllGroups.ELES_GROUP_KEY, "咒次元: 束魔精华");
        builder.add(AllGroups.ECES_GROUP_KEY, "咒次元: 附魔精华");
        builder.add(AllGroups.MISC_GROUP_KEY, "咒次元: 杂项");
    }

    private static void addItemTranslation(TranslationBuilder builder)
    {
        builder.add(AllItems.DEBUG_STAFF, "调试法杖");
        builder.add(AllItems.DISENCHANTED_ESSENCE, "祛魔精华");
        builder.add(AllItems.MENDING_ESSENCE, "修复精华");
        builder.add(AllItems.QUEST_SCROLL, "任务卷轴");
    }

    private static void addStatusEffectTranslation(TranslationBuilder builder)
    {
        builder.add(AllStatusEffects.PHASE, "相位");
        builder.add(AllStatusEffects.IGNITE, "引火");
        builder.add(AllStatusEffects.FROST_AURA, "霜环");
        builder.add(AllStatusEffects.FROSTED, "霜冻");
        builder.add(AllStatusEffects.SPELL_HASTE, "施法急速");
    }

    private static void addLootBagTranslation(TranslationBuilder builder)
    {
        for (SDContents ins : SDContents.values())
        {
            builder.add(ins.entry.nameKey(), ins.nameZh);
            builder.add(ins.entry.descKey(), ins.descZh);
        }
        for (SDBags ins : SDBags.values())
        {
            builder.add(ins.entry.nameKey(), ins.nameZh);
        }
    }
}
