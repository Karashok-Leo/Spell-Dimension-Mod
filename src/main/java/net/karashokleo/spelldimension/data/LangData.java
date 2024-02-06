package net.karashokleo.spelldimension.data;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricLanguageProvider;
import net.karashokleo.spelldimension.item.AllGroups;
import net.karashokleo.spelldimension.item.AllItems;
import net.karashokleo.spelldimension.effect.AllStatusEffects;
import net.karashokleo.spelldimension.misc.Mage;
import net.karashokleo.spelldimension.misc.MageMajor;
import net.karashokleo.spelldimension.spell.BlazingMark;
import net.karashokleo.spelldimension.spell.NucleusSpell;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.attribute.EntityAttribute;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Identifier;
import net.spell_engine.api.item.trinket.SpellBookItem;
import net.spell_power.api.MagicSchool;
import org.jetbrains.annotations.Nullable;

public class LangData extends FabricLanguageProvider
{
    public static final String TEXT_SUCCESS = "text.spell-dimension.success";
    public static final String TEXT_FAILURE = "text.spell-dimension.failure";

    public static final String MAGE = "mage.spell-dimension.mage";
    public static final String MAGE_UPGRADE = "mage.spell-dimension.upgrade";
    public static final String MAGE_RESET = "mage.spell-dimension.reset";
    public static final String MAGE_CLEAR = "mage.spell-dimension.clear";

    public static final String TOOLTIP_INVALID = "tooltip.spell-dimension.invalid";
    public static final String TOOLTIP_ESSENCE_USE = "tooltip.spell-dimension.essence.use";
    public static final String TOOLTIP_MEDAL_USE = "tooltip.spell-dimension.medal.use";
    public static final String TOOLTIP_EFFECT = "tooltip.spell-dimension.effect";
    public static final String TOOLTIP_LEVEL = "tooltip.spell-dimension.level";
    public static final String TOOLTIP_THRESHOLD = "tooltip.spell-dimension.threshold";
    public static final String TOOLTIP_MODIFIER = "tooltip.spell-dimension.modifier";
    public static final String TOOLTIP_REQUIRE = "tooltip.spell-dimension.require";

    public static String getSlotKey(EquipmentSlot slot)
    {
        return "slot.spell-dimension." + slot.name();
    }

    public static String getGradeKey(int grade)
    {
        return "grade.spell-dimension." + grade;
    }

    public static String getMageKey(MagicSchool school)
    {
        return "mage.spell-dimension." + school.name();
    }

    public static String getMajorKey(MageMajor major)
    {
        return "major.spell-dimension." + major.name();
    }

