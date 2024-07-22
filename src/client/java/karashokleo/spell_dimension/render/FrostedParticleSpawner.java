package karashokleo.spell_dimension.render;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class FrostedParticleSpawner implements CustomParticleStatusEffect.Spawner
{
    private static final ParticleBatch PARTICLE = new ParticleBatch(
            "spell_engine:frost_hit",
            ParticleBatch.Shape.SPHERE,
            ParticleBatch.Origin.CENTER,
            null,
            0,
            0,
            1,
            0.1F,
            0.4F,
            0,
            0,
            0,
            false
    );

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier)
    {
        var scaledParticles = new ParticleBatch(PARTICLE);
        scaledParticles.count *= (amplifier + 1);
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, scaledParticles);
    }
}
