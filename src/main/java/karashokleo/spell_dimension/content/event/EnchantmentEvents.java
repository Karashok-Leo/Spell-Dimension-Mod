package karashokleo.spell_dimension.content.event;

import io.github.fabricators_of_create.porting_lib.core.event.BaseEvent;
import io.github.fabricators_of_create.porting_lib.entity.events.living.MobEffectEvent;
import karashokleo.l2hostility.api.event.ModifyDispellImmuneFactorCallback;
import karashokleo.l2hostility.compat.trinket.TrinketCompat;
import karashokleo.l2hostility.init.LHTags;
import karashokleo.leobrary.damage.api.modify.DamagePhase;
import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.enchantment.EffectImmunityEnchantment;
import karashokleo.spell_dimension.content.enchantment.SpellBladeAmplifyEnchantment;
import karashokleo.spell_dimension.content.enchantment.SpellImpactEnchantment;
import karashokleo.spell_dimension.init.AllEnchantments;
import karashokleo.spell_dimension.init.AllTags;
import karashokleo.spell_dimension.util.SchoolUtil;
import net.combatroll.api.event.ServerSideRollEvents;
import net.fabricmc.fabric.api.item.v1.ModifyItemAttributeModifiersCallback;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.MathHelper;
import net.spell_power.api.SpellPower;
import net.spell_power.api.SpellSchool;

import java.util.Map;

public class EnchantmentEvents
{
    public static void init()
    {
        // Spell blade amplify enchantment
        ModifyItemAttributeModifiersCallback.EVENT.register((stack, slot, attributeModifiers) ->
        {
            if (slot != EquipmentSlot.MAINHAND)
            {
                return;
            }
            if (!(stack.getItem() instanceof SwordItem))
            {
                return;
            }
            for (Enchantment enchantment : EnchantmentHelper.get(stack).keySet())
            {
                if (!(enchantment instanceof SpellBladeAmplifyEnchantment ench))
                {
                    continue;
                }
                ench.operateModifiers(stack, attributeModifiers);
            }
        });

        // Breastplate Immunity
        MobEffectEvent.APPLICABLE.register(event ->
        {
            if (TrinketCompat.getTrinketItems(event.getEntity(),
                e -> e.isIn(AllTags.BREASTPLATE_SLOT)).stream().anyMatch(
                e -> EnchantmentHelper.get(e).keySet().stream().anyMatch(
                    enchantment -> enchantment instanceof EffectImmunityEnchantment immunity &&
                        immunity.test(event.getEffectInstance()))))
            {
                event.setResult(BaseEvent.Result.DENY);
            }
        });

        // Spell impact enchantment
        SpellImpactEvents.POST.register((world, caster, targets, spellInfo) ->
        {
            Map<SpellImpactEnchantment, SpellImpactEnchantment.Context> map = SpellImpactEnchantment.getContexts(caster);
            map.forEach((enchantment, context) -> enchantment.onSpellImpact(world, caster, context, targets, spellInfo));
        });

        // Spell Tearing
        ModifyDispellImmuneFactorCallback.EVENT.register((difficulty, entity, level, source, amount, factor) ->
        {
            if (source.getAttacker() instanceof LivingEntity living)
            {
                int tearingLv = EnchantmentHelper.getEquipmentLevel(AllEnchantments.SPELL_TEARING, living);
                float f = MathHelper.clamp(tearingLv * 0.2f, 0f, 1f);
                return factor * (1 - f);
            }
            return factor;
        });

        // Spell Resistance
        DamagePhase.ARMOR.addListener(0, damageAccess ->
        {
            if (!(damageAccess.getEntity() instanceof PlayerEntity player))
            {
                return;
            }

            if (damageAccess.getSource().isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            {
                return;
            }

            int totalLevel = 0;
            for (ItemStack stack : player.getArmorItems())
            {
                totalLevel += EnchantmentHelper.getLevel(AllEnchantments.SPELL_RESISTANCE, stack);
            }

            double totalSpellPower = 0;
            for (SpellSchool school : SchoolUtil.getLivingSchools(player))
            {
                totalSpellPower += SpellPower.getSpellPower(school, player).baseValue();
            }

            float damageReduction = (float) (totalLevel * 0.01 * totalSpellPower);
            damageAccess.addModifier(originalDamage -> Math.max(0, originalDamage - damageReduction));
        });

        // Spell Leech
        DamagePhase.APPLY.addListener(0, damageAccess ->
        {
            if (!damageAccess.getSource().isIn(LHTags.MAGIC))
            {
                return;
            }
            if (!(damageAccess.getAttacker() instanceof PlayerEntity player))
            {
                return;
            }

            int totalLevel = EnchantmentHelper.getLevel(AllEnchantments.SPELL_LEECH, player.getMainHandStack());

            double totalSpellPower = 0;
            for (SpellSchool school : SchoolUtil.getLivingSchools(player))
            {
                totalSpellPower += SpellPower.getSpellPower(school, player).baseValue();
            }

            float damageLeech = (float) (totalLevel * 0.01 * totalSpellPower);
            player.heal(damageLeech);
            damageAccess.addModifier(originalDamage -> Math.max(0, originalDamage - damageLeech * 5));
        });

        ServerSideRollEvents.PLAYER_START_ROLLING.register(AllEnchantments.DASH_RESISTANCE::onDash);
    }
}
