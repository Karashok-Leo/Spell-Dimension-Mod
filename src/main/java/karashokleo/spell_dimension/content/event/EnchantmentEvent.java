package karashokleo.spell_dimension.content.event;

import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.leobrary.damage.api.modify.DamageModifier;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.enchantment.SpellImpactEnchantment;
import karashokleo.spell_dimension.init.AllEnchantments;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;

import java.util.HashMap;
import java.util.Map;

public class EnchantmentEvent
{
    public static void init()
    {
        SpellImpactEvents.BEFORE.register((world, caster, targets, spellInfo) ->
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

        DamagePhase.ARMOR.registerModifier(0, damageAccess ->
        {
            if (!(damageAccess.getEntity() instanceof PlayerEntity player))
                return;

            int totalLevel = 0;
            for (ItemStack stack : player.getArmorItems())
                totalLevel += EnchantmentHelper.getLevel(AllEnchantments.SPELL_RESISTANCE, stack);

            double totalSpellPower = 0;
            for (SpellSchool school : SchoolUtil.getPlayerSchool(player))
                totalSpellPower += SpellPower.getSpellPower(school, player).baseValue();

            float damageReduction = (float) (totalLevel * 0.01 * totalSpellPower);
            damageAccess.addModifier(DamageModifier.add(-damageReduction));
        });

        DamagePhase.APPLY.registerModifier(0, damageAccess ->
        {
            if (!(damageAccess.getAttacker() instanceof PlayerEntity player))
                return;

            int totalLevel = EnchantmentHelper.getLevel(AllEnchantments.SPELL_LEECH, player.getMainHandStack());

            double totalSpellPower = 0;
            for (SpellSchool school : SchoolUtil.getPlayerSchool(player))
                totalSpellPower += SpellPower.getSpellPower(school, player).baseValue();

            float damageLeech = (float) (totalLevel * 0.01 * totalSpellPower);
            player.heal(damageLeech);
            damageAccess.addModifier(DamageModifier.add(-damageLeech));
        });
    }
}
