package karashokleo.spell_dimension.content.enchantment;

import karashokleo.spell_dimension.init.AllStatusEffects;
import karashokleo.spell_dimension.init.AllTags;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;
import net.spell_power.api.enchantment.EnchantmentRestriction;

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
        EnchantmentRestriction.permit(
                this,
                stack -> stack.isIn(AllTags.MELEE_WEAPONS) || stack.isIn(AllTags.ARMOR)
        );
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, int totalLevel, List<Entity> targets, SpellInfo spellInfo)
    {
        caster.addStatusEffect(new StatusEffectInstance(AllStatusEffects.SPELL_HASTE, 40, totalLevel));
    }
}
