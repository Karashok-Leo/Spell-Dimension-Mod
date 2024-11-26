package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

public class TraitEffectImmunityEnchantment extends EffectImmunityEnchantment
{
    private final StatusEffect effect;

    public TraitEffectImmunityEnchantment(StatusEffect effect)
    {
        super();
        this.effect = effect;
    }

    @Override
    public boolean test(StatusEffectInstance effectInstance)
    {
        return this.effect == effectInstance.getEffectType();
    }

    public StatusEffect getEffect()
    {
        return effect;
    }
}