    public LangData(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public void generateTranslations(TranslationBuilder builder)
    {
        AllItems.ALL.forEach(item ->
                builder.add(item, getName(item)));
        AllStatusEffects.ALL.forEach(effect ->
                builder.add(effect, getName(effect)));

        builder.add(AllGroups.BOOKS_GROUP_KEY, "Spell Plus: Spell Books");
        builder.add(AllGroups.EES_GROUP_KEY, "Spell Plus: Enchanted Essences");
        builder.add(AllGroups.MEDALS_GROUP_KEY, "Spell Plus: Mage Medals");
//        builder.add(AllGroups.UES_GROUP_KEY, "Spell Plus: Upgrade Essences");
        builder.add(AllGroups.MISC_GROUP_KEY, "Spell Plus: Misc");

        builder.add(TEXT_SUCCESS, "Successful use of essence!");
        builder.add(TEXT_FAILURE, "Failed to use essence!");

        builder.add(MAGE, "Mage");
        builder.add(MAGE_UPGRADE, "Level Up!");
        builder.add(MAGE_RESET, "You've lost sth...");
        builder.add(MAGE_CLEAR, "You are ignorant.");

        builder.add(TOOLTIP_INVALID, "Invalid Nbt Data!");
        builder.add(TOOLTIP_ESSENCE_USE, "When in Main Hand, press the right button to apply effect on the item in Off Hand.");
        builder.add(TOOLTIP_MEDAL_USE, "Use to become:");
        builder.add(TOOLTIP_EFFECT, "Effect:");
        builder.add(TOOLTIP_LEVEL, "Level: %s");
        builder.add(TOOLTIP_THRESHOLD, "Threshold: %s");
        builder.add(TOOLTIP_MODIFIER, "Modifier:");
        builder.add(TOOLTIP_REQUIRE, "Requirement:");

        builder.add(getSlotKey(EquipmentSlot.MAINHAND), "Slot: Main Hand");
        builder.add(getSlotKey(EquipmentSlot.OFFHAND), "Slot: Off Hand");
        builder.add(getSlotKey(EquipmentSlot.HEAD), "Slot: Head");
        builder.add(getSlotKey(EquipmentSlot.CHEST), "Slot: Chest");
        builder.add(getSlotKey(EquipmentSlot.LEGS), "Slot: Legs");
        builder.add(getSlotKey(EquipmentSlot.FEET), "Slot: Feet");

        builder.add(getGradeKey(0), "Primary");
        builder.add(getGradeKey(1), "Secondary");
        builder.add(getGradeKey(2), "Advanced");
        builder.add(getGradeKey(3), "Perfect");

        for (MagicSchool school : MagicSchool.values())
            builder.add(getMageKey(school), getDefaultName(school.name()));
        for (MageMajor major : MageMajor.values())
            builder.add(getMajorKey(major), getDefaultName(major.name()));

        addSpellTranslation(builder, "converge", "Launching a spell to explode at the landing point and make enemies converged.");
        addSpellTranslation(builder, "phase", "Apply phase effect to oneself.\nPhase: You are free as a bird, even more than a bird.");

        addSpellTranslation(builder, "blast", "Causing a powerful flame explosion.");
        addSpellTranslation(builder, "ignite", "Apply ignite effect to oneself.\nIgnite: Your attack will leave a mark on the enemy, and some of the damage you inflict during this period will damage the enemy again in " + BlazingMark.getTriggerTime() + " seconds.");

        addSpellTranslation(builder, "aura", "Apply aura effect to oneself.\nAura: Within the frozen aura, livings get Frosted.");
        addSpellTranslation(builder, "nucleus", "Freeze the target's heart into a ice nucleus, explode in " + NucleusSpell.TOTAL_DURATION / 20F + " seconds, and shoot icicles into the surrounding area.");
        addSpellTranslation(builder, "icicle", "Shooting an icicle, which can cause a chain reaction.");

        addSpellTranslation(builder, "power", "Apply strength effect to oneself.");
        addSpellTranslation(builder, "regen", "Apply regen effect to oneself.");
        addSpellTranslation(builder, "resist", "Apply resist effect to oneself.");
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
        if (item instanceof SpellBookItem) return getSpellBookName(id);
        else return getDefaultName(id);
    }

    private static String getName(EntityAttribute attribute)
    {
        String[] words = attribute.getTranslationKey().split("\\.");
        String name = words[words.length - 1];
        return getDefaultName(name);
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

    private static String getSpellBookName(Identifier id)
    {
        String[] words = id.getPath().split("_");
        StringBuilder sb = new StringBuilder();
        sb.append(Character.toUpperCase(words[0].charAt(0)))
                .append(words[0].substring(1).toLowerCase())
                .append(words.length > 4 ? " • " : " ");
        for (int i = 1; i < words.length; i++)
            if (!words[i].isEmpty())
                sb.append(Character.toUpperCase(words[i].charAt(0)))
                        .append(words[i].substring(1).toLowerCase())
                        .append(" ");
        return sb.toString().trim();
    }

    public static Text getMageTitle(Mage mage)
    {
        return getMageTitle(mage.grade(), mage.school(), mage.major());
    }

    public static Text getMageTitle(int grade, @Nullable MagicSchool school, @Nullable MageMajor major)
    {
        MutableText title = Text.translatable(getGradeKey(grade));
        if (school != null)
            title.append(" ")
                    .append(Text.translatable(getMageKey(school)));
        title.append(" ")
                .append(Text.translatable(MAGE));
        if (major != null)
            title.append(" - ")
                    .append(Text.translatable(getMajorKey(major)));
        if (school == null)
            title.formatted(Formatting.GRAY);
        else
            title.setStyle(title.getStyle().withColor(school.color()));
        return title;
    }
}