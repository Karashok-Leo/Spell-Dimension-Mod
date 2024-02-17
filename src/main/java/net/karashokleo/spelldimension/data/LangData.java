package net.karashokleo.spelldimension.data;

import me.shedaniel.autoconfig.annotation.ConfigEntry;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.karashokleo.spelldimension.config.AllConfig;
import net.karashokleo.spelldimension.item.AllGroups;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.karashokleo.spelldimension.spell.BlazingMark;
import net.karashokleo.spelldimension.spell.NucleusSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.util.Identifier;
import net.spell_power.api.MagicSchool;

import java.lang.reflect.Field;

public class LangData extends FabricLanguageProvider
{
    //Config
    public static final String CONFIG_TITLE = "text.autoconfig.spell-dimension.title";
    public static final String CONFIG_OPTION = "text.autoconfig.spell-dimension.option";
    //Misc
    public static final String MAGE = "misc.spell-dimension.mage";
    public static final String MASTERY = "misc.spell-dimension.mastery";
    public static final String SPELL_BOOK = "misc.spell-dimension.book";
    public static final String MAGE_MEDAL = "misc.spell-dimension.medal";
    public static final String BLANK_MAGE_MEDAL = "misc.spell-dimension.medal.blank";
    public static final String ENCHANTED_ESSENCE = "misc.spell-dimension.enchanted_essence";

    //Enum
    public static final String ENUM_GRADE = "enum.spell-dimension.grade.";
    public static final String ENUM_SCHOOL = "enum.spell-dimension.school.";
    public static final String ENUM_MAJOR = "enum.spell-dimension.major.";
    public static final String ENUM_SLOT = "enum.spell-dimension.slot.";

    //Title
    public static final String TITLE_UPGRADE = "mage.spell-dimension.upgrade";
    public static final String TITLE_CLEAR = "mage.spell-dimension.clear";
    public static final String TITLE_SUCCESS = "text.spell-dimension.success";
    public static final String TITLE_FAILURE = "text.spell-dimension.failure";

    //Tooltip
    public static final String TOOLTIP_INVALID = "tooltip.spell-dimension.invalid";
    public static final String TOOLTIP_ESSENCE_USE = "tooltip.spell-dimension.essence.use";
    public static final String TOOLTIP_MEDAL_USE = "tooltip.spell-dimension.medal.use";
    public static final String TOOLTIP_EFFECT = "tooltip.spell-dimension.effect";
    public static final String TOOLTIP_LEVEL = "tooltip.spell-dimension.level";
    public static final String TOOLTIP_THRESHOLD = "tooltip.spell-dimension.threshold";
    public static final String TOOLTIP_MODIFIER = "tooltip.spell-dimension.modifier";
    public static final String TOOLTIP_REQUIRE = "tooltip.spell-dimension.require";
    public static final String TOOLTIP_DISENCHANT = "tooltip.spell-dimension.disenchant";

    public LangData(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder)
    {
        addConfigTranslation(builder);
        addItemTranslation(builder);
        addGroupTranslation(builder);
        addStatusEffectTranslation(builder);
        addMiscTranslation(builder);
        addGradeTranslation(builder);
        addSchoolTranslation(builder);
        addMajorTranslation(builder);
        addSlotTranslation(builder);
        addTitleTranslation(builder);
        addTooltipTranslation(builder);
        addSpellTranslation(builder);
    }

    private static void addConfigTranslation(TranslationBuilder builder)
    {
        builder.add(CONFIG_TITLE, "Spell Dimension");
        addConfigTranslation(builder, AllConfig.class, CONFIG_OPTION);
    }

    private static void addConfigTranslation(TranslationBuilder builder, Class<?> c, String pre)
    {
        if (c.isPrimitive() || c.isArray()) return;
        Field[] fields = c.getFields();
        for (Field field : fields)
        {
            if (field.isAnnotationPresent(ConfigEntry.Gui.Excluded.class)) continue;
            String name = field.getName();
            String key = pre + "." + name;
            builder.add(key, getDefaultName(name));
            addConfigTranslation(builder, field.getType(), key);
        }
    }

    private static void addItemTranslation(TranslationBuilder builder)
    {
        builder.add(AllItems.DEBUG_STAFF, getName(AllItems.DEBUG_STAFF));
        builder.add(AllItems.DISENCHANTED_ESSENCE, getName(AllItems.DISENCHANTED_ESSENCE));
        AllItems.BASE_ESSENCES.values().forEach(items -> items.forEach(item -> builder.add(item, getName(item))));
        AllItems.SPELL_BOOKS.values().forEach(entry -> builder.add(entry.primary, getName(entry.primary)));

    }

    private static void addGroupTranslation(TranslationBuilder builder)
    {
        builder.add(AllGroups.BOOKS_GROUP_KEY, "Spell Dimension: Spell Books");
        builder.add(AllGroups.EES_GROUP_KEY, "Spell Dimension: Enchanted Essences");
        builder.add(AllGroups.MEDALS_GROUP_KEY, "Spell Dimension: Mage Medals");
        builder.add(AllGroups.MISC_GROUP_KEY, "Spell Dimension: Misc");
    }

    private static void addStatusEffectTranslation(TranslationBuilder builder)
    {
        AllStatusEffects.ALL.forEach(effect ->
                builder.add(effect, getName(effect)));
    }

