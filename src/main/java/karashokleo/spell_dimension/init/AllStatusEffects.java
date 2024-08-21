package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.effect.*;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectCategory;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.spell_engine.api.effect.Synchronized;
import net.spell_power.api.SpellPowerMechanics;

public class AllStatusEffects
{
    public static final IgniteEffect IGNITE = new IgniteEffect();
    public static final FrostAuraEffect FROST_AURA = new FrostAuraEffect();
    public static final FrostedEffect FROSTED = new FrostedEffect();
    public static final PhaseEffect PHASE = new PhaseEffect();
    //    public static final PhaseEffect ASTRAL_TRIP = new PhaseEffect();
    public static final StatusEffect SPELL_HASTE = new AttributeEffect(
            StatusEffectCategory.BENEFICIAL,
            0x000000,
            "spell_haste",
            SpellPowerMechanics.HASTE.attribute,
            0.05,
            EntityAttributeModifier.Operation.MULTIPLY_BASE
    );

    public static void register()
    {
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("phase"), PHASE);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("ignite"), IGNITE);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("aura"), FROST_AURA);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("frosted"), FROSTED);
//        Registry.register(Registries.STATUS_EFFECT, SpellPlus.modLoc("astral_trip"), ASTRAL_TRIP);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("spell_haste"), SPELL_HASTE);

        Synchronized.configure(PHASE, true);
        Synchronized.configure(FROSTED, true);
//        ActionImpairing.configure(ASTRAL_TRIP_EFFECT, EntityActionsAllowed.STUN);
    }
}
