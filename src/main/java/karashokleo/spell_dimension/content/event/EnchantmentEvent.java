package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.leobrary.damage.api.modify.DamageModifier;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.enchantment.EffectImmunityEnchantment;
import karashokleo.spell_dimension.content.enchantment.SpellImpactEnchantment;
import karashokleo.spell_dimension.init.AllEnchantments;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;
import net.spell_power.api.enchantment.EnchantmentRestriction;
import org.apache.commons.lang3.mutable.MutableInt;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class EnchantmentEvent
{
    public static void init()
    {
        MobEffectEvent.APPLICABLE.register(event ->
        {
            if (TrinketCompat.getTrinketItems(event.getEntity(),
                    e -> e.isIn(AllTags.BREASTPLATE_SLOT)).stream().anyMatch(
                    e -> EnchantmentHelper.get(e).keySet().stream().anyMatch(
                            enchantment -> enchantment instanceof EffectImmunityEnchantment immunity &&
                                           immunity.test(event.getEffectInstance()))))
                event.setResult(BaseEvent.Result.DENY);
        });

        AllEnchantments.EFFECT_IMMUNITY.keySet().forEach(enchantment ->
        {
            EnchantmentRestriction.permit(enchantment, stack -> stack.isIn(AllTags.BREASTPLATE_SLOT));
            EnchantmentRestriction.prohibit(enchantment, stack -> !stack.isIn(AllTags.BREASTPLATE_SLOT));
        });

        SpellImpactEvents.BEFORE.register((world, caster, targets, spellInfo) ->
        {
            Map<SpellImpactEnchantment, SpellImpactEnchantment.Context> map = new HashMap<>();
            for (ItemStack e : TrinketCompat.getItems(caster, e -> true))
                EnchantmentHelper.get(e).forEach((enchantment, level) ->
                {
                    if (enchantment instanceof SpellImpactEnchantment impactEnchantment)
                    {
                        map.compute(impactEnchantment, (en, ctx) ->
                        {
                            if (ctx == null)
                            {
                                MutableInt lv = new MutableInt(level);
                                ArrayList<ItemStack> list = new ArrayList<>();
                                list.add(e);
                                return new SpellImpactEnchantment.Context(lv, list);
                            } else
                            {
                                ctx.level().add(level);
                                ctx.stacks().add(e);
                                return ctx;
                            }
                        });
                    }
                });
            map.forEach((enchantment, context) -> enchantment.onSpellImpact(world, caster, context, targets, spellInfo));
        });

        DamagePhase.ARMOR.registerModifier(0, damageAccess ->
        {
            if (!(damageAccess.getEntity() instanceof PlayerEntity player))
                return;

            int totalLevel = 0;
            for (ItemStack stack : player.getArmorItems())
                totalLevel += EnchantmentHelper.getLevel(AllEnchantments.SPELL_RESISTANCE, stack);

            double totalSpellPower = 0;
            for (SpellSchool school : SchoolUtil.getEntitySchool(player))
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
            for (SpellSchool school : SchoolUtil.getEntitySchool(player))
                totalSpellPower += SpellPower.getSpellPower(school, player).baseValue();

            float damageLeech = (float) (totalLevel * 0.01 * totalSpellPower);
            player.heal(damageLeech);
            damageAccess.addModifier(DamageModifier.add(-damageLeech));
        });
    }
}