    private static void addMiscTranslation(TranslationBuilder builder)
    {
        builder.add(MAGE, "Mage");
        builder.add(MASTERY, "Mastery");
        builder.add(SPELL_BOOK, "Spell Book");
        builder.add(MAGE_MEDAL, "Mage Medal");
        builder.add(BLANK_MAGE_MEDAL, "Blank Mage Medal");
        builder.add(ENCHANTED_ESSENCE, "Enchanted Essence");
    }

    private static void addGradeTranslation(TranslationBuilder builder)
    {
        builder.add(ENUM_GRADE + 0, "Primary");
        builder.add(ENUM_GRADE + 1, "Secondary");
        builder.add(ENUM_GRADE + 2, "Advanced");
        builder.add(ENUM_GRADE + 3, "Perfect");
    }

    private static void addSchoolTranslation(TranslationBuilder builder)
    {
        for (MagicSchool school : MagicSchool.values())
            builder.add(ENUM_SCHOOL + school.name(), getDefaultName(school.name()));
    }

    private static void addMajorTranslation(TranslationBuilder builder)
    {
        for (MageMajor major : MageMajor.values())
            builder.add(ENUM_MAJOR + major.name(), getDefaultName(major.name()));
    }

    private static void addSlotTranslation(TranslationBuilder builder)
    {
        builder.add(ENUM_SLOT + EquipmentSlot.MAINHAND.name(), "Slot: Main Hand");
        builder.add(ENUM_SLOT + EquipmentSlot.OFFHAND.name(), "Slot: Off Hand");
        builder.add(ENUM_SLOT + EquipmentSlot.HEAD.name(), "Slot: Head");
        builder.add(ENUM_SLOT + EquipmentSlot.CHEST.name(), "Slot: Chest");
        builder.add(ENUM_SLOT + EquipmentSlot.LEGS.name(), "Slot: Legs");
        builder.add(ENUM_SLOT + EquipmentSlot.FEET.name(), "Slot: Feet");
    }

    private static void addTitleTranslation(TranslationBuilder builder)
    {
        builder.add(TITLE_UPGRADE, "Level Up!");
        builder.add(TITLE_CLEAR, "You are ignorant.");
        builder.add(TITLE_SUCCESS, "Successful use of essence!");
        builder.add(TITLE_FAILURE, "Failed to use essence!");
    }

    private static void addTooltipTranslation(TranslationBuilder builder)
    {
        builder.add(TOOLTIP_INVALID, "Invalid Nbt Data!");
        builder.add(TOOLTIP_ESSENCE_USE, "When in Main Hand, press the right button to apply effect on the item in Off Hand.");
        builder.add(TOOLTIP_MEDAL_USE, "Use to become:");
        builder.add(TOOLTIP_EFFECT, "Effect:");
        builder.add(TOOLTIP_LEVEL, "Level: %s");
        builder.add(TOOLTIP_THRESHOLD, "Threshold: %s");
        builder.add(TOOLTIP_MODIFIER, "Modifier:");
        builder.add(TOOLTIP_REQUIRE, "Requirement:");
        builder.add(TOOLTIP_DISENCHANT, "Remove attribute modifiers from your offhand item.");
    }

    private static void addSpellTranslation(TranslationBuilder builder)
    {
        addSpellTranslation(builder, "converge", "Launching a spell to explode at the landing point and make enemies converged.");
        addSpellTranslation(builder, "phase", "Apply phase {effect_amplifier} effect to oneself for {effect_duration} seconds. Phase: You are free as a bird, even more than a bird.");
        addSpellTranslation(builder, "flourish", "Spin rapidly, dealing {damage}_0 non-crit physical damage, plus {damage}_1 arcane damage.");

        addSpellTranslation(builder, "breath", "Incinerates targets in front, dealing up to {damage} fire spell damage every second.");
        addSpellTranslation(builder, "blast", "Causing a powerful flame explosion, repelling surrounding creatures and causing {damage} fire spell damage.");
        addSpellTranslation(builder, "ignite", "Apply ignite effect to oneself. Ignite: Your attack will leave a mark on the enemy, and some of the damage you inflict during this period will damage the enemy again in " + BlazingMark.getTriggerTime() + " seconds.");

        addSpellTranslation(builder, "aura", "Apply aura {effect_amplifier} effect to oneself for {effect_duration} seconds. Aura: Within the frozen aura, livings get Frosted.");
        addSpellTranslation(builder, "nucleus", "Freeze the target's heart into a ice nucleus, explode in " + NucleusSpell.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.");
        addSpellTranslation(builder, "icicle", "Shooting an icicle, which deals {damage} frost spell damage to the hit enemy and can cause a chain reaction.");

        addSpellTranslation(builder, "power", "Apply strength {effect_amplifier} effect to oneself for {effect_duration} seconds.");
        addSpellTranslation(builder, "regen", "Apply regen {effect_amplifier} effect to oneself for {effect_duration} seconds.");
        addSpellTranslation(builder, "resist", "Apply resist {effect_amplifier} effect to oneself for {effect_duration} seconds.");
    }

    private static void addSpellTranslation(TranslationBuilder builder, String name, String desc)
    {
        StringBuilder name_ = new StringBuilder(getDefaultName(name) + " ");
        for (int i = 1; i <= 3; i++)
        {
            name_.append("I");
            builder.add("spell.spell-dimension." + name + i + ".name", name_.toString());
            builder.add("spell.spell-dimension." + name + i + ".description", desc);
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