package karashokleo.spell_dimension.content.item.trinket;

import karashokleo.l2hostility.content.item.trinket.core.DamageListenerTrinket;
import karashokleo.l2hostility.content.item.trinket.core.SingleEpicTrinketItem;
import karashokleo.l2hostility.util.EffectHelper;
import karashokleo.spell_dimension.init.AllStatusEffects;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.tag.DamageTypeTags;

public class NirvanaStarfallItem extends SingleEpicTrinketItem implements DamageListenerTrinket
{
    public NirvanaStarfallItem()
    {
        super();
    }

    @Override
    public boolean allowDeath(ItemStack stack, LivingEntity entity, DamageSource source, float amount)
    {
        if (source.isIn(DamageTypeTags.BYPASSES_INVULNERABILITY))
            return true;

        StatusEffectInstance effect = entity.getStatusEffect(AllStatusEffects.NIRVANA);
        int level = effect == null ? 0 : effect.getAmplifier() + 1;

        if (level > 10)
            return true;

        EffectHelper.forceAddEffectWithEvent(entity, new StatusEffectInstance(AllStatusEffects.NIRVANA, 20 * 5, level), entity);

        entity.setHealth(entity.getMaxHealth());
        entity.getActiveStatusEffects()
                .keySet()
                .stream()
                .filter(e -> !e.isBeneficial())
                .forEach(entity::removeStatusEffect);
        return false;
    }
}
