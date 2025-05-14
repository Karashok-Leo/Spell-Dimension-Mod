package karashokleo.spell_dimension.init;

import karashokleo.spell_dimension.SpellDimension;
import karashokleo.spell_dimension.content.particle.ZapParticle;
import karashokleo.spell_dimension.content.particle.ZapParticleOption;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.particle.v1.ParticleFactoryRegistry;
import net.minecraft.particle.ParticleType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class AllParticles
{
    public static ParticleType<ZapParticleOption> ZAP_PARTICLE;

    public static void register()
    {
        ZAP_PARTICLE = new ZapParticleOption.Type();
        Registry.register(Registries.PARTICLE_TYPE, SpellDimension.modLoc("zap"), ZAP_PARTICLE);
    }

    @Environment(EnvType.CLIENT)
    public static void registerClient()
    {
        ParticleFactoryRegistry.getInstance().register(ZAP_PARTICLE, ZapParticle.Factory::new);
    }
}
