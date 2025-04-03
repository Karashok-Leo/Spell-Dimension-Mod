package karashokleo.spell_dimension.content.item.trinket;

import io.github.fabricators_of_create.porting_lib.entity.events.living.LivingHurtEvent;
import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.init.LHEffects;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.spell_power.api.SpellSchools;

public class FireItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public FireItem()
    {
    }

    @Override
    public void onHurting(ItemStack stack, LivingEntity entity, LivingHurtEvent event)
    {
        if (!event.getSource().isOf(SpellSchools.FIRE.damageType))
            return;
        int fireTicks = entity.getFireTicks();
        if (fireTicks <= 0)
            return;
        LivingEntity target = event.getEntity();
        StatusEffectInstance effect = target.getStatusEffect(AllStatusEffects.NIRVANA);
        if (effect == null)
        {
            EffectHelper.forceAddEffectWithEvent(
                    target,
                    new StatusEffectInstance(LHEffects.FLAME, fireTicks),
                    entity
            );
        } else
        {
            EffectHelper.forceAddEffectWithEvent(
                    target,
                    new StatusEffectInstance(LHEffects.FLAME, effect.getDuration(), effect.getAmplifier()),
                    entity
            );
        }
    }
}
