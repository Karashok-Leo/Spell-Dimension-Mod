package karashokleo.spell_dimension.content.object;

import karashokleo.leobrary.damage.api.state.DamageState;

import java.util.function.Predicate;

public class SoulNetDamageState implements DamageState
{
    public static final Predicate<DamageState> PREDICATE = state -> state instanceof SoulNetDamageState;

    @Override
    public String toString()
    {
        return "DamageState$SoulNet";
    }
}
