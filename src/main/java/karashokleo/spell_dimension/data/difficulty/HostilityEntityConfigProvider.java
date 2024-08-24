package karashokleo.spell_dimension.data.difficulty;

import karashokleo.l2hostility.data.config.provider.EntityConfigProvider;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;

public class HostilityEntityConfigProvider extends EntityConfigProvider
{
    public HostilityEntityConfigProvider(FabricDataOutput output)
    {
        super(output);
    }

    @Override
    public String getName()
    {
        return "SD Hostility Entity Config";
    }

    @Override
    public void addAll()
    {
    }
}
