package karashokleo.spell_dimension.content.object;

import karashokleo.leobrary.damage.api.state.DamageState;

import java.util.function.Predicate;

public class ChainLightningDamageState implements DamageState
{
    public static final Predicate<DamageState> PREDICATE = state -> state instanceof ChainLightningDamageState;

    @Override
    public String toString()
    {
        return "DamageState$ChainLightning";
    }
}
