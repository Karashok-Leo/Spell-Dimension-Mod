package karashokleo.spell_dimension.init;

import karashokleo.leobrary.damage.api.state.DamageState;
import karashokleo.leobrary.damage.api.state.IdentifierDamageState;
import karashokleo.spell_dimension.SpellDimension;

public class AllDamageStates
{
    public static final DamageState CHAIN_LIGHTNING = new IdentifierDamageState(SpellDimension.modLoc("chain_lightning"));
    public static final DamageState SOUL_NET = new IdentifierDamageState(SpellDimension.modLoc("soul_net"));
    public static final DamageState REQUIEM = new IdentifierDamageState(AllSpells.REQUIEM);

    public static void register()
    {
    }
}
