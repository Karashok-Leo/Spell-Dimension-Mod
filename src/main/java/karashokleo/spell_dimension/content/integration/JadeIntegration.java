package karashokleo.spell_dimension.content.integration;

import net.minecraft.entity.mob.MobEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;

public class JadeIntegration implements IWailaPlugin
{
    @Override
    public void registerClient(IWailaClientRegistration registration)
    {
        registration.registerEntityComponent(new SoulMinionInfo(), MobEntity.class);
    }
}
