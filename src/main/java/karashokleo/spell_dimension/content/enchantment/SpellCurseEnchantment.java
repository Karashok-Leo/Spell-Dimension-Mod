package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.init.LHEffects;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SpellCurseEnchantment extends SpellImpactEnchantment
{
    public SpellCurseEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.WEAPON, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, int totalLevel, List<Entity> targets, SpellInfo spellInfo)
    {

        for (Entity entity : targets)
            if (entity instanceof LivingEntity living)
            {
                if (ImpactUtil.isAlly(caster, living)) continue;
                living.addStatusEffect(new StatusEffectInstance(LHEffects.CURSE, 20 * (totalLevel + 1), 0));
            }
    }
}
