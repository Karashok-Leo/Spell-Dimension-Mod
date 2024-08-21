package karashokleo.spell_dimension.data.generic;

import karashokleo.spell_dimension.data.SDTexts;
import karashokleo.spell_dimension.data.loot_bag.SDBags;
import karashokleo.spell_dimension.data.loot_bag.SDContents;
import karashokleo.spell_dimension.init.AllGroups;
import karashokleo.spell_dimension.init.AllItems;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;

public class SDEnglishProvider extends FabricLanguageProvider
{
    public SDEnglishProvider(FabricDataOutput output)
    {
        super(output, "en_us");
    }

    @Override
    public void generateTranslations(TranslationBuilder builder)
    {
        for (SDTexts value : SDTexts.values())
            builder.add(value.getKey(), value.getEn());
        addGroupTranslation(builder);
        addItemTranslation(builder);
        addStatusEffectTranslation(builder);
        addLootBagTranslation(builder);
    }

    private static void addGroupTranslation(TranslationBuilder builder)
    {
        builder.add(AllGroups.BOOKS_GROUP_KEY, "Spell Dimension: Spell Books");
        builder.add(AllGroups.SPELL_SCROLLS_GROUP_KEY, "Spell Dimension: Spell Scrolls");
        builder.add(AllGroups.QUEST_SCROLLS_GROUP_KEY, "Spell Dimension: Quest Scrolls");
        builder.add(AllGroups.ELES_GROUP_KEY, "Spell Dimension: Enlightening Essences");
        builder.add(AllGroups.ECES_GROUP_KEY, "Spell Dimension: Enchanted Essences");
        builder.add(AllGroups.MISC_GROUP_KEY, "Spell Dimension: Misc");
    }

    private static void addItemTranslation(TranslationBuilder builder)
    {
        builder.add(AllItems.DEBUG_STAFF, getName(AllItems.DEBUG_STAFF));
        builder.add(AllItems.DISENCHANTED_ESSENCE, getName(AllItems.DISENCHANTED_ESSENCE));
        builder.add(AllItems.MENDING_ESSENCE, getName(AllItems.MENDING_ESSENCE));
        builder.add(AllItems.QUEST_SCROLL, getName(AllItems.QUEST_SCROLL));
    }

    private static void addStatusEffectTranslation(TranslationBuilder builder)
    {
        builder.add(AllStatusEffects.PHASE, getName(AllStatusEffects.PHASE));
        builder.add(AllStatusEffects.IGNITE, getName(AllStatusEffects.IGNITE));
        builder.add(AllStatusEffects.FROST_AURA, getName(AllStatusEffects.FROST_AURA));
        builder.add(AllStatusEffects.FROSTED, getName(AllStatusEffects.FROSTED));
        builder.add(AllStatusEffects.SPELL_HASTE, getName(AllStatusEffects.SPELL_HASTE));
    }

    private static void addLootBagTranslation(TranslationBuilder builder)
    {
        for (SDContents ins : SDContents.values())
        {
            builder.add(ins.entry.nameKey(), ins.nameEn);
            builder.add(ins.entry.descKey(), ins.descEn);
        }
        for (SDBags ins : SDBags.values())
        {
            builder.add(ins.entry.nameKey(), ins.nameEn);
        }
    }

    private static String getName(Item item)
    {
        Identifier id = Registries.ITEM.getId(item);
        return getDefaultName(id);
    }

    private static String getName(StatusEffect effect)
    {
        Identifier id = Registries.STATUS_EFFECT.getId(effect);
        assert id != null;
        return getDefaultName(id);
    }

    private static String getDefaultName(String name)
    {
        String[] words = name.split("_");
        StringBuilder sb = new StringBuilder();
        for (String word : words)
            if (!word.isEmpty())
                sb.append(Character.toUpperCase(word.charAt(0)))
                        .append(word.substring(1).toLowerCase())
                        .append(" ");
        return sb.toString().trim();
    }

    private static String getDefaultName(Identifier id)
    {
        return getDefaultName(id.getPath());
    }
}