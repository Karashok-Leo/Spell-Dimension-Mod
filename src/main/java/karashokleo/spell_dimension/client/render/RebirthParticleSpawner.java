package karashokleo.spell_dimension.client.render;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class RebirthParticleSpawner implements CustomParticleStatusEffect.Spawner
{
    private static final ParticleBatch PARTICLE = new ParticleBatch(
        "minecraft:soul",
        ParticleBatch.Shape.PIPE,
        ParticleBatch.Origin.FEET,
        null,
        0,
        0,
        10,
        0.08F,
        0.16F,
        0,
        0,
        0,
        false
    );

    @Override
    public void spawnParticles(LivingEntity livingEntity, int i)
    {
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, PARTICLE);
    }
}
