package karashokleo.spell_dimension.content.effect;

import io.github.ladysnake.pal.AbilitySource;
import io.github.ladysnake.pal.Pal;
import io.github.ladysnake.pal.VanillaAbilities;
import karashokleo.spell_dimension.SpellDimension;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.entity.player.PlayerEntity;

public class PhaseEffect extends StatusEffect
{
    public static final AbilitySource PHASE = Pal.getAbilitySource(SpellDimension.modLoc("phase"));

    public PhaseEffect()
    {
        super(StatusEffectCategory.BENEFICIAL, 0xA020F0);
    }

    @Override
    public void onApplied(LivingEntity entity, AttributeContainer attributes, int amplifier)
    {
        super.onApplied(entity, attributes, amplifier);
        if (entity.getWorld().isClient()) return;
        entity.noClip = true;
        if (entity instanceof PlayerEntity player)
            setFly(player, true);
    }

    @Override
    public void applyUpdateEffect(LivingEntity entity, int amplifier)
    {
    }

    @Override
    public void onRemoved(LivingEntity entity, AttributeContainer attributes, int amplifier)
    {
        super.onRemoved(entity, attributes, amplifier);
        if (entity.getWorld().isClient()) return;
        entity.noClip = false;
        if (entity instanceof PlayerEntity player)
            setFly(player, false);
    }

    public static void setFly(PlayerEntity player, boolean canFly)
    {
        if (canFly)
        {
            PHASE.grantTo(player, VanillaAbilities.ALLOW_FLYING);
            PHASE.grantTo(player, VanillaAbilities.FLYING);
        } else
        {
            PHASE.revokeFrom(player, VanillaAbilities.ALLOW_FLYING);
            PHASE.revokeFrom(player, VanillaAbilities.FLYING);
        }
    }
}
