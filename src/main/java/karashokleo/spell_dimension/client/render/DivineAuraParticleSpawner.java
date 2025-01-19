package karashokleo.spell_dimension.client.render;

import karashokleo.spell_dimension.content.effect.DivineAuraEffect;
import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class DivineAuraParticleSpawner implements CustomParticleStatusEffect.Spawner
{
    private static final ParticleBatch PARTICLE = new ParticleBatch(
            "spell_engine:holy_spark_mini",
            ParticleBatch.Shape.CIRCLE,
            ParticleBatch.Origin.CENTER,
            null,
            0,
            0,
            12,
            0.01F,
            0.05F,
            0,
            DivineAuraEffect.RANGE,
            0,
            false
    );

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier)
    {
        ParticleBatch scaledParticles = new ParticleBatch(PARTICLE);
        scaledParticles.count *= (float) (amplifier + 1);
        scaledParticles.max_speed *= livingEntity.getScaleFactor();
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, scaledParticles);
    }
}
