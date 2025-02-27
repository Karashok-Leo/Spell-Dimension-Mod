package karashokleo.spell_dimension.content.enchantment;

import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;

import java.util.function.Supplier;

public class TraitEffectImmunityEnchantment extends EffectImmunityEnchantment
{
    private final Supplier<StatusEffect> effectSupplier;

    public TraitEffectImmunityEnchantment(Supplier<StatusEffect> effectSupplier)
    {
        super();
        this.effectSupplier = effectSupplier;
    }

    @Override
    public boolean test(StatusEffectInstance effectInstance)
    {
        return this.getEffect() == effectInstance.getEffectType();
    }

    public StatusEffect getEffect()
    {
        return effectSupplier.get();
    }
}
