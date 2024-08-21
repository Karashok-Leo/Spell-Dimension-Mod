package karashokleo.spell_dimension.init;

import io.github.apace100.autotag.api.AutoTagRegistry;
import karashokleo.spell_dimension.util.TagUtil;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import net.spell_engine.SpellEngineMod;
import net.spell_power.SpellPowerMod;

import java.util.List;

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
    public static final TagKey<Item> BREAKABLE = TagUtil.itemTag(new Identifier("c:breakable"));

    public static final TagKey<Item> SPELL_POWER_GENERIC = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_spell_power_generic"));
    public static final TagKey<Item> SPELL_POWER_SOULFROST = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_spell_power_soulfrost"));
    public static final TagKey<Item> SPELL_POWER_SUNFIRE = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_spell_power_sunfire"));
    public static final TagKey<Item> SPELL_POWER_ENERGIZE = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_spell_power_energize"));
    public static final TagKey<Item> SPELL_POWER_CRITICAL_CHANCE = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_critical_chance"));
    public static final TagKey<Item> SPELL_POWER_CRITICAL_DAMAGE = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_critical_damage"));
    public static final TagKey<Item> SPELL_POWER_HASTE = TagUtil.itemTag(new Identifier(SpellPowerMod.ID, "enchant_haste"));
    public static final TagKey<Item> SPELL_INFINITY = TagUtil.itemTag(new Identifier(SpellEngineMod.ID, "enchant_spell_infinity"));

    public static void register()
    {
        AutoTagRegistry.register(
                Registries.ITEM,
                BREAKABLE,
                Item::isDamageable
        );
    }
}
