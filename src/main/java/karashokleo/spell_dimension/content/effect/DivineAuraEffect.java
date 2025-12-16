package karashokleo.spell_dimension.content.effect;

import karashokleo.spell_dimension.config.SpellConfig;
import karashokleo.spell_dimension.util.DamageUtil;
import karashokleo.spell_dimension.util.RelationUtil;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.predicate.entity.EntityPredicates;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.spell_power.api.SpellSchools;

import java.util.List;

public class DivineAuraEffect extends StatusEffect
{
    private static final int MIN_RADIUS = 3;
    private static final int MAX_RADIUS = 10;

    public static String getDesc(boolean en)
    {
        return (en ?
            "Every %s second, applies Absorption effect to surrounding allies and deals %.1fx spell power damage to surrounding enemies. Range >=%d and <=%d, increases by 1 for every 100 spell power." :
            "每隔%s秒，对周围友方施加伤害吸收效果，并对周围敌人造成%.1f倍法术强度的治愈法术伤害，范围>=%d且<=%d，每100法强增加1格。")
            .formatted(INTERVAL / 20, SpellConfig.DIVINE_AURA_FACTOR, MIN_RADIUS, MAX_RADIUS);
    }

    public static final int INTERVAL = 20;

    public DivineAuraEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xf4f960);
    }

    public static int getRadius(int amplifier)
    {
        return MathHelper.clamp(MIN_RADIUS + amplifier, MIN_RADIUS, MAX_RADIUS);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        World world = entity.getWorld();
        if (world.isClient())
        {
            return;
        }
        int radius = getRadius(amplifier);
        List<LivingEntity> entities = world.getEntitiesByClass(LivingEntity.class, entity.getBoundingBox().expand(radius), EntityPredicates.VALID_LIVING_ENTITY);
        for (LivingEntity target : entities)
        {
            if (target.distanceTo(entity) > radius)
            {
                continue;
            }
            if (RelationUtil.isAlly(entity, target))
            {
                target.addStatusEffect(new StatusEffectInstance(StatusEffects.ABSORPTION, 20, amplifier, false, false));
            } else
            {
                float damage = (float) DamageUtil.calculateDamage(entity, SpellSchools.HEALING, SpellConfig.DIVINE_AURA_FACTOR);
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
