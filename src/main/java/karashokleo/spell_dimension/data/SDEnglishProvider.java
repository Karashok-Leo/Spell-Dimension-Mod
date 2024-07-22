package karashokleo.spell_dimension.data;

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
    }

    private static void addGroupTranslation(TranslationBuilder builder)
    {
        builder.add(AllGroups.BOOKS_GROUP_KEY, "Spell Dimension: Spell Books");
        builder.add(AllGroups.SCROLLS_GROUP_KEY, "Spell Dimension: Spell Scrolls");
        builder.add(AllGroups.ELES_GROUP_KEY, "Spell Dimension: Enlightening Essences");
        builder.add(AllGroups.ECES_GROUP_KEY, "Spell Dimension: Enchanted Essences");
        builder.add(AllGroups.MISC_GROUP_KEY, "Spell Dimension: Misc");
    }

    private static void addItemTranslation(TranslationBuilder builder)
    {
        builder.add(AllItems.DEBUG_STAFF, getName(AllItems.DEBUG_STAFF));
        builder.add(AllItems.DISENCHANTED_ESSENCE, getName(AllItems.DISENCHANTED_ESSENCE));
        builder.add(AllItems.MENDING_ESSENCE, getName(AllItems.MENDING_ESSENCE));
    }

    private static void addStatusEffectTranslation(TranslationBuilder builder)
    {
        builder.add(AllStatusEffects.PHASE_EFFECT, getName(AllStatusEffects.PHASE_EFFECT));
        builder.add(AllStatusEffects.IGNITE_EFFECT, getName(AllStatusEffects.PHASE_EFFECT));
        builder.add(AllStatusEffects.FROST_AURA_EFFECT, getName(AllStatusEffects.PHASE_EFFECT));
        builder.add(AllStatusEffects.FROSTED_EFFECT, getName(AllStatusEffects.PHASE_EFFECT));
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