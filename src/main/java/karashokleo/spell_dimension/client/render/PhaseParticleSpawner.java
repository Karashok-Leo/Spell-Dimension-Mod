package karashokleo.spell_dimension.client.render;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class PhaseParticleSpawner implements CustomParticleStatusEffect.Spawner
{
    private static final ParticleBatch PARTICLE = new ParticleBatch(
        "minecraft:enchant",
        ParticleBatch.Shape.PIPE,
        ParticleBatch.Origin.CENTER,
        null,
        0,
        0,
        4,
        0.1F,
        0.2F,
        0,
        0,
        0,
        false
    );

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier)
    {
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, PARTICLE);
    }
}
