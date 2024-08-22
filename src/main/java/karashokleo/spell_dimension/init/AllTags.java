package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_engine.internals.SpellInfinityEnchantment;

import java.util.List;
import java.util.stream.Stream;

public class AllTags
{
    public static final List<TagKey<Item>> ESSENCE = List.of(
            TagUtil.itemTag("essence/0"),
            TagUtil.itemTag("essence/1"),
            TagUtil.itemTag("essence/2")
    );
    public static final TagKey<Item> ESSENCE_ALL = TagUtil.itemTag("essence/all");
    public static final TagKey<Item> RUNE = TagUtil.itemTag("rune");
    public static final TagKey<Item> HEART_FOOD = TagUtil.itemTag("heart_food");

    public static final TagKey<Item> MELEE_WEAPONS = TagUtil.itemTag(new Identifier("equipment_standard:melee_weapons"));
    public static final TagKey<Item> ARMOR = TagUtil.itemTag(new Identifier("equipment_standard:armor"));

    public static final List<TagKey<Item>> SPELL_POWER_ENCHANTMENT_TAGS = Stream.of(
            "enchant_spell_power_generic",
            "enchant_spell_power_soulfrost",
            "enchant_spell_power_sunfire",
            "enchant_spell_power_energize",
            "enchant_critical_chance",
            "enchant_critical_damage",
            "enchant_haste"
    ).map(s -> TagUtil.itemTag(new Identifier("spell_power", s))).toList();

    public static final TagKey<Item> SPELL_INFINITY = TagUtil.itemTag(SpellInfinityEnchantment.tagId);

    public static final List<TagKey<Item>> REFORGE_CORE_TAGS = Stream.of(1, 2, 3).map(i -> TagUtil.itemTag(new Identifier("equipment_standard:reforge_core/lv" + i))).toList();

    public static void register()
    {
    }
}
