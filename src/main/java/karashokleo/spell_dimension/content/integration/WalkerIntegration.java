package karashokleo.spell_dimension.content.integration;

import karashokleo.spell_dimension.SpellDimension;
import tocraft.walkers.Walkers;
import tocraft.walkers.api.platform.ApiLevel;
import tocraft.walkers.integrations.AbstractIntegration;
import tocraft.walkers.integrations.Integrations;

import java.util.ArrayList;

public class WalkerIntegration extends AbstractIntegration
{
    public static void init()
    {
        Integrations.register(SpellDimension.MOD_ID, WalkerIntegration::new);

        ApiLevel.setApiLevel(ApiLevel.DEFAULT);

        unregisterTraits("minecraft:husk", "walkers:burn_in_daylight");
        unregisterTraits("mutantmonsters:mutant_zombie", "walkers:burn_in_daylight");
        unregisterTraits("mutantmonsters:mutant_skeleton", "walkers:burn_in_daylight");
    }

    private static void unregisterTraits(String entityId, String traitId)
    {
        Walkers.CONFIG.traitBlacklist
            .computeIfAbsent(entityId, k -> new ArrayList<>())
            .add(traitId);
    }
}
