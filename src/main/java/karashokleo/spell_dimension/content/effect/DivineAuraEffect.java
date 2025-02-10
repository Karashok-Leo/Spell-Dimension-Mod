package karashokleo.spell_dimension.content.effect;

import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.ImpactUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class DivineAuraEffect extends StatusEffect
{
    public static String getDesc(boolean en)
    {
        return (en ?
                "Every %s second, applies Absorption effect to surrounding allies and deals %.1fx spell power damage to surrounding enemies." :
                "每隔%s秒，对周围友方施加伤害吸收效果，并对周围敌人造成%.1f倍法术强度的治愈法术伤害。")
                .formatted(INTERVAL / 20, SpellConfig.DIVINE_AURA_FACTOR);
    }

    public static final float RANGE = 2.5F;
    public static final int INTERVAL = 20;

    public DivineAuraEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xf4f960);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        World world = entity.getWorld();
        if (world.isClient()) return;
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(RANGE), EntityPredicates.VALID_LIVING_ENTITY);
        for (LivingEntity target : entities)
        {
            if (target.distanceTo(entity) > RANGE) continue;
            if (ImpactUtil.isAlly(entity, target))
            {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20, amplifier));
            } else
            {
                float damage = (float) DamageUtil.calculateDamage(entity, SpellSchools.HEALING, SpellConfig.DIVINE_AURA_FACTOR, amplifier);
                DamageUtil.spellDamage(target, SpellSchools.HEALING, entity, damage, false);
            }
        }
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % INTERVAL == 0;
    }
}
