package karashokleo.spell_dimension.content.enchantment;

import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import net.spell_engine.api.spell.SpellInfo;

import java.util.List;

public class SpellCurseEnchantment extends SpellImpactEnchantment
{
    public SpellCurseEnchantment()
    {
        super(Rarity.UNCOMMON, EnchantmentTarget.BREAKABLE, new EquipmentSlot[]{EquipmentSlot.MAINHAND});
    }

    @Override
    public int getMaxLevel()
    {
        return 5;
    }

    @Override
    public void onSpellImpact(World world, LivingEntity caster, Context context, List<Entity> targets, SpellInfo spellInfo)
    {
        for (Entity entity : targets)
            if (entity instanceof LivingEntity living)
            {
                if (ImpactUtil.isAlly(caster, living)) continue;
                EffectHelper.forceAddEffectWithEvent(living, new StatusEffectInstance(LHEffects.CURSE, 20 * (context.totalLevel() + 1), 0), caster);
            }
    }

    @Override
    public Formatting getColor()
    {
        return Formatting.DARK_PURPLE;
    }
}
