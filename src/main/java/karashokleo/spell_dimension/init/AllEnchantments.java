package karashokleo.spell_dimension.init;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.enchantment.SpellImpactEnchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class AllEnchantments
{
    public static void register()
    {
        SpellImpactEvents.AFTER.register((world, caster, targets, spellInfo) ->
        {
            Map<SpellImpactEnchantment, Integer> map = new HashMap<>();
            for (ItemStack e : TrinketCompat.getItems(caster, e -> true))
                EnchantmentHelper.get(e).forEach((enchantment, level) ->
                {
                    if (enchantment instanceof SpellImpactEnchantment impactEnchantment)
                        map.compute(impactEnchantment, (en, lv) -> (lv == null ? 0 : lv) + level);
                });
            map.forEach((enchantment, level) -> enchantment.onSpellImpact(world, caster, level, targets, spellInfo));
        });
    }
}
