package net.karashokleo.spelldimension.render;

import net.minecraft.entity.LivingEntity;
import net.spell_engine.api.effect.CustomParticleStatusEffect;
import net.spell_engine.api.spell.ParticleBatch;
import net.spell_engine.particle.ParticleHelper;

public class PhaseParticles implements CustomParticleStatusEffect.Spawner
{
    private static final ParticleBatch PARTICLES = new ParticleBatch(
            "minecraft:enchant",
            ParticleBatch.Shape.PIPE,
            ParticleBatch.Origin.CENTER,
            null,
            4,
            0.1F,
            0.2F,
            0
    );

    @Override
    public void spawnParticles(LivingEntity livingEntity, int amplifier)
    {
        ParticleHelper.play(livingEntity.getWorld(), livingEntity, PARTICLES);
    }
}
