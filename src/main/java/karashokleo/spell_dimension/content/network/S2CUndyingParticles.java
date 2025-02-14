package karashokleo.spell_dimension.content.network;

import dev.xkmc.l2serial.serialization.SerialClass;
import karashokleo.l2hostility.client.L2HostilityClient;
import karashokleo.l2hostility.content.network.S2CEntity;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.LivingEntity;
import net.minecraft.particle.ParticleTypes;

@SerialClass
public class S2CUndyingParticles extends S2CEntity
{
    @SerialClass.SerialField
    protected int duration;

    @Deprecated
    public S2CUndyingParticles()
    {
        super();
    }

    public S2CUndyingParticles(LivingEntity e, int duration)
    {
        super(e);
        this.duration = duration;
    }

    public S2CUndyingParticles(LivingEntity e)
    {
        this(e, 30);
    }

    @Override
    public void handle(ClientPlayerEntity player)
    {
        ClientWorld world = L2HostilityClient.getClientWorld();
        if (world != null && id >= 0 && world.getEntityById(id) instanceof LivingEntity entity)
        {
            L2HostilityClient.getClient().particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, duration);
        }
    }
}
