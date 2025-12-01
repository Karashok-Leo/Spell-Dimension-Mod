package karashokleo.spell_dimension.util;

import tocraft.walkers.api.platform.ApiLevel;
import tocraft.walkers.integrations.AbstractIntegration;

public class WalkerUtil extends AbstractIntegration
{
    @Override
    public void initialize()
    {
        ApiLevel.setApiLevel(ApiLevel.DEFAULT);
    }
}
