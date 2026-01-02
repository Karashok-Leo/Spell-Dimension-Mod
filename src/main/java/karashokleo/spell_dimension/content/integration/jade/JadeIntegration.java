package karashokleo.spell_dimension.content.integration.jade;

import net.minecraft.block.SpawnerBlock;
import net.minecraft.entity.mob.MobEntity;
import snownee.jade.api.IWailaClientRegistration;
import snownee.jade.api.IWailaPlugin;

public class JadeIntegration implements IWailaPlugin
{
    @Override
    public void registerClient(IWailaClientRegistration registration)
    {
        registration.registerEntityComponent(SoulMinionInfo.INSTANCE, MobEntity.class);
        registration.registerBlockComponent(SpawnerInfo.INSTANCE, SpawnerBlock.class);
    }
}
