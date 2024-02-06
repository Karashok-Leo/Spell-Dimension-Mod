package net.karashokleo.spelldimension.effect;

import net.karashokleo.spelldimension.SpellDimension;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.spell_engine.api.effect.Synchronized;

import java.util.ArrayList;
import java.util.List;

public class AllStatusEffects
{
    public static final List<StatusEffect> ALL = new ArrayList<>();
    public static final IgniteEffect IGNITE_EFFECT = new IgniteEffect();
    public static final FrostAuraEffect FROST_AURA_EFFECT = new FrostAuraEffect();
    public static final FrostedEffect FROSTED_EFFECT = new FrostedEffect();
    public static final PhaseEffect PHASE_EFFECT = new PhaseEffect();
//    public static final PhaseEffect ASTRAL_TRIP_EFFECT = new PhaseEffect();

    public static void register()
    {
        ALL.add(IGNITE_EFFECT);
        ALL.add(FROST_AURA_EFFECT);
        ALL.add(FROSTED_EFFECT);
        ALL.add(PHASE_EFFECT);

        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("phase"), PHASE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("ignite"), IGNITE_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("aura"), FROST_AURA_EFFECT);
        Registry.register(Registries.STATUS_EFFECT, SpellDimension.modLoc("frosted"), FROSTED_EFFECT);
//        Registry.register(Registries.STATUS_EFFECT, SpellPlus.modLoc("astral_trip"), ASTRAL_TRIP_EFFECT);

        Synchronized.configure(PHASE_EFFECT, true);
        Synchronized.configure(FROSTED_EFFECT, true);
//        ActionImpairing.configure(ASTRAL_TRIP_EFFECT, EntityActionsAllowed.STUN);
    }
}
