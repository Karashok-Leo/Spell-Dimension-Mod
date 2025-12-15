package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.api.SpellImpactEvents;
import karashokleo.spell_dimension.content.event.*;
import karashokleo.spell_dimension.content.spell.LightSpell;

public class AllEvents
{
    public static void init()
    {
        PlayerHealthEvents.init();
        EnchantmentEvents.init();
        DifficultyEvents.init();
        TrinketEvents.init();
        LightSpell.init();
        ConsciousOceanEvents.init();
        SpellImpactEvents.POST.register(AdaptiveCompat::postSpellImpact);
        SoulMinionEvents.init();
        MiscEvents.init();
    }
}
