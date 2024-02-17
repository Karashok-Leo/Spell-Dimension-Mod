package net.karashokleo.spelldimension.effect;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerAbilities;
import net.minecraft.entity.player.PlayerEntity;

public class PhaseEffect extends StatusEffect
{
    public PhaseEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xA020F0);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
        entity.noClip = true;
        if (entity instanceof PlayerEntity player)
            setFly(player, true);
    }

    @Override
    public boolean canApplyUpdateEffect(int duration, int amplifier)
    {
        return duration % 10 == 0;
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier)
    {
        super.onRemoved(entity, attributes, amplifier);
        entity.noClip = false;
        if (entity instanceof PlayerEntity player)
            setFly(player, false);
    }

    public static void setFly(PlayerEntity player, boolean canFly)
    {
        PlayerAbilities abilities = player.getAbilities();
        if (canFly)
        {
            abilities.allowFlying = true;
            abilities.flying = true;
        } else
        {
            abilities.allowFlying = player.isCreative() || player.isSpectator();
            abilities.flying = player.isCreative() || player.isSpectator();
        }
    }
}
