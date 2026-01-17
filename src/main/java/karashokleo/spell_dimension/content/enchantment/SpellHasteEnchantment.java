package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.SpellPowerMechanics;

import java.util.List;

public class SpellHasteEnchantment extends SpellImpactEnchantment
{
    public SpellHasteEnchantment()
    {
        super(
            Rarity.UNCOMMON,
            EnchantmentTarget.WEARABLE,
            EquipmentSlot.values()
        );
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo)
    {
        caster.addStatusEffect(new StatusEffectInstance(SpellPowerMechanics.HASTE.boostEffect, 40, context.totalLevel(), false, false, true));
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.GOLD;
    }
}
