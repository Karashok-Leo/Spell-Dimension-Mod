package karashokleo.spell_dimension.data;

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
    }

    private static void addGroupTranslation(TranslationBuilder builder)
    {
        builder.add(AllGroups.BOOKS_GROUP_KEY, "咒次元: 法术书");
        builder.add(AllGroups.ELES_GROUP_KEY, "咒次元: 束魔精华");
        builder.add(AllGroups.ECES_GROUP_KEY, "咒次元: 附魔精华");
        builder.add(AllGroups.MISC_GROUP_KEY, "咒次元: 杂项");
    }

    private static void addItemTranslation(TranslationBuilder builder)
    {
        builder.add(AllItems.DEBUG_STAFF, "调试法杖");
        builder.add(AllItems.DISENCHANTED_ESSENCE, "祛魔精华");
        builder.add(AllItems.MENDING_ESSENCE, "修复精华");
    }

    private static void addStatusEffectTranslation(TranslationBuilder builder)
    {
        builder.add(AllStatusEffects.PHASE_EFFECT, "相位");
        builder.add(AllStatusEffects.IGNITE_EFFECT, "引火");
        builder.add(AllStatusEffects.FROST_AURA_EFFECT, "霜环");
        builder.add(AllStatusEffects.FROSTED_EFFECT, "霜冻");
    }
}
